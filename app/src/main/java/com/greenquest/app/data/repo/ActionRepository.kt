package com.greenquest.app.data.repo

import com.greenquest.app.data.local.dao.ActionDao
import com.greenquest.app.data.local.entity.ActionEntity
import com.greenquest.app.data.local.entity.CompletedActionEntity
import com.greenquest.app.data.model.ActionItem
import com.greenquest.app.data.model.ProgressSummary
import com.greenquest.app.data.model.BadgeDef
import com.greenquest.app.data.model.BadgeStatus
import com.greenquest.app.data.model.WeeklyChallengeStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.combine
import kotlin.math.ceil
import kotlin.math.pow

class ActionRepository(private val dao: ActionDao) {

    fun observeActions(): Flow<List<ActionItem>> = dao.observeActions().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun getAction(id: String): ActionItem? = dao.getActionById(id)?.toDomain()

    suspend fun seedIfEmpty() {
        if (dao.actionsCount() == 0) {
            val items = listOf(
                ActionEntity("tree", "Plant a tree", "Plant a tree in your community or yard.", 50, "Nature"),
                ActionEntity("bike", "Bike commute", "Ride a bike instead of driving.", 30, "Transport"),
                ActionEntity("recycle", "Recycle waste", "Properly sort and recycle waste.", 20, "Waste"),
                ActionEntity("water", "Reduce water use", "Take a 3-minute shorter shower.", 15, "Water"),
                ActionEntity("lights", "Turn off lights", "Turn off unused lights for 2 hours.", 10, "Energy"),
                ActionEntity("bus", "Use public transit", "Take the bus or train.", 25, "Transport"),
                ActionEntity("tree2", "Plant native shrub", "Support biodiversity.", 40, "Nature"),
                ActionEntity("reuse", "Use reusable bag", "Avoid single-use bags.", 10, "Waste"),
                ActionEntity("solar", "Explore solar", "Research rooftop solar options.", 35, "Energy"),
                ActionEntity("meatless", "Meatless meal", "Have a meat-free meal.", 15, "Food")
            )
            dao.insertActions(items)
        }
    }

    fun observeWeeklyChallenge(target: Int = 10): Flow<WeeklyChallengeStatus> {
        val nowMs = System.currentTimeMillis()
        val dayMs = 24L * 60 * 60 * 1000
        val nowDay = nowMs / dayMs
        val weekBucket = nowDay / 7 // integer week bucket from epoch
        val weekStartDay = weekBucket * 7
        val weekEndDay = weekStartDay + 6
        val bonusId = "weekly_bonus_$weekBucket"

        val timestampsFlow = dao.observeCompletionTimestamps()
        val claimedFlow = dao.observeCompletedCountForAction(bonusId)

        return combine(timestampsFlow, claimedFlow) { stamps, claimedCount ->
            val progress = stamps.count { ts ->
                val d = ts / dayMs
                d in weekStartDay..weekEndDay
            }
            val canClaim = progress >= target && claimedCount == 0
            WeeklyChallengeStatus(
                title = "Complete $target actions this week",
                target = target,
                progress = progress,
                canClaim = canClaim,
                claimed = claimedCount > 0
            )
        }
    }

    suspend fun claimWeeklyChallenge(target: Int = 10, rewardPoints: Int = 100) {
        val nowMs = System.currentTimeMillis()
        val dayMs = 24L * 60 * 60 * 1000
        val nowDay = nowMs / dayMs
        val weekBucket = nowDay / 7
        val bonusId = "weekly_bonus_$weekBucket"

        // Ensure bonus action exists
        val existing = dao.getActionById(bonusId)
        if (existing == null) {
            dao.insertAction(
                ActionEntity(
                    id = bonusId,
                    title = "Weekly Challenge Reward",
                    description = "Bonus for completing weekly challenge",
                    points = rewardPoints,
                    category = "Challenge"
                )
            )
        }
        // Only add completion if not already claimed
        if (dao.completedCountForAction(bonusId) == 0) {
            dao.insertCompleted(
                CompletedActionEntity(actionId = bonusId, completedAt = System.currentTimeMillis())
            )
        }
    }

    suspend fun completeAction(actionId: String) {
        dao.insertCompleted(
            CompletedActionEntity(actionId = actionId, completedAt = System.currentTimeMillis())
        )
    }

    fun observeProgress(): Flow<ProgressSummary> =
        dao.observeTotalPoints().map { total ->
            val level = computeLevel(total)
            val nextAt = levelThreshold(level + 1)
            val prevAt = levelThreshold(level)
            val progress = if (nextAt == prevAt) 0f else (total - prevAt).toFloat() / (nextAt - prevAt)
            ProgressSummary(totalPoints = total, level = level, nextLevelAt = nextAt, progressToNext = progress)
        }

    fun observeBadges(): Flow<List<BadgeStatus>> {
        val pointsFlow = dao.observeTotalPoints()
        val completedFlow = dao.observeCompletedCount()
        val timestampsFlow = dao.observeCompletionTimestamps()

        val defs = listOf(
            BadgeDef("FIRST_ACTION", "First Steps", "Complete your first action"),
            BadgeDef("POINTS_100", "Eco Starter", "Reach 100 points"),
            BadgeDef("ACTIONS_25", "Action Hero", "Complete 25 actions"),
            BadgeDef("STREAK_7", "Consistency", "Complete actions 7 days in a row")
        )

        return combine(pointsFlow, completedFlow, timestampsFlow) { points, completed, timestamps ->
            val streak = computeStreakDays(timestamps)
            defs.map { def ->
                val earned = when (def.code) {
                    "FIRST_ACTION" -> completed >= 1
                    "POINTS_100" -> points >= 100
                    "ACTIONS_25" -> completed >= 25
                    "STREAK_7" -> streak >= 7
                    else -> false
                }
                BadgeStatus(badge = def, earned = earned)
            }
        }
    }

    private fun ActionEntity.toDomain() = ActionItem(id, title, description, points, category)

    private fun computeLevel(totalPoints: Int): Int {
        var n = 1
        while (totalPoints >= levelThreshold(n + 1)) n++
        return n
    }

    private fun levelThreshold(n: Int): Int = ceil(100.0 * n.toDouble().pow(1.3)).toInt()

    private fun computeStreakDays(timestamps: List<Long>): Int {
        if (timestamps.isEmpty()) return 0
        // Normalize to day buckets (UTC)
        val oneDay = 24L * 60 * 60 * 1000
        val days = timestamps.map { it / oneDay }.distinct().sortedDescending()
        var streak = 0
        var expected = days.first()
        for (d in days) {
            if (d == expected) {
                streak++
                expected--
            } else if (d < expected) {
                break
            }
        }
        return streak
    }
}

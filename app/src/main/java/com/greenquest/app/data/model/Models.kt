package com.greenquest.app.data.model

data class ActionItem(
    val id: String,
    val title: String,
    val description: String,
    val points: Int,
    val category: String,
)

data class ProgressSummary(
    val totalPoints: Int,
    val level: Int,
    val nextLevelAt: Int,
    val progressToNext: Float, // 0f..1f
)

data class CompletionResult(
    val addedPoints: Int,
    val newTotalPoints: Int,
    val newLevel: Int
)

data class BadgeDef(
    val code: String,
    val name: String,
    val description: String
)

data class BadgeStatus(
    val badge: BadgeDef,
    val earned: Boolean,
    val earnedAt: Long? = null
)

data class WeeklyChallengeStatus(
    val title: String,
    val target: Int,
    val progress: Int,
    val canClaim: Boolean,
    val claimed: Boolean
)

package com.greenquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.greenquest.app.data.local.entity.ActionEntity
import com.greenquest.app.data.local.entity.CompletedActionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionDao {
    @Query("SELECT * FROM actions ORDER BY title ASC")
    fun observeActions(): Flow<List<ActionEntity>>

    @Query("SELECT COUNT(*) FROM actions")
    suspend fun actionsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActions(items: List<ActionEntity>)

    @Query("SELECT * FROM actions WHERE id = :id LIMIT 1")
    suspend fun getActionById(id: String): ActionEntity?

    @Insert
    suspend fun insertCompleted(entry: CompletedActionEntity)

    @Query("SELECT COUNT(*) FROM completed_actions WHERE actionId = :id")
    suspend fun completedCountForAction(id: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAction(item: ActionEntity)

    @Query("SELECT COUNT(*) FROM completed_actions WHERE actionId = :id")
    fun observeCompletedCountForAction(id: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM completed_actions")
    suspend fun completedCount(): Int

    @Query("SELECT COUNT(*) FROM completed_actions")
    fun observeCompletedCount(): Flow<Int>

    @Query("SELECT IFNULL(SUM(points), 0) FROM actions a JOIN completed_actions c ON a.id = c.actionId")
    fun observeTotalPoints(): Flow<Int>

    @Query("SELECT completedAt FROM completed_actions ORDER BY completedAt DESC")
    fun observeCompletionTimestamps(): Flow<List<Long>>
}

package com.greenquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actions")
data class ActionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val points: Int,
    val category: String
)

@Entity(tableName = "completed_actions")
data class CompletedActionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val actionId: String,
    val completedAt: Long
)

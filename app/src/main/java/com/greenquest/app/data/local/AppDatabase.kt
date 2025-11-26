package com.greenquest.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.greenquest.app.data.local.dao.ActionDao
import com.greenquest.app.data.local.entity.ActionEntity
import com.greenquest.app.data.local.entity.CompletedActionEntity

@Database(
    entities = [ActionEntity::class, CompletedActionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun actionDao(): ActionDao
}

package com.panostob.mycourses.framework.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.panostob.mycourses.data.main.model.MyCourseEntity

@Database(
    entities = [
        MyCourseEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
}
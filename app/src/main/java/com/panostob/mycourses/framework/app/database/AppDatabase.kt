package com.panostob.mycourses.framework.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.panostob.mycourses.data.courses.model.MyCourseEntity
import com.panostob.mycourses.framework.courses.dao.CoursesDao

@Database(
    entities = [
        MyCourseEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
}
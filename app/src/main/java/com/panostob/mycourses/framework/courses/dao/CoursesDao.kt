package com.panostob.mycourses.framework.courses.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.panostob.mycourses.data.courses.model.MyCourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoursesDao {
    @Transaction
    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<MyCourseEntity>>

    @Transaction
    @Query("SELECT * FROM courses WHERE id = :courseId LIMIT 1")
    suspend fun getCourseById(courseId: Long): MyCourseEntity?

    @Upsert(entity = MyCourseEntity::class)
    suspend fun upsertCourse(coupon: MyCourseEntity): Long
}
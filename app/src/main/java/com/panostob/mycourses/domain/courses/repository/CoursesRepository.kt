package com.panostob.mycourses.domain.courses.repository

import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.domain.courses.entity.UpdateCourseResult
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    fun getAllCourses(): Flow<List<Course>>

    suspend fun getCourseById(courseId: Long): Course?

    suspend fun updateCourse(course: Course)
}

package com.panostob.mycourses.data.courses.datasource

import com.panostob.mycourses.data.courses.model.RemoteCourse
import com.panostob.mycourses.domain.courses.entity.Course
import kotlinx.coroutines.flow.Flow

interface CoursesDataSource {
    fun getAllCourses(): Flow<List<RemoteCourse?>?>

    suspend fun getCourseById(courseId: Long): RemoteCourse?

    suspend fun updateCourse(course: Course)
}

package com.panostob.mycourses.data.courses.repository

import com.panostob.mycourses.data.courses.datasource.CoursesDataSource
import com.panostob.mycourses.data.courses.mapper.CoursesMapper
import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.domain.courses.entity.UpdateCourseResult
import com.panostob.mycourses.domain.courses.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val dataSource: CoursesDataSource,
    private val coursesMapper: CoursesMapper
) : CoursesRepository {
    override fun getAllCourses(): Flow<List<Course>> {
        return dataSource.getAllCourses().map { coursesMapper(it) }
    }

    override suspend fun getCourseById(courseId: Long): Course? {
        return coursesMapper(dataSource.getCourseById(courseId))
    }

    override suspend fun updateCourse(course: Course) {
        return dataSource.updateCourse(course)
    }
}

package com.panostob.mycourses.framework.courses.datasource

import com.panostob.mycourses.data.courses.datasource.CoursesDataSource
import com.panostob.mycourses.data.courses.model.RemoteCourse
import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.framework.courses.dao.CoursesDao
import com.panostob.mycourses.framework.courses.mapper.CourseEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoursesDataSourceImpl @Inject constructor(
    private val coursesDao: CoursesDao,
    private val courseEntityMapper: CourseEntityMapper
) : CoursesDataSource {
    override fun getAllCourses(): Flow<List<RemoteCourse?>?> {
        return coursesDao.getAllCourses().map { courseEntityMapper(it) }
    }

    override suspend fun getCourseById(courseId: Long): RemoteCourse? {
        return courseEntityMapper(coursesDao.getCourseById(courseId))
    }

    override suspend fun updateCourse(course: Course) {
        return coursesDao.upsertCourse(courseEntityMapper(course))
    }
}

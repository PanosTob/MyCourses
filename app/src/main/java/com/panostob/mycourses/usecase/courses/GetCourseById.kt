package com.panostob.mycourses.usecase.courses

import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.domain.courses.repository.CoursesRepository
import com.panostob.mycourses.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetCourseById @Inject constructor(
    private val repository: CoursesRepository
) : UseCase {

    suspend operator fun invoke(courseId: String): Course? {
        return try {
            val id = courseId.toLongOrNull() ?: return null
            repository.getCourseById(id)
        } catch (ex: Exception) {
            Timber.Forest.tag(GetCourseById::class.java.simpleName).e(ex)
            null
        }
    }
}
package com.panostob.mycourses.usecase.courses

import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.domain.courses.repository.CoursesRepository
import com.panostob.mycourses.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class UpdateCourse @Inject constructor(
    private val repository: CoursesRepository
) : UseCase {

    suspend operator fun invoke(course: Course): Long {
        return try {
            repository.updateCourse(course)
        } catch (ex: Exception) {
            Timber.Forest.tag(GetAllCourses::class.java.simpleName).e(ex)
            0
        }
    }
}
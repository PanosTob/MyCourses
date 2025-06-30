package com.panostob.mycourses.usecase.courses

import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.domain.courses.repository.CoursesRepository
import com.panostob.mycourses.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject

class GetAllCourses @Inject constructor(
    private val repository: CoursesRepository
) : UseCase {

    operator fun invoke(): Flow<List<Course>> {
        return try {
            repository.getAllCourses()
        } catch (ex: Exception) {
            Timber.Forest.tag(GetAllCourses::class.java.simpleName).e(ex)
            flowOf()
        }
    }
}
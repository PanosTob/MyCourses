package com.panostob.mycourses.data.courses.mapper

import com.panostob.mycourses.data.Mapper
import com.panostob.mycourses.data.courses.model.RemoteCourse
import com.panostob.mycourses.domain.courses.entity.Course
import javax.inject.Inject

class CoursesMapper @Inject constructor() : Mapper {
    operator fun invoke(courses: List<RemoteCourse?>?): List<Course> {
        if (courses.isNullOrEmpty()) return emptyList()

        return courses.mapNotNull { remoteCourse ->
            remoteCourse ?: return@mapNotNull null

            invoke(remoteCourse)
        }
    }

    operator fun invoke(course: RemoteCourse?): Course? {
        course ?: return null

        return Course(
            id = course.id?.toInt() ?: 0,
            imageUrl = mapRemoteImageUrl(course.imageUrl),
            title = course.title ?: "",
            shortDescription = course.shortDescription ?: "",
            progressPercentage = course.progressPercentage
        )
    }
}
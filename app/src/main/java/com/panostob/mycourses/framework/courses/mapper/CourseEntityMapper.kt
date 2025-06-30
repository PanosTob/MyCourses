package com.panostob.mycourses.framework.courses.mapper

import com.panostob.mycourses.BuildConfig
import com.panostob.mycourses.data.Mapper
import com.panostob.mycourses.data.courses.model.MyCourseEntity
import com.panostob.mycourses.data.courses.model.RemoteCourse
import com.panostob.mycourses.domain.courses.entity.Course
import javax.inject.Inject

class CourseEntityMapper @Inject constructor() : Mapper {

    operator fun invoke(courses: List<MyCourseEntity?>?): List<RemoteCourse> {
        if (courses.isNullOrEmpty()) return emptyList()

        return courses.mapNotNull { myCourseEntity ->
            myCourseEntity ?: return@mapNotNull null

            invoke(myCourseEntity)
        }
    }

    operator fun invoke(courseEntity: MyCourseEntity?): RemoteCourse? {
        courseEntity ?: return null

        return RemoteCourse(
            id = courseEntity.id,
            imageUrl = courseEntity.imageUrl,
            title = courseEntity.title,
            shortDescription = courseEntity.shortDescription,
            progressPercentage = courseEntity.progressPercentage
        )
    }

    operator fun invoke(course: Course): MyCourseEntity {
        return MyCourseEntity(
            id = course.id,
            imageUrl = course.imageUrl.removePrefix(BuildConfig.API_MY_COURSES_IMAGES_URL.removeSuffix("/")),
            title = course.title,
            shortDescription = course.shortDescription,
            progressPercentage = course.progressPercentage?.div(100) ?: 0f
        )
    }
}

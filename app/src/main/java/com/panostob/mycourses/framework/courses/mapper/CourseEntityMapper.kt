package com.panostob.mycourses.framework.courses.mapper

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

    operator fun invoke(courseEntity: MyCourseEntity?): RemoteCourse {
        return RemoteCourse(
            id = courseEntity?.id,
            imageUrl = courseEntity?.imageUrl,
            title = courseEntity?.title,
            shortDescription = courseEntity?.shortDescription,
            progressPercentage = courseEntity?.progressPercentage
        )
    }

    operator fun invoke(course: Course): MyCourseEntity {
        /*return MyCourseEntity(
            imageUrl = course?.imageUrl,
            title = course?.title,
            shortDescription = course?.shortDescription,
            progressPercentage = course?.progressPercentage
        )*/
        return MyCourseEntity(
            id = 0,
            imageUrl = "",
            title = "",
            shortDescription = "",
            progressPercentage = 0f
        )
    }
}

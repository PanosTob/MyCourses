package com.panostob.mycourses.ui.courses.mapper

import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.ui.courses.model.CourseUiItem
import javax.inject.Inject

class CoursesUiMapper @Inject constructor() {

    operator fun invoke(courses: List<Course>): List<CourseUiItem> {
        return courses.map {
            CourseUiItem(
                id = it.id.toString(),
                title = it.title,
                shortDescription = it.shortDescription,
                imageUrl = it.imageUrl,
                progressPercentage = it.progressPercentage ?: 0f,
            )
        }
    }
}
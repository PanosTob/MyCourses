package com.panostob.mycourses.ui.courses.details.mapper

import androidx.compose.runtime.mutableFloatStateOf
import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsProgressSliderUiItem
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsUiItem
import javax.inject.Inject

class CourseDetailsUiMapper @Inject constructor() {

    operator fun invoke(course: Course): CourseDetailsUiItem {
        return CourseDetailsUiItem(
            id = course.id,
            title = course.title,
            shortDescription = course.shortDescription,
            imageUrl = course.imageUrl,
            progressSlider = CourseDetailsProgressSliderUiItem(
                progressPercentage = mutableFloatStateOf((course.progressPercentage ?: 0f) * 100)
            )
        )
    }
}
package com.panostob.mycourses.ui.courses.details.mapper

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.panostob.mycourses.R
import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.ui.base.theme.Spacing_2dp
import com.panostob.mycourses.ui.base.theme.Spacing_8dp
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsProgressSliderUiItem
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsUiItem
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsUiState
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

    operator fun invoke(course: CourseDetailsUiItem): Course {
        return Course(
            id = course.id,
            title = course.title,
            shortDescription = course.shortDescription,
            imageUrl = course.imageUrl,
            progressPercentage = course.progressSlider.progressPercentage.value
        )
    }
}
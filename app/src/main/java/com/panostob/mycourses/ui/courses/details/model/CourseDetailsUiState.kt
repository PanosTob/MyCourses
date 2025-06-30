package com.panostob.mycourses.ui.courses.details.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.panostob.mycourses.ui.app.model.DialogUiItem
import com.panostob.mycourses.ui.courses.details.composable.SaveButtonContent

data class CourseDetailsUiState(
    val isLoading: MutableState<Boolean> = mutableStateOf(false),
    val onEvent: CourseDetailsUiEvent,
    val courseDetails: CourseDetailsUiItem? = null,
    val saveCourseDetailsLoading: MutableState<Boolean> = mutableStateOf(false),
    val courseDetailsSavedState: MutableState<Boolean> = mutableStateOf(false),
    val courseSaveErrorDialog: MutableState<DialogUiItem?> = mutableStateOf(null),
) {
    val saveButtonContent: @Composable () -> Unit
        get() = { SaveButtonContent(saveCourseDetailsLoading.value) }
}

data class CourseDetailsUiEvent(
    val onCourseSaveRequested: () -> Unit
)

data class CourseDetailsUiItem(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val shortDescription: String,
    val progressSlider: CourseDetailsProgressSliderUiItem
)

data class CourseDetailsProgressSliderUiItem(
    val progressPercentage: MutableState<Float>,
) {
    val sliderPosition: @Composable () -> Float
        get() = { progressPercentage.value }

    val sliderPositionValueText: @Composable () -> String
        get() = { (progressPercentage.value).toInt().toString() }
}
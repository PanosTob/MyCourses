package com.panostob.mycourses.ui.courses.details.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import com.panostob.mycourses.R
import com.panostob.mycourses.util.compose.MyStringBuilder

data class CourseDetailsUiState(
    val isLoading: MutableState<Boolean> = mutableStateOf(false),
    val onEvent: CourseDetailsUiEvent,
    val courseDetails: CourseDetailsUiItem? = null
)

data class CourseDetailsUiEvent(
    val onCourseSaveRequested: () -> Unit
)

data class CourseDetailsUiItem(
    val id: Int = 0,
    val imageUrl: String = "",
    val title: String = "",
    val shortDescription: String = "",
    val progressSlider: CourseDetailsProgressSliderUiItem = CourseDetailsProgressSliderUiItem()
)

data class CourseDetailsProgressSliderUiItem(
    val progressPercentage: MutableState<Float> = mutableFloatStateOf(0f),
)
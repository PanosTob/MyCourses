package com.panostob.mycourses.ui.courses.details.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.panostob.mycourses.R
import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.domain.courses.entity.UpdateCourseResult
import com.panostob.mycourses.ui.app.model.DialogUiItem
import com.panostob.mycourses.ui.base.BaseViewModel
import com.panostob.mycourses.ui.courses.details.mapper.CourseDetailsUiMapper
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsUiEvent
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsUiState
import com.panostob.mycourses.ui.courses.details.navigation.CourseDetailsDestination
import com.panostob.mycourses.usecase.courses.GetCourseById
import com.panostob.mycourses.usecase.courses.UpdateCourse
import com.panostob.mycourses.util.compose.MyStringBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CourseDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCourseById: GetCourseById,
    private val updateCourse: UpdateCourse,
    private val courseDetailsUiMapper: CourseDetailsUiMapper
) : BaseViewModel() {
    private val args = savedStateHandle.toRoute<CourseDetailsDestination>()

    private val _uiState = MutableStateFlow(
        CourseDetailsUiState(
            isLoading = mutableStateOf(false),
            onEvent = CourseDetailsUiEvent(
                onCourseSaveRequested = ::onCourseSaveRequested,
            ),
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        launch(
            updateLoading = { _uiState.value.isLoading.value = it },
            function = {
                delay(1000)
                getCourseById(args.courseId)?.let {
                    updateCourseDetailsUI(it)
                }
            }
        )
    }

    private fun updateCourseDetailsUI(course: Course) {
        _uiState.update {
            it.copy(
                courseDetails = courseDetailsUiMapper(course)
            )
        }
    }

    private fun onCourseSaveRequested() {
        launch(
            updateLoading = { _uiState.value.saveCourseDetailsLoading.value = it },
            function = {
                _uiState.value.courseDetails?.let {
                    delay(1000)
                    val response = updateCourse(courseDetailsUiMapper(it))
                    if (response is UpdateCourseResult.Success) {
                        _uiState.value.courseDetailsSavedState.value = true
                    } else {
                        _uiState.value.courseSaveErrorDialog.value = DialogUiItem(
                            title = MyStringBuilder.StringResource(res = R.string.course_details_screen_save_dialog_title),
                            subtitle = MyStringBuilder.StringResource(res = R.string.course_details_screen_save_dialog_description),
                            onDismiss = { _uiState.value.courseSaveErrorDialog.value = null }
                        )
                    }
                }
            }
        )
    }
}
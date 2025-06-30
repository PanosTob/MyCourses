package com.panostob.mycourses.ui.courses.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.panostob.mycourses.domain.courses.entity.Course
import com.panostob.mycourses.ui.base.BaseViewModel
import com.panostob.mycourses.ui.courses.mapper.CoursesUiMapper
import com.panostob.mycourses.ui.courses.model.CoursesUiState
import com.panostob.mycourses.usecase.courses.GetAllCourses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val getAllCourses: GetAllCourses,
    private val coursesUiMapper: CoursesUiMapper
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(
        CoursesUiState(
            isLoading = mutableStateOf(false),
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        launch(
            updateLoading = { uiState.value.isLoading.value = it },
            function = {
                delay(1000)
                getAllCourses().collectLatest {
                    uiState.value.isLoading.value = false
                    updateCoursesUI(it)
                }
            }
        )
    }

    private fun updateCoursesUI(courses: List<Course>) {
        _uiState.value.courses.clear()
        _uiState.value.courses.addAll(coursesUiMapper(courses))
    }
}
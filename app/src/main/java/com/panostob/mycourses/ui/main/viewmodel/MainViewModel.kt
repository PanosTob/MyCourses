package com.panostob.mycourses.ui.main.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.panostob.mycourses.ui.base.BaseViewModel
import com.panostob.mycourses.ui.main.model.MainUiEvent
import com.panostob.mycourses.ui.main.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(
        MainUiState(
            isLoading = mutableStateOf(false),
            onEvent = MainUiEvent(
                onCreate = ::onCreate,
            ),
        )
    )
    val uiState = _uiState.asStateFlow()

    private fun onCreate() {

    }
}
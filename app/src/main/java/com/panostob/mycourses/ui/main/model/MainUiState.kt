package com.panostob.mycourses.ui.main.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class MainUiState(
    val isLoading: MutableState<Boolean> = mutableStateOf(false),
    val onEvent: MainUiEvent,
)

data class MainUiEvent(
    val onCreate: () -> Unit
)
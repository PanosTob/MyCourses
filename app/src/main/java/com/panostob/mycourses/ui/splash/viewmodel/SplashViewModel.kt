package com.panostob.mycourses.ui.splash.viewmodel

import com.panostob.mycourses.ui.splash.model.SplashUiState
import com.panostob.mycourses.ui.base.BaseViewModel
import com.panostob.mycourses.ui.main.navigation.MainDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState())
    val uiState = _uiState.asStateFlow()

    init {
        launch {
            delay(1000)
            _uiState.update { it.copy(navigationDestination = MainDestination) }
        }
    }
}
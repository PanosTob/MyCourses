package com.panostob.mycourses.ui.app

import androidx.lifecycle.SavedStateHandle
import com.panostob.mycourses.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<AppUiState> = MutableStateFlow(
        AppUiState(
            onEvent = AppUiEvent(
                onDismissConnectionView = ::onDismissConnectionView,
                onNetworkConnected = ::onNetworkConnected,
                onNetworkDisconnected = ::onNetworkDisconnected,
            )
        )
    )
    val uiState = _uiState.asStateFlow()

    private fun onDismissConnectionView() {
        _uiState.value.noInternetConnectionScreenVisible.value = false
    }

    fun onNetworkConnected() {
        _uiState.value.noInternetConnectionScreenVisible.value = false
    }

    fun onNetworkDisconnected() {
        _uiState.value.noInternetConnectionScreenVisible.value = true
    }
}
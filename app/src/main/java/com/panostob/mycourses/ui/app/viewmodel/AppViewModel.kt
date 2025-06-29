package com.panostob.mycourses.ui.app.viewmodel

import com.panostob.mycourses.R
import com.panostob.mycourses.ui.app.model.AppUiEvent
import com.panostob.mycourses.ui.app.model.AppUiState
import com.panostob.mycourses.ui.app.model.DialogUiItem
import com.panostob.mycourses.ui.base.BaseViewModel
import com.panostob.mycourses.util.compose.MyStringBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState: MutableStateFlow<AppUiState> = MutableStateFlow(
        AppUiState(
            onEvent = AppUiEvent(
                onDismissConnectionView = ::onDismissConnectionView,
                onNetworkConnected = ::onNetworkConnected,
                onNetworkDisconnected = ::onNetworkDisconnected,
                closeTheAppPrompt = ::closeTheAppPrompt
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

    private fun closeTheAppPrompt() {
        _uiState.value.dialogUiItem.value = DialogUiItem(
            title = MyStringBuilder.StringResource(res = R.string.close_the_app_dialog_title),
            subtitle = MyStringBuilder.StringResource(res = R.string.close_the_app_dialog_subtitle),
            dismissButtonText = MyStringBuilder.StringResource(res = R.string.close_the_app_dialog_dismiss_button_text),
            confirmButtonText = MyStringBuilder.StringResource(res = R.string.close_the_app_dialog_confirm_button_text),
            onConfirm = {
                _uiState.update { it.copy(closeTheApp = true) }
            },
            onDismiss = { _uiState.value.dialogUiItem.value = null }
        )
    }
}
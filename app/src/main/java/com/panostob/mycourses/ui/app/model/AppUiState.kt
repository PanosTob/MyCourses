package com.panostob.mycourses.ui.app.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.panostob.mycourses.R
import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import com.panostob.mycourses.util.compose.MyStringBuilder

data class AppUiState(
    val language: LanguageResult? = null,
    val noInternetConnectionScreenVisible: MutableState<Boolean> = mutableStateOf(false),
    val dialogUiItem: MutableState<DialogUiItem?> = mutableStateOf(null),
    val closeTheApp: Boolean = false,
    val onEvent: AppUiEvent,
)

data class AppUiEvent(
    val onDismissConnectionView: () -> Unit,
    val onNetworkConnected: () -> Unit,
    val onNetworkDisconnected: () -> Unit,
    val closeTheAppPrompt: () -> Unit
)

data class DialogUiItem(
    val title: MyStringBuilder,
    val subtitle: MyStringBuilder? = null,
    val textFieldValue: MutableState<String?> = mutableStateOf(null),
    val textFieldPlaceHolderText: MyStringBuilder = MyStringBuilder.Empty,
    val dismissButtonText: MyStringBuilder = MyStringBuilder.StringResource(res = R.string.ok),
    val confirmButtonText: MyStringBuilder = MyStringBuilder.Empty,
    val isConfirmEnabled: MutableState<Boolean> = mutableStateOf(true),
    val onDismiss: () -> Unit,
    val onConfirm: (() -> Unit)? = null,
    val onTextFieldValueChanged: ((String) -> Unit)? = null,
)

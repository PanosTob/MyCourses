package com.panostob.mycourses.ui.app

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import com.panostob.mycourses.util.compose.MyStringBuilder
import com.google.android.gms.common.api.ResolvableApiException
import com.panostob.mycourses.R

data class AppUiState(
    val loadingUiState: MutableState<Boolean> = mutableStateOf(false),
    val showAlertDialog: MutableState<DialogUiItem?> = mutableStateOf(null),
    val restartApplication: MutableState<Unit?> = mutableStateOf(null),
    val language: LanguageResult? = null,
    val deeplink: Uri? = null,
    val locationException: MutableState<ResolvableApiException?> = mutableStateOf(null),
    val noInternetConnectionScreenVisible: MutableState<Boolean> = mutableStateOf(false),
    val dialogUiItem: MutableState<DialogUiItem?> = mutableStateOf(null),
    val closeTheApp: Boolean = false,
    val onEvent: AppUiEvent,
)

data class AppUiEvent(
    val onDismissConnectionView: () -> Unit,
    val onNetworkConnected: () -> Unit,
    val onNetworkDisconnected: () -> Unit,
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

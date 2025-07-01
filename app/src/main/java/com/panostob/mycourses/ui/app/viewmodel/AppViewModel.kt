package com.panostob.mycourses.ui.app.viewmodel

import com.panostob.mycourses.R
import com.panostob.mycourses.ui.app.language.mapper.LanguageUiMapper
import com.panostob.mycourses.ui.app.language.model.BottomSheetUiEvent
import com.panostob.mycourses.ui.app.language.model.SelectionBottomSheetUiState
import com.panostob.mycourses.ui.app.language.model.SelectionUiItem
import com.panostob.mycourses.ui.app.model.AppUiEvent
import com.panostob.mycourses.ui.app.model.AppUiState
import com.panostob.mycourses.ui.app.model.DialogUiItem
import com.panostob.mycourses.ui.base.BaseViewModel
import com.panostob.mycourses.usecase.settings.GetLanguage
import com.panostob.mycourses.usecase.settings.GetLanguageList
import com.panostob.mycourses.usecase.settings.UpdateLanguage
import com.panostob.mycourses.util.compose.MyStringBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getLanguage: GetLanguage,
    private val updateLanguage: UpdateLanguage,
    getLanguageList: GetLanguageList,
    languageUiMapper: LanguageUiMapper,
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<AppUiState> = MutableStateFlow(
        AppUiState(
            languageUiState = MutableStateFlow(
                SelectionBottomSheetUiState(
                    options = languageUiMapper(getLanguageList()),
                    onEvent = BottomSheetUiEvent(
                        updateSelection = ::updateLanguageSelection,
                        onSubmitClick = ::onSubmitLanguage,
                        onDismiss = ::onLanguageDismiss,
                    ),
                )
            ),
            onEvent = AppUiEvent(
                onLanguageMenuOpenRequest = ::onLanguageMenuOpenRequest,
                onDismissConnectionView = ::onDismissConnectionView,
                onNetworkConnected = ::onNetworkConnected,
                onNetworkDisconnected = ::onNetworkDisconnected,
                closeTheAppPrompt = ::closeTheAppPrompt
            )
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        subscribeForLocaleChanges()
    }

    private fun subscribeForLocaleChanges() {
        launch {
            getLanguage().collectLatest { language ->
                _uiState.update { it.copy(language = language) }
            }
        }
    }

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

    private fun updateLanguageMenuVisibility(isVisible: Boolean) {
        uiState.value.languageUiState.update { langState ->
            langState.copy(isVisible = isVisible)
        }
    }

    private fun onLanguageMenuOpenRequest() {
        launch {
            val currentLanguage = getLanguage().first()
            val initialSelection = uiState.value.languageUiState.value.options.find { lang -> lang.id == currentLanguage.value }
            initialSelection?.let {
                updateLanguageSelection(it)
                _uiState.value.languageUiState.value.copy(initialOption = initialSelection)
            }
            updateLanguageMenuVisibility(true)
        }
    }

    private fun onLanguageDismiss() {
        updateLanguageSelection(uiState.value.languageUiState.value.initialOption)
        updateLanguageMenuVisibility(false)
    }

    private fun onSubmitLanguage() {
        val selectedLanguageCode = uiState.value.languageUiState.value.selectedOption?.id ?: return

        launch {
            updateLanguage(selectedLanguageCode)
        }

        updateLanguageMenuVisibility(false)
    }

    private fun updateLanguageSelection(selectionUiItem: SelectionUiItem?) {
        uiState.value.languageUiState.value.selectedOption?.isSelected?.value = false
        selectionUiItem?.isSelected?.value = true
    }
}

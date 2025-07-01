package com.panostob.mycourses.ui.app.language.model

import androidx.annotation.Size
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.panostob.mycourses.R
import com.panostob.mycourses.util.compose.MyStringBuilder

data class SelectionBottomSheetUiState(
    val isVisible: Boolean = false,
    val options: List<SelectionUiItem> = emptyList(),
    val initialOption: SelectionUiItem? = null,
    val isSubmitAlwaysEnabled: Boolean = false,
    val onEvent: BottomSheetUiEvent,
) {
    val isSubmitEnabled: Boolean
        get() = if (isSubmitAlwaysEnabled) true else initialOption != options.find { it.isSelected.value }

    val selectedOption: SelectionUiItem?
        get() = options.find { it.isSelected.value }
}

data class SelectionUiItem(
    @Size(min = 1) val id: String,
    val type: SelectionUiType,
    val isSelected: MutableState<Boolean> = mutableStateOf(false),
)

open class SelectionUiType(
    val titleRes: MyStringBuilder,
    val icon: String? = null,
    val errorIcon: Int? = null,
)

data class BottomSheetUiEvent(
    val updateSelection: (SelectionUiItem) -> Unit,
    val onSubmitClick: () -> Unit,
    val onDismiss: () -> Unit,
)

sealed class LanguageUiType(
    val title: MyStringBuilder,
) : SelectionUiType(
    titleRes = title,
) {
    data object LanguageGreek : LanguageUiType(title = MyStringBuilder.StringResource(R.string.language_greek_title))
    data object LanguageEnglish : LanguageUiType(title = MyStringBuilder.StringResource(R.string.language_english_title))
}
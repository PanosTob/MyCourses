package com.panostob.mycourses.ui.app.language.mapper

import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import com.panostob.mycourses.ui.app.language.model.SelectionUiItem
import javax.inject.Inject
import kotlin.collections.map

class LanguageUiMapper @Inject constructor(private val languageToSelectionUiMapper: LanguageToSelectionUiMapper) {
    operator fun invoke(languages: List<LanguageResult>): List<SelectionUiItem> {
        return languages.map { languageResult ->
            SelectionUiItem(
                id = languageResult.value,
                type = languageToSelectionUiMapper(languageResult),
            )
        }.distinctBy { it.id }
    }
}
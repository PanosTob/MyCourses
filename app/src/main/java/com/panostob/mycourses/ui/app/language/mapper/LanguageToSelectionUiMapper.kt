package com.panostob.mycourses.ui.app.language.mapper

import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import com.panostob.mycourses.ui.app.language.model.LanguageUiType
import com.panostob.mycourses.ui.app.language.model.SelectionUiType
import javax.inject.Inject

class LanguageToSelectionUiMapper @Inject constructor() {
    operator fun invoke(lang: LanguageResult): SelectionUiType {
        return when (lang) {
            LanguageResult.GREEK -> LanguageUiType.LanguageGreek
            LanguageResult.ENGLISH -> LanguageUiType.LanguageEnglish
        }
    }
}
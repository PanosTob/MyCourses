package com.panostob.mycourses.usecase.settings

import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import com.panostob.mycourses.usecase.UseCase
import javax.inject.Inject

class GetLanguageList @Inject constructor() : UseCase {
    operator fun invoke(): List<LanguageResult> {
        return LanguageResult.entries
    }
}
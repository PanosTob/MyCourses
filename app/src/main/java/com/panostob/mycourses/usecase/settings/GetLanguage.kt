package com.panostob.mycourses.usecase.settings

import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import com.panostob.mycourses.domain.settings.repository.SettingsRepository
import com.panostob.mycourses.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguage @Inject constructor(
    private val repository: SettingsRepository,
) : UseCase {
    operator fun invoke(): Flow<LanguageResult> {
        return repository.getLanguage()
    }
}
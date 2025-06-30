package com.panostob.mycourses.usecase.settings

import com.panostob.mycourses.domain.settings.repository.SettingsRepository
import com.panostob.mycourses.usecase.UseCase
import javax.inject.Inject

class UpdateLanguage @Inject constructor(
    private val repository: SettingsRepository,
) : UseCase {
    suspend operator fun invoke(language: String) {
        return repository.updateLanguage(language)
    }
}
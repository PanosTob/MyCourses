package com.panostob.mycourses.domain.settings.repository

import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getLanguage(): Flow<LanguageResult>

    suspend fun updateLanguage(language: String)
}
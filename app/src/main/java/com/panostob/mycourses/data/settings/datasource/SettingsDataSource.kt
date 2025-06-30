package com.panostob.mycourses.data.settings.datasource

import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {
    fun getLanguage(): Flow<LanguageResult>

    suspend fun updateLanguage(language: String)
}
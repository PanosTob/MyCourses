package com.panostob.mycourses.data.settings.repository

import com.panostob.mycourses.data.settings.datasource.SettingsDataSource
import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import com.panostob.mycourses.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataSource: SettingsDataSource,
) : SettingsRepository {
    override fun getLanguage(): Flow<LanguageResult> {
        return dataSource.getLanguage()
    }

    override suspend fun updateLanguage(language: String) {
        dataSource.updateLanguage(language)
    }
}
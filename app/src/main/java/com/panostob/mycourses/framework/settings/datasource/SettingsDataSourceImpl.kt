package com.panostob.mycourses.framework.settings.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.panostob.mycourses.data.settings.datasource.SettingsDataSource
import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class SettingsDataSourceImpl @Inject constructor(private val preferencesDataStore: DataStore<Preferences>) : SettingsDataSource {

    override fun getLanguage(): Flow<LanguageResult> {
        return preferencesDataStore.data.map { preferences ->
            when (preferences[LANGUAGE_KEY]) {
                LanguageResult.GREEK.value -> LanguageResult.GREEK
                LanguageResult.ENGLISH.value -> LanguageResult.ENGLISH
                else -> getLanguageFromLocale()
            }
        }
    }

    override suspend fun updateLanguage(language: String) {
        preferencesDataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    private fun getLanguageFromLocale(): LanguageResult {
        return when (Locale.getDefault().language) {
            LanguageResult.GREEK.value -> LanguageResult.GREEK
            else -> LanguageResult.ENGLISH
        }
    }

    companion object {
        internal val LANGUAGE_KEY = stringPreferencesKey("LANGUAGE")
    }
}
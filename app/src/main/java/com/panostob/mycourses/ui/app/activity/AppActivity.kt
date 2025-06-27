package com.panostob.mycourses.ui.app.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.panostob.mycourses.di.module.dataStore
import com.panostob.mycourses.domain.settings.language.entity.LanguageResult
import com.panostob.mycourses.framework.settings.datasource.SettingsDataSourceImpl.Companion.LANGUAGE_KEY
import com.panostob.mycourses.ui.app.composable.AppScreen
import com.panostob.mycourses.ui.app.navigation.AppNavHost
import com.panostob.mycourses.ui.app.viewmodel.AppViewModel
import com.panostob.mycourses.ui.base.theme.MyCoursesTheme
import com.panostob.mycourses.util.ext.updateLocale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

@AndroidEntryPoint
class AppActivity: ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()
    private var job: Job? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        subscribeUiChanges()

        setContent {
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()

            MyCoursesTheme {
                AppScreen(
                    uiState = uiState,
                )
            }
        }
    }

    override fun onDestroy() {
        job?.cancel()
        job = null
        super.onDestroy()
    }

    override fun attachBaseContext(newBase: Context) {
        runBlocking {
            val lang = newBase.dataStore.data.firstOrNull()?.get(LANGUAGE_KEY) ?: getLanguageFromLocale()
            super.attachBaseContext(newBase.updateLocale(lang))
        }
    }

    private fun getLanguageFromLocale(): String {
        return when (Locale.getDefault().language) {
            LanguageResult.GREEK.value -> LanguageResult.GREEK.value
            else -> LanguageResult.ENGLISH.value
        }
    }


    private fun subscribeUiChanges() {
        job = lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                if (it.language?.value != null && it.language.value != Locale.getDefault().language) {
                    recreate()
                }
                if (it.closeTheApp) {
                    finish()
                }
            }
        }
    }
}
package com.panostob.mycourses.ui.splash

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.panostob.mycourses.ui.app.navigation.NavigationToCallBack
import com.panostob.mycourses.ui.splash.composable.SplashScreen
import com.panostob.mycourses.ui.splash.viewmodel.SplashViewModel
import com.panostob.mycourses.util.navigation.NavigationDestination
import kotlinx.serialization.Serializable

@Serializable
object SplashDestination : NavigationDestination()

internal fun NavGraphBuilder.splashScreen(
    navigateTo: NavigationToCallBack
) {
    composable<SplashDestination>(
        enterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
    ) {
        val viewModel: SplashViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        SplashScreen(
            uiState = uiState,
            navigateTo = navigateTo,
        )
    }
}
package com.panostob.mycourses.ui.main.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.panostob.mycourses.ui.app.navigation.NavigationToCallBack
import com.panostob.mycourses.ui.main.composable.MainScreen
import com.panostob.mycourses.ui.main.viewmodel.MainViewModel
import com.panostob.mycourses.util.navigation.NavigationDestination
import kotlinx.serialization.Serializable

@Serializable
data object MainDestination : NavigationDestination()

fun NavGraphBuilder.mainScreen(
    navigateTo: NavigationToCallBack,
    onBackClicked: () -> Unit
) {
    composable<MainDestination>(
        enterTransition = { fadeIn() },
        popExitTransition = { slideOutHorizontally { offset -> offset } },
        exitTransition = { slideOutHorizontally { offset -> -offset } },
        popEnterTransition = { slideInHorizontally { offset -> -offset } },
    ) {
        val viewModel: MainViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        BackHandler {
            onBackClicked()
        }

//        MainSideEffects(uiState, isLoading, onLocationPermissionsGranted)

        MainScreen(
            modifier = Modifier
                .fillMaxSize(),
            uiState = uiState,
            navigateTo = navigateTo,
        )
    }
}

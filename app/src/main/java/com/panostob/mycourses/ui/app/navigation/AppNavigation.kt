package com.panostob.mycourses.ui.app.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.panostob.mycourses.ui.app.model.AppUiState
import com.panostob.mycourses.ui.main.navigation.mainScreen
import com.panostob.mycourses.ui.splash.SplashDestination
import com.panostob.mycourses.ui.splash.splashScreen
import com.panostob.mycourses.util.navigation.NavigationDestination
import com.panostob.mycourses.util.navigation.safeNavigate

internal typealias NavigationToCallBack = (NavigationDestination) -> Unit

@Composable
internal fun AppNavHost(
    uiState: State<AppUiState>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        enterTransition = { slideInHorizontally { it } },
        popExitTransition = { slideOutHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
        startDestination = SplashDestination,
    ) {
        splashScreen(
            navigateTo = { destination -> navController.safeNavigate(destination, popUpToRoute = SplashDestination) },
        )

        mainScreen(
            navigateTo = { destination -> navController.safeNavigate(destination) },
            onBackClicked = { uiState.value.onEvent.closeTheAppPrompt() }
        )
    }
}
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
import com.panostob.mycourses.ui.courses.details.navigation.courseDetailsScreen
import com.panostob.mycourses.ui.courses.navigation.coursesScreen
import com.panostob.mycourses.ui.splash.SplashDestination
import com.panostob.mycourses.ui.splash.splashScreen
import com.panostob.mycourses.util.navigation.NavigationDestination
import com.panostob.mycourses.util.navigation.safeNavigate

internal typealias NavigationToCallBack = (NavigationDestination) -> Unit

@Composable
internal fun AppNavHost(
    uiState: State<AppUiState>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    isLoading: (Boolean) -> Unit,
    onCloseTheAppRequest: () -> Unit
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

        coursesScreen(
            navigateTo = { destination -> navController.safeNavigate(destination) },
            onBackRequest = onCloseTheAppRequest,
            isLoading = isLoading
        )

        courseDetailsScreen(
            onBackRequest = { navController.navigateUp() },
            isLoading = isLoading
        )
    }
}
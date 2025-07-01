package com.panostob.mycourses.ui.app.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.panostob.mycourses.ui.app.model.DialogUiItem
import com.panostob.mycourses.ui.courses.details.navigation.courseDetailsScreen
import com.panostob.mycourses.ui.courses.navigation.coursesScreen
import com.panostob.mycourses.ui.splash.SplashDestination
import com.panostob.mycourses.ui.splash.splashScreen
import com.panostob.mycourses.util.navigation.NavigationDestination
import com.panostob.mycourses.util.navigation.safeNavigate

internal typealias NavigationToCallBack = (NavigationDestination) -> Unit

@ExperimentalMaterial3Api
@Composable
internal fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    showSaveErrorDialog: (DialogUiItem) -> Unit,
    onLanguageMenuOpenRequest: () -> Unit,
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
            onLanguageMenuOpenRequest = onLanguageMenuOpenRequest,
        )

        courseDetailsScreen(
            onBackRequest = { navController.navigateUp() },
            showSaveErrorDialog = showSaveErrorDialog
        )
    }
}
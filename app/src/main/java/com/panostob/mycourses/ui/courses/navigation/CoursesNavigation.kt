package com.panostob.mycourses.ui.courses.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.panostob.mycourses.ui.app.navigation.NavigationToCallBack
import com.panostob.mycourses.ui.courses.composable.CoursesScreen
import com.panostob.mycourses.ui.courses.model.CoursesUiState
import com.panostob.mycourses.ui.courses.viewmodel.CoursesViewModel
import com.panostob.mycourses.util.navigation.NavigationDestination
import kotlinx.serialization.Serializable

@Serializable
data object CoursesDestination : NavigationDestination()

fun NavGraphBuilder.coursesScreen(
    navigateTo: NavigationToCallBack,
    onBackRequest: () -> Unit,
    onLanguageMenuOpenRequest: () -> Unit,
) {
    composable<CoursesDestination>(
        enterTransition = { fadeIn() },
        popExitTransition = { slideOutHorizontally { offset -> offset } },
        exitTransition = { slideOutHorizontally { offset -> -offset } },
        popEnterTransition = { slideInHorizontally { offset -> -offset } },
    ) {
        val viewModel: CoursesViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        BackHandler {
            onBackRequest()
        }

        CoursesScreen(
            modifier = Modifier
                .fillMaxSize(),
            uiState = uiState,
            navigateTo = navigateTo,
            onLanguageMenuOpenRequest = onLanguageMenuOpenRequest
        )
    }
}
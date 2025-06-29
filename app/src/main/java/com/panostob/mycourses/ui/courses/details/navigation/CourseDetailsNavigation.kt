package com.panostob.mycourses.ui.courses.details.navigation

import androidx.activity.compose.BackHandler
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
import com.panostob.mycourses.ui.courses.details.composable.CourseDetailsScreen
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsUiState
import com.panostob.mycourses.ui.courses.details.viewmodel.CourseDetailsViewModel
import com.panostob.mycourses.util.navigation.NavigationDestination
import com.panostob.mycourses.util.navigation.slideInOutComposable
import kotlinx.serialization.Serializable

@Serializable
data class CourseDetailsDestination(val courseId: String) : NavigationDestination()

fun NavGraphBuilder.courseDetailsScreen(
    onBackRequest: () -> Unit,
    isLoading: (Boolean) -> Unit
) {
    slideInOutComposable<CourseDetailsDestination> {
        val viewModel: CourseDetailsViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        CourseDetailsSideEffects(uiState, isLoading)

        CourseDetailsScreen(
            modifier = Modifier
                .fillMaxSize(),
            uiState = uiState,
            onBackRequest = onBackRequest,
        )
    }
}

@Composable
private fun CourseDetailsSideEffects(
    uiState: State<CourseDetailsUiState>,
    isLoading: (Boolean) -> Unit,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(Unit) {
        snapshotFlow { uiState.value.isLoading.value }
            .flowWithLifecycle(lifecycle)
            .collect {
                isLoading(it)
            }
    }
}
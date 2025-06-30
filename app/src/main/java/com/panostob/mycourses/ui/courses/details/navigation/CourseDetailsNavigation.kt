package com.panostob.mycourses.ui.courses.details.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.panostob.mycourses.ui.app.model.DialogUiItem
import com.panostob.mycourses.ui.courses.details.composable.CourseDetailsScreen
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsUiState
import com.panostob.mycourses.ui.courses.details.viewmodel.CourseDetailsViewModel
import com.panostob.mycourses.util.navigation.NavigationDestination
import com.panostob.mycourses.util.navigation.slideInOutComposable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.serialization.Serializable

@Serializable
data class CourseDetailsDestination(val courseId: String) : NavigationDestination()

@ExperimentalMaterial3Api
fun NavGraphBuilder.courseDetailsScreen(
    onBackRequest: () -> Unit,
    showSaveErrorDialog: (DialogUiItem) -> Unit
) {
    slideInOutComposable<CourseDetailsDestination> {
        val viewModel: CourseDetailsViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        CourseDetailsSideEffects(uiState, onBackRequest, showSaveErrorDialog)

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
    navigateBack: () -> Unit,
    showSaveErrorDialog: (DialogUiItem) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(Unit) {
        snapshotFlow { uiState.value.courseDetailsSavedState.value }
            .filter { it }
            .flowWithLifecycle(lifecycle)
            .collectLatest { navigateBack() }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { uiState.value.courseSaveErrorDialog.value }
            .filterNotNull()
            .flowWithLifecycle(lifecycle)
            .collectLatest { showSaveErrorDialog(it) }
    }
}
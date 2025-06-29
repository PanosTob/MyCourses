package com.panostob.mycourses.ui.app.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.panostob.mycourses.R
import com.panostob.mycourses.ui.app.alertdialog.DialogScreen
import com.panostob.mycourses.ui.app.model.AppUiState
import com.panostob.mycourses.ui.app.navigation.AppNavHost
import com.panostob.mycourses.util.composable.shimmerLoading
import com.panostob.mycourses.util.network.connection.rememberConnectivityMonitor

@Composable
internal fun AppScreen(
    uiState: State<AppUiState>
) {
    AppSideEffects(
        uiState = uiState
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        DialogScreen(dialogUiItem = { uiState.value.dialogUiItem.value })

        AppNavHost(
            modifier = Modifier.systemBarsPadding(),
            uiState = uiState,
            isLoading = { uiState.value.loadingUiState.value = it },
            onCloseTheAppRequest = { uiState.value.onEvent.closeTheAppPrompt() }
        )

        AppLoadingSkeleton(
            loadingState = uiState.value.loadingUiState,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun MyCoursesLogoItem(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = ""
    )
}

@Composable
fun AppSideEffects(
    uiState: State<AppUiState>
) {
    rememberConnectivityMonitor(
        onConnected = { uiState.value.onEvent.onNetworkConnected() },
        onDisconnected = { uiState.value.onEvent.onNetworkDisconnected() }
    )
}

@Composable
fun AppLoadingSkeleton(
    loadingState: State<Boolean>,
    modifier: Modifier = Modifier
) {
    if (loadingState.value) {
        Box(modifier = modifier.shimmerLoading())
    }
}
package com.panostob.mycourses.ui.app.composable

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.panostob.mycourses.R
import com.panostob.mycourses.ui.app.alertdialog.DialogScreen
import com.panostob.mycourses.ui.app.model.AppUiState
import com.panostob.mycourses.ui.app.navigation.AppNavHost
import com.panostob.mycourses.util.network.connection.rememberConnectivityMonitor

@Composable
internal fun AppScreen(
    uiState: State<AppUiState>
) {
    AppSideEffects(
        uiState = uiState
    )

    DialogScreen(dialogUiItem = { uiState.value.dialogUiItem.value })

    AppNavHost(uiState)
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
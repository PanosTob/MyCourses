package com.panostob.mycourses.ui.app.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.panostob.mycourses.R
import com.panostob.mycourses.ui.app.alertdialog.DialogScreen
import com.panostob.mycourses.ui.app.model.AppUiState
import com.panostob.mycourses.ui.app.navigation.AppNavHost
import com.panostob.mycourses.ui.base.theme.Spacing_12dp
import com.panostob.mycourses.util.network.connection.rememberConnectivityMonitor

@ExperimentalMaterial3Api
@Composable
internal fun AppScreen(
    uiState: State<AppUiState>
) {
    AppSideEffects(uiState = uiState)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        DialogScreen(dialogUiItem = { uiState.value.dialogUiItem.value })

        AppNavHost(
            modifier = Modifier.systemBarsPadding(),
            onCloseTheAppRequest = { uiState.value.onEvent.closeTheAppPrompt() },
            showSaveErrorDialog = { uiState.value.dialogUiItem.value = it },
        )

        NoInternetConnectionMessageBar(uiState.value.noInternetConnectionScreenVisible)
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
fun BoxScope.NoInternetConnectionMessageBar(
    visibilityState: State<Boolean>
) {
    AnimatedVisibility(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        visible = visibilityState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(Spacing_12dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.no_internet_connection_screen_text), color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
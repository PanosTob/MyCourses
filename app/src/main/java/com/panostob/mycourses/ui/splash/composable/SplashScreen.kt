package com.panostob.mycourses.ui.splash.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.panostob.mycourses.ui.app.composable.MyCoursesLogoItem
import com.panostob.mycourses.ui.app.navigation.NavigationToCallBack
import com.panostob.mycourses.ui.base.theme.ColorPrimary
import com.panostob.mycourses.ui.splash.model.SplashUiState
import com.panostob.mycourses.util.navigation.NavigationDestination

@Composable
fun SplashScreen(
    uiState: State<SplashUiState>,
    navigateTo: NavigationToCallBack
) {
    SplashSideEffects(
        uiState = uiState,
        navigateTo = navigateTo,
    )

    SplashContent()
}

@Composable
private fun SplashContent() {
    Box(
        Modifier
            .fillMaxSize()
            .background(ColorPrimary)
    ) {
        MyCoursesLogoItem(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f)
                .aspectRatio(2f)
        )
    }
}

@Composable
private fun SplashSideEffects(
    uiState: State<SplashUiState>,
    navigateTo: (NavigationDestination) -> Unit,
) {
    LaunchedEffect(uiState.value.navigationDestination) {
        uiState.value.navigationDestination?.let { destination ->
            navigateTo(destination)
        }
    }
}
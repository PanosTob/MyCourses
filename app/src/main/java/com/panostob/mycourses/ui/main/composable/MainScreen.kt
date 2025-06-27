package com.panostob.mycourses.ui.main.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.panostob.mycourses.ui.app.navigation.NavigationToCallBack
import com.panostob.mycourses.ui.main.model.MainUiState

@Composable
fun MainScreen(
    uiState: State<MainUiState>,
    modifier: Modifier = Modifier,
    navigateTo: NavigationToCallBack
) {
}
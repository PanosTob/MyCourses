package com.panostob.mycourses.ui.base.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CustomRippleTheme(
    color: Color = Color.Black,
    rippleAlpha: RippleAlpha? = null,
    content: @Composable () -> Unit,
) {
    val rippleConfiguration = RippleConfiguration(color = color, rippleAlpha = rippleAlpha)

    CompositionLocalProvider(
        value = LocalRippleConfiguration provides rippleConfiguration,
        content = content
    )
}
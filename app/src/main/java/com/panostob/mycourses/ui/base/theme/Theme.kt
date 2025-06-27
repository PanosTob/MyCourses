package com.panostob.mycourses.ui.base.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable

@Composable
internal fun MyCoursesTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit) {

    val colorScheme = if (!useDarkTheme) LightColors else DarkColors

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = myCoursesTypography,
        content = content,
    )
}
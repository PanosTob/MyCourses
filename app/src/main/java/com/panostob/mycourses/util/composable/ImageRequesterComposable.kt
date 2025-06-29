package com.panostob.mycourses.util.composable

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest

@Composable
internal fun rememberImageRequester(
    context: Context = LocalContext.current,
): ImageRequest.Builder {
    return remember {
        ImageRequest.Builder(context)
    }
}
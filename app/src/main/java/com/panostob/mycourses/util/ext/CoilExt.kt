package com.panostob.mycourses.util.ext

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import coil3.asImage
import coil3.request.ImageRequest

fun ImageRequest.Builder.errorDrawable(@DrawableRes drawableResId: Int?) =
    error { factory ->
        drawableResId?.let { res ->
            factory.context.getDrawableCompat(res).asImage()
        }
    }

private fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable {
    return checkNotNull(AppCompatResources.getDrawable(this, resId)) { "Invalid resource ID: $resId" }
}
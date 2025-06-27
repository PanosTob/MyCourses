package com.panostob.mycourses.util.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density

fun DrawScope.drawRoundShapedRect(
    color: Color,
    shape: RoundedCornerShape,
){
    drawPath(
        path = roundedCornerPath(
            size = size,
            roundedCornerShape = shape,
            density = this
        ),
        color = color
    )
}

private fun roundedCornerPath(size: Size, roundedCornerShape: RoundedCornerShape, density: Density): Path {
    var topStart = roundedCornerShape.topStart.toPx(size, density)
    var topEnd = roundedCornerShape.topEnd.toPx(size, density)
    var bottomEnd = roundedCornerShape.bottomEnd.toPx(size, density)
    var bottomStart = roundedCornerShape.bottomStart.toPx(size, density)
    val minDimension = size.minDimension
    if (topStart + bottomStart > minDimension) {
        val scale = minDimension / (topStart + bottomStart)
        topStart *= scale
        bottomStart *= scale
    }
    if (topEnd + bottomEnd > minDimension) {
        val scale = minDimension / (topEnd + bottomEnd)
        topEnd *= scale
        bottomEnd *= scale
    }

    val path = Path()

    val roundRect = RoundRect(
        left = 0f,
        top = 0f,
        right = size.width,
        bottom = size.height,
        topLeftCornerRadius = CornerRadius(topStart, topStart),
        topRightCornerRadius = CornerRadius(topEnd, topEnd),
        bottomLeftCornerRadius = CornerRadius(bottomStart, bottomStart),
        bottomRightCornerRadius = CornerRadius(bottomEnd, bottomEnd)
    )
    path.addRoundRect(roundRect)
    return path
}
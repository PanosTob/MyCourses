package com.panostob.mycourses.ui.courses.model

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.panostob.mycourses.ui.base.theme.ColorProgressComplete
import kotlinx.serialization.Serializable

data class CoursesUiState(
    val isLoading: MutableState<Boolean> = mutableStateOf(false),
    val courses: SnapshotStateList<CourseUiItem> = mutableStateListOf(),
)

@Serializable
data class CourseUiItem(
    val id: String = "",
    val title: String = "",
    val shortDescription: String = "",
    val imageUrl: String = "",
    val progressPercentage: Float
) {
    val animatedProgressValue: @Composable () -> Float = {
        var targetProgress by rememberSaveable (progressPercentage) { mutableFloatStateOf(0f) }

        // Animate the progress value
        val animatedProgress by animateFloatAsState(
            targetValue = targetProgress,
            animationSpec = tween(durationMillis = 1000, delayMillis = 200), // Customize duration/delay
            label = "progressAnimation"
        )

        // Use LaunchedEffect to set the target progress when the item's actual progressPercentage is available
        // This ensures the animation starts from 0f towards the item.progressPercentage.
        LaunchedEffect(progressPercentage) {
            targetProgress = progressPercentage
        }

        animatedProgress
    }

    val progressBarPercentageLabel: String
        get() = "${progressPercentage.times(100).toInt()}%"

    val progressBarColor: Color
        @Composable
        get() = if (progressPercentage < 1f) {
            MaterialTheme.colorScheme.secondary
        } else {
            ColorProgressComplete
        }
}
package com.panostob.mycourses.ui.courses.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
    val progressBarColor: Color
        @Composable
        get() = if (progressPercentage < 1f) {
            MaterialTheme.colorScheme.secondary
        } else {
            ColorProgressComplete
        }
}
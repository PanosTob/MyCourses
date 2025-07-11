package com.panostob.mycourses.ui.courses.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.panostob.mycourses.R
import com.panostob.mycourses.ui.app.navigation.NavigationToCallBack
import com.panostob.mycourses.ui.base.theme.ColorDisabledButton
import com.panostob.mycourses.ui.base.theme.ColorPrimary
import com.panostob.mycourses.ui.base.theme.Spacing_12dp
import com.panostob.mycourses.ui.base.theme.Spacing_16dp
import com.panostob.mycourses.ui.base.theme.Spacing_24dp
import com.panostob.mycourses.ui.base.theme.Spacing_4dp
import com.panostob.mycourses.ui.base.theme.Spacing_8dp
import com.panostob.mycourses.ui.courses.details.navigation.CourseDetailsDestination
import com.panostob.mycourses.ui.courses.model.CourseUiItem
import com.panostob.mycourses.ui.courses.model.CoursesUiState
import com.panostob.mycourses.util.composable.noRippleClickable
import com.panostob.mycourses.util.composable.rememberImageRequester
import com.panostob.mycourses.util.composable.shimmerLoading
import com.panostob.mycourses.util.ext.errorDrawable

@Composable
fun CoursesScreen(
    uiState: State<CoursesUiState>,
    modifier: Modifier = Modifier,
    navigateTo: NavigationToCallBack,
    onLanguageMenuOpenRequest: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Spacing_16dp, start = Spacing_16dp, end = Spacing_16dp),
        verticalArrangement = Arrangement.spacedBy(Spacing_24dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(8f)
                .heightIn(Spacing_24dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center),
                text = stringResource(R.string.courses_screen_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.secondary)
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .sizeIn(Spacing_24dp)
                    .fillMaxWidth(0.08f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .clickable { onLanguageMenuOpenRequest() },
                painter = painterResource(R.drawable.ic_language),
                contentDescription = "Button Change App Language",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        LoadingSkeletonListContent(
            loadingState = uiState.value.isLoading,
            modifier = modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacing_16dp),
                contentPadding = PaddingValues(bottom = Spacing_16dp)
            ) {
                items(items = uiState.value.courses, key = { it.id }) { item ->
                    CoursesContentItem(
                        item = item,
                        onCourseItemClicked = { navigateTo(CourseDetailsDestination(item.id)) }
                    )
                }
            }
        }
    }
}

@Composable
fun CoursesContentItem(
    item: CourseUiItem,
    onCourseItemClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCourseItemClicked() },
        shape = RoundedCornerShape(Spacing_16dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = ColorPrimary,
            disabledContainerColor = ColorDisabledButton,
            disabledContentColor = ColorDisabledButton
        ),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.secondary),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f),
                model = rememberImageRequester()
                    .data(data = item.imageUrl)
                    .errorDrawable(R.drawable.ic_launcher_background)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing_16dp),
                verticalArrangement = Arrangement.spacedBy(Spacing_8dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.shortDescription,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CourseCompletionProgressBar(
                        modifier = Modifier
                            .weight(0.88f),
                        progress = item.animatedProgressValue(),
                        progressColor = item.progressBarColor,
                        height = Spacing_4dp
                    )
                    Text(
                        modifier = Modifier.weight(0.12f),
                        text = item.progressBarPercentageLabel,
                        textAlign = TextAlign.Center,
                        color = ColorPrimary,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}


@Composable
fun CourseCompletionProgressBar(
    modifier: Modifier = Modifier,
    progress: Float, // Range: 0f to 1f
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    height: Dp = Spacing_12dp
) {

    Canvas(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val barWidth = size.width
        val barHeight = height.toPx()
        val radius = barHeight / 2
        val progressWidth = barWidth * progress

        // Draw background bar
        drawRoundRect(
            color = backgroundColor,
            size = Size(barWidth, barHeight),
            cornerRadius = CornerRadius(radius, radius),
            topLeft = Offset(0f, (size.height - barHeight) / 2)
        )

        // Draw progress with rounded ends
        if (progress > 0) {
            drawRoundRect(
                color = progressColor,
                size = Size(progressWidth, barHeight),
                cornerRadius = CornerRadius(radius, radius),
                topLeft = Offset(0f, (size.height - barHeight) / 2)
            )
        }
    }
}

@Composable
fun LoadingSkeletonListContent(
    loadingState: State<Boolean>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        if (loadingState.value) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacing_16dp),
                userScrollEnabled = false
            ) {
                items(6) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f),
                        shape = RoundedCornerShape(Spacing_16dp),
                        colors = CardColors(
                            containerColor = Color.White,
                            contentColor = ColorPrimary,
                            disabledContainerColor = ColorDisabledButton,
                            disabledContentColor = ColorDisabledButton
                        ),
                        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.secondary),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(3f)
                                    .shimmerLoading()
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Spacing_16dp),
                                verticalArrangement = Arrangement.spacedBy(Spacing_8dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .shimmerLoading()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .shimmerLoading()
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .weight(0.9f)
                                            .height(Spacing_4dp)
                                            .shimmerLoading(),
                                    )
                                    Box(
                                        modifier = Modifier
                                            .weight(0.1f)
                                            .shimmerLoading()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            content()
        }
    }
}
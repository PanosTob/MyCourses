package com.panostob.mycourses.ui.courses.details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.panostob.mycourses.R
import com.panostob.mycourses.ui.base.theme.ColorSecondary
import com.panostob.mycourses.ui.base.theme.Spacing_12dp
import com.panostob.mycourses.ui.base.theme.Spacing_16dp
import com.panostob.mycourses.ui.base.theme.Spacing_24dp
import com.panostob.mycourses.ui.base.theme.Spacing_2dp
import com.panostob.mycourses.ui.base.theme.Spacing_4dp
import com.panostob.mycourses.ui.base.theme.Spacing_8dp
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsUiItem
import com.panostob.mycourses.ui.courses.details.model.CourseDetailsUiState
import com.panostob.mycourses.util.composable.CustomSlider
import com.panostob.mycourses.util.composable.CustomSliderDefaults
import com.panostob.mycourses.util.composable.noRippleClickable
import com.panostob.mycourses.util.composable.progress
import com.panostob.mycourses.util.composable.rememberImageRequester
import com.panostob.mycourses.util.composable.shimmerLoading
import com.panostob.mycourses.util.composable.track
import com.panostob.mycourses.util.ext.errorDrawable
import com.panostob.mycourses.util.ext.pxToDp

@ExperimentalMaterial3Api
@Composable
fun CourseDetailsScreen(
    uiState: State<CourseDetailsUiState>,
    modifier: Modifier = Modifier,
    onBackRequest: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        var mainContentTopPadding by remember { mutableIntStateOf(0) }
        var mainContentBottomPadding by remember { mutableIntStateOf(0) }
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .onSizeChanged {
                    mainContentTopPadding += it.height.pxToDp
                }
                .fillMaxWidth()
                .heightIn(Spacing_24dp)
                .padding(Spacing_16dp)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .sizeIn(Spacing_24dp)
                    .fillMaxWidth(0.08f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .noRippleClickable { onBackRequest() },
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "Button Navigate to the previous screen",
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .heightIn(min = Spacing_24dp),
                text = stringResource(R.string.course_details_screen_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.secondary)
            )
        }

        LoadingSkeletonDetailsContent(
            loadingState = uiState.value.isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = mainContentTopPadding.dp)
        ) {
            CourseDetailsContentItem(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = mainContentBottomPadding.dp),
                courseDetails = uiState.value.courseDetails
            )
            Row(
                modifier = Modifier
                    .onSizeChanged {
                        mainContentBottomPadding += it.height.pxToDp
                    }
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .aspectRatio(5f)
                    .heightIn(Spacing_24dp)
                    .padding(Spacing_16dp)
                    .graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(Spacing_8dp)
                    }
                    .drawBehind {
                        drawRect(size = size, color = ColorSecondary)
                    }
                    .clickable {
                        uiState.value.onEvent.onCourseSaveRequested()
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                uiState.value.saveButtonContent()
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun CourseDetailsContentItem(
    courseDetails: CourseDetailsUiItem?,
    modifier: Modifier = Modifier,
) {
    courseDetails?.let { item ->
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(Spacing_24dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(Spacing_24dp),
                model = rememberImageRequester()
                    .data(data = item.imageUrl)
                    .errorDrawable(R.drawable.ic_launcher_background)
                    .build(),
                contentDescription = ""
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing_16dp),
                verticalArrangement = Arrangement.spacedBy(Spacing_8dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.shortDescription,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.secondary)
                )

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(Spacing_16dp)) {
                    CustomSlider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = courseDetails.progressSlider.sliderPosition(),
                        onValueChange = { courseDetails.progressSlider.progressPercentage.value = it },
                        valueRange = 0f..100f,
                        thumb = { thumbValue ->
                            CustomSliderDefaults.Thumb(
                                thumbValue = "$thumbValue%",
                                color = MaterialTheme.colorScheme.secondary,
                                size = 12.dp,
                                content = {}
                            )
                        },
                        track = { sliderState ->
                            Box(
                                modifier = Modifier
                                    .track()
                                    .background(Color.White),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Box(
                                    modifier = Modifier
                                        .progress(sliderState = sliderState)
                                        .background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(Spacing_8dp))
                                )
                            }
                        }
                    )
                    Text(
                        text = stringResource(R.string.course_details_screen_completion_progress_slider_value_text, courseDetails.progressSlider.sliderPositionValueText()),
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.secondary)
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingSkeletonDetailsContent(
    loadingState: State<Boolean>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        if (loadingState.value) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(Spacing_24dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f)
                        .heightIn(Spacing_24dp)
                        .shimmerLoading()
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing_16dp),
                    verticalArrangement = Arrangement.spacedBy(Spacing_8dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(11f)
                            .shimmerLoading()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f)
                            .shimmerLoading()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Spacing_4dp)
                            .shimmerLoading(),
                    )
                }
            }
        } else {
            content()
        }
    }
}

@Composable
fun SaveButtonContent(isLoading: Boolean) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(Spacing_12dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = Spacing_2dp,
        )
    } else {
        Text(
            text = stringResource(R.string.course_details_screen_save_button_text),
            textAlign = TextAlign.Center,
            color = Color.White,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}
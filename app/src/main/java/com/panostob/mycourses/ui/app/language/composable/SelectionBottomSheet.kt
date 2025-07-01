package com.panostob.mycourses.ui.app.language.composable

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.flowWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.panostob.mycourses.R
import com.panostob.mycourses.ui.app.language.model.SelectionBottomSheetUiState
import com.panostob.mycourses.ui.app.language.model.SelectionUiItem
import com.panostob.mycourses.ui.base.theme.ColorSecondary
import com.panostob.mycourses.ui.base.theme.CustomRippleTheme
import com.panostob.mycourses.ui.base.theme.Spacing_12dp
import com.panostob.mycourses.ui.base.theme.Spacing_16dp
import com.panostob.mycourses.ui.base.theme.Spacing_24dp
import com.panostob.mycourses.ui.base.theme.Spacing_2dp
import com.panostob.mycourses.ui.base.theme.Spacing_32dp
import com.panostob.mycourses.ui.base.theme.Spacing_8dp
import com.panostob.mycourses.util.composable.AutoSizeText
import com.panostob.mycourses.util.compose.MyStringBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectionBottomSheet(
    uiState: State<SelectionBottomSheetUiState>,
    title: MyStringBuilder,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { uiState.value.isVisible }
            .filterNotNull()
            .filter { show -> show }
            .flowWithLifecycle(lifecycle)
            .collectLatest { uiState.value.isVisible }
    }

    BackHandler(uiState.value.isVisible) {
        coroutineScope.launch { uiState.value.onEvent.onDismiss() }
    }

    if (uiState.value.isVisible) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .systemBarsPadding(),
            onDismissRequest = { uiState.value.onEvent.onDismiss() },
            sheetState = bottomSheetState,
            shape = RoundedCornerShape(topStart = Spacing_16dp, topEnd = Spacing_16dp),
            containerColor = MaterialTheme.colorScheme.background,
            content = {
                BottomSheetContent(
                    title = title,
                    options = uiState.value.options,
                    isSubmitEnabled = { uiState.value.isSubmitEnabled },
                    onItemSelected = { uiState.value.onEvent.updateSelection(it) },
                    onSubmitClick = { uiState.value.onEvent.onSubmitClick() },
                )
            },
        )
    }
}

@Composable
internal fun BottomSheetContent(
    title: MyStringBuilder,
    options: List<SelectionUiItem>,
    isSubmitEnabled: () -> Boolean,
    onItemSelected: (SelectionUiItem) -> Unit,
    onSubmitClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = Spacing_32dp),
        verticalArrangement = Arrangement.spacedBy(Spacing_32dp),
    ) {
        BottomSheetHeader(title = title)
        BottomSheetSelections(
            modifier = Modifier.weight(1f, false),
            options = options,
            onItemSelected = { onItemSelected(it) },
        )
        GeneralButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing_32dp),
            text = stringResource(id = R.string.language_confirm_btn),
            enabled = isSubmitEnabled,
            color = MaterialTheme.colorScheme.tertiary,
            onClick = { onSubmitClick() },
        )
    }
}

@Composable
internal fun BottomSheetHeader(
    modifier: Modifier = Modifier,
    title: MyStringBuilder,
    color: Color = Color.Black,
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = title.build(),
        color = color,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        ),
    )
}

@Composable
private fun BottomSheetSelections(
    modifier: Modifier = Modifier,
    options: List<SelectionUiItem>,
    onItemSelected: (SelectionUiItem) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing_16dp),
    ) {
        items(items = options, key = { item -> item.id }) { item ->
            BottomSheetSelectionItem(
                title = item.type.titleRes,
                isSelected = item.isSelected,
                onItemSelected = { onItemSelected(item) }
            )
        }
    }
}

@Composable
fun BottomSheetSelectionItem(
    title: MyStringBuilder,
    isSelected: State<Boolean>,
    onItemSelected: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable { onItemSelected() }
            .padding(horizontal = Spacing_8dp, vertical = Spacing_12dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioDraw(
            modifier = Modifier.size(Spacing_24dp),
            isSelected = isSelected,
        )
        AutoSizeText(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Spacing_16dp),
            text = title.build(),
            color = Color.Black,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
            ),
        )
    }
}

@Composable
fun RadioDraw(
    modifier: Modifier,
    isSelected: State<Boolean>,
    strokePercentage: Float = 0.1f,
    fillColor: Color = ColorSecondary,
    strokeColor: Color = ColorSecondary,
) {
    Canvas(modifier = modifier.padding(Spacing_2dp)) {
        val circleRadius = size.minDimension / 2

        drawCircle(
            color = strokeColor,
            radius = circleRadius,
            style = Stroke(width = circleRadius * strokePercentage),
        )

        if (isSelected.value) {
            drawCircle(
                color = fillColor,
                radius = circleRadius * 0.8f,
            )
        }
    }
}

@Composable
internal fun GeneralButton(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    text: String,
    enabled: () -> Boolean = { true },
    color: Color = MaterialTheme.colorScheme.primary,
    disabledColor: Color = color.copy(alpha = 0.7f),
    textColor: Color = Color.White,
    maxFontSize: TextUnit = 18.sp,
    maxLines: Int = 1,
    style: TextStyle = MaterialTheme.typography.titleMedium.copy(lineHeight = 20.sp),
    contentPadding: PaddingValues = PaddingValues(horizontal = Spacing_8dp, vertical = Spacing_16dp),
    onClick: () -> Unit,
) {
    CustomRippleTheme {
        Button(
            modifier = modifier,
            enabled = enabled(),
            shape = shape,
            colors = ButtonDefaults.buttonColors(containerColor = color, disabledContainerColor = disabledColor),
            contentPadding = contentPadding,
            onClick = dropUnlessResumed { onClick() },
        ) {
            AutoSizeText(
                modifier = Modifier,
                text = text,
                maxFontSize = maxFontSize,
                color = textColor,
                maxLines = maxLines,
                textAlign = TextAlign.Center,
                style = style,
            )
        }
    }
}
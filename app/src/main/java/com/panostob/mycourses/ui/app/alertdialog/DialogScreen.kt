package com.panostob.mycourses.ui.app.alertdialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.font.FontWeight
import com.panostob.mycourses.ui.app.model.DialogUiItem
import com.panostob.mycourses.ui.base.theme.Spacing_12dp
import com.panostob.mycourses.ui.base.theme.Spacing_16dp
import com.panostob.mycourses.ui.base.theme.Spacing_8dp
import com.panostob.mycourses.util.composable.drawRoundShapedRect

@Composable
internal fun DialogScreen(dialogUiItem: () -> DialogUiItem?) {
    val item = dialogUiItem()
    val shape = RoundedCornerShape(Spacing_8dp)

    if (item != null) {
        AlertDialog(
            onDismissRequest = { item.onDismiss() },
            title = {
                Text(
                    text = item.title.build(),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Spacing_16dp),
                ) {
                    item.subtitle?.let {
                        Text(
                            text = item.subtitle.build(),
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    item.onTextFieldValueChanged?.let { onValueChanged ->
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Gray, shape = MaterialTheme.shapes.medium),
                            value = item.textFieldValue.value ?: "",
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                            ),
                            shape = MaterialTheme.shapes.medium,
                            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Gray),
                            placeholder = {
                                Text(
                                    text = item.textFieldPlaceHolderText.build(),
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            },
                            onValueChange = { onValueChanged(it) },
                        )
                    }
                }
            },
            dismissButton = {
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(color = MaterialTheme.colorScheme.primary, shape = shape)
                        .clickable { item.onDismiss() }
                        .padding(horizontal = Spacing_16dp, vertical = Spacing_12dp),
                    text = item.dismissButtonText.build(),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                )
            },
            confirmButton = {
                item.onConfirm?.let { onConfirm ->
                    Text(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .background(color = MaterialTheme.colorScheme.primary, shape = shape)
                            .drawBehind {
                                if (!item.isConfirmEnabled.value) {
                                    drawRoundShapedRect(color = Color.White.copy(alpha = 0.3f), shape = shape)
                                }
                            }
                            .clickable(item.isConfirmEnabled.value) { onConfirm() }
                            .padding(horizontal = Spacing_16dp, vertical = Spacing_12dp),
                        text = item.confirmButtonText.build(),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    )
                }
            },
        )
    }
}
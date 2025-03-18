package com.polly.housecowork.ui.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography


@Composable
fun HCWAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    titleText: String,
    contentText: String
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(
                    text = "Confirm", color = LocalColorScheme.current.onPrimary
                )
            }
        },
        title = {
            Text(
                text = titleText,
                style = LocalTypography.current.headlineSmall,
                color = LocalColorScheme.current.onBackground
            )
        },
        text = {
            Text(
                text = contentText,
                style = LocalTypography.current.bodySmall,
                color = LocalColorScheme.current.onBackground
            )
        },
        containerColor = LocalColorScheme.current.background,
    )
}




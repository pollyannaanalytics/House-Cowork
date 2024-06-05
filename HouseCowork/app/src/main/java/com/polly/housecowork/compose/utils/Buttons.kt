package com.polly.housecowork.compose.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.polly.housecowork.ui.theme.HCWShapes
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography

@Composable
fun StandardButton(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        contentPadding = contentPaddingValues,
        shape = HCWShapes.medium,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(LocalColorScheme.current.primary),
    ) {
        Text(
            text = text,
            style = LocalTypography.current.titleSmall
        )
    }
}
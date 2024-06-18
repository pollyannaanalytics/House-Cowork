package com.polly.housecowork.ui.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.HCWShapes
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography

@Composable
fun StandardButton(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    enabled: Boolean = true,
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
        ,
        contentPadding = contentPaddingValues,
        shape = HCWShapes.medium,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(LocalColorScheme.current.primary),
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            style = textStyle,
            color = LocalColorScheme.current.onBackground
        )
    }
}

@Composable
@Preview
fun StandardButtonPreview() {
    StandardButton(
        text = "Button",
        onClick = {},
        contentPaddingValues = PaddingValues(16.dp),
        textStyle = LocalTypography.current.titleMedium
    )
}
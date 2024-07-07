package com.polly.housecowork.ui.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalShapes
import com.polly.housecowork.ui.theme.LocalTypography

@Composable
fun StandardButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonColor: Color = LocalColorScheme.current.primary,
    buttonShape: Shape = LocalShapes.current.medium,
    text: String,
    textStyle: TextStyle,
    textColor: Color = LocalColorScheme.current.onBackground,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        shape = buttonShape,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(buttonColor),
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun PrimaryMediumButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    textStyle: TextStyle
) {
    StandardButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        onClick = onClick,
        buttonShape = LocalShapes.current.medium
    )
}

@Composable
fun PositiveButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
) {
    StandardButton(
        modifier,
        text = text,
        textStyle = textStyle,
        textColor = Color.White,
        onClick = onClick,
        buttonShape = LocalShapes.current.small,
        buttonColor = LocalColorScheme.current.onPrimary
    )
}

@Composable
fun NegativeButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
) {
    StandardButton(
        modifier,
        text = text,
        textStyle = textStyle,
        textColor = LocalColorScheme.current.onBackground,
        onClick = onClick,
        buttonShape = LocalShapes.current.small,
        buttonColor = LocalColorScheme.current.secondary
    )
}
package com.polly.housecowork.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.R
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
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    textStyle: TextStyle = LocalTypography.current.headlineLarge
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
    textStyle: TextStyle = LocalTypography.current.labelSmall,
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
    textStyle: TextStyle = LocalTypography.current.labelSmall,
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


@Composable
fun SeeMoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier
            .background(LocalColorScheme.current.primary),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "See more",
                style = LocalTypography.current.bodySmall,
                color = LocalColorScheme.current.onBackground
            )
            Icon(
                painter = painterResource(id = R.drawable.down_arrow_svgrepo_com__3_),
                contentDescription = "see more",
                tint = LocalColorScheme.current.onBackground,
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 8.dp)
                    .rotate(270f)
            )
        }
    }

}


@Preview
@Composable
fun PreviewButtons(){
    Column {
        StandardButton(text = "test", textStyle = LocalTypography.current.bodyMedium)
        SeeMoreButton()
        PrimaryMediumButton(text = "test", onClick = { /*TODO*/ })
        NegativeButton(text = "test", onClick = { /*TODO*/ })
    }

}
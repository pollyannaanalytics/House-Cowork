package com.polly.housecowork.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.polly.housecowork.ui.theme.LocalColorScheme

@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String? = null,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier.clip(CircleShape).background(LocalColorScheme.current.onBackground).aspectRatio(1f),
        contentScale = ContentScale.Crop
    )
}

@Composable
@Preview
fun CircularImagePreview() {
    CircularImage(
        imageUrl = "https://www.w3schools.com/w3images/avatar2.png",
        contentDescription = "profile image"
    )
}

package com.polly.housecowork.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun Avatar(modifier: Modifier = Modifier, imageURL: String = "") {
    if (imageURL.isNotEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageURL)
                .crossfade(true)
                .build(),
            contentDescription = "avatar",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Icon(
            Icons.Rounded.AccountCircle,
            contentDescription = "default avatar",
            modifier = modifier,
        )
    }
}

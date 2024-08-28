package com.polly.housecowork.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.polly.housecowork.dataclass.ProfileInfo

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    profileInfo: ProfileInfo,
    onClick: (ProfileInfo) -> Unit
) {
    if (profileInfo.avatar.isNotEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(profileInfo.avatar)
                .crossfade(true)
                .build(),
            contentDescription = "avatar",
            modifier = modifier.clickable { onClick(profileInfo) },
            contentScale = ContentScale.Crop,
        )
    } else {
        Icon(
            Icons.Rounded.AccountCircle,
            contentDescription = "default avatar",
            modifier = modifier,
        )
    }
}

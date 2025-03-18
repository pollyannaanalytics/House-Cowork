package com.polly.housecowork.ui.utils.compose

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.polly.housecowork.ui.theme.LocalColorScheme


sealed interface AvatarState {
    data object Edit : AvatarState
    data class View(val url: String) : AvatarState
}


@Composable
fun AvatarImage(
    modifier: Modifier = Modifier,
    chosenPhotoUri: Uri?,
    avatarState: AvatarState,
    onUploadPhotoClick: () -> Unit,
) {

    when (avatarState) {
        is AvatarState.Edit -> {
            EditAvatar(
                modifier = modifier,
                onUploadPhotoClick = onUploadPhotoClick,
                chosenPhotoUri = chosenPhotoUri,
            )
        }
        is AvatarState.View -> {
            ImageAvatar(
                modifier = modifier,
                imageUrl = avatarState.url,
                contentDescription = "profile image",
            )

        }
    }
}

@Composable
fun EditAvatar(
    modifier: Modifier = Modifier,
    chosenPhotoUri: Uri?,
    onUploadPhotoClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .clickable { onUploadPhotoClick() }
            .clip(CircleShape)
            .background(LocalColorScheme.current.onBackground)
            .aspectRatio(1f),
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        chosenPhotoUri?.let {
            ImageAvatar(
                imageUrl = it.toString(),
                contentDescription = "profile image chosen to update ",
            )
        }
            ?: Icon(
            imageVector = Linked_camera,
            contentDescription = "edit profile button",
            tint = Color.White,
        )
    }

}

@Composable
fun ImageAvatar(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String? = null,
) {
    Log.d("image", imageUrl)
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier.clip(CircleShape)
            .background(LocalColorScheme.current.onBackground)
            .aspectRatio(1f),
        contentScale = ContentScale.Crop
    )
}



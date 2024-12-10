package com.polly.housecowork.compose.profile

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
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.utils.compose.Linked_camera


sealed interface ProfileAvatarState {
    data object Edit : ProfileAvatarState
    data class View(val url: String) : ProfileAvatarState
}


@Composable
fun ProfileAvatarImage(
    modifier: Modifier = Modifier,
    avatarState: ProfileAvatarState,
    onUploadPhotoClick: () -> Unit = {},
) {
    when (avatarState) {
        is ProfileAvatarState.Edit -> {
            EditAvatar(
                modifier = modifier,
                onUploadPhotoClick = onUploadPhotoClick,
            )
        }
        is ProfileAvatarState.View -> {
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
        Icon(
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
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier.clip(CircleShape)
            .background(LocalColorScheme.current.onBackground)
            .aspectRatio(1f),
        contentScale = ContentScale.Crop
    )
}

@Composable
@Preview
fun EditAvatarPreview() {
    EditAvatar()
    ProfileAvatarName(
        name = "John Doe",
        photoUrl = "https://www.w3schools.com/w3images/avatar2.png",
        isEditMode = true,
        onEditClick = {},
    )
}


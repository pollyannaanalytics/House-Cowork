package com.polly.housecowork.compose.profile

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.polly.housecowork.local.model.Profile
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalShapes
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.compose.AvatarImage
import com.polly.housecowork.ui.utils.compose.AvatarState
import com.polly.housecowork.ui.utils.compose.Bio
import com.polly.housecowork.viewmodel.ErrState

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    profile: Profile?,
    onBioChange: (String) -> Unit,
    isEditMode: Boolean = false,
    onEditClick: () -> Unit,
    chosenPhotoUri: Uri?,
    onUploadPhotoClick: () -> Unit,
    errState: ErrState,
    onNameChange: (String) -> Unit,
) {
    Scaffold(modifier) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(LocalColorScheme.current.background)
        ) {
                ProfileAvatarName(
                    name = profile?.name ?: "",
                    photoUrl = profile?.avatar ?: "",
                    isEditMode = isEditMode,
                    onEditClick = onEditClick,
                    errState = errState.nameErr,
                    onProfileNameChange = onNameChange,
                    onUploadPhotoClick = onUploadPhotoClick,
                    chosenPhotoUri = chosenPhotoUri
                )
                Bio(
                    bio = profile?.bio ?: "",
                    isEditMode = isEditMode,
                    onBioChange = onBioChange,
                    errState = errState.bioErr
                )
        }

    }
}

@Composable
fun ProfileAvatarName(
    modifier: Modifier = Modifier,
    name: String,
    photoUrl: String,
    chosenPhotoUri: Uri?,
    isEditMode: Boolean = false,
    onProfileNameChange: (String) -> Unit,
    onUploadPhotoClick: () -> Unit,
    onEditClick: () -> Unit,
    errState: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        AvatarImage(
            modifier = Modifier
                .clickable { onEditClick() }
                .padding(16.dp)
                .fillMaxWidth(0.3f),
            avatarState = if (isEditMode)
                AvatarState.Edit else AvatarState.View(photoUrl),
            onUploadPhotoClick = onUploadPhotoClick,
            chosenPhotoUri = chosenPhotoUri,

        )
        ProfileName(
            name = name,
            onEditClick = onEditClick,
            isEditMode = isEditMode,
            onNameChange = onProfileNameChange,
            errState = errState
        )
    }
}


@Composable
fun ProfileName(
    name: String,
    onEditClick: () -> Unit = {},
    isEditMode: Boolean = false,
    onNameChange: (String) -> Unit = {},
    errState: Boolean = false
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        ProfileNameContent(
            isEditMode = isEditMode,
            name = name,
            onNameChange = onNameChange,
            errState = errState
        )
            EditButton(
                onClick = onEditClick,
                isEditMode = isEditMode
            )

    }
}

@Composable
fun ProfileNameContent(
    isEditMode: Boolean,
    name: String,
    onNameChange: (String) -> Unit = {},
    errState: Boolean = false
) {
    if (isEditMode) {
        ProfileNameEditTextField(
            name = name,
            onNameChange = onNameChange,
            errState = errState
        )
    } else {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = name,
            style = LocalTypography.current.headlineMedium
        )
    }
}

@Composable
fun ProfileNameEditTextField(
    name: String,
    onNameChange: (String) -> Unit = {},
    errState: Boolean = false
) {
    var nameState by remember { mutableStateOf(name) }

    BasicTextField(
        modifier = Modifier
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .widthIn(min = 50.dp)
            .border(
                1.dp,
                color = if (errState) Color.Red else LocalColorScheme.current.secondary,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
        value = nameState,
        textStyle = LocalTypography.current.headlineMedium,
        onValueChange = { value ->
            nameState = value
            onNameChange(value)
        },
    )
    if (errState) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = "*20 characters limited",
            style = LocalTypography.current.labelSmall,
            color = Color.Red
        )
    }

}

@Composable
fun EditButton(onClick: () -> Unit = {}, isEditMode: Boolean = false) {

    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = onClick,
        border = BorderStroke(1.dp, LocalColorScheme.current.secondary),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEditMode) LocalColorScheme.current.tertiary else Color.Transparent
        ),
        shape = LocalShapes.current.small
    ) {
        if (isEditMode) {
            Text(
                text = "Confirm",
                color = Color.White
            )
        } else {
            Text(
                text = "Edit",
                textAlign = TextAlign.Center,
                color = LocalColorScheme.current.secondary
            )
        }
    }
}






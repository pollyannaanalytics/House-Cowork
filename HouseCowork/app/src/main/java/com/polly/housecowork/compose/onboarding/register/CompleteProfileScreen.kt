package com.polly.housecowork.compose.onboarding.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.HCWAlertDialog
import com.polly.housecowork.ui.utils.HCWTextField
import com.polly.housecowork.ui.utils.PositiveButton
import com.polly.housecowork.ui.utils.compose.AvatarState
import com.polly.housecowork.ui.utils.compose.AvatarImage
import com.polly.housecowork.ui.utils.compose.Bio
import com.polly.housecowork.viewmodel.CompleteProfileViewModel

@Composable
fun CompleteProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: CompleteProfileViewModel = hiltViewModel(),
    onProfileComplete: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri ->
            viewModel.uploadProfilePhoto(imageUri)
        }
    }

    CompleteProfileContent(
        modifier = modifier,
        nickname = uiState.nickname,
        bio = uiState.bio,
        onNicknameChange = {
            viewModel.onNicknameChanged(it)
        },
        onBioChange = {
            viewModel.onBioChanged(it)
        },
        chosenPhotoUri = uiState.imageUri,
        avatarState = uiState.avatarState,
        onEditClick = {
            viewModel.onAvatarStateChanged(uiState.avatarState)
        },
        onUploadPhotoClick = {
            pickImageLauncher.launch("image/*")
        },
        isBioErr = !uiState.isBioValid,
        isBankInfoClicked = uiState.isBankInfoClicked,
        bankCode = uiState.bankCode,
        onBankCodeChange = {
            viewModel.onBankCodeChanged(it)
        },
        bankAccount = uiState.bankAccount,
        onBankAccountChange = {
            viewModel.onBankAccountChanged(it)
        },
        onBankInfoChange = {
            viewModel.onBankInfoClickedChange()
        },
        onDoneClick = {
            viewModel.onProfileComplete()
            onProfileComplete()
        },
        onSkipClick = {
            viewModel.onProfileSkip()
            onProfileComplete()
        }
    )


}

@Composable
fun CompleteProfileContent(
    modifier: Modifier = Modifier,
    nickname: String,
    bio: String,
    onNicknameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    chosenPhotoUri: Uri?,
    avatarState: AvatarState,
    onEditClick: () -> Unit,
    onUploadPhotoClick: () -> Unit,
    isBioErr: Boolean,
    bankCode: String = "",
    onBankCodeChange: (String) -> Unit,
    bankAccount: String = "",
    onBankAccountChange: (String) -> Unit,
    onBankInfoChange: () -> Unit,
    isBankInfoClicked: Boolean,
    onDoneClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Optional Infomation",
            style = LocalTypography.current.titleMedium,
            color = LocalColorScheme.current.onBackground
        )
        AvatarImage(
            modifier = Modifier
                .height(150.dp)
                .padding(top = 32.dp, bottom = 16.dp),
            chosenPhotoUri = chosenPhotoUri,
            avatarState = avatarState,
            onUploadPhotoClick = onUploadPhotoClick,
        )

        HCWTextField(
            modifier = Modifier.padding(16.dp),
            value = nickname,
            onValueChange = onNicknameChange,
            hint = "Your nick name",
        )

        BankAccountTextField(
            modifier = Modifier.padding(16.dp),
            bankCode = bankCode,
            bankAccount = bankAccount,
            onBankCodeChange = onBankCodeChange,
            onBankAccountChange = onBankAccountChange,
            onBankInfoChange = onBankInfoChange
        )

        Bio(
            bio = bio,
            isEditMode = true,
            onBioChange = onBioChange,
            errState = isBioErr
        )

        PositiveButton(
            modifier = Modifier.padding(top = 32.dp),
            text = "Done",
            textStyle = LocalTypography.current.titleMedium,
            onClick = onDoneClick
        )

        Text(
            modifier = Modifier
                .padding(top = 32.dp)
                .clickable {
                    onSkipClick()
                },
            text = "Complete it later",
            style = LocalTypography.current.bodySmall,
            color = LocalColorScheme.current.onSecondary
        )

        if (isBankInfoClicked) {
            HCWAlertDialog(
                onDismissRequest = onBankInfoChange,
                titleText = "Why we need your bank info?",
                contentText = "Your room mate can transfer money to you more easily, but this is okay if you don't want to input it."
            )
        }
    }
}

@Composable
fun BankAccountTextField(
    modifier: Modifier = Modifier,
    bankCode: String,
    bankAccount: String,
    onBankCodeChange: (String) -> Unit,
    onBankAccountChange: (String) -> Unit,
    onBankInfoChange: () -> Unit,

    ) {

    Column(modifier = modifier) {
        Row {
            Text(
                text = "Bank Info",
                style = LocalTypography.current.bodyLarge
            )

            IconButton(
                modifier = Modifier
                    .width(24.dp)
                    .aspectRatio(1f)
                    .padding(start = 4.dp),
                onClick = onBankInfoChange
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.Info,
                    contentDescription = "why we need input bank info",
                    tint = LocalColorScheme.current.onSecondary
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HCWTextField(
                modifier = Modifier.weight(1f),
                value = bankCode,
                onValueChange = onBankCodeChange,
                hint = "Bank Code" ,
                hintStyle = LocalTypography.current.bodySmall,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )
            HCWTextField(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 4.dp),
                value = bankAccount,
                onValueChange = onBankAccountChange,
                hint = "Bank Account",
            )
        }
    }
}


@Preview
@Composable
fun CompleteProfileScreenPreview() {
    CompleteProfileScreen(
        modifier = Modifier.background(LocalColorScheme.current.background),
        onProfileComplete = {}
    )
}

package com.polly.housecowork.compose.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.viewmodel.ProfileViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateOnClick: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel(),
) {

    val profileUiState by viewModel.profileUiState.collectAsStateWithLifecycle()
    val profileEditModeState by viewModel.profileEditModeState.collectAsStateWithLifecycle()
    val errState by viewModel.errState.collectAsStateWithLifecycle()
    val calendarState by viewModel.calendarState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri ->
            viewModel.uploadProfilePhoto(imageUri)
        }
    }





    ProfileContent(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .background(LocalColorScheme.current.background),
        profileInfo = profileUiState.profileInfo,
        tasks = profileUiState.assignedTasks,
        isEditMode = profileEditModeState.isEditMode,
        onBackMonthClick = { viewModel.getPreviousMonth() },
        onNextMonthClick = { viewModel.getNextMonth() },
        calendarUiModel = calendarState.monthData,
        calendarMonthTitle = calendarState.monthTitle,
        onEditClick = { viewModel.changeEditMode() },
        errState = errState,
        onNameChange = { name -> viewModel.updateProfileName(name) },
        onBioChange = { bio -> viewModel.updateProfileBio(bio) },
        onUploadPhotoClick = { pickImageLauncher.launch("image/*") },
        chosenPhotoUri = profileEditModeState.imageUri
    )
}






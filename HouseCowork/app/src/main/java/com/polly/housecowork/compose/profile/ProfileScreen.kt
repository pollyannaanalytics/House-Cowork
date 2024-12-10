package com.polly.housecowork.compose.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.viewmodel.ProfileViewModel



data class ErrState(
    var nameErr: Boolean = false,
    var bioErr: Boolean = false
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateOnClick: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel(),
) {

    val profileInfo by viewModel.profileInfo.collectAsStateWithLifecycle()
    val tasks by viewModel.assignedTasks.collectAsState()
    val isEditMode by viewModel.isEditMode.collectAsState()

    val calendarDate by viewModel.calendarUiModel.collectAsState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )

    val errState by remember{
        derivedStateOf {
            profileInfo?.let {
                return@derivedStateOf ErrState(
                    nameErr = it.name.length > 20,
                    bioErr = it.bio.length > 200
                )
            }?: ErrState()

        }
    }



    LaunchedEffect(profileInfo) {
        profileInfo?.let {
            viewModel.getAssignedTasks(it.id)
        }
    }

    ProfileContent(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .background(LocalColorScheme.current.background),
        profileInfo = profileInfo,
        tasks = tasks,
        isEditMode = isEditMode,
        onBackClick = navigateOnClick,
        calendarUiModel = calendarDate,
        onEditClick = { viewModel.changeEditMode() },
        errState = errState,
        onNameChange = { name -> viewModel.updateProfileName(name) },
        onBioChange = { bio -> viewModel.updateProfileBio(bio) },
    )
}






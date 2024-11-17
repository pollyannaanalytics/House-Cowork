package com.polly.housecowork.compose.createtask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.compose.createtask.content.CreateTaskContent
import com.polly.housecowork.utils.ComposeUtils
import com.polly.housecowork.viewmodel.CreateTaskViewModel

@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateTaskViewModel = hiltViewModel(),
    navigateOnClick: () -> Unit = {}
) {
    val taskUiState by viewModel.taskUiState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val isPublic by viewModel.isPublic.collectAsState()


    CreateTaskContent(
        modifier = modifier
            .padding(ComposeUtils.Padding)
            .fillMaxWidth(),
        taskUiState = taskUiState,
        onTitleChange = { viewModel.setTaskTitle(it) },
        onDueTimeChange = { hour, min -> viewModel.setDueTime(hour, min)},
        onAssigneeClick = { viewModel.setAssignedUser(it) },
        onPublicChange = { viewModel.setPublicLevel(it) },
        isPublic = isPublic,
        allUserNames = taskUiState.selectableUsers,
        onCancelClick = { navigateOnClick() },
        onDoneClick = { viewModel.checkFinish() },
        errorState = errorState,
        onDismissTimeError = { viewModel.clearDueTimeError() }
    )
}



@Preview
@Composable
fun PreviewCreateTaskScreen() {
}
package com.polly.housecowork.compose.createtask

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.compose.createtask.content.CreateTaskContent
import com.polly.housecowork.utils.ComposeUtils
import com.polly.housecowork.viewmodel.CreateTaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateTaskViewModel = hiltViewModel(),
    navigateOnClick: () -> Unit = {}
) {
    val taskUiState by viewModel.taskUiState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val isPublic by viewModel.isPublic.collectAsState()
    val calendarState by viewModel.calendarState.collectAsState()
    var showTimePickerSheet by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )
    val scrollState = rememberScrollState()

    LaunchedEffect(taskUiState.shouldScrollTop) {
        if (taskUiState.shouldScrollTop) {
            scrollState.animateScrollTo(0)
        }
    }

    LaunchedEffect(errorState.titleError) {
        if (errorState.titleError) {
            vibrator.cancel()
            val effect = VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_TICK)
            vibrator.vibrate(effect)
        }
    }

    CreateTaskContent(
        modifier = modifier
            .padding(ComposeUtils.Padding)
            .fillMaxSize(),
        scrollBehavior = scrollBehavior,
        scrollState = scrollState,
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
        onDismissTimeError = { viewModel.clearDueTimeError() },
        currentMonthTitle = calendarState.monthTitle,
        dates = calendarState.monthData.visibleDates,
        onBackClick = { viewModel.getPreviousMonth() },
        onNextClick = { viewModel.getNextMonth() },
        showTimePickerSheet = showTimePickerSheet,
        onTimePickerDismiss = { showTimePickerSheet = true },
    )
}

package com.polly.housecowork.compose.createtask.content

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.polly.housecowork.compose.createtask.content.timepicker.TimePickerBottomSheet
import com.polly.housecowork.compose.createtask.dataclass.ErrorState
import com.polly.housecowork.compose.createtask.dataclass.TaskState
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.HCWAlertDialog
import com.polly.housecowork.ui.utils.HCWDatePicker
import com.polly.housecowork.ui.utils.NegativeButton
import com.polly.housecowork.ui.utils.PositiveButton
import com.polly.housecowork.utils.ComposeUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskContent(
    modifier: Modifier = Modifier,
    taskUiState: TaskState,
    errorState: ErrorState,
    onTitleChange: (String) -> Unit,
    onDueTimeChange: (Int, Int) -> Unit,
    onAssigneeClick: (String) -> Unit,
    allUserNames: List<String>,
    onDismissTimeError: () -> Unit,
    isPublic: Boolean,
    onPublicChange: (Boolean) -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
){
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = taskUiState.dueTime
    )
    val focusManager = LocalFocusManager.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )

    var showTimePickerSheet by remember { mutableStateOf(false) }
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

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

    Scaffold(
        modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .clickable { focusManager.clearFocus() },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = innerPadding.calculateTopPadding())
                .background(LocalColorScheme.current.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CreateTaskInputSection(
                title = taskUiState.title,
                isError = errorState.titleError,
                onTitleChange = { onTitleChange(it) },
                onClearFocus = { focusManager.clearFocus() }
            )
            AssignUserSection(
                modifier = Modifier.fillMaxWidth().padding(ComposeUtils.ContentPadding),
                allUserNames = allUserNames,
                isPublic = isPublic,
                onAssigneeClick = { onAssigneeClick(it) },
                onPublicChange = { onPublicChange(it) }
            )

            TaskDueTimeSettingSection(
                datePickerState = datePickerState,
                dueHour = taskUiState.dueHour,
                dueMinute = taskUiState.dueMinute,
                onTimePickerClick = {
                    showTimePickerSheet = enableTimePicker(showTimePickerSheet)
                }
            )

            ActionButtonSection(
                modifier = Modifier.fillMaxWidth(),
                onCancelClick = onCancelClick,
                onDoneClick = onDoneClick
            )

            HandleDialogs(
                showTimePickerSheet = showTimePickerSheet,
                showDueTimeError = errorState.dueTimeError,
                onTimeSelected = { hour, minute ->
                    onDueTimeChange(hour, minute)
                },
                onTimePickerDismiss = { showTimePickerSheet = false },
                onDismissTimeError = onDismissTimeError
            )
        }
    }
}

@Composable
private fun CreateTaskInputSection(
    modifier: Modifier = Modifier,
    title: String,
    isError: Boolean,
    onTitleChange: (String) -> Unit,
    onClearFocus: () -> Unit,
){
    CreateTaskTextField(
        modifier = modifier,
        onTextChange = onTitleChange,
        defaultText = title,
        isTaskEmptyError = isError,
        clearFocus = onClearFocus
    )
    if (isError) {
        Text(
            text = "Task title cannot be empty",
            style = LocalTypography.current.bodyMedium,
            color = LocalColorScheme.current.error,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

@Composable
private fun AssignUserSection(
    modifier: Modifier = Modifier,
    allUserNames: List<String>,
    onAssigneeClick: (String) -> Unit,
    isPublic: Boolean,
    onPublicChange: (Boolean) -> Unit,
){

    AssignDrawer(
        modifier = modifier,
        itemList = { allUserNames },
        onAssigneeClick = onAssigneeClick
    )

    PublicSwitch(
        isPublic = isPublic,
        onPublicChange = onPublicChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskDueTimeSettingSection(
    datePickerState: DatePickerState,
    dueHour: Int,
    dueMinute: Int,
    onTimePickerClick: () -> Unit
){
    HCWDatePicker(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        datePickerState = { datePickerState }
    )

    TaskDueTime(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        dueHour = { dueHour },
        dueMinute = { dueMinute },
        showTimePicker = onTimePickerClick
    )
}

@Composable
private fun ActionButtonSection(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        NegativeButton(
            text = "Cancel",
            textStyle = LocalTypography.current.labelSmall,
            onClick = onCancelClick
        )
        Spacer(modifier = Modifier.padding(16.dp))
        PositiveButton(
            text = "Done",
            textStyle = LocalTypography.current.labelSmall,
            onClick = onDoneClick
        )
    }

}
@Composable
private fun HandleDialogs(
    showTimePickerSheet: Boolean,
    showDueTimeError: Boolean,
    onTimeSelected: (Int, Int) -> Unit,
    onTimePickerDismiss: () -> Unit,
    onDismissTimeError: () -> Unit
) {
    if (showTimePickerSheet) {
        TimePickerBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            onTimeSelected = onTimeSelected,
            showBottomSheet = { show -> if (!show) onTimePickerDismiss() }
        )
    }

    if (showDueTimeError) {
        HCWAlertDialog(
            titleText = "Oops",
            contentText = "You are choosing a past date,\nstill wanna continue?",
            onDismissRequest = onDismissTimeError
        )
    }
}



private fun enableTimePicker(showTickPickerBottomSheet: Boolean): Boolean {
    return !showTickPickerBottomSheet
}

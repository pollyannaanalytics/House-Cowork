package com.polly.housecowork.compose.createtask.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.polly.housecowork.compose.createtask.content.timepicker.TimePickerBottomSheet
import com.polly.housecowork.compose.createtask.dataclass.ErrorState
import com.polly.housecowork.compose.createtask.dataclass.TaskState
import com.polly.housecowork.dataclass.CalendarUiModel
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.HCWAlertDialog
import com.polly.housecowork.ui.utils.HCWCalendar
import com.polly.housecowork.ui.utils.NegativeButton
import com.polly.housecowork.ui.utils.PositiveButton
import com.polly.housecowork.utils.ComposeUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskContent(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    scrollState: androidx.compose.foundation.ScrollState,
    showTimePickerSheet: Boolean,
    onTimePickerDismiss: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    currentMonthTitle: String,
    dates: List<CalendarUiModel.Date>,
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
) {
    Scaffold(
        modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .background(LocalColorScheme.current.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CreateTaskInputSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ComposeUtils.ContentPadding),
                title = taskUiState.title,
                isError = errorState.titleError,
                onTitleChange = { onTitleChange(it) },
            )
            AssignUserSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ComposeUtils.ContentPadding),
                allUserNames = allUserNames,
                isPublic = isPublic,
                onAssigneeClick = { onAssigneeClick(it) },
                onPublicChange = { onPublicChange(it) }
            )

            TaskDueTimeSettingSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ComposeUtils.ContentPadding),
                onTimePickerClick = {  },
                dueHour = taskUiState.dueHour,
                dueMinute = taskUiState.dueMinute,
                currentMonthTitle = currentMonthTitle,
                dates = dates,
                onBackClick = onBackClick,
                onNextClick = onNextClick
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
                onTimePickerDismiss = onTimePickerDismiss,
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
    onTitleChange: (String) -> Unit
) {
    CreateTaskTextField(
        modifier = modifier,
        onValueChange = onTitleChange,
        value = title,
        isTaskEmptyError = isError,
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
) {

    AssignDrawer(
        modifier = modifier,
        itemList = allUserNames,
        onAssigneeClick = onAssigneeClick
    )

    PublicSwitch(
        modifier = modifier,
        isPublic = isPublic,
        onPublicChange = onPublicChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskDueTimeSettingSection(
    modifier: Modifier = Modifier,
    currentMonthTitle: String,
    dates: List<CalendarUiModel.Date>,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    dueHour: Int,
    dueMinute: Int,
    onTimePickerClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Select due date",
            style = LocalTypography.current.titleMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth().padding(ComposeUtils.ContentPadding)
        )
        HCWCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ComposeUtils.ContentPadding),
            onClick = { },
            currentMonthTitle = currentMonthTitle,
            dates = dates,
            onBackClick = onBackClick,
            onForwardClick = onNextClick
        )

        TaskDueTime(
            dueHour = { dueHour },
            dueMinute = { dueMinute },
            showTimePicker = onTimePickerClick
        )
    }

}

@Composable
private fun ActionButtonSection(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
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


package com.polly.housecowork.compose.createtask

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.compose.createtask.datepicker.HCWDatePicker
import com.polly.housecowork.compose.createtask.timepicker.TimePickerBottomSheet
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.HCWAlertDialog
import com.polly.housecowork.ui.utils.NegativeButton
import com.polly.housecowork.ui.utils.PositiveButton
import com.polly.housecowork.viewmodel.CreateTaskViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateTaskViewModel = hiltViewModel(),
    navigateOnClick: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = viewModel.dueTime.collectAsState().value
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )
    var showTickPickerBottomSheet by remember {
        mutableStateOf(false)
    }
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator


    val scrollState = rememberScrollState()
    val shouldScrollTop by viewModel.shouldScrollTop.collectAsState()
    val assignedUser by viewModel.assignedUser.collectAsState()
    val allUsers by viewModel.allUsers.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    val dueHour by viewModel.dueHour.collectAsState()
    val dueMinute by viewModel.dueMinute.collectAsState()

    LaunchedEffect(shouldScrollTop) {
        if (shouldScrollTop) {
            scrollState.animateScrollTo(0)
        }
    }

    LaunchedEffect(errorState.titleError) {
        if (errorState.titleError) {
            vibrator.cancel()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_TICK)
                vibrator.vibrate(effect)
            } else {
                vibrator.vibrate(100)
            }
        }
    }

    Scaffold(
        modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .clickable { focusManager.clearFocus() },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalColorScheme.current.background,
                    titleContentColor = LocalColorScheme.current.onBackground
                ),
                navigationIcon = {
                    IconButton(onClick = { navigateOnClick() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            modifier = Modifier.fillMaxHeight(),
                            tint = LocalColorScheme.current.onBackground,
                        )
                    }

                },
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Create a Task",
                        style = LocalTypography.current.titleSmall,
                    )

                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxHeight()
                        )
                    }

                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = innerPadding.calculateTopPadding())
                .background(LocalColorScheme.current.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CreateTaskTextField(
                onTextChange = { titleChange ->
                    viewModel.clearTaskEmptyError()
                    viewModel.setTaskTitle(titleChange)
                },
                isTaskEmptyError = errorState.titleError ,
                clearFocus = { focusManager.clearFocus() }
            )
            if (errorState.titleError) {
                Text(
                    text = "Task title cannot be empty",
                    style = LocalTypography.current.bodyMedium,
                    color = LocalColorScheme.current.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
            AssignDrawer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                itemList = { allUsers },
                selectedUserName = { assignedUser?.name },
                onAssigneeClick = {
                    viewModel.setAssignedUser(it)
                }
            )
            HCWDatePicker(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                datePickerState = { datePickerState },
            )

            TaskDueTime(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                dueHour = { dueHour },
                dueMinute = { dueMinute }
            ) {
                showTickPickerBottomSheet = enableTimePicker(showTickPickerBottomSheet)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                NegativeButton(
                    text = "Cancel",
                    textStyle = LocalTypography.current.labelSmall
                ) {
                    navigateOnClick()
                }
                Spacer(
                    modifier = Modifier
                        .padding(16.dp)
                )
                PositiveButton(
                    text = "Done",
                    textStyle = LocalTypography.current.labelSmall
                ) {
                    datePickerState.selectedDateMillis?.let { viewModel.setDueDate(it) }
                    viewModel.checkFinish()
                }
            }

            if (showTickPickerBottomSheet) {
                TimePickerBottomSheet(
                    modifier = Modifier.fillMaxWidth(),
                    onTimeSelected = { hour, minute ->
                        viewModel.setDueClock(hour, minute)
                    },
                    showBottomSheet = {
                        showTickPickerBottomSheet = it
                    }
                )
            }
            if (errorState.dueTimeError) {
                HCWAlertDialog(
                    titleText = "Oops",
                    contentText = "You are choosing a past date,\n" +
                            "still wanna continue?",
                    onDismissRequest = {
                        viewModel.clearDueTimeError()
                    }
                )
            }

        }
    }
}

private fun enableTimePicker(showTickPickerBottomSheet: Boolean): Boolean {
    return !showTickPickerBottomSheet
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewCreateTaskScreen() {
    CreateTaskScreen()
}
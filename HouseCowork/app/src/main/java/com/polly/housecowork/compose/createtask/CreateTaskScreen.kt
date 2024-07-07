package com.polly.housecowork.compose.createtask

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.compose.createtask.datepicker.HCWDatePicker
import com.polly.housecowork.compose.createtask.timepicker.TimePickerBottomSheet
import com.polly.housecowork.dataclass.UserInfo
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.NegativeButton
import com.polly.housecowork.ui.utils.PositiveButton
import java.time.Instant


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(modifier: Modifier = Modifier) {
    val testUsers = listOf(
        UserInfo(0L, "User 1", "email", "url"),
        UserInfo(1L, "User 2", "email", "url"),
        UserInfo(2L, "User 3", "email", "url"),
    )
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )
    var showTickPickerBottomSheet by remember {
        mutableStateOf(false)
    }
    var scheduleTime by remember {
        mutableLongStateOf(0L)
    }

    Scaffold(
        modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalColorScheme.current.background,
                    titleContentColor = LocalColorScheme.current.onBackground
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.fillMaxHeight()

                    )
                },
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Create a Task",
                        style = LocalTypography.current.titleSmall,
                    )

                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Close",
                        modifier = Modifier.fillMaxHeight()
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = innerPadding.calculateTopPadding())
                .background(LocalColorScheme.current.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CreateTaskTextField()
            AssignDrawer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                itemList = testUsers.map { it.name },
                onItemClick = {}
            )
            HCWDatePicker(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                datePickerState = datePickerState,
            )

            TaskDueTime(
                modifier = Modifier.padding(16.dp)
            ) { showTimePicker ->
                showTickPickerBottomSheet = showTimePicker
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                NegativeButton(
                    text = "Cancel",
                    textStyle = LocalTypography.current.labelSmall) {
                }
                Spacer(
                    modifier = Modifier
                        .padding(16.dp)
                )
                PositiveButton(
                    text = "Done",
                    textStyle = LocalTypography.current.labelSmall) {

                }
            }

            if (showTickPickerBottomSheet) {
                TimePickerBottomSheet(
                    modifier = Modifier.fillMaxWidth(),
                    selectedDate = { datePickerState.selectedDateMillis },
                    onTimeSelected = {
                        scheduleTime = it
                    },
                    showBottomSheet = {
                        showTickPickerBottomSheet = it
                    }
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewCreateTaskScreen() {
    CreateTaskScreen()
}
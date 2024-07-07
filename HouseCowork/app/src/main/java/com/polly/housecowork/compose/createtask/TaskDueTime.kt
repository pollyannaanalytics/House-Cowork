package com.polly.housecowork.compose.createtask

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun TaskDueTime(
    modifier: Modifier = Modifier,
    scheduledDueTime: Long = 0L,
    showTimePicker: (Boolean) -> Unit = {}
) {
    val context = LocalContext.current
    var dueTimeState by remember {
        mutableLongStateOf(0L)
    }
    var dueHourState by remember {
        mutableLongStateOf(0L)
    }
    var dueMinuteState by remember {
        mutableLongStateOf(0L)
    }
    var enableTimePicker by remember {
        mutableStateOf(false)
    }

    val simpleHourFormat = SimpleDateFormat("HH")

    if (scheduledDueTime == 0L) {
        dueTimeState = System.currentTimeMillis()
        val currentDate = Date(dueTimeState)
        dueHourState = simpleHourFormat.format(currentDate).toLong()
        dueMinuteState = (dueTimeState / 1000 / 60 % 60)
    } else {
        dueTimeState = scheduledDueTime
        dueHourState = simpleHourFormat.format(scheduledDueTime).toLong()
        dueMinuteState = (dueTimeState / 1000 / 60 % 60)
    }
    Row(
        modifier = modifier.clickable {
            enableTimePicker = !enableTimePicker
            showTimePicker(enableTimePicker)
        },
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .background(LocalColorScheme.current.surfaceTint)
                .padding(8.dp),
            text = if (dueHourState < 10L) "0$dueHourState" else dueHourState.toString(),
            style = LocalTypography.current.displayLarge,
            color = LocalColorScheme.current.onBackground,
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            text = ":",
            style = LocalTypography.current.headlineLarge,
            color = LocalColorScheme.current.onBackground
        )
        Text(
            modifier = Modifier
                .background(LocalColorScheme.current.surfaceTint)
                .padding(8.dp),
            text = if (dueMinuteState < 10L) "0$dueMinuteState" else dueMinuteState.toString(),
            style = LocalTypography.current.displayLarge,
            color = LocalColorScheme.current.onBackground,

        )
    }

}

@Preview
@Composable
fun TaskDueTimePreview() {
    TaskDueTime(
        modifier = Modifier,
    )
}
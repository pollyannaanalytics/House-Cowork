package com.polly.housecowork.compose.createtask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography

@Composable
fun TaskDueTime(
    modifier: Modifier = Modifier,
    dueHour: () -> Int,
    dueMinute: () -> Int,
    showTimePicker: () -> Unit = {}
) {
    Row(
        modifier = modifier.clickable {
            showTimePicker()
        },
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = if (dueHour() < 10) "0${dueHour()}" else dueHour().toString(),
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
                .padding(8.dp),
            text = if (dueMinute() < 10) "0${dueMinute()}" else dueMinute().toString(),
            style = LocalTypography.current.displayLarge,
            color = LocalColorScheme.current.onBackground,

        )
    }

}


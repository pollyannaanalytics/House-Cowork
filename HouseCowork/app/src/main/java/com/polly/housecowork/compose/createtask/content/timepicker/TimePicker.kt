package com.polly.housecowork.compose.createtask.content.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.polly.housecowork.dataclass.SelectorOptions
import com.polly.housecowork.dataclass.Time
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.utils.DateUtils
import com.polly.housecowork.utils.ComposeUtils.Companion.lightPallet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerBottomSheet(
    modifier: Modifier = Modifier,
    showBottomSheet: (Boolean) -> Unit,
    onTimeSelected: (Int, Int) -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = modifier,
        sheetState = modalBottomSheetState,
        containerColor = Color.White,
        onDismissRequest = {
            showBottomSheet(false)
        },
        content = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Select time",
                        style = LocalTypography.current.labelSmall
                    )
                    Text(
                        modifier = Modifier.clickable {
                            showBottomSheet(false)
                        },
                        text = "Done",
                        style = LocalTypography.current.labelSmall,
                        color = LocalColorScheme.current.secondary
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(3.dp, Color.Gray)
                )
                WheelTimePicker(
                    onTimeChanged = { hour, minute ->
                        onTimeSelected(hour, minute)
                    }
                )
            }
        }
    )
}

@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    offset: Int = 4,
    startTime: Time = Time(DateUtils.getCurrentHour(), DateUtils.getCurrentMinute()),
    onTimeChanged: (Int, Int) -> Unit = { _, _ -> },
    textSize: Int = 16
) {
    var selectedTime by remember { mutableStateOf(startTime) }
    val hours = mutableListOf<Int>().apply {
        for (hour in 0..23) {
            add(hour)
        }
    }
    val minutes = mutableListOf<Int>().apply {
        for (minute in 0..59) {
            add(minute)
        }
    }

    LaunchedEffect(selectedTime) {
        onTimeChanged(selectedTime.hour, selectedTime.minute)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        contentAlignment = Alignment.Center
    ) {
        val height = (textSize + 10).dp

        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            InfiniteWheel(
                modifier = Modifier.weight(3f),
                itemSize = DpSize(150.dp, height),
                rowOffset = offset,
                currentSelect = 0,
                onFocusItem = {
                    selectedTime = selectedTime.copy(hour = hours[it])
                },
                selectorOption = SelectorOptions().copy(
                    selectEffectEnabled = true,
                    enabled = false
                ),
                itemCount = hours.size,
                content = {
                    Text(
                        modifier = Modifier.width(50.dp),
                        text = if (hours[it] < 10) "0${hours[it]}" else "${hours[it]}",
                        textAlign = TextAlign.Center,
                        style = LocalTypography.current.titleSmall,
                        color = LocalColorScheme.current.onBackground
                    )
                })
            InfiniteWheel(
                modifier = Modifier.weight(3f),
                itemSize = DpSize(150.dp, height),
                rowOffset = offset,
                currentSelect = 0,
                onFocusItem = {
                    selectedTime = selectedTime.copy(minute = minutes[it])
                },
                selectorOption = SelectorOptions().copy(
                    selectEffectEnabled = true,
                    enabled = false
                ),
                itemCount = minutes.size,
                content = {
                    Text(
                        text = if (minutes[it] < 10) "0${minutes[it]}" else "${minutes[it]}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(100.dp),
                        style = LocalTypography.current.titleSmall,
                        color = LocalColorScheme.current.onBackground
                    )
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = lightPallet
                    )
                ),
        ) {}
        SelectorView(offset = offset)
    }
}

@Composable
@Preview
fun PreviewWheelTimePicker() {
    WheelTimePicker(
    )
}




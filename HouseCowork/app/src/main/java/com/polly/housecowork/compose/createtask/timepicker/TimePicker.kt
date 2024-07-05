package com.polly.housecowork.compose.createtask.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.timepicker.TimeFormat
import com.polly.housecowork.dataclass.SelectorOptions
import com.polly.housecowork.dataclass.Time
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.utils.DateUtils
import com.polly.housecowork.utils.lightPallet


@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    offset: Int = 4,
    selectorEffectEnabled: Boolean = true,
    startTime: Time = Time(DateUtils.getCurrentHour(), DateUtils.getCurrentMinute()),
    selectorEffectEnable: (Boolean) -> Unit,
    onTimeChanged: (Int, Int) -> Unit = { _, _ -> },
    textSize: Int = 16,
    timeFormat: Int = TimeFormat.CLOCK_12H
) {
    var selectedTime by remember { mutableStateOf(startTime) }

    val formats = listOf<String>("AM", "PM")
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
    val fontSize = maxOf(13, minOf(19, textSize))

    LaunchedEffect(selectedTime) {
        onTimeChanged(selectedTime.hour, selectedTime.minute)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(color = LocalColorScheme.current.background),
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
                        fontSize = fontSize.sp,
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
                selectorOption = SelectorOptions().copy(selectEffectEnabled = selectorEffectEnabled, enabled = false),
                itemCount = minutes.size,
                content = {
                    Text(
                        text = if(minutes[it] < 10) "0${minutes[it]}" else "${minutes[it]}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(100.dp),
                        fontSize = fontSize.sp,
                        color = LocalColorScheme.current.onBackground
                    )
                }
            )



            if (timeFormat == TimeFormat.CLOCK_12H) {
                InfiniteWheel(
                    modifier = Modifier.weight(2f),
                    itemSize = DpSize(150.dp, height),
                    currentSelect = 0,
                    itemCount = formats.size,
                    rowOffset = offset,
                    isEndless = false,
                    selectorOption = SelectorOptions().copy(
                        selectEffectEnabled = true,
                        enabled = false
                    ),
                    onFocusItem = {
                        selectedTime = selectedTime.copy(format = formats[it])
                    },
                    content = {
                        Text(
                            modifier = Modifier.width(50.dp),
                            text = formats[it],
                            textAlign = TextAlign.Center,
                            fontSize = fontSize.sp,
                            color = LocalColorScheme.current.onBackground
                        )
                    },
                )
            }
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
        SelectorView( offset = offset)
    }
}

@Composable
@Preview
fun PreviewWheelTimePicker() {
    WheelTimePicker(
        timeFormat = TimeFormat.CLOCK_12H,
        selectorEffectEnable = {}
    )
}




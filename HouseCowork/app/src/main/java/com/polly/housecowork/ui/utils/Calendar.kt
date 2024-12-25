package com.polly.housecowork.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.polly.housecowork.dataclass.CalendarUiModel
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.utils.ComposeUtils
import java.time.DayOfWeek

@Composable
@ExperimentalMaterial3Api
fun HCWCalendar(
    modifier: Modifier = Modifier,
    currentMonthTitle: String,
    dates: List<CalendarUiModel.Date>,
    onBackClick: () -> Unit,
    onForwardClick: () -> Unit,
    onClick: () -> Unit,
) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(ComposeUtils.Elevation),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.background(LocalColorScheme.current.surface)
        ) {
            Header(
                modifier = modifier.fillMaxWidth(),
                currentMonthTitle = currentMonthTitle,
                onBackClick = onBackClick,
                onForwardClick = onForwardClick
            )
            CalendarGrid(
                dates = dates,
                onClick = onClick,
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally),
            )
        }
    }

}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    currentMonthTitle: String,
    onBackClick: () -> Unit,
    onForwardClick: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        IconButton(onBackClick) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Previous",
                tint = LocalColorScheme.current.onBackground
            )
        }
        Text(
            text = currentMonthTitle,
            modifier = Modifier
                .align(Alignment.CenterVertically)
            ,
            style = LocalTypography.current.titleSmall,
            color = LocalColorScheme.current.onBackground
        )

        IconButton(onForwardClick) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Next",
                tint = LocalColorScheme.current.onBackground
            )
        }

    }
}


@Composable
private fun CalendarGrid(
    modifier: Modifier = Modifier,
    dates: List<CalendarUiModel.Date>,
    onClick: () -> Unit,
    startFromSunday: Boolean = false,
) {
    val weekdayFirstDay = dates.firstOrNull()?.date?.dayOfWeek?.value ?: 1
    val weekdays = if (startFromSunday) {
        listOf(7) + (1..6)  // Sunday is 7, then Monday(1) to Saturday(6)
    } else {
        (1..7).toList()     // Monday(1) to Sunday(7)
    }

    HCWCalendarUi(
        modifier
    ) {
        weekdays.forEach { dayNumber ->
            val dayOfWeek = DayOfWeek.of(dayNumber)
            WeekdayItem(weekday = CalendarUiModel.Date.DAY_MAPPING[dayOfWeek] ?: "")
        }

        val emptySpaces = if (startFromSunday) {
            if (weekdayFirstDay == 7) 0 else weekdayFirstDay - 1
        } else {
            weekdayFirstDay - 1
        }

        repeat(emptySpaces) {
            Spacer(modifier = Modifier)
        }
        dates.forEach { dateModel ->
            CalendarItem(
                calendarDate = dateModel,
                onClick = { onClick },
            )
        }
    }


}

@Composable
private fun HCWCalendarUi(
    modifier: Modifier = Modifier,
    horizontalGapDp: Dp = 2.dp,
    verticalGapDp: Dp = 2.dp,
    content: @Composable () -> Unit,
) {
    val horizontalGap = with(LocalDensity.current) {
        horizontalGapDp.roundToPx()
    }
    val verticalGap = with(LocalDensity.current) {
        verticalGapDp.roundToPx()
    }
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val totalWidthWithoutGap = constraints.maxWidth - (horizontalGap * 6)
        val singleWidth = totalWidthWithoutGap / 7

        val xPos: MutableList<Int> = mutableListOf()
        val yPos: MutableList<Int> = mutableListOf()
        var currentX = 0
        var currentY = 0
        measurables.forEach { _ ->
            xPos.add(currentX)
            yPos.add(currentY)
            if (currentX + singleWidth + horizontalGap > totalWidthWithoutGap) {
                currentX = 0
                currentY += singleWidth + verticalGap
            } else {
                currentX += singleWidth + horizontalGap
            }
        }

        val placeables: List<Placeable> = measurables.map { measurable ->
            measurable.measure(constraints.copy(maxHeight = singleWidth, maxWidth = singleWidth))
        }

        layout(
            width = constraints.maxWidth,
            height = currentY + singleWidth + verticalGap,
        ) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = xPos[index],
                    y = yPos[index],
                )
            }
        }
    }
}


@Composable
private fun CalendarItem(
    modifier: Modifier = Modifier,
    calendarDate: CalendarUiModel.Date,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(2.dp)
            .clip(CircleShape)
            .background(
                color = if (calendarDate.isSelected) LocalColorScheme.current.onPrimary else Color.Transparent
            )
            .clickable(onClick = onClick)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = calendarDate.date.dayOfMonth.toString(),
            color = if (calendarDate.isSelected) Color.White else LocalColorScheme.current.onBackground,
        )


    }

}

@Composable
private fun WeekdayItem(
    modifier: Modifier = Modifier,
    weekday: String
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = weekday.take(1),
            color = LocalColorScheme.current.onPrimary,
        )
    }

}


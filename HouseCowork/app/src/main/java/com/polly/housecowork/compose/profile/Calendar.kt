package com.polly.housecowork.compose.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.dataclass.AssignedTask
import com.polly.housecowork.dataclass.CalendarUiModel
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.TaskStatus
import java.time.LocalDate


@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    currentDateTitle: String,
    onBackClick: () -> Unit,
    onForwardClick: () -> Unit,
    dates: List<CalendarUiModel.Date>,
    tasks: List<AssignedTask>
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Header(
            currentDateTitle = currentDateTitle,
            onBackClick = onBackClick,
            onForwardClick = onForwardClick
        )
        Content(dates = dates)
        TaskContent(tasks = tasks)
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    currentDateTitle: String,
    onBackClick: () -> Unit,
    onForwardClick: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        IconButton(onBackClick) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Previous"
            )
        }
        Text(
            text = currentDateTitle,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = LocalTypography.current.titleSmall,
        )

        IconButton(onForwardClick) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Next",
            )
        }

    }
}

@Composable
fun Content(dates: List<CalendarUiModel.Date>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(
            items = dates.take(7),
            key = { it.date.dayOfMonth }
        ) { date ->
            ContentItem(date = date)
        }
    }

}

@Composable
fun ContentItem(date: CalendarUiModel.Date) {
    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(45.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (date.isSelected) LocalColorScheme.current.onSecondary else Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = LocalColorScheme.current.onBackground
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (date.isSelected) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(
                            color = Color.Red,
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.height(2.dp))
            }

            Text(
                text = date.day, // day "Mon", "Tue"
                style = LocalTypography.current.labelSmall,
                color = LocalColorScheme.current.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = date.date.dayOfMonth.toString(), // date "15", "16"
                style = LocalTypography.current.titleMedium,
                color = LocalColorScheme.current.onSecondary
            )
        }
    }
}

@Composable
fun TaskContent(modifier: Modifier = Modifier, tasks: List<AssignedTask>) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 150.dp)
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalColorScheme.current.background,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = LocalColorScheme.current.onBackground
        )
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = tasks,
                key = { it.id }
                ) { task ->
                TaskContentItem(task = task)
            }
        }


    }
}

@Composable
fun TaskContentItem(modifier: Modifier = Modifier, task: AssignedTask) {
    Row(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .align(Alignment.CenterVertically)
                .background(
                    color = Color.Red,
                    shape = CircleShape
                )
        )
        Text(
            text = task.dueTime,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 4.dp),
            style = LocalTypography.current.bodySmall,
            color = LocalColorScheme.current.onBackground
        )
        Text(
            text = task.title,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp),
            style = LocalTypography.current.bodySmall,
            color = LocalColorScheme.current.onBackground
        )

    }
}

@Composable
@Preview
fun HeaderPreview() {
    Calendar(
        currentDateTitle = "September 2021",
        onBackClick = {},
        onForwardClick = {},
        dates = listOf(
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now().plusDays(1),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now().plusDays(2),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now().plusDays(3),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now().plusDays(4),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now().plusDays(5),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now().plusDays(6),
                isSelected = false,
                isToday = false
            ),
        ),
        tasks = listOf(

        )
    )
}
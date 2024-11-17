package com.polly.housecowork.compose.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
    tasks: List<Task>
) {
    Column(modifier = modifier) {
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
    LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        items(count = 7) {
            ContentItem(date = dates[it])
        }
    }

}

@Composable
fun ContentItem(date: CalendarUiModel.Date) {
    Card(
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.cardColors(
            // background colors of the selected date
            // and the non-selected date are different
            containerColor = if (date.isSelected) {
                LocalColorScheme.current.onSecondary
            } else {
                Color.White
            }
        ),
        border = BorderStroke(
            width = 1.dp,
            color = LocalColorScheme.current.onBackground
        )
    ) {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                if (date.isSelected) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .align(Alignment.CenterVertically)
                            .background(
                                color = Color.Red,
                                shape = CircleShape
                            )
                    )
                }

                Text(
                    text = date.day, // day "Mon", "Tue"
                    style = LocalTypography.current.labelSmall,
                    color = LocalColorScheme.current.onPrimary
                )
            }

            Text(
                text = date.date.dayOfMonth.toString(), // date "15", "16"
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = LocalTypography.current.titleMedium,
                color = LocalColorScheme.current.onSecondary
            )
        }
    }
}

@Composable
fun TaskContent(modifier: Modifier = Modifier, tasks: List<Task>) {
    Card(
        modifier = modifier.fillMaxWidth().heightIn(min = 100.dp, max = 150.dp).padding(vertical = 8.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalColorScheme.current.background,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = LocalColorScheme.current.onBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn {
                 items(tasks) { task ->
                    TaskContentItem(task = task)
                }
            }
        }

    }
}

@Composable
fun TaskContentItem(modifier: Modifier = Modifier, task: Task){
    Row(
        modifier = modifier,
    ){
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
            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 4.dp),
            style = LocalTypography.current.bodySmall,
            color = LocalColorScheme.current.onBackground
        )
        Text(
            text = task.title,
            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 8.dp),
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
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
        ),
        tasks = listOf(
            Task(
                title = "Task 1",
                dueTime = "10:00",
                accessLevel = AccessLevel.PUBLIC,
               assigneeStatus = emptyList(),
                description = "Task 1 description",
                taskStatus = TaskStatus.OPEN,
                createdTime = System.currentTimeMillis(),
                updatedTime = System.currentTimeMillis(),
                dueDate = "2023-07-28T14:30:00Z",
                id = 1,
                owner = ProfileInfo(
                    name = "王大明",
                    nickName = "小明",
                    bio = "喜歡攝影和旅行的工程師fasdfasdfasdfasdfadsfasdfasdfasdfadsfasdfjalsjdfoiajfoiajwefoiawjfeowjeofijweiofjoiajefoaiwejfioawjeoifjoaiwjefiojawieofjioajfoiwjfiowjeofijwoiefrjiqoweiroqjwoeirjqoiewrjiqojeroiqjweiorjqoierjiowjreoijwiroe",
                    imageUrl = "https://i.pravatar.cc/150?img=1",
                    bankAccount = "012-345-678912",
                    email = "ming.wang@example.com",
                    updateTime = System.currentTimeMillis()
                )
            ),
        )
    )
}
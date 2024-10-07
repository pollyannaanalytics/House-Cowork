package com.polly.housecowork.compose.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.polly.housecowork.dataclass.AssigneeStatus
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.TaskStatus
import kotlin.math.absoluteValue

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecentTaskView(
    modifier: Modifier = Modifier,
    onTaskClick: (Task) -> Unit = {},
    pagerState: PagerState,
    tasksMap: Map<ToDoType, List<Task>>,
) {
    val currentPage by remember { mutableIntStateOf(0) }

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(state = pagerState) { page ->
            val tasksType = tasksMap.keys.toList()[page]
            val tasks = tasksMap[tasksType] ?: emptyList()
            SingleTaskCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        // Apply transformation
                        val scale = lerp(
                            0.8f,
                            1f,
                            1 - pageOffset.coerceIn(0f, 1f)
                        )

                        scaleX = scale
                        scaleY = scale

                        shape = RoundedCornerShape(16.dp)
                    },
                toDoTitle = tasksType.title,
                tasks = tasks,
                isExpired = false
            )
        }
        GreyDotIndicator(
            modifier = Modifier.padding(8.dp),
            pageCount = tasksMap.size,
            currentPage = currentPage
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SingleTaskCard(
    modifier: Modifier = Modifier,
    toDoTitle: String,
    tasks: List<Task>,
    isExpired: Boolean
) {

    ElevatedCard (
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp)
            .clip(RoundedCornerShape(16.dp))
            .drawBehind {
                val strokeWidth = 1 * density

                if (toDoTitle == ToDoType.EXPIRED.title) drawLine(
                    Color.LightGray,
                    Offset(0f, strokeWidth),
                    Offset(0f, size.height),
                    strokeWidth
                )

                if (toDoTitle == ToDoType.THREE_DAYS_FUTURE.title) {
                    drawLine(
                        Color.LightGray,
                        Offset(size.width, strokeWidth),
                        Offset(size.width, size.height),
                        strokeWidth
                    )
                }
            }
            .padding(8.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = LocalColorScheme.current.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = toDoTitle,
                style = LocalTypography.current.titleSmall,
                color = LocalColorScheme.current.onBackground
            )
            LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                items(tasks.size) { index ->
                    val taskItem = tasks[index]
                    BulletItem(
                        taskTitle = taskItem.title,
                        isExpired = isExpired,
                        taskTime = taskItem.dueTime
                    )
                }
            }
        }
    }
}



@Composable
fun BulletItem (modifier: Modifier = Modifier, taskTitle: String, isExpired: Boolean, taskTime: String) {
    val bulletColor = if (isExpired) Color.Red else LocalColorScheme.current.onTertiary
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .padding(end = 8.dp)
            .size(8.dp)
            .background(bulletColor, CircleShape)
        )
        Text(modifier = Modifier.padding(horizontal = 4.dp), text = taskTime)
        Text(taskTitle)
    }
}


@Composable
fun GreyDotIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int
) {
    Row(
        modifier = modifier,
    ) {
        repeat(pageCount) {
            val color = if (it == currentPage) Color.Black else Color.Gray
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .padding(1.dp)
                    .clip(CircleShape)
                    .background(color = color)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SingleTaskCardPreview() {
    SingleTaskCard(
        toDoTitle = "Today's To-Do",
        tasks = List(2) {Task(
            id = 1,
            title = "Task 1",
            description = "Description 1",
            accessLevel = AccessLevel.PUBLIC,
            taskStatus = TaskStatus.IN_PROGRESS,
            assigneeStatus = List(2){
                AssigneeStatus(
                    id = 1,
                    assigneeId = 1,
                    taskId = 1,
                    status = 1
                )
            },
            dueDate = "2023-07-28",
            dueTime = "14:30",
            createdTime = 1630000000000,
            owner = ProfileInfo(
                id = 1,
                name = "Owner 1",
                email = "pinyunwuu@gmail.com",
                avatar = "https://www.google.com",
                bankAccount = "2232323",
                nickName = "Polly",
                updateTime = 1212222222,
            ),
            updatedTime = 1630000000000)
        },
        isExpired = false
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecentTaskViewPreview() {
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )
    val tasksMap = mapOf(
        ToDoType.TODAY to listOf(
            Task(
                id = 1,
                owner = ProfileInfo(
                    name = "Mock Profile",
                    nickName = "Mock Profile Description",
                    avatar = "https://mock.com",
                    bankAccount = "23232323",
                    email = "pinyunwuu@gmail.com",
                    updateTime = 123232323
                ),
                title = "Grocery Shopping",
                description = "Buy milk, eggs, bread",
                accessLevel = AccessLevel.PRIVATE,
                taskStatus = TaskStatus.IN_PROGRESS,
                dueDate = "2023-07-28",
                dueTime = "14:30",
                assigneeStatus = emptyList(),
                createdTime = 1690886400000,
                updatedTime = 1690886400000
            ),
            Task(
                id = 2,
                owner = ProfileInfo(
                    name = "Mock Profile",
                    nickName = "Mock Profile Description",
                    avatar = "https://mock.com",
                    bankAccount = "23232323",
                    email = "pinyunwuu@gmail.com",
                    updateTime = 123232323
                ),
                title = "Book Doctor Appointment",
                description = "Schedule a check-up",
                accessLevel = AccessLevel.PRIVATE,
                taskStatus = TaskStatus.IN_PROGRESS,
                dueDate = "2023-07-28",
                dueTime = "14:30",
                assigneeStatus = emptyList(),
                createdTime = 1690897200000,
                updatedTime = 1690897200000
            )
        ),
        ToDoType.TOMORROW to listOf(
            Task(
                id = 3,
                owner = ProfileInfo(
                    name = "Mock Profile",
                    nickName = "Mock Profile Description",
                    avatar = "https://mock.com",
                    bankAccount = "23232323",
                    email = "pinyunwuu@gmail.com",
                    updateTime = 123232323
                ),
                title = "Pay Bills",
                description = "Pay electricity and internet bills",
                accessLevel = AccessLevel.PRIVATE,
                taskStatus = TaskStatus.IN_PROGRESS,
                dueDate = "2023-07-28",
                dueTime = "14:30",
                assigneeStatus = emptyList(),
                createdTime = 1690972800000,
                updatedTime = 1690972800000
            )
        )
    )
    RecentTaskView(pagerState = pagerState, tasksMap = tasksMap)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RecentTaskViewPreviewScreen() {
    RecentTaskViewPreview()
}
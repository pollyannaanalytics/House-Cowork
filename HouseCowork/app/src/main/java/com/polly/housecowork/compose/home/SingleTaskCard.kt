package com.polly.housecowork.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.polly.housecowork.R
import com.polly.housecowork.dataclass.TaskState
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.utils.ComposeUtils
import com.polly.housecowork.utils.ComposeUtils.Companion.CardCornerRadius
import com.polly.housecowork.utils.ComposeUtils.Companion.ContentPadding
import com.polly.housecowork.utils.ComposeUtils.Companion.Padding

@Stable
data class TaskItem(val title: String, val dueTime: String)

private val CardMineHeight = 200.dp
private val BulletSize = 8.dp
private val StrokeWidth = 16.dp
private val DotSize = 8.dp
private val DotPadding = 1.dp
private val TextPadding = 4.dp

@Composable
fun SingleTaskCard(
    modifier: Modifier = Modifier,
    toDoTitle: String,
    tasks: List<TaskState>,
    isExpired: Boolean,
) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(ComposeUtils.Elevation),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = CardMineHeight)
            .clip(RoundedCornerShape(CardCornerRadius))
            .padding(ContentPadding)
        ,
        colors = CardDefaults.cardColors(
            containerColor = LocalColorScheme.current.surface
        )
    ) {
        val strokeWidth = remember { StrokeWidth }
        Column(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = CardMineHeight)
            .drawBehind {
                if (toDoTitle == ToDoType.EXPIRED.title) {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = strokeWidth.toPx()
                    )
                }
                if (toDoTitle == ToDoType.THREE_DAYS_FUTURE.title) {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth.toPx()
                    )
                }
            }
            .padding(16.dp)
        ) {
            Text(
                text = toDoTitle,
                style = LocalTypography.current.titleSmall,
                color = LocalColorScheme.current.onBackground
            )
            if(tasks.isEmpty()){
                EmptyContent(
                    Modifier.fillMaxWidth()
                )
            }
            else {
                LazyColumn(
                    modifier = Modifier.padding(top = ContentPadding),
                    contentPadding = PaddingValues(vertical = TextPadding)
                ) {
                    items(
                        items = tasks,
                        key = { task -> task.id }
                    ) { task ->

                        BulletItem(
                            isExpired = isExpired,
                            taskItem = TaskItem(
                                title = task.title,
                                dueTime = task.dueTime
                            )
                        )
                    }
                }
            }
        }
        if (tasks.size >= 5) {
            MoreButton(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun MoreButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .background(LocalColorScheme.current.primary)
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = modifier
                .fillMaxHeight(0.1f)
                .heightIn(min = 16.dp)
                .padding(horizontal = ContentPadding)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End) {
            Text(
                "More",
                style = LocalTypography.current.bodySmall,
                color = LocalColorScheme.current.onBackground
            )
            Icon(
                modifier = Modifier.padding(TextPadding),
                painter = painterResource(id = R.drawable.right_arrow_svg_collection),
                contentDescription = "More",
                tint = LocalColorScheme.current.onBackground,
            )
        }

    }
}

@Composable
private fun EmptyContent(modifier: Modifier = Modifier){
    Row(
        modifier = modifier.padding(Padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
        ){
        Image(
            modifier = Modifier.fillMaxWidth(0.25f),
            painter = painterResource(R.drawable.dinasaur_out_of_the_door),
            contentDescription = "Dinasaur out of the door",
        )
        Text(
            modifier = Modifier.padding(ContentPadding),
            text = "Weeeee, there’s no task!",
            style = LocalTypography.current.bodySmall,
            color = LocalColorScheme.current.onBackground
        )


    }
}

@Composable
private fun BulletItem(
    modifier: Modifier = Modifier,
    taskItem: TaskItem,
    isExpired: Boolean,
) {
    val bulletColor = if (isExpired) Color.Red else LocalColorScheme.current.onTertiary
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = ContentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = ContentPadding)
                .size(BulletSize)
                .background(bulletColor, CircleShape)
        )
        Text(
            modifier = Modifier.padding(horizontal = TextPadding),
            text = taskItem.dueTime,
            style = LocalTypography.current.bodySmall,
            color = LocalColorScheme.current.onBackground
        )
        Text(text = taskItem.title, maxLines = 1, color = LocalColorScheme.current.onBackground)
    }
}


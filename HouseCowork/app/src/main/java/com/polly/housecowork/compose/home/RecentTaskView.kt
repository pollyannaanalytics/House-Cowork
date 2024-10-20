package com.polly.housecowork.compose.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.polly.housecowork.dataclass.AssignedTask
import com.polly.housecowork.ui.theme.LocalColorScheme
import kotlin.math.absoluteValue

@Composable
fun RecentTaskView(
    modifier: Modifier = Modifier,
    onTaskClick: (AssignedTask) -> Unit = {},
    tasksMap: Map<ToDoType, List<AssignedTask>>,
) {
    val pagerState = rememberPagerState(pageCount = { tasksMap.size })
    val pageOrder by remember {
        derivedStateOf {
           tasksMap.keys.toList()[pagerState.currentPage].eventOrder
        }
    }
    Log.i("RecentTaskView", "pagerState: ${pagerState.currentPage}")

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        HorizontalPager(state = pagerState) { page ->
            val tasksType = remember { tasksMap.keys.toList()[page] }
            val tasks = remember(tasksMap, tasksType) {tasksMap[tasksType]?: emptyList()}
            SingleTaskCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        scaleX = lerp(0.8f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                        scaleY = scaleX
                    },
                toDoTitle = tasksType.title,
                tasks = tasks,
                isExpired = tasksType == ToDoType.EXPIRED
            )
        }
        GreyDotIndicator(
            modifier = Modifier.padding(8.dp),
            pageCount = ToDoType.entries.size,
            currentPage = pageOrder
        )
    }
}

@Composable
fun GreyDotIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int
) {
    val dotBlack = LocalColorScheme.current.onBackground
    val dotGrey = Color.Gray
    LazyRow(modifier = modifier) {
        items(pageCount) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(8.dp)
                    .padding(1.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == currentPage) dotBlack  else dotGrey
                    )
            )
        }
    }
}


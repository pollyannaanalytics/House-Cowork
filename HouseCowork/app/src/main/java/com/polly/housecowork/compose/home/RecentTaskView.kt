package com.polly.housecowork.compose.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.polly.housecowork.R
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.dataclass.TaskDto
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.SingleTaskCard
import com.polly.housecowork.ui.utils.dashBorder
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecentTaskView(
    modifier: Modifier = Modifier,
    onTaskClick: (Task) -> Unit = {},
    pagerState: PagerState,
    pages: List<Task>,
) {
    val currentPage by remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (currentPage == 0) {
            EmptyTaskCard()
            return
        }
        HorizontalPager(
            state = pagerState,
            modifier = modifier
        ) { task ->
            SingleTaskCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - task) + pagerState
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
                        shadowElevation = 16f

                    }
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                title = pages[task].title,
                description = pages[task].description,
                imageURL = ""
            )
        }
        GreyDotIndicator(
            modifier = Modifier.padding(8.dp),
            pageCount = pages.size,
            currentPage = currentPage
        )
    }
}

@Composable
fun EmptyTaskCard(modifier: Modifier = Modifier) {
    Card(
        modifier
            .dashBorder(width = 4.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            Modifier
                .background(LocalColorScheme.current.background)
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.wrapContentHeight().fillMaxWidth(0.4f),
                painter = painterResource(id = R.drawable.dinasaur_out_of_the_door),
                contentDescription = "dinosaurs"
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Wanna start a task?",
                style = LocalTypography.current.labelMedium,
                color = LocalColorScheme.current.tertiary
            )
        }
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

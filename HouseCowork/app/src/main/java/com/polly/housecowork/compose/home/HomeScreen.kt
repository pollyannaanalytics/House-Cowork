package com.polly.housecowork.compose.home


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.dataclass.Categories
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.utils.Avatar
import com.polly.housecowork.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.DinosaurType
import com.polly.housecowork.ui.utils.PrimaryMediumButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    taskList: List<Task> = listOf(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    categoryList: List<Categories> = listOf(),
    onTaskClick: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { taskList.size })
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            HomeAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .background(LocalColorScheme.current.background)
                    .padding(16.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalColorScheme.current.background)
                .padding(innerPadding)
            ,
        ) {
            HomeScreenTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            RecentTaskView(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                pagerState = pagerState,
                pages = taskList.toTypedArray()
            )

            TaskButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onClick = { onTaskClick() }
            )

            TaskStats(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                ,
                dinosaurType = DinosaurType.Egg,
                taskStats = 0
            )
        }
    }
}

@Composable
fun HomeAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu"
        )

        Avatar(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)

        )
    }
}

@Composable
fun TaskButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    PrimaryMediumButton(
        modifier = modifier.padding(16.dp),
        text = "Start a task!",
        onClick = { onClick() },
        textStyle = LocalTypography.current.headlineLarge
    )
}


@Composable
fun HomeScreenTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Let's",
            style = LocalTypography.current.titleSmall,
            color = LocalColorScheme.current.onBackground
        )
        Text(
            text = "House Cowork",
            style = LocalTypography.current.headlineMedium,
            modifier = Modifier.padding(top = 4.dp),
            color = LocalColorScheme.current.onBackground
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        taskList = listOf(),
    )
}


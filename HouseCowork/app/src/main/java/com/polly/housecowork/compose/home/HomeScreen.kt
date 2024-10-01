package com.polly.housecowork.compose.home


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.PrimaryMediumButton
import com.polly.housecowork.utils.Screen
import com.polly.housecowork.utils.StepState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateTo: (StepState) -> Unit
) {
    val dinosaurTypeState by homeViewModel.dinosaurType.collectAsState()
    val progressTasks by homeViewModel.progressTasks.collectAsState()
    val doneTasks by homeViewModel.doneTasks.collectAsState()

    val pagerState = rememberPagerState(pageCount = { progressTasks.size })
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalColorScheme.current.background)
                .padding(innerPadding),
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
                pages = progressTasks,
                onTaskClick = { task ->
                    navigateTo(Screen.TaskDetail(task.id))
                }
            )

            TaskButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onClick = {
                    navigateTo(Screen.CreateTask)
                }
            )

            TaskStats(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                dinosaurType = dinosaurTypeState,
                taskStats = doneTasks.size
            )
        }
    }
}

@Composable
fun TaskButton(
    modifier: Modifier = Modifier, onClick: () -> Unit = {}
) {
    PrimaryMediumButton(
        modifier = modifier.padding(16.dp),
        text = "Start a task!",
        onClick = { onClick() },
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
            modifier = Modifier.padding(top = 4.dp),
            color = LocalColorScheme.current.onBackground
        )
    }
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(
//    )
//}


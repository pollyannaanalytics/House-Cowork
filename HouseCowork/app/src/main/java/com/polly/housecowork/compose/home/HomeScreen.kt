package com.polly.housecowork.compose.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.ui.utils.DinosaurType
import com.polly.housecowork.ui.utils.TaskStatus
import com.polly.housecowork.utils.StepState
import kotlinx.coroutines.flow.MutableStateFlow

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
                .padding(innerPadding)
                .background(LocalColorScheme.current.background)
                .padding(16.dp)
            ,
        ) {
            HomeScreenTitle(
                modifier = Modifier
                    .fillMaxWidth()
            )
            RecentTaskView(
                modifier = Modifier
                    .fillMaxWidth(),
                pagerState = pagerState,
                tasksMap = progressTasks
            )

            TaskStats(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                ,
                dinosaurType = dinosaurTypeState,
                taskStats = doneTasks.size
            )
        }
    }
}



@Composable
fun HomeScreenTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = "Let's",
            style = LocalTypography.current.titleMedium,
            color = LocalColorScheme.current.onBackground
        )
        Text(
            text = "House Cowork",
            modifier = Modifier.padding(top = 4.dp),
            style = LocalTypography.current.headlineMedium,
            color = LocalColorScheme.current.onBackground
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val dinosaurTypeState = DinosaurType.Dinosaur
    val progressTasks = mapOf(
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
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LocalColorScheme.current.background)
                .padding(16.dp)
            ,
        ) {
            HomeScreenTitle(
                modifier = Modifier
                    .fillMaxWidth()
            )
            RecentTaskView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                ,
                pagerState = pagerState,
                tasksMap = progressTasks
            )

            TaskStats(
                modifier = Modifier
                    .fillMaxWidth(),
                dinosaurType = dinosaurTypeState,
                taskStats = 10
            )
        }
    }
}
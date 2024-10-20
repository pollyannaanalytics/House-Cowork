package com.polly.housecowork.compose.home


import android.util.Log
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
import com.polly.housecowork.utils.StepState
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateTo: (StepState) -> Unit
) {
    val dinosaurTypeState by homeViewModel.dinosaurType.collectAsState()
    val progressTasks by homeViewModel.progressTasks.collectAsState()
    val doneTasks by homeViewModel.doneTasks.collectAsState()
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

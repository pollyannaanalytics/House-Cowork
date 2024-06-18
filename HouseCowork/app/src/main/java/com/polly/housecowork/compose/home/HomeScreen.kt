package com.polly.housecowork.compose.home


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.polly.housecowork.ui.theme.HCWTypo
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.utils.Avatar
import com.polly.housecowork.ui.utils.StandardButton
import com.polly.housecowork.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.DinosaurType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    taskList: List<Task> = listOf(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    categoryList: List<Categories> = listOf()
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
                .padding(innerPadding),
        ) {
            HomeScreenTitle(
                Modifier.padding(16.dp)
            )

            RecentTaskView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                pagerState = pagerState,
                pages = taskList.toTypedArray()
            )

            TaskButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                onClick = {}
            )

            TaskStats(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                dinosaurType = DinosaurType.Dinosaur,
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
    StandardButton(
        modifier = modifier,
        text = "Start a task!",
        onClick = { /*TODO*/ },
        contentPaddingValues = PaddingValues(16.dp),
        textStyle = LocalTypography.current.displaySmall
    )
}


@Composable
fun HomeScreenTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Let's",
            style = HCWTypo.displaySmall,
            color = LocalColorScheme.current.onBackground
        )
        Text(
            text = "House Cowork",
            style = HCWTypo.headlineLarge,
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


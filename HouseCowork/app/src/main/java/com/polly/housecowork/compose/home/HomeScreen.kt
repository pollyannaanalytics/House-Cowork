package com.polly.housecowork.compose.home


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.polly.housecowork.dataclass.Categories
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.ui.theme.HCWTypo
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.utils.Avatar
import com.polly.housecowork.ui.utils.StandardButton
import com.polly.housecowork.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel

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
                    .padding(16.dp)
                    .fillMaxWidth()
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                TaskButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, end = 16.dp),
                    onClick = {}
                )
                CalendarButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, end = 16.dp),
                    onClick = {}
                )
            }
            RecentTaskView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                pagerState = pagerState,
                pages = taskList.toTypedArray()
            )
            TaskCategories(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                categoryList = categoryList
            )
        }
    }
}

@Composable
fun TaskButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    StandardButton(
        modifier = modifier,
        text = "Tasks",
        onClick = { /*TODO*/ },
        contentPaddingValues = PaddingValues(16.dp)
    )
}

@Composable
fun CalendarButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    StandardButton(
        modifier = modifier,
        text = "Calendar",
        onClick = { /*TODO*/ },
        contentPaddingValues = PaddingValues(16.dp)
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

@Composable
fun HomeAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu"
        )

        Avatar(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .aspectRatio(1f)
        )
    }
}


//@Preview
//@Composable
//fun HomeScreenPreview() {
//
//    HomeScreen(
//        taskList = taskList,
//        categoryList = categories
//    )
//}
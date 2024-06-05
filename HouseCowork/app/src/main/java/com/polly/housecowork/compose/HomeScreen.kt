package com.polly.housecowork.compose


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.polly.housecowork.ui.theme.HCWTypo
import com.polly.housecowork.compose.utils.StandardButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        style = HCWTypo.titleMedium,
                    )
                },
                modifier = Modifier.wrapContentWidth()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            HomeScreenTitle(
                Modifier.padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )  {
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

        }
    }
}

@Composable
fun TaskButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
){
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
    Text(
        text = "Welcome to House Cowork",
        style = HCWTypo.headlineLarge,
        modifier = modifier
    )

}

@Composable
fun Avatar(modifier: Modifier = Modifier, imageURL: String = "") {
    if (imageURL.isNotEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageURL)
                .crossfade(true)
                .build(),
            contentDescription = "avatar",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Icon(
            Icons.Default.AccountCircle,
            contentDescription = "default avatar",
            modifier = modifier
                .background(color = Color.Gray)
                .size(50.dp),
        )
    }
}



@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
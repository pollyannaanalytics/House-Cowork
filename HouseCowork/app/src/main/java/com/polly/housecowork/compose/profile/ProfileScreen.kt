package com.polly.housecowork.compose.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.HCWDatePicker
import com.polly.housecowork.ui.utils.PrimaryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navigateOnClick: () -> Unit = {}) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = 0L
    )

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )

    Scaffold(
        modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .background(LocalColorScheme.current.background),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalColorScheme.current.background,
                    titleContentColor = LocalColorScheme.current.onBackground
                ),
                navigationIcon = {
                    IconButton(onClick = { navigateOnClick() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Close",
                            modifier = Modifier.fillMaxSize(),
                            tint = LocalColorScheme.current.onBackground,
                        )
                    }
                },
                title = { Text(text = "Profile") },
                actions = {},
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .background(LocalColorScheme.current.background)
        ) {
            ProfileAvatarName(
                modifier = Modifier.fillMaxWidth(),
                name = { "Pollyanna" },
                photoUrl = { "" })

            Bio()
            HCWDatePicker(
                modifier = Modifier.padding(16.dp),
                datePickerState = { datePickerState })


        }

    }

}

@Composable
fun ProfileAvatarName(modifier: Modifier = Modifier, name: () -> String, photoUrl: () -> String) {
    Row(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Start,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.3f)
                .aspectRatio(1f),
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = name(),
            style = LocalTypography.current.headlineMedium
        )

    }

}

@Composable
fun Bio(modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Bio", style = LocalTypography.current.titleMedium
        )
        PrimaryCard(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxHeight(0.3f)
                .padding(start = 16.dp, end = 16.dp),
            description = { "I am Pollyanna Wu, love to do house work" },
            textStyle = LocalTypography.current.titleSmall
        )

    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
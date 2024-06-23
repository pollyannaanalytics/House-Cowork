package com.polly.housecowork.compose.createtask

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.Avatar


@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier
            .fillMaxSize(),
        topBar = {
            CreateTaskAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .padding(16.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(LocalColorScheme.current.background)
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
        ) {
            CreateTaskTextField(
                modifier
                    .fillMaxWidth()
            )

        }

    }
}






@Composable
fun SelectDate(
    modifier: Modifier = Modifier,
    weekList: List<String> = listOf("M", "Tu", "W", "Th", "Fri", "Sa", "Su")
) {

    LazyRow(modifier) {
        items(weekList) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(LocalColorScheme.current.surface)
                    .border(1.dp, LocalColorScheme.current.secondary, RoundedCornerShape(25.dp))
                    .padding(8.dp),
                text = it,
                style = LocalTypography.current.labelMedium,
                color = LocalColorScheme.current.background,
            )
        }

    }
}

@Composable
fun CreateTaskAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            modifier = Modifier.fillMaxHeight()

        )
        Avatar(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f).padding(8.dp)
        )
    }
}


@Preview
@Composable
fun PreviewCreateTaskScreen() {
    CreateTaskScreen(
        modifier = Modifier
            .fillMaxSize()
    )
}
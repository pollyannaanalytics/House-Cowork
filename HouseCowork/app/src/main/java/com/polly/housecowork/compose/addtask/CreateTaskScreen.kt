package com.polly.housecowork.compose.addtask

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography


@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier
            .fillMaxSize(),
        topBar = {
            Text(
                text = "Create a Task",
                style = LocalTypography.current.labelLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center

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
            CreateTextField(
                modifier
                    .fillMaxWidth()
                    .padding(16.dp)


            )

        }

    }
}

@Composable
fun CreateTextField(
    modifier: Modifier = Modifier
) {
    var taskName = remember { "" }
    Row(modifier) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(25.dp))
                .background(LocalColorScheme.current.surface)
                .padding(16.dp),
            value = taskName, onValueChange = { input ->
                taskName = input
            },
            maxLines = 1,
            decorationBox = { innerTextField ->
                if (taskName.isEmpty()) {
                    Text(
                        text = "Create a Task",
                        color = LocalColorScheme.current.secondary
                    )
                }
            }
        )
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

@Preview
@Composable
fun PreviewCreateTaskScreen() {
    CreateTaskScreen(
        modifier = Modifier
            .fillMaxSize()
    )
}
package com.polly.housecowork.compose.createtask

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme

@Composable
fun CreateTaskTextField(
    modifier: Modifier = Modifier
) {
    var taskName = remember { "" }
    Row(modifier) {
        BasicTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(LocalColorScheme.current.surface)
                .border(1.dp, LocalColorScheme.current.secondary, RoundedCornerShape(16.dp))
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


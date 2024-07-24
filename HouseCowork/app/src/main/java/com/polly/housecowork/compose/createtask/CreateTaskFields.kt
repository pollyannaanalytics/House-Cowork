package com.polly.housecowork.compose.createtask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.polly.housecowork.ui.utils.HCWTextField

@Composable
fun CreateTaskTextField(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    isTaskEmptyError: (Boolean) -> Boolean,
    clearFocus: () -> Unit
) {
    Row(
        modifier
            .clickable { clearFocus() }
    ) {
        HCWTextField(
            onTextChange = onTextChange,
            hint = "Create a Task",
            errorState = isTaskEmptyError
        )
    }
}


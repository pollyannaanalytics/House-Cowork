package com.polly.housecowork.compose.createtask.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.polly.housecowork.ui.utils.HCWTextField

@Composable
fun CreateTaskTextField(
    modifier: Modifier = Modifier,
    defaultText: String = "",
    onTextChange: (String) -> Unit,
    isTaskEmptyError: Boolean,
    clearFocus: () -> Unit
) {
    Row(
        modifier
            .clickable { clearFocus() }
    ) {
        HCWTextField(
            onTextChange = onTextChange,
            defaultText = defaultText,
            hint = "Create a Task",
            errorState = isTaskEmptyError
        )
    }
}


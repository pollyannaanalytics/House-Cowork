package com.polly.housecowork.compose.createtask.content

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.polly.housecowork.ui.utils.HCWTextField

@Composable
fun CreateTaskTextField(
    modifier: Modifier = Modifier,
    defaultText: String = "",
    onTextChange: (String) -> Unit,
    isTaskEmptyError: Boolean
) {
    HCWTextField(
        modifier = modifier,
        onTextChange = onTextChange,
        defaultText = defaultText,
        hint = "Create a Task",
        errorState = isTaskEmptyError
    )

}


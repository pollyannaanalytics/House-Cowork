package com.polly.housecowork.compose.createtask.content

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.polly.housecowork.ui.utils.HCWTextField

@Composable
fun CreateTaskTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isTaskEmptyError: Boolean
) {
    HCWTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        hint = "Create a Task",
        isError = isTaskEmptyError,
    )

}


package com.polly.housecowork.compose.createtask

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme

@Composable
fun CreateTaskTextField(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    errorState: () -> ErrorState,
    clearFocus: () -> Unit
) {

    val borderColor =
        if (errorState() is ErrorState.None) LocalColorScheme.current.secondary else LocalColorScheme.current.error
    var textState by remember {
        mutableStateOf(TextFieldValue())
    }
    Row(
        modifier
            .clickable { clearFocus() }
    ) {
        BasicTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(LocalColorScheme.current.surface)
                .border(1.dp, borderColor, RoundedCornerShape(16.dp))
                .padding(16.dp),
            value = textState,
            onValueChange = {
                textState = it
                onTextChange(it.text)
            },
            maxLines = 1,
            decorationBox = { innerTextField ->
                if (textState.text.isEmpty()) {
                    Text(
                        text = "Create a Task",
                        color = LocalColorScheme.current.secondary
                    )
                }
                innerTextField()
            }
        )
    }
}


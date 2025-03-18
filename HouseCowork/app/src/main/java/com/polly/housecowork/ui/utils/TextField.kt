package com.polly.housecowork.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography

@Composable
fun HCWTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    hintStyle: androidx.compose.ui.text.TextStyle = LocalTypography.current.bodyMedium,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = when (keyboardOptions.keyboardType) {
        KeyboardType.Password -> PasswordVisualTransformation()
        else -> VisualTransformation.None
    }
) {
    val borderColor = if (isError) {
        LocalColorScheme.current.error
    } else {
        LocalColorScheme.current.secondary
    }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(LocalColorScheme.current.surface)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(12.dp),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        maxLines = 1,
        textStyle = LocalTypography.current.bodyMedium,
        decorationBox = { innerTextField ->
            Box(contentAlignment = CenterStart,){
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        color = LocalColorScheme.current.secondary,
                        style = hintStyle,
                    )
                }
                innerTextField()
            }
        }
    )
}


@Preview
@Composable
fun HCWTextFieldPreview() {
    HCWTextField(value = "", onValueChange = {}, hint = "Create a Task", isError = false)
}
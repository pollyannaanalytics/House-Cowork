package com.polly.housecowork.ui.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
    onTextChange: (String) -> Unit, hint: String,
    errorState: Boolean = false,
    keyboardOptions: () -> KeyboardOptions = { KeyboardOptions(keyboardType = KeyboardType.Text) },
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val borderColor =
        if (errorState) LocalColorScheme.current.error else LocalColorScheme.current.secondary
    var textState by remember {
        mutableStateOf(TextFieldValue())
    }
    Log.e("HCWTextField", "textState: $textState, passwordState: $errorState")
    BasicTextField(
        modifier = modifier
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
        keyboardOptions = keyboardOptions(),
        visualTransformation = if (keyboardOptions().keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
        maxLines = 1,
        textStyle = LocalTypography.current.bodyMedium,
        decorationBox = { innerTextField ->
            if (textState.text.isEmpty()) {
                Text(
                    text = hint,
                    color = LocalColorScheme.current.secondary,
                    style = LocalTypography.current.bodyMedium,
                )
            }
            innerTextField()
        }
    )

}


@Preview
@Composable
fun HCWTextFieldPreview() {
    HCWTextField(onTextChange = {}, hint = "Create a Task", errorState = false)
}
package com.polly.housecowork.compose.onboarding.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.HCWTextField
import com.polly.housecowork.ui.utils.PositiveButton

@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    nameOnChange: (String) -> Unit,
    emailOnChange: (String) -> Unit,
    passwordOnChange: (String) -> Unit,
    repeatPasswordOnChange: (String) -> Unit,
    emailError: Boolean,
    passwordError: Boolean,
    repeatPasswordError: Boolean,
    joinOnClick: () -> Unit
) {
    var passwordKeyboard by remember { mutableStateOf(KeyboardOptions(keyboardType = KeyboardType.Password)) }
    val focusRequesters = List(4) { FocusRequester() }

    Column(
        modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = "*All blanks need to be filled",
            style = LocalTypography.current.bodyMedium
        )
        HCWTextField(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            onTextChange = { nameOnChange(it) },
            hint = "Name  ( ex. Polly Wu )",
            keyboardOptions = {
                KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            },
            keyboardActions = KeyboardActions(
                onNext = { moveFocus(0, focusRequesters) }
            ))
        HCWTextField(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            onTextChange = { emailOnChange(it) },
            hint = "E-mail",
            errorState = emailError,
            keyboardOptions = {
                KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            },
            keyboardActions = KeyboardActions(
                onNext = { moveFocus(1, focusRequesters) }
            )
        )
        if (emailError) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp, start = 16.dp),
                text = "*Please enter a valid email address.",
                style = LocalTypography.current.bodyMedium,
                color = LocalColorScheme.current.error
            )
        }

        HCWTextField(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            onTextChange = { passwordOnChange(it) },
            hint = "Password",
            errorState = passwordError,
            keyboardOptions = {
                KeyboardOptions(
                    imeAction = ImeAction.Next
                )
                passwordKeyboard
            },
            keyboardActions = KeyboardActions(
                onNext = { moveFocus(2, focusRequesters) }
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp, start = 16.dp),
            text = "*Required at least 10 characters, and contain at least one uppercase character.",
            style = LocalTypography.current.bodyMedium,
            color = if (passwordError) LocalColorScheme.current.error else LocalColorScheme.current.secondary,
        )
        HCWTextField(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            onTextChange = { repeatPasswordOnChange(it) },
            hint = "Repeat Password",
            errorState = repeatPasswordError,
            keyboardOptions = {
                KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = passwordKeyboard.keyboardType)

            },
            keyboardActions = KeyboardActions(
                onDone = {
                    joinOnClick()
                }
            )
        )

        if (repeatPasswordError) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp, start = 16.dp),
                text = "*Passwords do not match.",
                style = LocalTypography.current.bodyMedium,
                color = LocalColorScheme.current.error
            )
        }
        ShowPasswordCheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) { shouldShowPassword ->
            passwordKeyboard =
                if (shouldShowPassword) KeyboardOptions(keyboardType = KeyboardType.Text) else KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
        }
        PositiveButton(
            modifier = Modifier.padding(top = 8.dp),
            text = "Join !", textStyle = LocalTypography.current.titleMedium,
            onClick = { joinOnClick() }
        )
    }
}

fun moveFocus(currentIndex: Int, focusRequesters: List<FocusRequester>) {
    if (currentIndex < focusRequesters.size - 1) {
        focusRequesters[currentIndex + 1].requestFocus()
    }
}
package com.polly.housecowork.compose.onboarding.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
    name: String,
    nickName: String,
    email: String,
    password: String,
    repeatPassword: String,
    onNickNameChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    isPasswordShown: Boolean,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    isRepeatPasswordError: Boolean,
    onJoinClick: () -> Unit
) {
    val nickNameFocus = remember { FocusRequester() }
    val emailFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }
    val repeatPasswordFocus = remember { FocusRequester() }

    Column(
        modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            modifier = commonTextFieldModifier,
            text = "*All blanks need to be filled",
            style = LocalTypography.current.bodySmall
        )
        BasicInfoFields(
            name = name,
            nickName = nickName,
            onNameChange = onNameChange,
            onNickNameChange = onNickNameChange,
            nickNameFocus = nickNameFocus,
            emailFocus = emailFocus
        )

        EmailInfoFields(
            email = email,
            isEmailError = isEmailError,
            onEmailChange = onEmailChange,
            passwordFocus = passwordFocus,
            emailFocus = emailFocus
        )

        PasswordFields(
            password = password,
            repeatPassword = repeatPassword,
            isPasswordShown = isPasswordShown,
            isPasswordError = isPasswordError,
            isRepeatPasswordError = isRepeatPasswordError,
            onPasswordChange = onPasswordChange,
            onRepeatPasswordChange = onRepeatPasswordChange,
            onJoinClick = onJoinClick,
            repeatPasswordFocus = repeatPasswordFocus,
            passwordFocus = passwordFocus
        )

        ShowPasswordCheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            isChecked = isPasswordShown,
            onCheckChanged = onCheckedChange
        )
        PositiveButton(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(0.4f),
            text = "Join !", textStyle = LocalTypography.current.titleMedium,
            onClick = { onJoinClick() }
        )
    }
}

@Composable
fun BasicInfoFields(
    name: String,
    nickName: String,
    onNameChange: (String) -> Unit,
    onNickNameChange: (String) -> Unit,
    nickNameFocus: FocusRequester,
    emailFocus: FocusRequester
){
    HCWTextField(
        modifier = commonTextFieldModifier,
        value = name,
        onValueChange = { onNameChange(it) },
        hint = "Name  ( ex. Pollyanna Wu )",
        keyboardOptions = regularKeyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = { nickNameFocus.requestFocus() }
        ))
    HCWTextField(
        modifier = commonTextFieldModifier.focusRequester(nickNameFocus),
        value = nickName,
        onValueChange = { onNickNameChange(it) },
        hint = "Nickname  ( ex. Polly )",
        keyboardOptions = regularKeyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = { emailFocus.requestFocus() }
        )
    )

}

@Composable
fun EmailInfoFields(
    email: String,
    isEmailError: Boolean,
    onEmailChange: (String) -> Unit,
    emailFocus: FocusRequester,
    passwordFocus: FocusRequester
){
    HCWTextField(
        modifier = commonTextFieldModifier,
        value = email,
        onValueChange = { onEmailChange(it) },
        hint = "E-mail",
        isError = isEmailError,
        keyboardOptions = regularKeyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = {
                passwordFocus.requestFocus()
            }
        )
    )
    if (isEmailError) {
        Text(
            modifier = errorTextModifier.focusRequester(emailFocus),
            text = "*Please enter a valid email address.",
            style = LocalTypography.current.bodySmall,
            color = LocalColorScheme.current.error
        )
    }
}

@Composable
private fun PasswordFields(
    password: String,
    repeatPassword: String,
    isPasswordShown: Boolean,
    isPasswordError: Boolean,
    isRepeatPasswordError: Boolean,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onJoinClick: () -> Unit,
    passwordFocus: FocusRequester,
    repeatPasswordFocus: FocusRequester
) {
    val passwordKeyboardType = if (isPasswordShown) KeyboardType.Text else KeyboardType.Password
    val passwordKeyboardOptions = KeyboardOptions(
        keyboardType = passwordKeyboardType,
        imeAction = ImeAction.Next
    )

    val doneKeyboardOptions = KeyboardOptions(
        keyboardType = passwordKeyboardType,
        imeAction = ImeAction.Done
    )

    HCWTextField(
        modifier = commonTextFieldModifier.focusRequester(passwordFocus),
        value = password,
        onValueChange = { onPasswordChange(it) },
        hint = "Password",
        isError = isPasswordError,
        keyboardOptions = passwordKeyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = {
                repeatPasswordFocus.requestFocus()
            }
        )
    )
    Text(
        modifier = errorTextModifier,
        text = "*Required at least 10 characters, and contain at least one uppercase character.",
        style = LocalTypography.current.bodySmall,
        color = if (isPasswordError) LocalColorScheme.current.error else LocalColorScheme.current.onBackground,
    )
    HCWTextField(
        modifier = commonTextFieldModifier.focusRequester(repeatPasswordFocus),
        value = repeatPassword,
        onValueChange = { onRepeatPasswordChange(it) },
        hint = "Repeat Password",
        isError = isRepeatPasswordError,
        keyboardOptions = doneKeyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                onJoinClick()
            }
        )
    )

    if (isRepeatPasswordError) {
        Text(
            modifier = errorTextModifier,
            text = "*Passwords do not match.",
            style = LocalTypography.current.bodySmall,
            color = LocalColorScheme.current.error
        )
    }

}

private val regularKeyboardOptions = KeyboardOptions(
    imeAction = ImeAction.Next,
    keyboardType = KeyboardType.Text
)

private val commonTextFieldModifier = Modifier
    .fillMaxWidth()
    .padding(top = 16.dp)

private val errorTextModifier = Modifier
    .fillMaxWidth()
    .padding(top = 4.dp, start = 16.dp)


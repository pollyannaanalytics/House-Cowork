package com.polly.housecowork.compose.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.HCWTextField
import com.polly.housecowork.ui.utils.PositiveButton

@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    nameError: () -> Boolean,
    emailError: () -> Boolean,
    passwordError: () -> Boolean,
    repeatPasswordError: () -> Boolean
) {
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
            onTextChange = {},
            hint = "Name",
            errorState = { nameError() })
        HCWTextField(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            onTextChange = {},
            hint = "E-mail",
            errorState = { emailError() })
        HCWTextField(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            onTextChange = {},
            hint = "Password",
            errorState = { passwordError() })
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp, start = 16.dp),
            text = "*Required at least 10 characters",
            style = LocalTypography.current.bodyMedium
        )
        HCWTextField(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            onTextChange = {},
            hint = "Repeat Password",
            errorState = { repeatPasswordError() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        ShowPasswordCheckBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {

        }
        PositiveButton(
            modifier = Modifier.padding(top = 8.dp),
            text = "Join !", textStyle = LocalTypography.current.titleMedium
        ) {

        }
    }


}

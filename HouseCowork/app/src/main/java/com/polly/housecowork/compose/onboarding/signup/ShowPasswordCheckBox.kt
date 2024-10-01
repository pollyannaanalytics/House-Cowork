package com.polly.housecowork.compose.onboarding.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.polly.housecowork.ui.theme.LocalTypography

@Composable
fun ShowPasswordCheckBox(
    modifier: Modifier,
    onCheckChanged: (Boolean) -> Unit
) {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                onCheckChanged(it)
            })
        Text(text = "Show Password", style = LocalTypography.current.bodyMedium)
    }
}

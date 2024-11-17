package com.polly.housecowork.compose.createtask.content

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.utils.AccessLevel
import com.polly.housecowork.utils.ComposeUtils

@Composable
fun PublicSwitch(
    modifier: Modifier = Modifier,
    onPublicChange: (Boolean) -> Unit,
    isPublic: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {
        Text("Public")
        Switch(
            modifier = Modifier.padding(start = ComposeUtils.Padding),
            checked = isPublic,
            onCheckedChange = {
                onPublicChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = LocalColorScheme.current.primary,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = LocalColorScheme.current.secondary

            )
        )
    }
}
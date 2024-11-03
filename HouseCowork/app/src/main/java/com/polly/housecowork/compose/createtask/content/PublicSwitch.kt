package com.polly.housecowork.compose.createtask.content

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.utils.AccessLevel

@Composable
fun PublicSwitch(
    modifier: Modifier = Modifier,
    onPublicChange: (Boolean) -> Unit,
    isPublic: Boolean
) {
    Row(modifier) {
        Text("Public")
        Switch(
            modifier = Modifier.padding(start = 8.dp),
            checked = isPublic,
            colors = SwitchDefaults.colors(
                checkedThumbColor = LocalColorScheme.current.primary,
            ),
            onCheckedChange = {
                onPublicChange(it)
            }
        )
    }
}
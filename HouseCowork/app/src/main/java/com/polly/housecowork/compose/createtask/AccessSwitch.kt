package com.polly.housecowork.compose.createtask

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme

@Composable
fun AccessSwitch(
    modifier: Modifier,
    isPublicCheck: (Boolean) -> Unit
) {
    var isPublic by remember { mutableStateOf(false) }
    Row(modifier) {
        Text("Public")
        Switch(
            modifier = Modifier.padding(start = 8.dp),
            checked = isPublic,
            colors = SwitchDefaults.colors(
                checkedThumbColor = LocalColorScheme.current.primary,
            ),
            onCheckedChange = {
                isPublic = it
                isPublicCheck(it)
            }
        )
    }
}
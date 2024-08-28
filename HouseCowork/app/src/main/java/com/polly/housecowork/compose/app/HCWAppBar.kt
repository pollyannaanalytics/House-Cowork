package com.polly.housecowork.compose.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.ui.utils.Avatar

@Composable
fun HCWAppBar(
    modifier: Modifier = Modifier,
    profileInfo: ProfileInfo,
    onClick: (ProfileInfo) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu"
        )

        Avatar(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            profileInfo = profileInfo,
            onClick = onClick
        )
    }
}
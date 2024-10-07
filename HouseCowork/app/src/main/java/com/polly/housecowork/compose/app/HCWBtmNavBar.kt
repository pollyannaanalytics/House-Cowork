package com.polly.housecowork.compose.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polly.housecowork.R
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.utils.compose.BadgeDollarSign
import com.polly.housecowork.ui.utils.compose.Chat


@Composable
fun HCWBtmNavBar(navController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    BottomAppBar(
        containerColor = LocalColorScheme.current.primary,
        contentColor = LocalColorScheme.current.onBackground,
    ) {
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = {}
        ) {
            Icon(
                imageVector = Chat,
                contentDescription = "chat",
                tint = Color.White
            )
        }
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = {}
        ) {
            Icon(
                imageVector = BadgeDollarSign,
                contentDescription = "Money",
                tint = Color.White
            )

        }
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Profile",
                tint = Color.White
            )
        }


        IconButton(
            modifier = Modifier.weight(1f),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Profile",
                tint = Color.White
            )
        }

    }
}



@Preview
@Composable
fun HCWBtmNavBarPreview() {
    val context = androidx.compose.ui.platform.LocalContext.current
    HCWBtmNavBar(navController = NavController(context))
}
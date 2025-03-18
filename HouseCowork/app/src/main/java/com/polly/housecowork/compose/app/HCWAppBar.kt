package com.polly.housecowork.compose.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HCWAppBar(
    modifier: Modifier = Modifier,
    navigateToProfile: () -> Unit,
    title: String
) {
    val isHome = title == Screen.Home.title
        CenterAlignedTopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = LocalColorScheme.current.background,
                titleContentColor = LocalColorScheme.current.onBackground
            ),
            title = {
                if (!isHome) {
                    Text(text = title, style = LocalTypography.current.titleMedium)
                }
            },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    if (isHome) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = LocalColorScheme.current.onBackground,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "go back",
                            tint = LocalColorScheme.current.onBackground,
                        )
                    }
                }
            },

            actions = {
                IconButton(onClick = { navigateToProfile() }) {
                    if (isHome) {
                        Icon(
                            imageVector = Icons.Rounded.AccountCircle,
                            contentDescription = "profile",
                            tint = LocalColorScheme.current.onBackground,
                        )
                    }
                }
            }
        )

}

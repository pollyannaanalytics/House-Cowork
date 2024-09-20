package com.polly.housecowork.compose.app

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.utils.Screen
import com.polly.housecowork.viewmodel.HCWAppViewModel


@Composable
fun HCWApp(
    viewModel: HCWAppViewModel= hiltViewModel()) {
    val navController = rememberNavController()
    val profileInfoState by viewModel.profileInfo.collectAsState()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val showTopBar = when (currentRoute) {
        Screen.SignUp.route, -> false
        else -> true
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            profileInfoState?.let {
                if(showTopBar){
                HCWAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .background(LocalColorScheme.current.background)
                        .padding(16.dp),
                    profileInfo = it,
                    navigateToProfile = { userProfile ->
                        navController.navigate(
                            Screen.Profile(profileId = userProfile.id).route
                        )
                    },
                    title = { currentRoute ?: Screen.Home.route }
                )
                }
            }
        }
    ) { innerPadding ->
        HCWNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            profileId = { profileInfoState?.id ?: 0 },
            taskId = { 0 }
        )
    }
}
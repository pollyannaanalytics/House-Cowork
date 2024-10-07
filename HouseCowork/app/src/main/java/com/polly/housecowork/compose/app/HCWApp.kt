package com.polly.housecowork.compose.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.polly.housecowork.model.auth.AuthState
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.utils.Screen
import com.polly.housecowork.utils.StepState
import com.polly.housecowork.viewmodel.HCWAppViewModel


@Composable
fun HCWApp(viewModel: HCWAppViewModel= hiltViewModel()) {
    val navController = rememberNavController()
    val profileInfoState by viewModel.profileInfo.collectAsState()
    val authState by viewModel.authState.collectAsState()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    var bottomBarHeight by remember { mutableStateOf(0) }
    var fabHeight by remember { mutableStateOf(0) }
    var fabOffsetY by remember { mutableStateOf(0.dp) }

    val localDensity = LocalDensity.current
    val showTopBar = when (currentRoute) {
        Screen.SignUp.route -> false
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
                        .onGloballyPositioned { coordinates ->
                            bottomBarHeight = coordinates.size.height
                        }
                        .background(LocalColorScheme.current.background)
                        .padding(bottom = 16.dp),
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
        },

        bottomBar = {
            HCWBtmNavBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .onGloballyPositioned {
                        fabHeight = it.size.height
                    }
                    .offset(y = fabOffsetY)
                    .border(
                        width = 5.dp,
                        color = LocalColorScheme.current.onPrimary,
                        shape = CircleShape
                    ),
                shape = CircleShape,
                containerColor = Color.White,
                onClick = {
                    navController.navigate(StepState.CreateTask.step)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = LocalColorScheme.current.onPrimary,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->

        LaunchedEffect(fabHeight) {
            val offset = (fabHeight).toFloat()
            fabOffsetY = with(localDensity) { offset.toDp() }
        }


        HCWNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            profileId = { profileInfoState?.id ?: 0 },
            taskId = { 0 },
            graphStartDestination = if (authState is AuthState.Login) StepState.Home else StepState.Onboarding

        )
    }
}

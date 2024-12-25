package com.polly.housecowork.compose.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.polly.housecowork.model.auth.AuthState
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.utils.Screen
import com.polly.housecowork.viewmodel.HCWAppViewModel

@Composable
fun HCWApp(viewModel: HCWAppViewModel = hiltViewModel()) {
    val authState by viewModel.authState.collectAsState()
    val appState = rememberHCWAppState(
        authState = authState,
    )

    HCWAppContent(
        appState = appState
    )
}

@Composable
private fun HCWAppContent(
    appState: HCWAppState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                showTopBar = appState.showTopBar,
                currentScreenTitle = appState.currentScreen.title,
                onNavigateToProfile = {
                    appState.navigate(Screen.Profile)
                }
            )
        },
        bottomBar = {
            AppBottomBar(
                showBottomBar = appState.showBottomBar,
                onNavigate = appState::navigate
            )
        }
    ) { innerPadding ->
        HCWNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = appState.navController,
            appState = appState,
            userId = appState.userId
        )
    }
}

@Composable
private fun rememberHCWAppState(
    authState: AuthState,
    navController: NavHostController = rememberNavController()
): HCWAppState {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = remember(currentBackStackEntry){
        Screen.fromRoute(currentBackStackEntry?.destination?.route)
    }
    return remember(navController, authState, currentBackStackEntry) {
        HCWAppState(
            navController = navController,
            authState = authState,
            currentScreen = currentScreen
        )
    }
}

class HCWAppState(
    val navController: NavHostController,
    val authState: AuthState,
    val currentScreen: Screen
) {
    val userId: Int
        get() = when (authState) {
            is AuthState.Login -> authState.userId
            else -> -1
        }

    val showTopBar: Boolean
        get() = when (currentScreen) {
            is Screen.OnBoarding, Screen.Splash -> false
            else -> true
        }

    val showBottomBar: Boolean
        get() = when (currentScreen) {
            is Screen.OnBoarding, Screen.Splash, Screen.Task.Create -> false
            is Screen.House -> false
            else -> true
        }


    val startDestination: Screen
        get() = if (authState is AuthState.Login) Screen.Splash else Screen.OnBoarding.SignUp


    fun navigate(stepState: Screen) {
        navController.navigate(stepState.route)
    }

}


@Composable
private fun AppTopBar(
    showTopBar: Boolean,
    currentScreenTitle: String?,
    onNavigateToProfile: () -> Unit
) {
    if (showTopBar) {
        HCWAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalColorScheme.current.background),
            navigateToProfile = onNavigateToProfile,
            title = { currentScreenTitle ?: Screen.Home.title }
        )
    }

}

@Composable
private fun AppBottomBar(
    showBottomBar: Boolean,
    onNavigate: (Screen) -> Unit
) {
    if (showBottomBar) {
        HCWBtmNavBar(
            onNavigateClick = onNavigate
        )
    }
}

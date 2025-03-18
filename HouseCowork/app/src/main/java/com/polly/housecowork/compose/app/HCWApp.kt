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
import com.polly.housecowork.model.auth.OnboardingState
import com.polly.housecowork.model.auth.OnboardingState.Companion.toRoute
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.utils.Screen
import com.polly.housecowork.viewmodel.HCWAppViewModel

@Composable
fun HCWApp(viewModel: HCWAppViewModel = hiltViewModel()) {
    val userState by viewModel.userNextState.collectAsState()
    val appState = rememberHCWAppState(
      onboardingState = userState,
    )

    HCWAppContent(
        appState = appState
    )
}

@Composable
private fun HCWAppContent(
    appState: HCWAppState,
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
        )
    }
}

@Composable
private fun rememberHCWAppState(
    onboardingState: OnboardingState,
    navController: NavHostController = rememberNavController()
): HCWAppState {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = remember(currentBackStackEntry){
        Screen.fromRoute(currentBackStackEntry?.destination?.route)
    }
    return remember(navController, onboardingState, currentBackStackEntry) {
        HCWAppState(
            navController = navController,
            onboardingState = onboardingState,
            currentScreen = currentScreen
        )
    }
}

class HCWAppState(
    val navController: NavHostController,
    val onboardingState: OnboardingState,
    val currentScreen: Screen,
) {

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


    val startDestination: String
        get() = when (onboardingState) {
            OnboardingState.Auth.Complete -> Screen.Splash.route
            else -> Screen.OnBoarding.BASE_ROUTE
        }

    val userNextRoute: String
        get() = onboardingState.toRoute()



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
            title =  currentScreenTitle ?: Screen.Home.title
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

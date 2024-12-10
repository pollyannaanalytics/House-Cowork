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
import com.polly.housecowork.compose.onboarding.OnboardingStep
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.model.auth.AuthState
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.utils.Screen
import com.polly.housecowork.utils.Step
import com.polly.housecowork.utils.StepState
import com.polly.housecowork.viewmodel.HCWAppViewModel

@Composable
fun HCWApp(viewModel: HCWAppViewModel = hiltViewModel()) {
    val profileInfo by viewModel.profileInfo.collectAsState()
    val authState by viewModel.authState.collectAsState()
    val appState = rememberHCWAppState(
        profileInfo = profileInfo,
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
                profileInfo = appState.profileInfo,
                currentRoute = appState.currentRoute,
                onNavigateToProfile = {
                    appState.navigateToProfile()
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
            profileId = { appState.profileInfo?.id ?: 0 },
            taskId = { 0 },
            graphStartDestination = appState.startDestination
        )
    }
}

@Composable
private fun rememberHCWAppState(
    profileInfo: ProfileInfo?,
    authState: AuthState,
    navController: NavHostController = rememberNavController()
): HCWAppState {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    return remember(navController, profileInfo, authState, currentBackStackEntry) {
        HCWAppState(
            navController = navController,
            profileInfo = profileInfo,
            authState = authState,
            currentRoute = currentBackStackEntry?.destination?.route
        )
    }
}

private class HCWAppState(
    val navController: NavHostController,
    val profileInfo: ProfileInfo?,
    val authState: AuthState,
    val currentRoute: String?
) {
    val showTopBar: Boolean
        get() = when (currentRoute) {
            Step.REGISTER_STEP-> false
            else -> true
        }

    val showBottomBar: Boolean
        get() = when (currentRoute) {
            Step.REGISTER_STEP, Screen.CreateTask.route -> false
            else -> true
        }

    val startDestination: StepState
        get() = if (authState is AuthState.Login) StepState.Home else StepState.Onboarding

    fun navigateToProfile() {
        navController.navigate(StepState.Profile.step)
    }

    fun navigate(stepState: StepState) {
        navController.navigate(stepState.step)
    }
}


@Composable
private fun AppTopBar(
    showTopBar: Boolean,
    profileInfo: ProfileInfo?,
    currentRoute: String?,
    onNavigateToProfile: () -> Unit
) {
    profileInfo?.let {
        if (showTopBar) {
            HCWAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LocalColorScheme.current.background),
                profileInfo = it,
                navigateToProfile = onNavigateToProfile,
                title = { currentRoute ?: Screen.Home.route }
            )
        }
    }
}

@Composable
private fun AppBottomBar(
    showBottomBar: Boolean,
    onNavigate: (StepState) -> Unit
) {
    if (showBottomBar) {
        HCWBtmNavBar(
            onNavigateClick = onNavigate
        )
    }
}

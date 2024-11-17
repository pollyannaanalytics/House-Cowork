package com.polly.housecowork.compose.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.HCWDatePicker
import com.polly.housecowork.ui.utils.PrimaryCard
import com.polly.housecowork.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateOnClick: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel()
    ) {
    
    val profileInfo by viewModel.profileInfo.collectAsState()
    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    ProfileContent(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .background(LocalColorScheme.current.background),
        profileInfo = profileInfo,
        tasks = emptyList(),
        dates = emptyList()
    )
}




@Composable
fun Bio(modifier: Modifier = Modifier, description: () -> String) {
    Column(modifier) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Bio", style = LocalTypography.current.titleMedium
        )
        PrimaryCard(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxHeight(0.3f)
                .padding(start = 16.dp, end = 16.dp),
            description = { description() },
            textStyle = LocalTypography.current.titleSmall
        )

    }
}


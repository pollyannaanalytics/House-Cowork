package com.polly.housecowork.compose.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.polly.housecowork.ui.theme.LocalTypography

@Composable
fun RegisterCompleteScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit) {

    Column(modifier) {
        Text("You are completely registered.\n", style = LocalTypography.current.titleMedium)



    }
}
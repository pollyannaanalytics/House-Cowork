package com.polly.housecowork.compose.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.R
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.StandardButton

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onNavigateHome: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
    ) {
        Text(
            text = "You are completely registered.",
            style = LocalTypography.current.headlineSmall
        )
        Image(
            painter = painterResource(R.drawable.house_cowork_logo),
            contentDescription = "House Cowork Logo",
        )
        StandardButton(
            text = "Start!",
            buttonColor = LocalColorScheme.current.onPrimary,
            textStyle = LocalTypography.current.headlineMedium,
            textColor = Color.White,
            onClick = onNavigateHome

        )

    }
}

@Composable
@Preview
fun PreviewSplashScreen() {
    SplashScreen()

}
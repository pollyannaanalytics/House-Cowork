package com.polly.housecowork.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryCard(
    modifier: Modifier = Modifier,
    description: () -> String,
    textStyle: TextStyle = LocalTypography.current.bodyMedium,
    onClick: () -> Unit = {}
){
    Card(
        modifier = modifier
            .fillMaxWidth()
        ,
        colors = CardDefaults.cardColors(
            containerColor = LocalColorScheme.current.primary
        ),
        onClick = { onClick() }) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = description(),
                style = textStyle
            )
        }

    }

}
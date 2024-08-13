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

@Composable
fun SingleTaskCard(
    modifier: Modifier = Modifier,
    title: String = "Title",
    description: String = "Description",
    imageURL: String = ""
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .shadow(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(LocalColorScheme.current.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, bottom = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = LocalTypography.current.titleSmall,
                )
                if (imageURL.isNotEmpty()) {
                    Avatar(
                        imageURL = imageURL,
                        modifier = Modifier
                            .fillMaxWidth(0.2f)
                            .aspectRatio(1f)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            )
                            .fillMaxWidth(0.2f)
                            .aspectRatio(1f)
                    )
                }
            }
            Text(
                text = description,
                style = LocalTypography.current.bodyMedium,
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
            )

            SeeMoreButton(
                modifier = Modifier.padding(top = 16.dp)
            )

        }

    }
}


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
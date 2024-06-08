package com.polly.housecowork.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.R
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
                    style = LocalTypography.current.titleMedium,
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
                style = LocalTypography.current.bodyLarge,
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

@Composable
fun SeeMoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier
            .background(LocalColorScheme.current.primary),
        onClick = onClick,
    ) {
        Row (
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically){
            Text(
                text = "See more",
                style = LocalTypography.current.titleSmall,
                color = LocalColorScheme.current.onBackground
            )
          Icon(
                painter = painterResource(id = R.drawable.down_arrow_svgrepo_com__3_),
                contentDescription = "see more",
                tint = LocalColorScheme.current.onBackground,
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 8.dp)
                    .rotate(270f)
            )
        }
    }

}

@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    number: Int,
    description: String
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))


    ) {
        Column(
            modifier = Modifier
                .background(LocalColorScheme.current.tertiary)
                .fillMaxSize()
                .padding(16.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${number}",
                style = LocalTypography.current.displaySmall,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Text(
                text = description,
                style = LocalTypography.current.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color.White
            )

        }

    }
}




//@Composable
//@Preview
//fun SummaryCardPreview() {
//    SummaryCard(number = 12, description = "Task this week")
//}

@Composable
@Preview
fun SingleTaskCardPreview() {
    SingleTaskCard(
        title = "倒垃圾",
        description = "今天晚上 9:00 ，要倒回收",
        imageURL = ""
    )
}



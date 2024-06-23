package com.polly.housecowork.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.R
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.DinosaurType

@Composable
fun TaskStats(
    modifier: Modifier = Modifier,
    taskStats: Int,
    dinosaurType: DinosaurType,
) {

    val taskStatsState = remember {
        taskStats
    }
    val dinosaurTypeState = remember {
        dinosaurType
    }
    Card(
        modifier
            .border(1.dp, LocalColorScheme.current.tertiary, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .background(LocalColorScheme.current.background)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Task Count",
                style = LocalTypography.current.headlineSmall
            )
            Row(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.Bottom,
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                when (dinosaurTypeState) {
                    is DinosaurType.Egg -> {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight(),
                            painter = painterResource(id = R.drawable.egg),
                            contentDescription = "dinosour"
                        )
                    }

                    is DinosaurType.EggOut -> {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight(),
                            painter = painterResource(id = R.drawable.egg_out),
                            contentDescription = "dinosour"
                        )
                    }

                    is DinosaurType.Dinosaur -> {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight(),
                            painter = painterResource(id = R.drawable.dinosaur),
                            contentDescription = "dinosour"
                        )
                    }

                    is DinosaurType.DinosaurKing -> {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight(),
                            painter = painterResource(id = R.drawable.dinosaur_king),
                            contentDescription = "dinosour"
                        )
                    }
                }
                Row(
                    Modifier.padding(start = 8.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text(
                        text = taskStatsState.toString(),
                        style = LocalTypography.current.displayLarge
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = " / month",
                        style = LocalTypography.current.titleMedium
                    )
                }


            }
        }

    }
}

@Composable
@Preview
fun TaskStatsPreview() {
    TaskStats(
        dinosaurType = DinosaurType.Dinosaur,
        taskStats = 10,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
            .padding(16.dp)
    )
}


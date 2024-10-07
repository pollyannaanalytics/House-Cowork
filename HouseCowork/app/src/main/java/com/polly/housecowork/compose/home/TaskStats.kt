package com.polly.housecowork.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    Card(modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalColorScheme.current.primary,
        ),

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.Bottom,
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = taskStatsState.toString(),
                        style = LocalTypography.current.displayLarge,
                        color = LocalColorScheme.current.onBackground
                    )
                    Column(
                        Modifier.padding(start = 8.dp),
                    ) {
                        Text("tasks", color = LocalColorScheme.current.onBackground)
                        Text("/ month", color = LocalColorScheme.current.onBackground)
                    }
                }
                Column(modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                    ) {
                    when (dinosaurTypeState) {
                        is DinosaurType.Egg -> {
                            Image(
                                painter = painterResource(id = R.drawable.egg),
                                contentDescription = "dinosour"
                            )
                        }

                        is DinosaurType.EggOut -> {
                            Image(
                                painter = painterResource(id = R.drawable.egg_out),
                                contentDescription = "dinosour"
                            )
                        }

                        is DinosaurType.Dinosaur -> {
                            Image(
                                painter = painterResource(id = R.drawable.dinosaur),
                                contentDescription = "dinosour"
                            )
                        }

                        is DinosaurType.DinosaurKing -> {
                            Image(
                                painter = painterResource(id = R.drawable.dinosaur_king),
                                contentDescription = "dinosour"
                            )
                        }
                    }
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


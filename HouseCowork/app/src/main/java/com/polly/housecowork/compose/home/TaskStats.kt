package com.polly.housecowork.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.R
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.DinosaurType
import com.polly.housecowork.utils.ComposeUtils

@Composable
fun TaskStats(
    modifier: Modifier = Modifier,
    taskStats: Int,
    dinosaurType: DinosaurType,
) {

    Card(
        modifier,
        colors = CardDefaults.cardColors(
            containerColor = LocalColorScheme.current.primary,
        )
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TaskCountSection(
                modifier = Modifier.padding(end = ComposeUtils.Padding),
                taskCount = taskStats
            )
            DinosaurImage(
                modifier = Modifier.fillMaxHeight().padding(ComposeUtils.Padding),
                dinosaurType = dinosaurType
            )
        }
    }
}

@Composable
fun TaskCountSection(
    modifier: Modifier,
    taskCount: Int
) {
    Row(
        modifier,
        verticalAlignment = androidx.compose.ui.Alignment.Bottom
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = taskCount.toString(),
            style = LocalTypography.current.displayLarge,
            color = LocalColorScheme.current.onBackground
        )
        Column(Modifier.padding(start = 8.dp),) {
            Text("tasks", color = LocalColorScheme.current.onBackground)
            Text("/ month", color = LocalColorScheme.current.onBackground)
        }

    }
}

@Composable
fun DinosaurImage(
    modifier: Modifier,
    dinosaurType: DinosaurType
) {
    val imageRes = when (dinosaurType) {
        DinosaurType.Egg -> R.drawable.egg
        DinosaurType.EggOut -> R.drawable.egg_out
        DinosaurType.Dinosaur -> R.drawable.dinosaur
        DinosaurType.DinosaurKing -> R.drawable.dinosaur_king
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "dinosour",
        modifier = modifier
    )
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


@Composable
@Preview
fun TaskCountSectionPreview() {
    TaskCountSection(
        taskCount = 10,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

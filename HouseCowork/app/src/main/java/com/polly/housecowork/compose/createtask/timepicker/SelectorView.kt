package com.polly.housecowork.compose.createtask.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme


@Composable
fun SelectorView(modifier: Modifier = Modifier, offset: Int) {
    Column(
        modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .weight(offset.toFloat())
                .fillMaxWidth()
                .alpha(0.7f)
                .background(LocalColorScheme.current.background),
        )


        Column(
            modifier = Modifier
                .weight(1.13f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(0.5.dp)
                    .alpha(0.5f)
                    .background(LocalColorScheme.current.onBackground)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .height(0.5.dp)
                    .alpha(0.5f)
                    .background(LocalColorScheme.current.onBackground)
                    .fillMaxWidth()
            )

        }



        Box(
            modifier = Modifier
                .weight(offset.toFloat())
                .fillMaxWidth()
                .alpha(0.7f)
                .background(LocalColorScheme.current.background)

        ,
        )
    }
}
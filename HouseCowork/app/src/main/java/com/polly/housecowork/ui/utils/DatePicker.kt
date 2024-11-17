package com.polly.housecowork.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.utils.ComposeUtils

@Composable
@ExperimentalMaterial3Api
fun HCWDatePicker(
    modifier: Modifier = Modifier,
    datePickerState: () -> DatePickerState,
) {
    Card(
        modifier,
        shape = RoundedCornerShape(16.dp),
    ) {
    DatePicker(
        modifier = Modifier
            .background(LocalColorScheme.current.surface)
            .fillMaxWidth(),
        state = datePickerState(),
        colors = DatePickerDefaults.colors(
            titleContentColor = LocalColorScheme.current.onBackground,
            headlineContentColor = LocalColorScheme.current.onBackground,
            selectedDayContainerColor = LocalColorScheme.current.onPrimary,
            dayContentColor = LocalColorScheme.current.onBackground,
            selectedDayContentColor = Color.White,
        ),
    )
    }

}

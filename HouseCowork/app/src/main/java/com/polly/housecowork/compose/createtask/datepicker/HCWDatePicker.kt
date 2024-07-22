package com.polly.housecowork.compose.createtask.datepicker

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme

@Composable
@ExperimentalMaterial3Api
fun HCWDatePicker(
    modifier: Modifier = Modifier,
    datePickerState: () -> DatePickerState,
) {
    Card(
        modifier
            .shadow(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .background(LocalColorScheme.current.background)
                .fillMaxWidth()
        ) {
            DatePicker(
                modifier = Modifier
                    .background(LocalColorScheme.current.surface)
                    .fillMaxWidth(),
                state = datePickerState(),
                colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = LocalColorScheme.current.onPrimary,
                    dayContentColor = LocalColorScheme.current.onBackground,
                    selectedDayContentColor = Color.White,
                ),
            )
        }

    }
}


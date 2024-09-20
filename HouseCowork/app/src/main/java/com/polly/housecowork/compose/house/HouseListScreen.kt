package com.polly.housecowork.compose.house

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.dataclass.House
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.viewmodel.HouseListViewModel

@Composable
fun HouseListScreen (
    modifier: Modifier = Modifier,
    viewModel: HouseListViewModel = hiltViewModel(),
    navigateToHouseDetail: (House) -> Unit = {}
){
    val houseList by viewModel.houseList.collectAsState()

    Column(modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = "My House List", style = LocalTypography.current.titleMedium
        )
        LazyColumn(
            verticalArrangement =  Arrangement.Center
        ) {
            items(houseList.size) { index ->
                Card(
                    modifier = Modifier
                        .clickable { navigateToHouseDetail(houseList[index]) }
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                    containerColor = LocalColorScheme.current.primary)
                ) {
                    Row(modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = houseList[index].name, style = LocalTypography.current.headlineMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = houseList[index].members.toString(), style = LocalTypography.current.titleSmall
                        )
                    }

                }
            }

        }
    }
}

@Preview
@Composable
fun HouseScreenPreview() {
    HouseListScreen()
}
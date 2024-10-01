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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.viewmodel.HouseListViewModel

@Composable
fun HouseListScreen (
    modifier: Modifier = Modifier,
    viewModel: HouseListViewModel = hiltViewModel(),
    navigateToJoinHouse: () -> Unit = {},
    navigateToCreateHouse: () -> Unit = {}
){
    val houseList by viewModel.houseList.collectAsState()
    var isAddHouseClick by remember{ mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                if (isAddHouseClick) {
                    AddHouseOption(
                        text = "Create a new House",
                        optionOnClick = {
                            isAddHouseClick = !isAddHouseClick
                            navigateToJoinHouse()
                        }
                    )
                    AddHouseOption(
                        text = "Join a House",
                        optionOnClick = {
                            isAddHouseClick = !isAddHouseClick
                            navigateToCreateHouse()

                        }
                    )

                }
                FloatingActionButton(
                    containerColor = LocalColorScheme.current.onPrimary,
                    shape = CircleShape,
                    contentColor = Color.White,
                    onClick = { isAddHouseClick = !isAddHouseClick }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create a new House"
                    )
                }
            }
        },

    ) { contentPadding ->
        Column(modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = "My House List", style = LocalTypography.current.titleMedium
            )
            LazyColumn(
                verticalArrangement =  Arrangement.Center
            )      {
                items(houseList.size) { index ->
                    Card(
                        modifier = Modifier
                            .clickable { navigateToJoinHouse() }
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

}

@Composable
fun AddHouseOption(modifier: Modifier = Modifier,
                   text: String = "",
                   optionOnClick: () -> Unit = {}
                   ) {
    Card(
        modifier = modifier.fillMaxWidth(0.5f)
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable { optionOnClick() },
        colors = CardDefaults.cardColors(
            containerColor = LocalColorScheme.current.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            style = LocalTypography.current.titleMedium,
            textAlign = TextAlign.Center,
            text = text)
    }
}

@Preview
@Composable
fun HouseScreenPreview() {
    HouseListScreen()
}
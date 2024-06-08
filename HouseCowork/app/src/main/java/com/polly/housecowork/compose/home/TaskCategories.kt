package com.polly.housecowork.compose.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.dataclass.Categories
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.SummaryCard

@Composable
fun TaskCategories(
    modifier: Modifier = Modifier,
    categoryList: List<Categories> = listOf()
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Task Categories",
            style = LocalTypography.current.titleMedium)
        LazyVerticalGrid(
            modifier = Modifier.padding(top = 8.dp),
            columns = GridCells.Fixed(2)) {
            items(categoryList.size) { index ->
               SummaryCard(
                   modifier = Modifier.padding(8.dp),
                   number = categoryList[index].taskNumber,
                   description = categoryList[index].categoryDescription
               )
            }
        }
    }
}

@Composable
@Preview
fun TaskCategoriesPreview() {
    val categories = listOf(
        Categories(
            taskNumber = 10,
            categoryDescription = "Cleaning",

            ),
        Categories(
            taskNumber = 5,
            categoryDescription = "Cooking"
        ),
        Categories(
            taskNumber = 3,
            categoryDescription = "Shopping"
        ),
        Categories(
            taskNumber = 2,
            categoryDescription = "Laundry"
        )
    )
    TaskCategories(categoryList = categories)
}


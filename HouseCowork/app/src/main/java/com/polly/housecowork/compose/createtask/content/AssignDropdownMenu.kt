package com.polly.housecowork.compose.createtask.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignDrawer(
    modifier: Modifier,
    itemList: List<String>,
    onAssigneeClick: (String) -> Unit
) {
    var shouldExpanded by remember { mutableStateOf(false) }
    var selectedUserName: String by remember {
        mutableStateOf("everyone")
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .wrapContentWidth(), text = "Assign to",
            style = LocalTypography.current.bodySmall
        )
        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(start = 16.dp)
                .border(1.dp, LocalColorScheme.current.secondary, RoundedCornerShape(8.dp))
                .background(LocalColorScheme.current.surface, RoundedCornerShape(8.dp))
            ,
            expanded = shouldExpanded,
            onExpandedChange = {
                shouldExpanded = !shouldExpanded
            }) {
            DefaultAssignItem(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                selectedUser = { selectedUserName },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = shouldExpanded)
                }
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .background(LocalColorScheme.current.surface),
                expanded = shouldExpanded,
                onDismissRequest = { shouldExpanded = false }) {
                itemList.forEachIndexed { index, userName ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LocalColorScheme.current.surface),
                        text = { Text(text = userName) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Done,
                                contentDescription = "Check"
                            )
                        },
                        onClick = {
                            onAssigneeClick(userName)
                            selectedUserName = userName
                            shouldExpanded = false
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = LocalColorScheme.current.onBackground,
                            leadingIconColor = LocalColorScheme.current.onBackground,
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun DefaultAssignItem(
    modifier: Modifier = Modifier,
    selectedUser: () -> String,
    trailingIcon: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(LocalColorScheme.current.surface, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = selectedUser(),
            )

            trailingIcon()
        }
    }
}

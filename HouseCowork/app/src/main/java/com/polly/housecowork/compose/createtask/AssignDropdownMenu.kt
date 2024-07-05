package com.polly.housecowork.compose.createtask

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
fun AssignDrawer(modifier: Modifier, itemList: List<String>, onItemClick: (Int) -> Unit) {
    var shouldExpanded by remember { mutableStateOf(false) }
    val (selectedUser, setSelectedUser) = remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .wrapContentWidth(), text = "Assign to",
            style = LocalTypography.current.titleMedium
        )
        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .border(1.dp, LocalColorScheme.current.secondary, RoundedCornerShape(8.dp))
                .background(LocalColorScheme.current.surface, RoundedCornerShape(8.dp)),
            expanded = shouldExpanded,
            onExpandedChange = {
                shouldExpanded = !shouldExpanded
            }) {
            DefaultAssignItem(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                selectedUserName = selectedUser,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = shouldExpanded)
                }
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .background(LocalColorScheme.current.surface),
                expanded = shouldExpanded,
                onDismissRequest = { shouldExpanded = false }) {
                itemList.forEachIndexed { index, user ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LocalColorScheme.current.surface),
                        text = { Text(text = user) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = "User Icon"
                            )
                        },
                        onClick = {
                            setSelectedUser(user)
                            onItemClick(index)
                            shouldExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DefaultAssignItem(
    modifier: Modifier = Modifier,
    selectedUserName: String,
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

            if (selectedUserName.isEmpty()) {
                Text(modifier = Modifier.padding(start = 4.dp), text = "everyone")
            } else {
                Text(modifier = Modifier.padding(start = 4.dp), text = selectedUserName)
            }
            trailingIcon()
        }
    }
}

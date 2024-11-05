package com.polly.housecowork.compose.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.compose.BadgeDollarSign
import com.polly.housecowork.ui.utils.compose.Chat
import com.polly.housecowork.utils.StepState


@Composable
fun HCWBtmNavBar(
    onNavigateClick: (StepState) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxWidth()){
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
            ,
            colors = CardDefaults.cardColors(contentColor = LocalColorScheme.current.primary, containerColor = LocalColorScheme.current.primary),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(
                topStart = 25.dp,
                topEnd = 25.dp
            )){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround)  {

                BottomNavItem(
                    itemData = BottomNavItemData(
                        imageVector = Chat,
                        selected = true,
                        onClick = {
                            onNavigateClick(StepState.Chat)
                        },
                        label = "Chat"
                    )
                )
                BottomNavItem(
                    itemData = BottomNavItemData(
                        imageVector = BadgeDollarSign,
                        selected = false,
                        onClick = {
                            onNavigateClick(StepState.Money)
                        },
                        label = "Money"
                    )
                )

                Spacer(modifier = Modifier.size(64.dp))

                BottomNavItem(
                    itemData = BottomNavItemData(
                        imageVector = Icons.Filled.Home,
                        selected = false,
                        onClick = {
                            onNavigateClick(StepState.Home)
                        },
                        label = "Home"
                    )
                )
                BottomNavItem(
                    itemData = BottomNavItemData(
                        imageVector = Icons.Filled.AccountCircle,
                        selected = false,
                        onClick = {
                            onNavigateClick(StepState.Profile)
                        },
                        label = "Profile"
                    )
                )
            }


        }

        LargeFloatingActionButton(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
                .padding(vertical = 4.dp)
                .border(width = 8.dp, shape = CircleShape, color = LocalColorScheme.current.onPrimary)
            ,
            onClick = { onNavigateClick(StepState.CreateTask) },
            shape = CircleShape,
            containerColor = Color.White,
            contentColor = LocalColorScheme.current.onPrimary
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = LocalColorScheme.current.onPrimary,
            )
        }

    }
}

@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    itemData: BottomNavItemData,
    itemPadding: Dp = 8.dp
    ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                itemData.onClick()
            }
            .padding(vertical = itemPadding)

    ) {
        Icon(
            imageVector = itemData.imageVector,
            tint = Color.White,
            contentDescription = itemData.label,
            )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = itemData.label,
            color =  Color.White,
            style = LocalTypography.current.bodySmall,
        )
    }
}

data class BottomNavItemData(
    val imageVector: ImageVector, // For Material Icons
    val drawableResId: Int? = null, // For Drawable Resources
    val selected: Boolean = false,
    val onClick: () -> Unit,
    val label: String
)

@Preview
@Composable
fun HCWBtmNavBarPreview() {
    HCWBtmNavBar()
}
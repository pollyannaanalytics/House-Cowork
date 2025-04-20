package com.polly.housecowork.compose.house

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.polly.housecowork.dataclass.HouseMember
import com.polly.housecowork.ui.theme.DeepYellow
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.StandardButton
import com.polly.housecowork.ui.utils.compose.Linked_camera
import com.polly.housecowork.viewmodel.HouseDetailViewModel

@Composable
fun HouseDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: HouseDetailViewModel = hiltViewModel(),
    houseId: Int
) {
    viewModel.fetchHouseDetail(houseId)
    val houseDetail by viewModel.houseDetail.collectAsStateWithLifecycle()

    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HouseIntroSection(
                description = houseDetail.description,
                rules = houseDetail.rules
            )
        }

        item {
            MemberSection(
                memberIds = houseDetail.memberIds
            )
        }
        item {
            AddMemberButton()
        }
    }
}

@Composable
fun HouseIntroSection(
    modifier: Modifier = Modifier,
    description: String,
    rules: List<String>
) {
    Column(modifier.fillMaxWidth()) {
        Text(
            text = "About the House",
            style = LocalTypography.current.titleMedium,
            color = Color.Black,
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(16.dp))

        Column(
            modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(15.dp)
                )
                .border(
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(Color.White)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = description,
                style = LocalTypography.current.bodyMedium,
                color = Color.Black,
                textAlign = TextAlign.Start
            )

            rules.forEach { rule ->
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = rule,
                    style = LocalTypography.current.bodyMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
fun MemberSection(
    modifier: Modifier = Modifier,
    memberIds: List<Int>
) {
    Column(modifier.fillMaxWidth()) {
        Text(
            text = "Members (${memberIds.size})",
            style = LocalTypography.current.titleMedium,
            color = Color.Black,
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(16.dp))
        val mockMembers = listOf(
            HouseMember(0, "Jumbo", ""),
            HouseMember(1, "Polly", ""),
            HouseMember(2, "Chloe", ""),
            HouseMember(3, "Rol", ""),
        )
        mockMembers.forEach { member ->
            MemberCard(member)
        }
    }
}

@Composable
fun MemberCard(
    member: HouseMember
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(LocalColorScheme.current.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MemberAvatar(avatarUrl = member.avatarUrl)
        Spacer(Modifier.width(24.dp))
        Text(
            text = member.name,
            style = LocalTypography.current.bodyLarge,
            color = Color.Black,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun MemberAvatar(
    modifier: Modifier = Modifier,
    avatarUrl: String
) {
    Card(
        modifier
            .fillMaxWidth(0.15f)
            .size(54.dp)
            .aspectRatio(1f)
            .clip(shape = CircleShape),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD9D9D9),
            contentColor = Color.White
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center

        ) {
            if (avatarUrl.isEmpty()) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize(0.6f),
                    imageVector = Linked_camera,
                    contentDescription = "Camera",
                )

            }
        }

    }
}

@Composable
fun AddMemberButton() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        StandardButton(
            buttonColor = DeepYellow,
            text = "+ Member",
            textColor = Color.White,
            textStyle = LocalTypography.current.bodyMedium
        ) {
            // TODO add member onclick
        }
    }
}
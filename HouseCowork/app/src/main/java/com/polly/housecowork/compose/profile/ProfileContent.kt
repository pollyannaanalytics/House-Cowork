package com.polly.housecowork.compose.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polly.housecowork.dataclass.CalendarUiModel
import com.polly.housecowork.dataclass.ProfileInfo
import com.polly.housecowork.dataclass.Task
import com.polly.housecowork.ui.CircularImage
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalShapes
import com.polly.housecowork.ui.theme.LocalTypography
import java.time.LocalDate

@Composable
fun ProfileContent(modifier: Modifier = Modifier, profileInfo: ProfileInfo?, tasks: List<Task>, dates: List<CalendarUiModel.Date>) {
    Scaffold(modifier) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(LocalColorScheme.current.background)
        ) {
            profileInfo?.let {
                ProfileAvatarName(
                    name = it.name,
                    photoUrl = it.imageUrl
                )
                ProfileBio(bio = it.bio)
                Calendar(
                    currentDateTitle = "November",
                    onBackClick = {},
                    onForwardClick = {},
                    dates = dates,
                    tasks = tasks
                )

            }

        }

    }
}

@Composable
fun ProfileAvatarName(modifier: Modifier = Modifier, name: String, photoUrl: String) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        CircularImage(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.3f),
            imageUrl = photoUrl,
            contentDescription = "profile image"
        )
        ProfileName(name = name)
    }
}


@Composable
fun ProfileName(name: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = name,
            style = LocalTypography.current.headlineMedium
        )
        EditButton(
            onClick = {}
        )

    }
}

@Composable
fun EditButton(onClick: () -> Unit = {}) {
    TextButton (
        modifier = Modifier.width(80.dp),
        onClick = onClick,
        border = BorderStroke(1.dp, LocalColorScheme.current.secondary),
        shape = LocalShapes.current.medium
    ) {
        Text(
            text = "Edit",
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            style = LocalTypography.current.bodySmall,
            color = LocalColorScheme.current.secondary
        )
    }
}

@Composable
fun ProfileBio(bio: String) {
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = "Bio",
        style = LocalTypography.current.bodyLarge
    )
    Column(
        modifier = Modifier
            .heightIn(min = 100.dp, max = 150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(LocalShapes.current.medium)
            .background(LocalColorScheme.current.primary)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = bio,
            maxLines = 5,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            style = LocalTypography.current.bodySmall,
            softWrap = true
        )
    }


}

@Composable
@Preview
fun ProfileContentPreview() {
    ProfileContent(
        profileInfo = ProfileInfo(
            name = "王大明",
            nickName = "小明",
            bio = "喜歡攝影和旅行的工程師fasdfasdfasdfasdfadsfasdfasdfasdfadsfasdfjalsjdfoiajfoiajwefoiawjfeowjeofijweiofjoiajefoaiwejfioawjeoifjoaiwjefiojawieofjioajfoiwjfiowjeofijwoiefrjiqoweiroqjwoeirjqoiewrjiqojeroiqjweiorjqoierjiowjreoijwiroe",
            imageUrl = "https://i.pravatar.cc/150?img=1",
            bankAccount = "012-345-678912",
            email = "ming.wang@example.com",
            updateTime = System.currentTimeMillis()
        ),
        tasks = emptyList(),
        dates = listOf(
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
            CalendarUiModel.Date(
                date = LocalDate.now(),
                isSelected = false,
                isToday = false
            ),
        ),
    )
}
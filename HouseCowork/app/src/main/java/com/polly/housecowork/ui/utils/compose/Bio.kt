package com.polly.housecowork.ui.utils.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalShapes
import com.polly.housecowork.ui.theme.LocalTypography

@Composable
fun Bio(bio: String, isEditMode: Boolean, onBioChange: (String) -> Unit = {}, errState: Boolean) {

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "Your Bio",
            style = LocalTypography.current.bodyLarge
        )

        if (isEditMode) {
            BioEditContent(
                bio = bio,
                onBioChange = onBioChange,
                errState = errState
            )
        } else {
            BioRegularContent(
                bio = bio
            )
        }
    }
}

@Composable
fun BioRegularContent(
    modifier: Modifier = Modifier,
    bio: String
) {
    Box(
        modifier = modifier
            .heightIn(min = 100.dp, max = 150.dp)
            .clip(LocalShapes.current.medium)
            .background(LocalColorScheme.current.primary)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = bio,
            maxLines = 5,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            style = LocalTypography.current.bodySmall,
            softWrap = true
        )
    }
}

@Composable
fun BioEditContent(
    modifier: Modifier = Modifier,
    onBioChange: (String) -> Unit = {},
    bio: String,
    errState: Boolean
) {
    var bioState by remember { mutableStateOf(bio) }
    val regularBorderColor = LocalColorScheme.current.secondary

    val borderColor by remember {
        derivedStateOf { if (errState) Color.Red else regularBorderColor }
    }

    Box(
        modifier = modifier
            .heightIn(min = 100.dp, max = 150.dp)
            .clip(LocalShapes.current.medium)
            .background(Color.White)
            .border(1.dp, borderColor, LocalShapes.current.medium)
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = bioState,
            onValueChange = { value ->
                bioState = value
                onBioChange(value)
            },
            textStyle = LocalTypography.current.bodySmall,
            maxLines = 1,

            )
    }
    if (errState) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = "*200 words limited",
            style = LocalTypography.current.labelSmall,
            color = Color.Red
        )
    }

}

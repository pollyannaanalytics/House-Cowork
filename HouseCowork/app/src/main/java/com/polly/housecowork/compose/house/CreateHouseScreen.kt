package com.polly.housecowork.compose.house

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.polly.housecowork.ui.theme.LocalTypography
import com.polly.housecowork.ui.utils.HCWTextField
import com.polly.housecowork.ui.utils.PositiveButton
import com.polly.housecowork.ui.utils.compose.Linked_camera
import com.polly.housecowork.viewmodel.CreateHouseViewModel

@Composable
fun CreateHouseScreen(
    modifier: Modifier = Modifier,
    createHouseViewModel: CreateHouseViewModel = hiltViewModel(),
    navigateOnClick: () -> Unit = {},
    onSuccess: (Int) -> Unit = {},
) {
    val houseInfo by createHouseViewModel.houseInfo.collectAsStateWithLifecycle()

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri ->
            createHouseViewModel.uploadImage(imageUri)
        }
    }

    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        HouseAvatarCard(Modifier.padding(16.dp),
            imageUrl =  houseInfo.imageUrl ,
            choosePhoto = {pickImageLauncher.launch("image/*")}
        )
        HCWTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            value = houseInfo.name,
            onValueChange = { createHouseViewModel.onNameChanged(it) },
            hint = "House Name",
            isError = houseInfo.isHouseNameErr,
        )
        if(houseInfo.isHouseNameErr){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = "*20 characters is limited.",
                style = LocalTypography.current.bodySmall,
                color = Color.Red,
                textAlign = TextAlign.Start
            )
        }
        HCWTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            value = houseInfo.description,
            onValueChange = {
                createHouseViewModel.onDescriptionChanged(it)
            },
            hint = "Description(Optional)",
        )
        HCWTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f)

                .padding(horizontal = 16.dp, vertical = 8.dp),
            value = houseInfo.rules,
            onValueChange = {
                createHouseViewModel.onRulesChanged(it)
            },
            hint = "House Rules(Optional)",
        )

        PositiveButton(
            modifier = Modifier.padding(top = 16.dp),
            text = "Done", textStyle = LocalTypography.current.titleMedium,
            onClick = {
                createHouseViewModel.houseDetail.value?.id?.let {
                    createHouseViewModel.createHouse {
                        onSuccess(it)
                    }
                }
            }
        )


    }
}

@Composable
fun HouseAvatarCard(modifier: Modifier, choosePhoto: () -> Unit, imageUrl: String) {
    Card(
        modifier
            .fillMaxWidth(0.25f)
            .aspectRatio(1f)
            .clip(shape = RoundedCornerShape(4.dp))
            .clickable { choosePhoto() }
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray,
            contentColor = Color.White
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center

        ) {
            if (imageUrl.isEmpty()) {
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

@Preview
@Composable
fun PreviewCreateHouseScreen() {
    CreateHouseScreen(modifier = Modifier)
}


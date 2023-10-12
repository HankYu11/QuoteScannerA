package com.axlt.quotescanner.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axlt.quotescanner.R
import com.axlt.quotescanner.getBitmap
import com.axlt.quotescanner.ui.components.ButtonGroup
import com.axlt.quotescanner.ui.components.ButtonGroupButton
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CollectionScreen(
    onNavigateToCamera: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA) { granted ->
        println("yas!!!!!")
    }
    var textCardString by remember { mutableStateOf("") }
    val contentResolver = LocalContext.current.contentResolver
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            uri?.let {
                getBitmap(it, contentResolver)?.let { bitmap ->
                    recognizer.process(bitmap, 0).addOnSuccessListener {
                        textCardString = it.text
                        println("${it.text} !!!!!!!!!!")
                    }
                }
            }
        }
    var isButtonGroupExpanded by remember {
        mutableStateOf(false)
    }

    val collectionButtons = listOf(
        object : ButtonGroupButton {
            override val iconId: Int
                get() = R.drawable.ic_pen
            override val text: String
                get() = "Manual"

            override fun onButtonClicked() {
                isButtonGroupExpanded = ! isButtonGroupExpanded
                // TODO: navigate to edit page
            }
        },
        object : ButtonGroupButton {
            override val iconId: Int
                get() = R.drawable.ic_camara
            override val text: String
                get() = "Camara"

            override fun onButtonClicked() {
                if (!cameraPermissionState.status.isGranted) {
                    cameraPermissionState.launchPermissionRequest()
                } else {
                    onNavigateToCamera()
                }
            }
        }, object : ButtonGroupButton {
            override val iconId: Int
                get() = R.drawable.ic_image
            override val text: String
                get() = "Album"

            override fun onButtonClicked() {
                println("clicked!!")
            }
        })

    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        Column() {
            Text(
                modifier = Modifier
                    .clickable {
                        galleryLauncher.launch("image/*")
                    }, text = "test button"
            )

            if (textCardString.isNotEmpty()) {
                TextCard(text = textCardString)
            }
        }

        if (isButtonGroupExpanded) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x55000000))
                    .clickable {
                        isButtonGroupExpanded = !isButtonGroupExpanded
                    }
            )
        }

        ButtonGroup(
            buttons = collectionButtons,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter),
            isExpanded = isButtonGroupExpanded,
            onMainButtonClicked = { isButtonGroupExpanded = !isButtonGroupExpanded },
        )
    }
}

@Composable
fun TextCard(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(20.dp)
            .background(Color.Gray)
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
fun CollectionScreenPreview() {
    CollectionScreen({})
}
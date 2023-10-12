package com.axlt.quotescanner.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropScreen(
    photoUri: String,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Icons.Default.ArrowBack
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            AsyncImage(modifier = Modifier.fillMaxSize(), model = photoUri, contentDescription = "photoUri")
        }
    }
}
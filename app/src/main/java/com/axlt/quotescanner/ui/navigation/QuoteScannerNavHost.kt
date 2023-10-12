package com.axlt.quotescanner.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.axlt.quotescanner.ui.screens.CameraView
import com.axlt.quotescanner.ui.screens.CollectionScreen
import com.axlt.quotescanner.ui.screens.CropScreen
import java.util.concurrent.Executors

@Composable
fun QuoteScannerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "collection"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("collection") {
            CollectionScreen(
                onNavigateToCamera = { navController.navigate("camera") },
            )
        }
        composable("camera") {
            CameraView(
                executor = Executors.newSingleThreadExecutor(),
                onError = { Log.e("hank", "View error:", it) },
                onImageCaptured = {
                    Log.d("hank", "uri: $it")
                    navController.navigate("crop/123")
                }
            )
        }
        composable("crop/{photoUri}",
            arguments = listOf(navArgument("photoUri") { type = NavType.StringType })
        ) {
            CropScreen(photoUri = it.arguments?.getString("photoUri")!!)
        }
    }
}
package com.example.shrinidhi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.shrinidhi.ui.screens.home.HomeScreen
import com.example.shrinidhi.ui.screens.scan.ScanScreen
import com.example.shrinidhi.ui.screens.library.DiseaseLibraryScreen
import com.example.shrinidhi.ui.screens.detail.DiseaseDetailScreen
import com.example.shrinidhi.ui.screens.onboarding.*
import com.example.shrinidhi.ui.screens.splash.SplashScreen
import kotlinx.coroutines.delay
import com.example.shrinidhi.ui.screens.scan.PredictionScreen

@Composable
fun CottonCareNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash,
        modifier = modifier
    ) {
        // Splash
        composable(NavRoutes.Splash) {
            SplashScreen()
            androidx.compose.runtime.LaunchedEffect(Unit) {
                delay(3000)
                navController.navigate(NavRoutes.Welcome) {
                    popUpTo(NavRoutes.Splash) { inclusive = true }
                }
            }
        }

        // Onboarding
        composable(NavRoutes.Welcome) { WelcomeScreen(navController) }
        composable(NavRoutes.Detect) { DetectScreen(navController) }
        composable(NavRoutes.LibraryIntro) { LibraryIntroScreen(navController) }
        composable(NavRoutes.RemediesIntro) { RemediesIntroScreen(navController) }

        // Main
        composable(NavRoutes.Home) { HomeScreen(navController) }
        composable(NavRoutes.Scan) { ScanScreen(navController) }
        composable(NavRoutes.Library) { DiseaseLibraryScreen(navController) }
        composable("${NavRoutes.Prediction}?uri={uri}") { backStackEntry ->
            val uri = backStackEntry.arguments?.getString("uri")
            PredictionScreen(navController, uri)
        }

        // Detail
        composable(
            route = "${NavRoutes.DiseaseDetail}/{diseaseName}",
            arguments = listOf(navArgument("diseaseName") { type = NavType.StringType })
        ) { backStackEntry ->
            val diseaseName = backStackEntry.arguments?.getString("diseaseName") ?: ""
            DiseaseDetailScreen(diseaseName = diseaseName, navController = navController)
        }
    }
}
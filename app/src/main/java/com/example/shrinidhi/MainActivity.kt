package com.example.shrinidhi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shrinidhi.navigation.CottonCareNavGraph
import com.example.shrinidhi.navigation.NavRoutes
import com.example.shrinidhi.ui.components.BottomBar
import com.example.shrinidhi.ui.theme.ShrinidhiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShrinidhiTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                // ✅ Safely handle routes with arguments
                val currentRoute = navBackStackEntry?.destination?.route
                    ?.substringBefore("/") ?: NavRoutes.Splash

                // ✅ Decide when to show bottom bar
                val showBottomBar = when (currentRoute) {
                    NavRoutes.Home, NavRoutes.Scan, NavRoutes.Library -> true
                    else -> false
                }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomBar(navController, currentRoute)
                        }
                    }
                ) { innerPadding ->
                    CottonCareNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

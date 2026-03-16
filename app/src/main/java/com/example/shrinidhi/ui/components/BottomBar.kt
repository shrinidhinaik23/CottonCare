package com.example.shrinidhi.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shrinidhi.navigation.NavRoutes

@Composable
fun BottomBar(navController: NavController, currentRoute: String?) {
    val items = listOf(
        BarItem("Home", Icons.Filled.Home, NavRoutes.Home),
        BarItem("Scan", Icons.Filled.QrCodeScanner, NavRoutes.Scan),
        BarItem("Library", Icons.Filled.LibraryBooks, NavRoutes.Library),
    )

    NavigationBar {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (navController.currentDestination?.route != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF4CAF50),
                    selectedTextColor = Color(0xFF4CAF50),
                    indicatorColor = Color(0x224CAF50)
                )
            )
        }
    }
}

private data class BarItem(val label: String, val icon: ImageVector, val route: String)

private val NavController.currentDestination: NavDestination?
    get() = currentBackStackEntry?.destination
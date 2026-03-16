package com.example.shrinidhi.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shrinidhi.R
import com.example.shrinidhi.navigation.NavRoutes
import com.example.shrinidhi.ui.components.PrimaryButton
import com.example.shrinidhi.ui.theme.TextSecondary

@Composable
fun RemediesIntroScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.height(16.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.ic_shield),
                contentDescription = null,
                modifier = Modifier.size(96.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text("Learn Remedies & Prevention", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "Get actionable advice to treat and prevent cotton diseases effectively.",
                color = TextSecondary
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = {
                navController.navigate(NavRoutes.Home) {
                    popUpTo(NavRoutes.Welcome) { inclusive = true }
                }
            }) {
                Text("Skip")
            }
            PrimaryButton(text = "Get Started", onClick = {
                navController.navigate(NavRoutes.Home) {
                    popUpTo(NavRoutes.Welcome) { inclusive = true }
                }
            })
        }
    }
}

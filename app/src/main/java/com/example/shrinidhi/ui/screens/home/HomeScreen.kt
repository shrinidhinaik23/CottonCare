package com.example.shrinidhi.ui.screens.home

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shrinidhi.R
import com.example.shrinidhi.model.ScanHistoryItem
import com.example.shrinidhi.ui.theme.TextSecondary
import com.example.shrinidhi.utils.ScanHistoryManager
import androidx.core.net.toUri

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val manager = remember { ScanHistoryManager(context) }

    var historyList by remember { mutableStateOf(emptyList<ScanHistoryItem>()) }

    LaunchedEffect(Unit) {
        historyList = manager.getHistory().reversed() // newest first
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // ---------------------------------------------------------
        // 👋 HEADER
        // ---------------------------------------------------------
        Text("Hello, Farmer! 👋", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Monitor your cotton plant health", color = TextSecondary)



        // ---------------------------------------------------------
        // 🌿 QUICK HEALTH CHECK (SCAN BUTTON)
        // ---------------------------------------------------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Quick Health Check", fontWeight = FontWeight.Bold)
                Text("Scan your cotton plant now", color = TextSecondary)

                Button(
                    onClick = { navController.navigate("scan") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_scan_button),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Start Scanning", color = Color.White)
                }
            }
        }



        // ---------------------------------------------------------
        // 💡 DAILY TIP
        // ---------------------------------------------------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("💡 Daily Tip", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text(
                    "Morning light gives the best leaf clarity for accurate scanning.",
                    color = TextSecondary
                )
            }
        }



        // ---------------------------------------------------------
        // 📜 RECENT SCAN HISTORY (BOTTOM)
        // ---------------------------------------------------------
        Text("📜 Recent Scan History", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth().height(250.dp)
        ) {
            items(historyList) { item ->

                val bmp = remember(item.imageUri) {
                    try {
                        val input = context.contentResolver.openInputStream(item.imageUri.toUri())
                        BitmapFactory.decodeStream(input)
                    } catch (e: Exception) {
                        null
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Image thumbnail
                        bmp?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.size(70.dp)
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text("Disease: ${item.result}", fontWeight = FontWeight.SemiBold)
                            Text("Date: ${item.date}", color = TextSecondary)
                        }
                    }
                }
            }
        }
    }
}

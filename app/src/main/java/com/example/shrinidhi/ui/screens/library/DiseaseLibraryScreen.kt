package com.example.shrinidhi.ui.screens.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shrinidhi.navigation.NavRoutes
import com.example.shrinidhi.ui.theme.TextSecondary

data class Disease(
    val name: String,
    val description: String,
    val severity: String,
    val symptoms: List<String>,
    val causes: List<String>,
    val treatment: List<String>,
    val prevention: List<String>
)

@Composable
fun DiseaseLibraryScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }

    val allDiseases = listOf(
        Disease(
            name = "Healthy Plant ",
            description = "No disease detected",
            severity = "low",
            symptoms = listOf(
                "✅ Leaves look green and fresh",
                "✅ No spots or curling",
                "✅ Stem is strong and plant grows well"
            ),
            causes = listOf("✅ Nothing bad — plant is healthy"),
            treatment = listOf("✅ No medicine needed"),
            prevention = listOf(
                "🌱 Keep soil healthy with compost",
                "👀 Check plants often to catch problems early"
            )
        ),

        Disease(
            name = "Bacterial Blight ",
            description = "Tiny germs attack cotton leaves",
            severity = "high",
            symptoms = listOf(
                "⚠️ Wet, square‑shaped spots on leaves",
                "⚠️ Spots turn dark with yellow edges",
                "⚠️ Spots follow the leaf veins",
                "⚠️ Leaves may dry up and fall",
                "⚠️ Black marks on stems and cotton balls"
            ),
            causes = listOf(
                "🌾 Dirty or infected seeds",
                "🌧️ Too much rain and wet leaves",
                "🌬️ Plants too close together, no air"
            ),
            treatment = listOf(
                "🧴 Spray copper medicine (safe spray; copper kills germs on leaves)",
                "🗑️ Remove sick leaves and throw them away",
                "💧 Water the soil, not the leaves"
            ),
            prevention = listOf(
                "🌾 Use clean, certified seeds",
                "🔄 Change crops each year (crop rotation)",
                "🌬️ Leave space between plants for airflow"
            )
        ),

        Disease(
            name = "Fusarium Wilt ",
            description = "Fungus living in the soil",
            severity = "high",
            symptoms = listOf(
                "⚠️ Bottom leaves turn yellow first",
                "⚠️ Plant stops growing and looks weak",
                "⚠️ Brown lines inside the stem when cut"
            ),
            causes = listOf(
                "🦠 Fungus stays in soil for many years",
                "💧 Too much water or poor drainage",
                "🌱 Weak soil health"
            ),
            treatment = listOf(
                "🧴 Use fungus medicine (fungicide) early in soil",
                "🌾 Plant cotton types that resist fungus",
                "🌱 Add compost to make soil strong"
            ),
            prevention = listOf(
                "🔄 Rotate crops with cereals or other plants",
                "💧 Do not overwater; keep soil drained",
                "🧹 Clean tools before use"
            )
        ),

        Disease(
            name = "Cotton Leaf Curl Virus ",
            description = "Spread by tiny insects called whiteflies",
            severity = "high",
            symptoms = listOf(
                "⚠️ Leaves curl tightly and look thick",
                "⚠️ Plant stays small and weak",
                "⚠️ Fewer cotton balls grow"
            ),
            causes = listOf(
                "🐞 Whiteflies carry the virus from plant to plant",
                "🌧️ Warm, wet weather helps whiteflies grow",
                "🌾 Sick plants nearby spread the virus"
            ),
            treatment = listOf(
                "🧴 Spray bug medicine to kill whiteflies",
                "🗑️ Remove and destroy sick plants",
                "🌾 Plant cotton that resists the virus"
            ),
            prevention = listOf(
                "👀 Watch leaves often for whiteflies",
                "✨ Use shiny mulch or sticky traps to stop whiteflies",
                "🚫 Do not plant near infected crops or weeds"
            )
        )
    )

    val filtered = allDiseases.filter {
        it.name.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        item {
            Text("Disease Library", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Learn about cotton diseases", color = TextSecondary)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search diseases...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
        }

        items(filtered) { disease ->
            val bg = when (disease.severity.lowercase()) {
                "high" -> Color(0xFFFFEBEE)
                "medium" -> Color(0xFFFFF3E0)
                else -> Color(0xFFF1F8E9)
            }
            val chipColor = when (disease.severity.lowercase()) {
                "high" -> Color(0xFFD32F2F)
                "medium" -> Color(0xFFF57C00)
                else -> Color(0xFF388E3C)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("${NavRoutes.DiseaseDetail}/${disease.name}")
                    },
                colors = CardDefaults.cardColors(containerColor = bg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(disease.name, fontWeight = FontWeight.Bold)
                        AssistChip(
                            onClick = {},
                            label = { Text(disease.severity.uppercase()) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = chipColor.copy(alpha = 0.12f),
                                labelColor = chipColor
                            )
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(disease.description, color = TextSecondary)
                }
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}
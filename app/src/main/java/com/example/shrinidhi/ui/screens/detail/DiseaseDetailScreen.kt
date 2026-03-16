package com.example.shrinidhi.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shrinidhi.ui.screens.library.Disease
import com.example.shrinidhi.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseDetailScreen(diseaseName: String, navController: NavController) {
    val allDiseases = listOf(
        Disease(
            name = "Healthy Plant",
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
            name = "Bacterial Blight",
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
                "🧴 Spray copper medicine (Kocide 3000, Graliz Bactriguard)",
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
            name = "Fusarium Wilt",
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
                "🧴 Use fungus medicine (Propiconazole, Carbendazim)",
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
            name = "Cotton Leaf Curl Virus",
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
                "🧴 Spray bug medicine (Imidacloprid, Thiamethoxam, Acetamiprid)",
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

    // ✅ Safe lookup
    val disease = allDiseases.find {
        it.name.trim().equals(diseaseName.trim(), ignoreCase = true)
    }

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(disease?.name ?: "Disease Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (disease == null) {
            Column(Modifier.padding(16.dp)) {
                Text("❌ Disease not found.", color = MaterialTheme.colorScheme.error)
                Text("Please check the name or try again.", color = TextSecondary)
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(disease.name, style = MaterialTheme.typography.titleLarge)
                Text(disease.description, fontStyle = FontStyle.Italic, color = TextSecondary)
                Spacer(Modifier.height(8.dp))

                val tabs = listOf("Symptoms", "Causes", "Treatment", "Prevention")
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                val content = when (selectedTab) {
                    0 -> disease.symptoms
                    1 -> disease.causes
                    2 -> disease.treatment
                    3 -> disease.prevention
                    else -> emptyList()
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    content.forEach { item ->
                        Text(item, color = TextSecondary)
                    }
                }
            }
        }
    }
}

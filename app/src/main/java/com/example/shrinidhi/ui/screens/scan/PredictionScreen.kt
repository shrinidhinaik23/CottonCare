package com.example.shrinidhi.ui.screens.scan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.shrinidhi.model.ScanHistoryItem
import com.example.shrinidhi.navigation.NavRoutes
import com.example.shrinidhi.ui.theme.TextSecondary
import com.example.shrinidhi.utils.ScanHistoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat
import java.util.*

data class DiseaseInfo(
    val index: Int,
    val severity: String,
    val remedy: String
)

@Composable
fun PredictionScreen(navController: NavController, uri: String?) {

    val context = LocalContext.current
    val historyManager = remember { ScanHistoryManager(context) }

    var result by remember { mutableStateOf("Analyzing image...") }
    var confidence by remember { mutableFloatStateOf(0f) }
    var isLoading by remember { mutableStateOf(true) }

    val bitmap = remember(uri) {
        uri?.let {
            try {
                val stream = context.contentResolver.openInputStream(it.toUri())
                BitmapFactory.decodeStream(stream)
            } catch (e: Exception) {
                null
            }
        }
    }

    LaunchedEffect(bitmap) {
        bitmap?.let { bmp ->

            try {
                isLoading = true

                // Optional cotton green check
                if (!isCottonLeaf(bmp)) {
                    result = "❌ This does not look like a cotton leaf."
                    isLoading = false
                    return@LaunchedEffect
                }

                val prediction = withContext(Dispatchers.Default) {

                    val interpreter = Interpreter(
                        loadModelFile(context, "cotton_disease_mobilenet.tflite")
                    )

                    val labels = loadLabels(context)
                    val input = preprocessImage(bmp)

                    val output = Array(1) { FloatArray(labels.size) }
                    interpreter.run(input, output)
                    interpreter.close()

                    val bestIndex =
                        output[0].indices.maxByOrNull { output[0][it] } ?: -1

                    confidence = output[0][bestIndex] * 100f

                    val foundLabel =
                        labels.entries.firstOrNull { it.value.index == bestIndex }?.key

                    val info = labels[foundLabel]

                    if (foundLabel == null || confidence < 70f) {
                        return@withContext """
                            🌿 Disease: UNKNOWN
                            ⚠ Severity: Unknown
                            💊 Remedy: Could not identify
                            Confidence: ${"%.2f".format(confidence)}%
                        """.trimIndent()
                    }

                    historyManager.addToHistory(
                        ScanHistoryItem(
                            id = System.currentTimeMillis().toInt(),
                            imageUri = uri ?: "",
                            result = foundLabel,
                            date = SimpleDateFormat(
                                "dd MMM yyyy, hh:mm a",
                                Locale.getDefault()
                            ).format(Date())
                        )
                    )

                    """
                        🌿 Disease: $foundLabel
                        ⚠ Severity: ${info?.severity}
                        💊 Remedy: ${info?.remedy}
                        Confidence: ${"%.2f".format(confidence)}%
                    """.trimIndent()
                }

                result = prediction
                isLoading = false

            } catch (e: Exception) {
                result = "❌ Error: ${e.localizedMessage}"
                isLoading = false
            }
        }
    }

    Column(modifier = Modifier.padding(24.dp)) {

        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (!isLoading) {
            Text(result, fontSize = 16.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(16.dp))

            if (!result.contains("UNKNOWN") && !result.contains("Error")) {
                Button(
                    onClick = {
                        val diseaseName = result
                            .substringAfter("Disease: ")
                            .substringBefore("\n")
                            .trim()

                        navController.navigate("${NavRoutes.DiseaseDetail}/$diseaseName")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("📖 View Details", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("🔙 Retake Image", fontWeight = FontWeight.Bold)
        }
    }
}

/* ------------------ HELPERS ------------------ */

fun isCottonLeaf(bitmap: Bitmap): Boolean {
    var green = 0
    var total = 0
    for (y in 0 until bitmap.height step 10) {
        for (x in 0 until bitmap.width step 10) {
            total++
            val pixel = bitmap.getPixel(x, y)
            val r = (pixel shr 16) and 0xFF
            val g = (pixel shr 8) and 0xFF
            val b = pixel and 0xFF
            if (g > r && g > b && g > 90) green++
        }
    }
    return green.toFloat() / total > 0.40f
}

fun loadModelFile(context: Context, fileName: String): ByteBuffer {
    val fd = context.assets.openFd(fileName)
    val stream = FileInputStream(fd.fileDescriptor)
    return stream.channel.map(
        FileChannel.MapMode.READ_ONLY,
        fd.startOffset,
        fd.length
    )
}

fun loadLabels(context: Context): Map<String, DiseaseInfo> {
    val jsonStr = context.assets.open("class_indices.json")
        .bufferedReader()
        .use { it.readText() }

    val json = JSONObject(jsonStr)
    val map = mutableMapOf<String, DiseaseInfo>()

    for (key in json.keys()) {
        val obj = json.getJSONObject(key)
        map[key] = DiseaseInfo(
            index = obj.getInt("index"),
            severity = obj.getString("severity"),
            remedy = obj.getString("remedy")
        )
    }
    return map
}

fun preprocessImage(bitmap: Bitmap): ByteBuffer {
    val size = 224
    val scaled = Bitmap.createScaledBitmap(bitmap, size, size, true)
    val buffer = ByteBuffer.allocateDirect(4 * size * size * 3)
    buffer.order(ByteOrder.nativeOrder())

    for (y in 0 until size) {
        for (x in 0 until size) {
            val pixel = scaled.getPixel(x, y)
            buffer.putFloat(((pixel shr 16) and 0xFF) / 255f)
            buffer.putFloat(((pixel shr 8) and 0xFF) / 255f)
            buffer.putFloat((pixel and 0xFF) / 255f)
        }
    }
    return buffer
}
package com.example.shrinidhi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenDark,
    surface = SurfaceGray,
    onPrimary = TextPrimary,
    onSurface = TextSecondary
)

@Composable
fun ShrinidhiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content
    )
}

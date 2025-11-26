package com.greenquest.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = GreenOnPrimary,
    secondary = GreenSecondary,
    tertiary = GreenTertiary,
    background = LightBackground,
    surface = LightSurface
)

private val DarkColors = darkColorScheme(
    primary = GreenPrimary,
    onPrimary = GreenOnPrimary,
    secondary = GreenSecondary,
    tertiary = GreenTertiary,
    background = DarkBackground,
    surface = DarkSurface
)

@Composable
fun GreenQuestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

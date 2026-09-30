package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = SkyBluePrimary,
    onPrimary = Color.White,
    primaryContainer = SkyBlueContainer,
    onPrimaryContainer = SkyBlueDark,
    secondary = SkyBlueDark,
    onSecondary = Color.White,
    secondaryContainer = SkyBlueSurface,
    onSecondaryContainer = SkyBluePrimary,
    tertiary = SuccessEmerald,
    onTertiary = Color.White,
    tertiaryContainer = SuccessContainer,
    onTertiaryContainer = Color(0xFF065F46),
    background = SurfaceBackground,
    onBackground = NavyDark,
    surface = PureWhite,
    onSurface = NavyDark,
    surfaceVariant = SkyBlueSurface,
    onSurfaceVariant = NavyText,
    outline = BorderSubtle,
    outlineVariant = Color(0xFFCBD5E1),
    error = ErrorRose,
    onError = Color.White,
    errorContainer = ErrorContainer,
    onErrorContainer = Color(0xFF991B1B)
)

private val DarkColorScheme = darkColorScheme(
    primary = SkyBlueLight,
    onPrimary = NavyDark,
    primaryContainer = SkyBlueDark,
    onPrimaryContainer = SkyBlueContainer,
    secondary = SkyBlueLight,
    onSecondary = NavyDark,
    secondaryContainer = Color(0xFF1E293B),
    onSecondaryContainer = SkyBlueLight,
    tertiary = SuccessEmerald,
    onTertiary = Color.White,
    background = NavyDark,
    onBackground = Color(0xFFF1F5F9),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFE2E8F0),
    outline = Color(0xFF475569),
    error = ErrorRose,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Enforce distinct branded Sky Blue SaaS aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

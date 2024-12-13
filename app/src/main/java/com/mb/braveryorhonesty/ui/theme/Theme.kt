package com.mb.braveryorhonesty.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF4081), // Jasny różowy
    onPrimary = Color(0xFF5D001A), // Tekst na różowym
    primaryContainer = Color(0xFF5D001A), // Ciemniejszy różowy
    onPrimaryContainer = Color(0xFFFF80AB), // Tekst na kontenerach

    secondary = Color(0xFF0288D1), // Błękitny
    onSecondary = Color(0xFF00233E), // Tekst na błękitnym
    secondaryContainer = Color(0xFF003F5C), // Ciemniejszy błękit
    onSecondaryContainer = Color(0xFF81D4FA), // Tekst na kontenerach

    tertiary = Color(0xFFCDDC39), // Limonkowy
    onTertiary = Color(0xFF333300), // Tekst na limonkowym
    tertiaryContainer = Color(0xFF556700), // Ciemniejszy limonkowy
    onTertiaryContainer = Color(0xFFF0F4C3), // Tekst na kontenerach

    background = Color(0xFF121212), // Ciemne tło
    onBackground = Color(0xFFE0E0E0), // Tekst na tle

    surface = Color(0xFF1E1E1E), // Powierzchnia
    onSurface = Color(0xFFE0E0E0), // Tekst na powierzchni

    surfaceVariant = Color(0xFF2E2E2E), // Powierzchnia wariant
    onSurfaceVariant = Color(0xFFBDBDBD), // Tekst na powierzchni wariant

    outline = Color(0xFF757575), // Kontury
    error = Color(0xFFCF6679), // Czerwony (błędy)
    onError = Color(0xFF1E1E1E) // Tekst na czerwonym
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF4081), // Jasny różowy
    onPrimary = Color(0xFFFFFFFF), // Tekst na różowym
    primaryContainer = Color(0xFFFF80AB), // Jaśniejszy różowy
    onPrimaryContainer = Color(0xFF5D001A), // Tekst na kontenerach

    secondary = Color(0xFF03A9F4), // Błękitny
    onSecondary = Color(0xFFFFFFFF), // Tekst na błękitnym
    secondaryContainer = Color(0xFF81D4FA), // Jaśniejszy błękit
    onSecondaryContainer = Color(0xFF00233E), // Tekst na kontenerach

    tertiary = Color(0xFFCDDC39), // Limonkowy
    onTertiary = Color(0xFF333300), // Tekst na limonkowym
    tertiaryContainer = Color(0xFFF0F4C3), // Jaśniejszy limonkowy
    onTertiaryContainer = Color(0xFF3C4700), // Tekst na kontenerach

    background = Color(0xFFFFFFFF), // Jasne tło
    onBackground = Color(0xFF212121), // Tekst na tle

    surface = Color(0xFFFFFFFF), // Powierzchnia
    onSurface = Color(0xFF212121), // Tekst na powierzchni

    surfaceVariant = Color(0xFFE0E0E0), // Powierzchnia wariant
    onSurfaceVariant = Color(0xFF424242), // Tekst na powierzchni wariant

    outline = Color(0xFFBDBDBD), // Kontury
    error = Color(0xFFD32F2F), // Czerwony (błędy)
    onError = Color(0xFFFFFFFF) // Tekst na czerwonym
)


@Composable
fun BraveryOrHonestyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
package com.tecsup.pokemon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Paleta de fuego: naranja intenso, ámbar y fondos cálidos
private val Claro = lightColorScheme(
    primary = Color(0xFFD84315),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDBCF),
    onPrimaryContainer = Color(0xFF3B0A00),
    secondary = Color(0xFFF57C00),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE0B2),
    onSecondaryContainer = Color(0xFF3E2000),
    tertiary = Color(0xFFFFB300),
    background = Color(0xFFFFF8F5),
    onBackground = Color(0xFF241A17),
    surface = Color(0xFFFFF8F5),
    onSurface = Color(0xFF241A17),
    surfaceVariant = Color(0xFFF5DED6),
    onSurfaceVariant = Color(0xFF53433E),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

private val Oscuro = darkColorScheme(
    primary = Color(0xFFFFB59C),
    onPrimary = Color(0xFF5F1500),
    primaryContainer = Color(0xFF8A2A0B),
    onPrimaryContainer = Color(0xFFFFDBCF),
    secondary = Color(0xFFFFB86B),
    onSecondary = Color(0xFF4A2800),
    secondaryContainer = Color(0xFF6A3B00),
    onSecondaryContainer = Color(0xFFFFE0B2),
    tertiary = Color(0xFFFFCA28),
    background = Color(0xFF1A110E),
    onBackground = Color(0xFFF1DFD9),
    surface = Color(0xFF1A110E),
    onSurface = Color(0xFFF1DFD9),
    surfaceVariant = Color(0xFF53433E),
    onSurfaceVariant = Color(0xFFD8C2BA)
)

private val Formas = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

@Composable
fun PokedexTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) Oscuro else Claro,
        shapes = Formas,
        content = content
    )
}

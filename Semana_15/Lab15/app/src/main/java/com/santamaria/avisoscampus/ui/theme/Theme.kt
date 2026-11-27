package com.santamaria.avisoscampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AzulTema = Color(0xFF1B6CA8)
val AzulSuave = Color(0xFFE8F1F8)
val Fondo = Color(0xFFF4F7FA)
val ErrorRojo = Color(0xFFC62828)
val ErrorSuave = Color(0xFFFDECEA)
val Verde = Color(0xFF2E7D32)
val VerdeSuave = Color(0xFFE6F4E7)
val Gris = Color(0xFF6B7785)
val GrisSuave = Color(0xFFE9EDF1)
val Ambar = Color(0xFFF5B533)

private val Esquema = lightColorScheme(
    primary = AzulTema,
    onPrimary = Color.White,
    primaryContainer = AzulSuave,
    background = Fondo,
    surface = Color.White,
    error = ErrorRojo,
    errorContainer = ErrorSuave,
    onErrorContainer = ErrorRojo
)

@Composable
fun AvisosCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Esquema, content = content)
}

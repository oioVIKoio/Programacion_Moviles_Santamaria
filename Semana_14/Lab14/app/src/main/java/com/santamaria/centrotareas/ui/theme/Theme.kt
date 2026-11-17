package com.santamaria.centrotareas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Solo tema claro, a propósito: así lo pide el diseño.
private val Esquema = lightColorScheme(
    primary = Azul,
    onPrimary = Color.White,
    primaryContainer = AzulSuave,
    onPrimaryContainer = Azul,
    secondary = Tinta,
    onSecondary = Color.White,
    tertiary = Exito,
    onTertiary = Color.White,
    tertiaryContainer = ExitoSuave,
    onTertiaryContainer = Exito,
    background = Fondo,
    onBackground = Tinta,
    surface = Superficie,
    onSurface = Tinta,
    onSurfaceVariant = TextoSecundario,
    outline = Linea,
    error = Error,
    onError = Color.White,
    errorContainer = ErrorSuave,
    onErrorContainer = Error
)

@Composable
fun CentroTareasTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = Esquema,
        typography = Tipografia,
        shapes = Formas,
        content = content
    )
}

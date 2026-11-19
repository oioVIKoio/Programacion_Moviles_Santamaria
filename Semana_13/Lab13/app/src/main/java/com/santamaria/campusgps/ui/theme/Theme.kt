package com.santamaria.campusgps.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Tema claro del diseño. El tema oscuro es la mejora «Modo oscuro»: la misma paleta con fondos oscuros.
private val EsquemaClaro = lightColorScheme(
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
    outlineVariant = Color(0xFFCBD3DE),
    error = Error,
    onError = Color.White,
    errorContainer = ErrorSuave,
    onErrorContainer = Error
)

private val EsquemaOscuro = darkColorScheme(
    primary = AzulOscuro,
    onPrimary = Color.White,
    primaryContainer = AzulSuaveOscuro,
    onPrimaryContainer = AzulOscuro,
    secondary = TextoOscuro,
    onSecondary = FondoOscuro,
    tertiary = ExitoOscuro,
    onTertiary = FondoOscuro,
    tertiaryContainer = ExitoSuaveOscuro,
    onTertiaryContainer = ExitoOscuro,
    background = FondoOscuro,
    onBackground = TextoOscuro,
    surface = SuperficieOscura,
    onSurface = TextoOscuro,
    onSurfaceVariant = TextoSecundarioOscuro,
    outline = LineaOscura,
    outlineVariant = LineaFuerteOscura,
    error = ErrorOscuro,
    onError = FondoOscuro,
    errorContainer = ErrorSuaveOscuro,
    onErrorContainer = ErrorOscuro
)

@Composable
fun CampusGPSTheme(oscuro: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (oscuro) EsquemaOscuro else EsquemaClaro,
        typography = Tipografia,
        shapes = Formas,
        content = content
    )
}

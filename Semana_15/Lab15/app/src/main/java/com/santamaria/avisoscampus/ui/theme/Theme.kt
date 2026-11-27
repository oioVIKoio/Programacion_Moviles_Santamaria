package com.santamaria.avisoscampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.santamaria.avisoscampus.R

// Paleta del rediseño (Figma Make). Marino de base, un acento lima y neutros fríos.
val Marino = Color(0xFF142C46)
val MarinoBoton = Color(0xFF193B5A)
val Lima = Color(0xFFD4F599)
val LimaTexto = Color(0xFF233C19)
val Tinta = Color(0xFF172B42)
val Fondo = Color(0xFFF5F7FA)
val Borde = Color(0xFFE2E8ED)
val Pizarra600 = Color(0xFF475569)
val Pizarra500 = Color(0xFF64748B)
val Pizarra300 = Color(0xFFCBD5E1)
val IconoSuave = Color(0xFF496985)
val IconoOscuro = Color(0xFF29445C)
val FondoIcono = Color(0xFFEEF2F6)
val Separador = Color(0xFFF1F5F9)
val CajaRecordatorio = Color(0xFFF4F6F9)
val AnilloPista = Color(0xFFDCE5EE)
val AnilloProgreso = Color(0xFF315C7E)
val SwitchApagado = Color(0xFFD9E1E8)

val PermitidoFondo = Color(0xFFEAF5DF)
val PermitidoTexto = Color(0xFF355B20)
val DenegadoFondo = Color(0xFFFEF2F2)
val DenegadoTexto = Color(0xFF991B1B)
val ErrorFondo = Color(0xFFFEF2F2)
val ErrorTexto = Color(0xFF7F1D1D)
val SinPedirFondo = Color(0xFFF1F5F9)

// Etiquetas de origen del historial
val BotonFondo = Color(0xFFEFF6FF)
val BotonTexto = Color(0xFF1E40AF)
val RecordatorioFondo = Color(0xFFFFFBEB)
val RecordatorioTexto = Color(0xFF78350F)
val AvionFondo = Color(0xFFF5F3FF)
val AvionTexto = Color(0xFF5B21B6)

// Manrope para todo el texto y JetBrains Mono para los segundos (fuentes variables: un archivo por familia)
@OptIn(ExperimentalTextApi::class)
private fun variable(res: Int, peso: FontWeight) = Font(
    resId = res,
    weight = peso,
    variationSettings = FontVariation.Settings(FontVariation.weight(peso.weight))
)

val Manrope = FontFamily(
    variable(R.font.manrope, FontWeight.Normal),
    variable(R.font.manrope, FontWeight.Medium),
    variable(R.font.manrope, FontWeight.SemiBold),
    variable(R.font.manrope, FontWeight.Bold),
    variable(R.font.manrope, FontWeight.ExtraBold)
)

val JetBrainsMono = FontFamily(
    variable(R.font.jetbrains_mono, FontWeight.Medium),
    variable(R.font.jetbrains_mono, FontWeight.SemiBold)
)

private val Base = TextStyle(fontFamily = Manrope, color = Tinta)
private val Tipografia = Typography().run {
    copy(
        bodyLarge = bodyLarge.merge(Base), bodyMedium = bodyMedium.merge(Base), bodySmall = bodySmall.merge(Base),
        titleMedium = titleMedium.merge(Base), titleSmall = titleSmall.merge(Base),
        labelLarge = labelLarge.merge(Base), labelMedium = labelMedium.merge(Base), labelSmall = labelSmall.merge(Base)
    )
}

private val Esquema = lightColorScheme(
    primary = MarinoBoton,
    onPrimary = Color.White,
    secondary = Lima,
    onSecondary = LimaTexto,
    background = Fondo,
    onBackground = Tinta,
    surface = Color.White,
    onSurface = Tinta,
    onSurfaceVariant = Pizarra600,
    outline = Borde,
    error = DenegadoTexto,
    errorContainer = ErrorFondo,
    onErrorContainer = ErrorTexto
)

@Composable
fun AvisosCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Esquema, typography = Tipografia, content = content)
}

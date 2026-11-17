package com.santamaria.centrotareas.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.santamaria.centrotareas.R

// Manrope para texto y JetBrains Mono para los números (distancias, coordenadas).
// Ambas son fuentes variables: un solo archivo por familia, un peso por Font().
@OptIn(ExperimentalTextApi::class)
private fun variable(res: Int, peso: FontWeight) = Font(
    resId = res,
    weight = peso,
    variationSettings = FontVariation.Settings(FontVariation.weight(peso.weight))
)

val Manrope = FontFamily(
    variable(R.font.manrope, FontWeight.Medium),
    variable(R.font.manrope, FontWeight.SemiBold),
    variable(R.font.manrope, FontWeight.Bold),
    variable(R.font.manrope, FontWeight.ExtraBold)
)

val JetBrainsMono = FontFamily(
    variable(R.font.jetbrains_mono, FontWeight.Medium),
    variable(R.font.jetbrains_mono, FontWeight.SemiBold),
    variable(R.font.jetbrains_mono, FontWeight.Bold)
)

val Tipografia = Typography(
    titleLarge = TextStyle(fontFamily = Manrope, fontWeight = FontWeight.Bold, fontSize = 20.sp, letterSpacing = (-0.4).sp),
    titleMedium = TextStyle(fontFamily = Manrope, fontWeight = FontWeight.Bold, fontSize = 16.sp),
    bodyLarge = TextStyle(fontFamily = Manrope, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 21.sp),
    bodyMedium = TextStyle(fontFamily = Manrope, fontWeight = FontWeight.Medium, fontSize = 13.sp, lineHeight = 19.sp),
    bodySmall = TextStyle(fontFamily = Manrope, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 17.sp),
    labelLarge = TextStyle(fontFamily = Manrope, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp),
    labelMedium = TextStyle(fontFamily = Manrope, fontWeight = FontWeight.Bold, fontSize = 12.sp),
    labelSmall = TextStyle(fontFamily = Manrope, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 0.6.sp)
)

// Estilos de números, usados por la lista y las coordenadas
val NumeroGrande = TextStyle(fontFamily = JetBrainsMono, fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = (-0.6).sp)
val NumeroChico = TextStyle(fontFamily = JetBrainsMono, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, letterSpacing = (-0.4).sp)

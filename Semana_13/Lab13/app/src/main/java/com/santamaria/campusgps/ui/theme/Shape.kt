package com.santamaria.campusgps.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Botones píldora, filas de 15 dp, cajas de mensaje de 14 dp y hoja inferior de 28 dp
val FormaBoton = RoundedCornerShape(percent = 50)
val FormaFila = RoundedCornerShape(15.dp)
val FormaMensaje = RoundedCornerShape(14.dp)
val FormaHoja = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)

val Formas = Shapes(
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(15.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

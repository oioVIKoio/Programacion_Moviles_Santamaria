package com.santamaria.campusgps.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.santamaria.campusgps.ui.theme.Azul

/**
 * Tu posición: punto azul con una flecha que apunta hacia donde miras y, con «Mi rumbo», un abanico de luz.
 * Se dibuja apuntando hacia arriba; el marcador del mapa lo gira con el rumbo.
 */
@Composable
fun PuntoUsuario(conAbanico: Boolean, conFlecha: Boolean) {
    Canvas(Modifier.size(150.dp)) {
        val centro = center
        if (conAbanico) {
            // Abanico de 70 grados que se desvanece hacia afuera
            drawArc(
                brush = Brush.radialGradient(
                    colors = listOf(Azul.copy(alpha = 0.55f), Azul.copy(alpha = 0f)),
                    center = centro,
                    radius = size.minDimension / 2
                ),
                startAngle = -90f - 35f,
                sweepAngle = 70f,
                useCenter = true
            )
        }
        drawCircle(Color.White, radius = 11.dp.toPx(), center = centro)
        drawCircle(Azul, radius = 8.dp.toPx(), center = centro)
        if (conFlecha) {
            val flecha = Path().apply {
                moveTo(centro.x, centro.y - 27.dp.toPx())
                lineTo(centro.x - 8.dp.toPx(), centro.y - 14.dp.toPx())
                lineTo(centro.x + 8.dp.toPx(), centro.y - 14.dp.toPx())
                close()
            }
            drawPath(flecha, Azul)
            drawPath(flecha, Color.White, style = Stroke(width = 1.5.dp.toPx()))
        }
    }
}

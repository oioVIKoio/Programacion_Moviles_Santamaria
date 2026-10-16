package tecsup.santamaria.lavacampus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Dibuja con Canvas la lavadora (wash), la plancha (iron) o la camisa (shirt)
@Composable
fun IconoServicio(
    icono: String,
    modifier: Modifier = Modifier,
    tamano: Dp = 44.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    fondo: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Box(
        modifier = modifier
            .size(tamano)
            .background(fondo, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(tamano).padding(tamano * 0.22f)) {
            val trazo = Stroke(width = size.minDimension * 0.08f, join = StrokeJoin.Round)
            when (icono) {
                "iron" -> plancha(color, trazo)
                "shirt" -> camisa(color, trazo)
                else -> lavadora(color, trazo)
            }
        }
    }
}

private fun DrawScope.lavadora(color: Color, trazo: Stroke) {
    val w = size.width
    val h = size.height
    drawRoundRect(color, size = Size(w, h), cornerRadius = CornerRadius(w * 0.15f), style = trazo)
    drawLine(color, Offset(0f, h * 0.24f), Offset(w, h * 0.24f), strokeWidth = trazo.width)
    drawCircle(color, radius = w * 0.05f, center = Offset(w * 0.2f, h * 0.12f))
    drawCircle(color, radius = w * 0.05f, center = Offset(w * 0.36f, h * 0.12f))
    drawCircle(color, radius = w * 0.26f, center = Offset(w * 0.5f, h * 0.6f), style = trazo)
}

private fun DrawScope.plancha(color: Color, trazo: Stroke) {
    val w = size.width
    val h = size.height
    val base = Path().apply {
        moveTo(w * 0.05f, h * 0.8f)
        lineTo(w * 0.95f, h * 0.8f)
        lineTo(w * 0.95f, h * 0.45f)
        lineTo(w * 0.45f, h * 0.45f)
        quadraticTo(w * 0.15f, h * 0.48f, w * 0.05f, h * 0.8f)
        close()
    }
    drawPath(base, color, style = trazo)
    // asa
    val asa = Path().apply {
        moveTo(w * 0.55f, h * 0.45f)
        lineTo(w * 0.55f, h * 0.2f)
        lineTo(w * 0.95f, h * 0.2f)
        lineTo(w * 0.95f, h * 0.45f)
    }
    drawPath(asa, color, style = trazo)
}

private fun DrawScope.camisa(color: Color, trazo: Stroke) {
    val w = size.width
    val h = size.height
    val polo = Path().apply {
        moveTo(w * 0.35f, h * 0.05f)
        lineTo(w * 0.0f, h * 0.25f)
        lineTo(w * 0.12f, h * 0.45f)
        lineTo(w * 0.22f, h * 0.4f)
        lineTo(w * 0.22f, h * 0.95f)
        lineTo(w * 0.78f, h * 0.95f)
        lineTo(w * 0.78f, h * 0.4f)
        lineTo(w * 0.88f, h * 0.45f)
        lineTo(w * 1.0f, h * 0.25f)
        lineTo(w * 0.65f, h * 0.05f)
        quadraticTo(w * 0.5f, h * 0.22f, w * 0.35f, h * 0.05f)
        close()
    }
    drawPath(polo, color, style = trazo)
}

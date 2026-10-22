package tecsup.santamaria.lavacampus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.model.EstadoPedido

// Línea de seguimiento: Recibido, En proceso, Listo, Entregado (dibujada con Canvas)
@Composable
fun StepperEstado(
    estado: EstadoPedido,
    modifier: Modifier = Modifier,
    vertical: Boolean = false,
    detalles: List<String> = emptyList()   // texto a la derecha de cada etapa (solo vertical)
) {
    val actual = EstadoPedido.etapas.indexOf(estado)   // -1 si está cancelado
    val activo = MaterialTheme.colorScheme.primary
    val inactivo = MaterialTheme.colorScheme.outline
    if (vertical) {
        Column(modifier) {
            EstadoPedido.etapas.forEachIndexed { i, etapa ->
                Row(verticalAlignment = Alignment.Top) {
                    Canvas(Modifier.width(24.dp).height(if (i < 3) 44.dp else 22.dp)) {
                        val c = Offset(size.width / 2, 11.dp.toPx())
                        if (i < 3) {
                            drawLine(
                                if (i < actual) activo else inactivo,
                                Offset(c.x, c.y), Offset(c.x, size.height + 11.dp.toPx()),
                                strokeWidth = 2.dp.toPx()
                            )
                        }
                        punto(c, i <= actual, activo, inactivo)
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        etapa.etiqueta,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (i == actual) FontWeight.Bold else FontWeight.Normal,
                        color = if (i <= actual) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f).padding(top = 2.dp)
                    )
                    Text(
                        detalles.getOrElse(i) { if (i <= actual) "" else "Pendiente" },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            }
        }
    } else {
        Column(modifier.fillMaxWidth()) {
            Canvas(Modifier.fillMaxWidth().height(24.dp)) {
                val paso = (size.width - 24.dp.toPx()) / 3
                val y = size.height / 2
                for (i in 0 until 3) {
                    val x1 = 12.dp.toPx() + paso * i
                    drawLine(
                        if (i < actual) activo else inactivo,
                        Offset(x1, y), Offset(x1 + paso, y),
                        strokeWidth = 2.dp.toPx()
                    )
                }
                for (i in 0..3) punto(Offset(12.dp.toPx() + paso * i, y), i <= actual, activo, inactivo)
            }
            Row(Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                EstadoPedido.etapas.forEachIndexed { i, etapa ->
                    Text(
                        etapa.etiqueta,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = if (i == actual) FontWeight.Bold else FontWeight.Normal,
                        color = if (i <= actual) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(64.dp)
                    )
                }
            }
        }
    }
}

private fun DrawScope.punto(c: Offset, hecho: Boolean, activo: Color, inactivo: Color) {
    val r = 10.dp.toPx()
    if (hecho) {
        drawCircle(activo, r, c)
        val check = Path().apply {
            moveTo(c.x - r * 0.45f, c.y)
            lineTo(c.x - r * 0.1f, c.y + r * 0.35f)
            lineTo(c.x + r * 0.45f, c.y - r * 0.3f)
        }
        drawPath(check, Color.White, style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round))
    } else {
        drawCircle(Color.White, r, c)
        drawCircle(inactivo, r, c, style = Stroke(width = 2.dp.toPx()))
    }
}

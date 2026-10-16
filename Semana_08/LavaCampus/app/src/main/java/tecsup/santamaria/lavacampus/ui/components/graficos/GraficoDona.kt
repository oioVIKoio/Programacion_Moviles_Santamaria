package tecsup.santamaria.lavacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GraficoDona(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier,
    tamano: Dp = 110.dp,
    textoCentro: String = "pedidos"
) {
    val total = datos.sumOf { it.valor.toDouble() }.toFloat()
    val vacio = MaterialTheme.colorScheme.outline
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(Modifier.size(tamano)) {
                val grosor = size.minDimension * 0.18f
                val estilo = Stroke(width = grosor)
                if (total == 0f) {
                    drawArc(vacio, 0f, 360f, false, style = estilo)
                } else {
                    var inicio = -90f
                    datos.forEach { d ->
                        val barrido = d.valor / total * 360f
                        drawArc(d.color, inicio, barrido, false, style = estilo)
                        inicio += barrido
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(total.toInt().toString(), style = MaterialTheme.typography.titleLarge)
                Text(textoCentro, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Spacer(Modifier.width(20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.weight(1f)) {
            datos.forEach { d ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(10.dp).background(d.color, RoundedCornerShape(3.dp)))
                    Spacer(Modifier.width(8.dp))
                    Text(d.etiqueta, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                    Text(d.valor.toInt().toString(), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

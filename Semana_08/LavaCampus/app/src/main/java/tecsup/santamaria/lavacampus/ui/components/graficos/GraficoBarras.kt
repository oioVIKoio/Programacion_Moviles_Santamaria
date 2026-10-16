package tecsup.santamaria.lavacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GraficoBarras(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier,
    alto: Dp = 90.dp
) {
    val maximo = (datos.maxOfOrNull { it.valor } ?: 0f).coerceAtLeast(1f)
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom) {
        datos.forEach { d ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(d.valor.toInt().toString(), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                Canvas(Modifier.width(44.dp).height(alto)) {
                    val h = size.height * (d.valor / maximo)
                    drawRoundRect(
                        d.color,
                        topLeft = Offset(0f, size.height - h),
                        size = Size(size.width, h),
                        cornerRadius = CornerRadius(6.dp.toPx())
                    )
                }
                Text(d.etiqueta, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

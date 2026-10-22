package tecsup.santamaria.lavacampus.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.model.EstadoPedido

@Composable
fun PedidoCard(
    numero: Int,
    resumen: String,          // ej. "2.0 kg · Lun 19 oct"
    total: Double,
    estado: EstadoPedido,
    icono: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    titulo: String = "Pedido #%03d".format(numero),
    casillero: String? = null,
    extra: @Composable ColumnScope.() -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                IconoServicio(
                    icono,
                    tamano = 40.dp,
                    color = estado.color,
                    fondo = estado.color.copy(alpha = 0.12f)
                )
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(titulo, style = MaterialTheme.typography.titleMedium)
                    Text(resumen, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(6.dp))
                    EstadoPedidoChip(estado)
                }
                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    Text(
                        "S/ %.2f".format(total),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    if (casillero != null && estado == EstadoPedido.LISTO) {
                        Text(
                            "Casillero $casillero",
                            style = MaterialTheme.typography.bodySmall,
                            color = EstadoPedido.LISTO.color,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            extra()
        }
    }
}

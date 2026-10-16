package tecsup.santamaria.lavacampus.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.model.EstadoPedido

@Composable
fun EstadoPedidoChip(estado: EstadoPedido, modifier: Modifier = Modifier) {
    Surface(
        color = estado.color.copy(alpha = 0.14f),
        contentColor = estado.color,
        shape = RoundedCornerShape(50),
        modifier = modifier
    ) {
        Text(
            estado.etiqueta,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )
    }
}

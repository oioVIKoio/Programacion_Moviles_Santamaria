package tecsup.santamaria.lavacampus.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Botones menos y más. Con cantidad 0 el servicio queda apagado
@Composable
fun SelectorCantidad(
    valor: Double,
    paso: Double,
    minimo: Double,
    maximo: Double,
    onMenos: () -> Unit,
    onMas: () -> Unit,
    modifier: Modifier = Modifier,
    sufijo: String = ""
) {
    val colores = IconButtonDefaults.filledIconButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        disabledContainerColor = MaterialTheme.colorScheme.outline
    )
    val texto = if (paso % 1.0 == 0.0) valor.toInt().toString() else "%.1f".format(valor)
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        FilledIconButton(onClick = onMenos, enabled = valor > 0, shape = CircleShape, colors = colores, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Remove, contentDescription = "Menos", modifier = Modifier.size(16.dp))
        }
        Text(
            if (sufijo.isNotEmpty() && valor > 0) "$texto $sufijo" else texto,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(min = 48.dp)
        )
        FilledIconButton(onClick = onMas, enabled = valor < maximo, shape = CircleShape, colors = colores, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Add, contentDescription = "Más", modifier = Modifier.size(16.dp))
        }
    }
}

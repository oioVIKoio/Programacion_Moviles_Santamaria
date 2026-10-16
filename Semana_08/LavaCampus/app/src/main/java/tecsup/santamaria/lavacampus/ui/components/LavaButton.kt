package tecsup.santamaria.lavacampus.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LavaButton(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contorno: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val forma = RoundedCornerShape(12.dp)
    if (contorno) {
        OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            shape = forma,
            border = BorderStroke(1.dp, if (enabled) color else MaterialTheme.colorScheme.outline),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = color),
            modifier = modifier.fillMaxWidth().height(50.dp)
        ) { Text(texto, style = MaterialTheme.typography.labelLarge) }
    } else {
        Button(
            onClick = onClick,
            enabled = enabled,
            shape = forma,
            colors = ButtonDefaults.buttonColors(containerColor = color),
            modifier = modifier.fillMaxWidth().height(50.dp)
        ) { Text(texto, style = MaterialTheme.typography.labelLarge) }
    }
}

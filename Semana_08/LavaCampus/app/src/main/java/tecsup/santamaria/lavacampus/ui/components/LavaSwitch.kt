package tecsup.santamaria.lavacampus.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Switch con los colores del tema: apagado en gris, como el mockup
@Composable
fun LavaSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedTrackColor = MaterialTheme.colorScheme.primary,
            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            uncheckedThumbColor = MaterialTheme.colorScheme.outline,
            uncheckedBorderColor = MaterialTheme.colorScheme.outline
        )
    )
}

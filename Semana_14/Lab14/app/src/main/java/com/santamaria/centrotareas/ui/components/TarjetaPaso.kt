package com.santamaria.centrotareas.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * Tarjeta plegable de un paso: ícono, "PASO 0N", título y, abierta, su contenido.
 * El diseño las muestra como acordeón (una abierta a la vez).
 */
@Composable
fun TarjetaPaso(
    numero: Int,
    titulo: String,
    icono: ImageVector,
    abierta: Boolean,
    onAlternar: () -> Unit,
    modifier: Modifier = Modifier,
    contenido: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onAlternar)
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        "PASO 0$numero",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(titulo, style = MaterialTheme.typography.titleMedium)
                }
                Icon(
                    if (abierta) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                    contentDescription = if (abierta) "Plegar" else "Abrir",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            AnimatedVisibility(abierta) {
                Column(
                    modifier = Modifier.padding(start = 18.dp, end = 18.dp, bottom = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) { contenido() }
            }
        }
    }
}

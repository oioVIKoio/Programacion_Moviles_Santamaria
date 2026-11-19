package com.santamaria.campusgps.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.santamaria.campusgps.ui.theme.FormaMensaje

enum class TipoMensaje { ERROR, EXITO }

/** Recuadro con ícono, título, texto y, si hace falta, una acción ("Abrir ajustes", "Reintentar"). */
@Composable
fun CajaMensaje(
    tipo: TipoMensaje,
    titulo: String,
    texto: String,
    modifier: Modifier = Modifier,
    accion: String? = null,
    onAccion: () -> Unit = {}
) {
    val color = if (tipo == TipoMensaje.ERROR) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary
    val fondo = if (tipo == TipoMensaje.ERROR) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.tertiaryContainer
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = FormaMensaje,
        color = fondo,
        border = BorderStroke(1.dp, color.copy(alpha = 0.28f))
    ) {
        Row(Modifier.padding(14.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Icon(
                imageVector = if (tipo == TipoMensaje.ERROR) Icons.Outlined.ErrorOutline else Icons.Outlined.CheckCircleOutline,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(titulo, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                Text(texto, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (accion != null) {
                    Row(
                        modifier = Modifier.clickable(onClick = onAccion).padding(top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(accion, style = MaterialTheme.typography.labelMedium, color = color)
                        Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

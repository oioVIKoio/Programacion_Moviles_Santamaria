package com.santamaria.avisoscampus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.santamaria.avisoscampus.model.Aviso
import com.santamaria.avisoscampus.model.Origen
import com.santamaria.avisoscampus.ui.theme.Ambar
import com.santamaria.avisoscampus.ui.theme.AzulTema
import com.santamaria.avisoscampus.ui.theme.Gris
import com.santamaria.avisoscampus.ui.theme.GrisSuave

@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                "Historial",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            if (avisos.isEmpty()) {
                Text("Sin avisos todavía", color = Gris)
            } else {
                // Column + forEach (pocas filas): la pantalla ya se desplaza, no se usa LazyColumn
                avisos.take(20).forEach { FilaAviso(it) }
            }
        }
    }
}

@Composable
private fun FilaAviso(aviso: Aviso) {
    val colorOrigen = when (aviso.origen) {
        Origen.BOTON -> AzulTema
        Origen.RECORDATORIO -> Ambar
        Origen.MODO_AVION -> Color(0xFF6A4FB3)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(GrisSuave)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            aviso.origen.texto,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(colorOrigen)
                .padding(horizontal = 8.dp, vertical = 3.dp)
        )
        Column(Modifier.weight(1f)) {
            Text(aviso.titulo, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(aviso.texto, color = Gris, style = MaterialTheme.typography.bodySmall)
        }
        Text(aviso.hora, color = Gris, style = MaterialTheme.typography.bodySmall)
    }
}

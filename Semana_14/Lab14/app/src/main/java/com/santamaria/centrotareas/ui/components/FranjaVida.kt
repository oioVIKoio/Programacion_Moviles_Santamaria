package com.santamaria.centrotareas.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santamaria.centrotareas.ui.theme.Exito
import com.santamaria.centrotareas.ui.theme.ExitoSuave
import com.santamaria.centrotareas.ui.theme.FormaBoton
import com.santamaria.centrotareas.ui.theme.NumeroChico

/**
 * "Prueba de vida": si el contador de Tócame deja de sumar, la pantalla está congelada.
 * Es la prueba que pide la guía en cada paso.
 */
@Composable
fun FranjaVida(toques: Int, onToque: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(ExitoSuave)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.size(10.dp).background(Exito, CircleShape))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text("PRUEBA DE VIDA", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Si sigue sumando, la pantalla está viva", style = MaterialTheme.typography.labelMedium)
        }
        OutlinedButton(
            onClick = onToque,
            shape = FormaBoton,
            border = BorderStroke(1.dp, Exito.copy(alpha = 0.5f)),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = androidx.compose.ui.graphics.Color.White, contentColor = Exito)
        ) { Text("Tócame: $toques", style = NumeroChico) }
    }
}

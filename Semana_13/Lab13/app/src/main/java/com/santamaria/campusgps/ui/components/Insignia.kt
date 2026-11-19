package com.santamaria.campusgps.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santamaria.campusgps.ui.theme.Exito
import com.santamaria.campusgps.ui.theme.FormaBoton

/** Etiqueta pequeña, por ejemplo "Más cercano". */
@Composable
fun Insignia(texto: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = FormaBoton,
        color = MaterialTheme.colorScheme.tertiaryContainer,
        border = BorderStroke(1.dp, Exito.copy(alpha = 0.25f))
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelSmall,
            color = Exito,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

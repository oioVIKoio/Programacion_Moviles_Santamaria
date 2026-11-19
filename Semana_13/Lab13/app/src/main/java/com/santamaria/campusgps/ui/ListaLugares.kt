package com.santamaria.campusgps.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santamaria.campusgps.model.LugarConDistancia
import com.santamaria.campusgps.ui.components.Insignia
import com.santamaria.campusgps.ui.theme.AzulSuave
import com.santamaria.campusgps.ui.theme.Exito
import com.santamaria.campusgps.ui.theme.FormaFila
import com.santamaria.campusgps.ui.theme.NumeroGrande
import com.santamaria.campusgps.ui.theme.ReferenciaTexto
import com.santamaria.campusgps.util.formatearCaminata
import com.santamaria.campusgps.util.formatearDistancia


@OptIn(ExperimentalLayoutApi::class)
/** Lista de lugares: no sabe nada del ViewModel, solo recibe datos y avisa el toque por id. */
@Composable
fun ListaLugares(
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    masCercano: Int?,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    dentro: Set<Int> = emptySet(),
    referencia: Int? = null
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(lugares, key = { it.lugar.id }) { item ->
            val elegido = item.lugar.id == seleccionado
            Surface(
                modifier = Modifier.fillMaxWidth().clickable { onClick(item.lugar.id) },
                shape = FormaFila,
                color = if (elegido) AzulSuave else MaterialTheme.colorScheme.surface,
                border = BorderStroke(
                    if (elegido) 1.5.dp else 1.dp,
                    if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            ) {
                Row(Modifier.padding(horizontal = 14.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(iconoDe(item.lugar.categoria), null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(item.lugar.nombre, style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp))
                        // Una sola línea: categoría, y a continuación si cae en el radio o es tu referencia
                        val esReferencia = item.lugar.id == referencia
                        val esDentro = item.lugar.id in dentro
                        FlowRow(
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            itemVerticalAlignment = Alignment.CenterVertically
                        ) {
                            if (item.lugar.id == masCercano) Insignia("Más cercano")
                        Text(
                            item.lugar.categoria +
                                (if (esDentro) " · dentro del radio" else "") +
                                (if (esReferencia) " · tu referencia" else ""),
                            style = MaterialTheme.typography.bodySmall,
                            color = if (esReferencia) ReferenciaTexto else if (esDentro) Exito else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        if (item.metros == null) {
                            Text("Sin calcular", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else {
                            Text(formatearDistancia(item.metros), style = NumeroGrande)
                            Text(formatearCaminata(item.metros), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

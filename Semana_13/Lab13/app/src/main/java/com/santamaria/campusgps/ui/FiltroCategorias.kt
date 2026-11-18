package com.santamaria.campusgps.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santamaria.campusgps.ui.theme.AzulSuave

/** Fila de chips para filtrar por categoría. No sabe del ViewModel: recibe datos y avisa la elección. */
@Composable
fun FiltroCategorias(
    categorias: List<String>,
    actual: String?,
    onElegir: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    // Colores del diseño: el chip elegido va en azul suave, no en el lila por defecto de Material
    val colores = FilterChipDefaults.filterChipColors(
        selectedContainerColor = AzulSuave,
        selectedLabelColor = MaterialTheme.colorScheme.primary
    )
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(selected = actual == null, onClick = { onElegir(null) }, label = { Text("Todos") }, colors = colores)
        categorias.forEach { categoria ->
            FilterChip(
                selected = actual == categoria,
                onClick = { onElegir(if (actual == categoria) null else categoria) },
                label = { Text(categoria) },
                colors = colores
            )
        }
    }
}

/** Fila de chips para elegir el radio de cercanía (100, 200, 300 m o sin radio). */
@Composable
fun FiltroRadio(
    radios: List<Int>,
    actual: Int?,
    onElegir: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val colores = FilterChipDefaults.filterChipColors(
        selectedContainerColor = AzulSuave,
        selectedLabelColor = MaterialTheme.colorScheme.primary
    )
    Row(modifier = modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(selected = actual == null, onClick = { onElegir(null) }, label = { Text("Sin radio") }, colors = colores)
        radios.forEach { metros ->
            FilterChip(
                selected = actual == metros,
                onClick = { onElegir(if (actual == metros) null else metros) },
                label = { Text("$metros m") },
                colors = colores
            )
        }
    }
}

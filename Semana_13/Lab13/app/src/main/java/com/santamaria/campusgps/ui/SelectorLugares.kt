package com.santamaria.campusgps.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santamaria.campusgps.model.Lugar

/** Chip "Lugares" con un menú de casillas: marca cuáles lugares se ven. No sabe del ViewModel. */
@Composable
fun SelectorLugares(
    todos: List<Lugar>,
    ocultos: Set<Int>,
    onAlternar: (Int) -> Unit,
    onMostrarTodos: () -> Unit,
    modifier: Modifier = Modifier
) {
    var abierto by remember { mutableStateOf(false) }
    val visibles = todos.count { it.id !in ocultos }
    Box(modifier) {
        FilterChip(
            selected = ocultos.isNotEmpty(),
            onClick = { abierto = true },
            label = { Text("Lugares $visibles/${todos.size}") },
            leadingIcon = { Icon(Icons.Outlined.Place, null, Modifier.size(18.dp)) },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedLabelColor = MaterialTheme.colorScheme.primary,
                selectedLeadingIconColor = MaterialTheme.colorScheme.primary
            )
        )
        DropdownMenu(expanded = abierto, onDismissRequest = { abierto = false }) {
            todos.forEach { lugar ->
                DropdownMenuItem(
                    text = { Text(lugar.nombre) },
                    leadingIcon = { Checkbox(checked = lugar.id !in ocultos, onCheckedChange = null) },
                    onClick = { onAlternar(lugar.id) }
                )
            }
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text("Mostrar todos") },
                enabled = ocultos.isNotEmpty(),
                onClick = { onMostrarTodos() }
            )
        }
    }
}

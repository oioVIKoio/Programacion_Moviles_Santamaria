package com.tecsup.pokemon.ui.lista

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.pokemon.domain.model.Item
import com.tecsup.pokemon.ui.UiState
import com.tecsup.pokemon.ui.components.Cargando
import com.tecsup.pokemon.ui.components.ErrorView

// Datos de prueba: se reemplazan cuando la lista consuma la API
val itemsDePrueba = listOf(
    Item("charmander", "Charmander", "Tipo fuego", null),
    Item("charmeleon", "Charmeleon", "Tipo fuego", null),
    Item("charizard", "Charizard", "Tipo fuego", null),
    Item("vulpix", "Vulpix", "Tipo fuego", null),
    Item("growlithe", "Growlithe", "Tipo fuego", null)
)

@Composable
fun ListaScreen(
    estado: UiState<List<Item>>,
    onItemClick: (Item) -> Unit,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (estado) {
        is UiState.Cargando -> Cargando(modifier)
        is UiState.Error -> ErrorView(estado.mensaje, onReintentar, modifier)
        is UiState.Exito -> {
            if (estado.datos.isEmpty()) {
                Column(modifier.fillMaxSize().padding(24.dp)) {
                    Text("No hay Pokémon para mostrar", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(estado.datos, key = { it.id }) { item ->
                        Card(Modifier.fillMaxWidth().clickable { onItemClick(item) }) {
                            Column(Modifier.padding(16.dp)) {
                                Text(item.titulo, style = MaterialTheme.typography.titleMedium)
                                Text(item.subtitulo, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

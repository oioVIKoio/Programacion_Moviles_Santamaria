package com.tecsup.pokemon.ui.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.pokemon.domain.model.Item
import com.tecsup.pokemon.ui.UiState
import com.tecsup.pokemon.ui.components.Cargando
import com.tecsup.pokemon.ui.components.ErrorView
import com.tecsup.pokemon.ui.lista.ImagenPokemon

@Composable
fun DetalleScreen(
    estado: UiState<Item>,
    onReintentar: () -> Unit,
    onFavorito: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (estado) {
        is UiState.Cargando -> Cargando(modifier)
        is UiState.Error -> ErrorView(estado.mensaje, onReintentar, modifier)
        is UiState.Exito -> {
            val item = estado.datos
            Column(
                modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ImagenPokemon(item.imagenUrl, 200.dp)
                Text(item.titulo, style = MaterialTheme.typography.headlineLarge)

                // El subtítulo trae los datos separados por " · " (número, tipos, altura y peso)
                Card(
                    Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f))
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        item.subtitulo.split(" · ").forEach {
                            Text(it, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }

                if (item.esFavorito) {
                    OutlinedButton(onClick = onFavorito, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Filled.Star, contentDescription = null, modifier = Modifier.size(18.dp))
                        Text("Quitar de favoritos", modifier = Modifier.padding(start = 8.dp))
                    }
                } else {
                    Button(onClick = onFavorito, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Filled.StarBorder, contentDescription = null, modifier = Modifier.size(18.dp))
                        Text("Marcar como favorito", modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}

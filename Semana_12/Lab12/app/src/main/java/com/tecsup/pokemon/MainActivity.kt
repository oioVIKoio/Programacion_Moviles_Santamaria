package com.tecsup.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.tecsup.pokemon.ui.UiState
import com.tecsup.pokemon.ui.lista.ListaScreen
import com.tecsup.pokemon.ui.lista.itemsDePrueba

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(Modifier.fillMaxSize()) { padding ->
                    ListaScreen(
                        estado = UiState.Exito(itemsDePrueba),
                        onItemClick = {},
                        onFavorito = {},
                        onReintentar = {},
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

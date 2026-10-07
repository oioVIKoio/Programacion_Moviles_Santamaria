package com.santamaria.saludpluscitas.ui.screens.resultados

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.data.repository.Repositorio
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.BarraNavegacion
import com.santamaria.saludpluscitas.ui.components.BarraSuperior
import com.santamaria.saludpluscitas.ui.components.TarjetaResultado
import com.santamaria.saludpluscitas.ui.theme.TextoSecundario

// Reto 13, vista sin diseño: lista fija de Repositorio.resultados,
// los más recientes primero. Es uno de los destinos de la NavigationBar.
@Composable
fun ResultadosScreen(
    navController: NavController
) {
    val resultados = Repositorio.resultados.sortedByDescending { it.fecha }
    val listos = resultados.count { it.listo }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BarraSuperior(
                titulo = "Mis resultados",
                onAtras = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BarraNavegacion(
                navController = navController,
                rutaActual = Rutas.Resultados.ruta
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "$listos de ${resultados.size} resultados listos",
                    fontSize = 14.sp,
                    color = TextoSecundario
                )
            }
            items(resultados, key = { it.id }) { resultado ->
                TarjetaResultado(resultado = resultado)
            }
        }
    }
}

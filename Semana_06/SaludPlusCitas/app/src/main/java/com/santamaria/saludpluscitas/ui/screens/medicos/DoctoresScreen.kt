package com.santamaria.saludpluscitas.ui.screens.medicos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.data.repository.Repositorio
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.BarraSuperior
import com.santamaria.saludpluscitas.ui.components.CampoBusqueda
import com.santamaria.saludpluscitas.ui.components.EncabezadoEspecialidad
import com.santamaria.saludpluscitas.ui.components.EstadoVacio
import com.santamaria.saludpluscitas.ui.components.TarjetaMedico

// Menú lateral → Doctores: todos los médicos de todas las sedes,
// agrupados por especialidad. Tocar uno abre su agenda (fecha y hora).
@Composable
fun DoctoresScreen(
    navController: NavController
) {
    var busqueda by remember { mutableStateOf("") }
    val grupos = Repositorio.medicosPorEspecialidadAgrupados(texto = busqueda)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BarraSuperior(
                titulo = "Doctores",
                onAtras = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            CampoBusqueda(
                valor = busqueda,
                onValorChange = { busqueda = it },
                placeholder = "Buscar médico o especialidad..."
            )

            if (grupos.isEmpty()) {
                EstadoVacio(mensaje = "No se encontraron médicos")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    grupos.forEach { (especialidad, medicos) ->
                        item(key = "esp-${especialidad.id}") {
                            EncabezadoEspecialidad(especialidad, medicos.size)
                        }
                        items(medicos, key = { it.id }) { medico ->
                            TarjetaMedico(
                                medico = medico,
                                onClick = { navController.navigate(Rutas.FechaHora.crearRuta(medico.id)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

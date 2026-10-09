package com.santamaria.saludpluscitas.ui.screens.agendamiento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.data.repository.Repositorio
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.BarraSuperior
import com.santamaria.saludpluscitas.ui.components.CampoBusqueda
import com.santamaria.saludpluscitas.ui.components.EncabezadoEspecialidad
import com.santamaria.saludpluscitas.ui.components.EstadoVacio
import com.santamaria.saludpluscitas.ui.components.TarjetaMedico
import com.santamaria.saludpluscitas.ui.theme.MoradoPrimario

// Paso 2 del agendamiento: médicos de la sede agrupados por especialidad.
// Los chips filtran por especialidad y el buscador por nombre.
@Composable
fun MedicosSedeScreen(
    navController: NavController,
    sedeId: Int
) {
    val sede = Repositorio.obtenerSede(sedeId)

    var busqueda by remember { mutableStateOf("") }
    // null = todas las especialidades.
    var filtro by rememberSaveable { mutableStateOf<Int?>(null) }

    val todos = Repositorio.medicosPorEspecialidadAgrupados(sedeId = sedeId)
    val grupos = Repositorio.medicosPorEspecialidadAgrupados(sedeId = sedeId, texto = busqueda)
        .filterKeys { filtro == null || it.id == filtro }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BarraSuperior(
                titulo = sede?.nombre ?: "Sede",
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
            if (sede == null) {
                EstadoVacio(mensaje = "La sede no existe")
                return@Column
            }

            CampoBusqueda(
                valor = busqueda,
                onValorChange = { busqueda = it },
                placeholder = "Buscar médico o especialidad..."
            )

            LazyRow(
                contentPadding = PaddingValues(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    ChipEspecialidad("Todas", filtro == null) { filtro = null }
                }
                items(todos.keys.toList(), key = { it.id }) { especialidad ->
                    ChipEspecialidad(especialidad.nombre, filtro == especialidad.id) {
                        filtro = especialidad.id
                    }
                }
            }

            if (grupos.isEmpty()) {
                EstadoVacio(mensaje = "No se encontraron médicos en esta sede")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 16.dp),
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

@Composable
private fun ChipEspecialidad(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = seleccionado,
        onClick = onClick,
        label = { Text(texto) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MoradoPrimario,
            selectedLabelColor = Color.White
        )
    )
}

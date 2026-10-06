package com.santamaria.saludpluscitas.ui.screens.agendamiento

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Título "Médicos de <especialidad>" con Repositorio.obtenerEspecialidad.
//       Lupa para buscar (Repositorio.buscarMedicos).
//       LazyColumn con Repositorio.medicosPorEspecialidad (mejor calificados primero).
//       Tarjeta: avatar, nombre, profesión, ★ calificación (reseñas) y chip de disponibilidad.
//       Al tocar un médico → Fecha y hora con su medicoId.
@Composable
fun MedicosScreen(
    navController: NavController,
    especialidadId: Int
) {
    PantallaEnConstruccion(
        titulo = "5. Médicos",
        detalle = "especialidadId = $especialidadId",
        "Elegir médico (médico 5)" to { navController.navigate(Rutas.FechaHora.crearRuta(5)) },
        "Volver" to { navController.popBackStack() }
    )
}

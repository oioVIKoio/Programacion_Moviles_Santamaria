package com.santamaria.saludpluscitas.ui.screens.agendamiento

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Tarjeta del médico (Repositorio.obtenerMedico).
//       Fase 1: lista fija de 5 días (fecha ISO "2026-10-07") con el día seleccionado en azul.
//       LazyVerticalGrid de 3 columnas con Repositorio.horariosDisponibles(medicoId, fecha).
//       Al cambiar de día, reiniciar la hora seleccionada.
//       Botón "Continuar" habilitado solo con día y hora → Confirmar cita.
@Composable
fun FechaHoraScreen(
    navController: NavController,
    medicoId: Int
) {
    PantallaEnConstruccion(
        titulo = "6. Fecha y hora",
        detalle = "medicoId = $medicoId",
        "Continuar" to { navController.navigate(Rutas.ConfirmarCita.crearRuta(medicoId, "2026-10-07", "09:30")) },
        "Volver" to { navController.popBackStack() }
    )
}

package com.santamaria.saludpluscitas.ui.screens.agendamiento

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Tarjeta del médico con profesión y CMP.
//       Filas con ícono: Fecha, Hora ("09:30 a 10:00"), Tipo de atención y Dirección.
//       Campo "Motivo de consulta (opcional)".
//       Botón "Agendar cita": Repositorio.agendarCita y navegar a Cita agendada
//       con popUpTo para borrar el flujo de agendamiento del historial.
@Composable
fun ConfirmarCitaScreen(
    navController: NavController,
    medicoId: Int,
    fecha: String,
    hora: String
) {
    PantallaEnConstruccion(
        titulo = "7. Confirmar cita",
        detalle = "medicoId = $medicoId · fecha = $fecha · hora = $hora",
        "Agendar cita" to { navController.navigate(Rutas.CitaExitosa.crearRuta(1)) },
        "Volver" to { navController.popBackStack() }
    )
}

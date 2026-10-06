package com.santamaria.saludpluscitas.ui.screens.agendamiento

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Diseñar con el estilo de la app: ícono de éxito y resumen de la cita (Repositorio.obtenerCita).
//       Botones "Ver mis citas" → Mis citas e "Ir al inicio" → Inicio.
//       Atrás no debe volver a Confirmar cita.
@Composable
fun CitaExitosaScreen(
    navController: NavController,
    citaId: Int
) {
    PantallaEnConstruccion(
        titulo = "9. Cita agendada",
        detalle = "citaId = $citaId",
        "Ver mis citas" to { navController.navigate(Rutas.MisCitas.ruta) },
        "Ir al inicio" to { navController.navigate(Rutas.Home.ruta) }
    )
}

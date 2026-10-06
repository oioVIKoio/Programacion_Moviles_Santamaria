package com.santamaria.saludpluscitas.ui.screens.citas

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Barra superior con flecha atrás.
//       LazyColumn con Repositorio.citasDelUsuario.
//       Mensaje de lista vacía cuando no hay citas.
//       Al tocar una cita → Detalle de cita (reto).
@Composable
fun MisCitasScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        titulo = "10. Mis citas",
        detalle = "",
        "Ver detalle (cita 1)" to { navController.navigate(Rutas.DetalleCita.crearRuta(1)) },
        "Volver" to { navController.popBackStack() }
    )
}

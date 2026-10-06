package com.santamaria.saludpluscitas.ui.screens.perfil

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Datos del usuarioActual (nombre, teléfono, correo) y cantidad de citas.
//       Botón "Cerrar sesión": Repositorio.cerrarSesion y volver al Splash limpiando el historial.
@Composable
fun PerfilScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        titulo = "11. Perfil / Mis datos",
        detalle = "",
        "Cerrar sesión" to { navController.navigate(Rutas.Splash.ruta) },
        "Volver" to { navController.popBackStack() }
    )
}

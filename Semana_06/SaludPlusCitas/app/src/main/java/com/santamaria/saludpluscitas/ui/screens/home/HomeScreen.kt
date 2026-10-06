package com.santamaria.saludpluscitas.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Scaffold con NavigationBar en bottomBar: Inicio, Citas, Resultados, Perfil.
//       Saludo "¡Hola, <nombre>!" con el usuarioActual y "¿Qué deseas hacer hoy?".
//       Campana arriba a la derecha → Notificaciones.
//       Grid 2x2 de tarjetas: Agendar cita, Mis citas, Mis datos, Resultados.
//       "Especialidades destacadas" con LazyRow (Repositorio.especialidadesDestacadas) y "Ver todas".
@Composable
fun HomeScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        titulo = "3. Inicio",
        detalle = "",
        "Agendar cita" to { navController.navigate(Rutas.Especialidades.ruta) },
        "Mis citas" to { navController.navigate(Rutas.MisCitas.ruta) },
        "Mis datos" to { navController.navigate(Rutas.Perfil.ruta) },
        "Resultados" to { navController.navigate(Rutas.Resultados.ruta) },
        "Notificaciones" to { navController.navigate(Rutas.Notificaciones.ruta) }
    )
}

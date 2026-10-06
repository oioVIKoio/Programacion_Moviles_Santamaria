package com.santamaria.saludpluscitas.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Logo de la clínica, "Clínica SaludPlus" y "Tu salud, nuestra prioridad".
//       Ilustración del doctor (Image).
//       Botón "Comenzar" → Registro y enlace "Ya tengo una cuenta" → Login.
@Composable
fun SplashScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        titulo = "1. Splash",
        detalle = "",
        "Comenzar" to { navController.navigate(Rutas.Registro.ruta) },
        "Ya tengo una cuenta" to { navController.navigate(Rutas.Login.ruta) }
    )
}

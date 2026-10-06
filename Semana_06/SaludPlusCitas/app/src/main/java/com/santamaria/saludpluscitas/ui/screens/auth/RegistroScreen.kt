package com.santamaria.saludpluscitas.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Estados para nombre, teléfono, correo (opcional) y contraseña.
//       OutlinedTextField con ícono a la izquierda para cada campo.
//       Validaciones: nombre no vacío, teléfono de 9 dígitos, contraseña mínima, correo con @ si se llena.
//       Repositorio.registrarUsuario; si se registra, iniciar sesión e ir al Inicio.
//       Enlace "Términos y Condiciones" → Términos y "¿Ya tienes cuenta? Iniciar sesión" → Login.
@Composable
fun RegistroScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        titulo = "2. Registro",
        detalle = "",
        "Registrarme" to { navController.navigate(Rutas.Home.ruta) },
        "Términos y Condiciones" to { navController.navigate(Rutas.Terminos.ruta) },
        "Iniciar sesión" to { navController.navigate(Rutas.Login.ruta) }
    )
}

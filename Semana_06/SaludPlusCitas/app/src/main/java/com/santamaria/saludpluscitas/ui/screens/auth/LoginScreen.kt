package com.santamaria.saludpluscitas.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Diseñar con el mismo estilo del Registro (campos con ícono, botón azul).
//       Campos: teléfono o correo y contraseña.
//       Repositorio.iniciarSesion (find); mostrar error si las credenciales no coinciden.
//       Al entrar, ir al Inicio sin poder volver al login con Atrás.
//       Enlace "¿No tienes cuenta? Regístrate" → Registro.
@Composable
fun LoginScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        titulo = "8. Iniciar sesión",
        detalle = "",
        "Ingresar" to { navController.navigate(Rutas.Home.ruta) },
        "Crear cuenta" to { navController.navigate(Rutas.Registro.ruta) }
    )
}

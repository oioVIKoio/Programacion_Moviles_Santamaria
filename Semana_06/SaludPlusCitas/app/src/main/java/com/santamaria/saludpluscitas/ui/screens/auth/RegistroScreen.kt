package com.santamaria.saludpluscitas.ui.screens.auth

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.data.repository.Repositorio
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.BotonPrimario
import com.santamaria.saludpluscitas.ui.components.CampoTexto
import com.santamaria.saludpluscitas.ui.components.TextoConEnlace
import com.santamaria.saludpluscitas.ui.theme.MoradoPrimario
import com.santamaria.saludpluscitas.ui.theme.TextoSecundario

@Composable
fun RegistroScreen(
    navController: NavController
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmar by rememberSaveable { mutableStateOf("") }

    // Los errores se muestran recién después de intentar registrarse.
    var intentado by remember { mutableStateOf(false) }
    var errorRegistro by remember { mutableStateOf<String?>(null) }

    val nombreLimpio = nombre.trim()
    val errorNombre = when {
        nombreLimpio.isBlank() -> "Ingresa tu nombre completo"
        nombreLimpio.length < 3 -> "El nombre es muy corto"
        !nombreLimpio.all { it.isLetter() || it == ' ' } -> "Solo letras y espacios"
        nombreLimpio.split(" ").filter { it.isNotBlank() }.size < 2 -> "Ingresa nombre y apellido"
        else -> null
    }
    val errorTelefono = when {
        telefono.length != 9 -> "El teléfono debe tener 9 dígitos"
        !telefono.startsWith("9") -> "El celular debe empezar con 9"
        else -> null
    }
    val errorCorreo = if (correo.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches()) {
        "Correo no válido"
    } else {
        null
    }
    val errorPassword = when {
        password.length < 6 -> "Mínimo 6 caracteres"
        password.length > 20 -> "Máximo 20 caracteres"
        password.contains(' ') -> "Sin espacios"
        else -> null
    }
    val errorConfirmar = if (confirmar != password) "Las contraseñas no coinciden" else null
    val formularioValido = listOf(errorNombre, errorTelefono, errorCorreo, errorPassword, errorConfirmar).all { it == null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Crear cuenta",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Regístrate para agendar tus citas",
            fontSize = 15.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(28.dp))

        CampoTexto(
            etiqueta = "Nombre completo",
            valor = nombre,
            onValorChange = { nombre = it },
            icono = Icons.Default.Person,
            error = if (intentado) errorNombre else null
        )
        Spacer(modifier = Modifier.height(8.dp))
        CampoTexto(
            etiqueta = "Teléfono",
            valor = telefono,
            // Solo dígitos y máximo 9.
            onValorChange = { nuevo -> telefono = nuevo.filter { it.isDigit() }.take(9) },
            icono = Icons.Default.Phone,
            tipoTeclado = KeyboardType.Phone,
            error = if (intentado) errorTelefono else null
        )
        Spacer(modifier = Modifier.height(8.dp))
        CampoTexto(
            etiqueta = "Correo (opcional)",
            valor = correo,
            onValorChange = { correo = it },
            icono = Icons.Default.Email,
            tipoTeclado = KeyboardType.Email,
            error = if (intentado) errorCorreo else null
        )
        Spacer(modifier = Modifier.height(8.dp))
        CampoTexto(
            etiqueta = "Contraseña",
            valor = password,
            onValorChange = { password = it },
            icono = Icons.Default.Lock,
            esPassword = true,
            error = if (intentado) errorPassword else null
        )
        Spacer(modifier = Modifier.height(8.dp))
        CampoTexto(
            etiqueta = "Confirmar contraseña",
            valor = confirmar,
            onValorChange = { confirmar = it },
            icono = Icons.Default.Lock,
            esPassword = true,
            error = if (intentado) errorConfirmar else null
        )

        Spacer(modifier = Modifier.height(20.dp))

        errorRegistro?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        BotonPrimario(
            texto = "Registrarme",
            onClick = {
                intentado = true
                errorRegistro = null
                if (formularioValido) {
                    val registrado = Repositorio.registrarUsuario(nombre, telefono, correo, password)
                    if (registrado) {
                        // El registro no inicia sesión: vuelve al Login con el teléfono
                        // lleno y un mensaje de confirmación. Atrás no regresa al registro.
                        navController.navigate(Rutas.Login.crearRuta(telefono)) {
                            popUpTo(Rutas.Registro.ruta) { inclusive = true }
                        }
                    } else {
                        errorRegistro = "Ese teléfono o correo ya está registrado"
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Al registrarte aceptas nuestros",
            fontSize = 13.sp,
            color = TextoSecundario
        )
        Text(
            text = "Términos y Condiciones",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MoradoPrimario,
            modifier = Modifier
                .clickable { navController.navigate(Rutas.Terminos.ruta) }
                .padding(4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextoConEnlace(
            texto = "¿Ya tienes cuenta? ",
            enlace = "Iniciar sesión",
            onClick = {
                navController.navigate(Rutas.Login.crearRuta()) {
                    popUpTo(Rutas.Registro.ruta) { inclusive = true }
                }
            }
        )
    }
}

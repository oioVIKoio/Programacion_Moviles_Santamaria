package com.santamaria.saludpluscitas.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.ui.components.BarraSuperior
import com.santamaria.saludpluscitas.ui.components.BotonPrimario
import com.santamaria.saludpluscitas.ui.theme.TextoSecundario

// Cada sección: título y texto.
private val secciones = listOf(
    "1. Uso de la aplicación" to
        "SaludPlus permite registrarse, buscar especialidades y médicos, y agendar, ver y cancelar citas " +
        "en la Clínica SaludPlus. El uso de la app implica la aceptación de estos términos.",
    "2. Cuenta del paciente" to
        "El paciente es responsable de que su nombre y teléfono sean correctos y de mantener su contraseña " +
        "en reserva. Cada número de teléfono solo puede tener una cuenta.",
    "3. Citas" to
        "Cada cita dura 30 minutos y queda reservada para el médico, la fecha y la hora elegidos. " +
        "Se recomienda llegar 15 minutos antes. Si no puede asistir, cancele la cita desde la app para liberar el horario.",
    "4. Resultados" to
        "Los resultados de exámenes se muestran como referencia. Su interpretación debe hacerla siempre el médico tratante.",
    "5. Datos personales" to
        "Los datos se usan solo para gestionar las citas del paciente. En esta versión se guardan en el " +
        "dispositivo y se borran al cerrar la aplicación.",
    "6. Cambios" to
        "La clínica puede actualizar estos términos. Los cambios se mostrarán en esta misma pantalla."
)

// Reto 15, vista sin diseño: se abre desde el enlace del Registro.
// El texto va en una Column con verticalScroll.
@Composable
fun TerminosScreen(
    navController: NavController
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BarraSuperior(
                titulo = "Términos y condiciones",
                onAtras = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Última actualización: octubre de 2026",
                fontSize = 13.sp,
                color = TextoSecundario
            )

            secciones.forEach { (titulo, texto) ->
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = texto,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            BotonPrimario(
                texto = "Entendido",
                onClick = { navController.popBackStack() }
            )
        }
    }
}

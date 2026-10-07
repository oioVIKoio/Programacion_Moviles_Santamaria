package com.santamaria.saludpluscitas.ui.screens.citas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices

import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.data.repository.Repositorio
import com.santamaria.saludpluscitas.ui.components.BarraSuperior
import com.santamaria.saludpluscitas.ui.components.EstadoVacio
import com.santamaria.saludpluscitas.ui.components.FilaDato
import com.santamaria.saludpluscitas.ui.components.ResumenMedico
import com.santamaria.saludpluscitas.ui.components.formatearFecha
import com.santamaria.saludpluscitas.ui.components.rangoHora
import com.santamaria.saludpluscitas.ui.theme.BordeSuave
import com.santamaria.saludpluscitas.ui.theme.RojoTexto
import com.santamaria.saludpluscitas.ui.theme.SuperficieBlanca

// Reto 12, vista sin diseño: sigue el estilo de Confirmar cita.
// Cancelar pide confirmación con un AlertDialog y libera el horario.
@Composable
fun DetalleCitaScreen(
    navController: NavController,
    citaId: Int
) {
    val cita = Repositorio.obtenerCita(citaId)
    val medico = cita?.let { Repositorio.obtenerMedico(it.medicoId) }
    val especialidad = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }

    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BarraSuperior(
                titulo = "Detalle de cita",
                onAtras = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        if (cita == null) {
            EstadoVacio(
                mensaje = "Esta cita ya no existe",
                icono = Icons.Default.EventBusy,
                modifier = Modifier.padding(innerPadding)
            )
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            if (medico != null) {
                ResumenMedico(medico = medico, mostrarCmp = true)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SuperficieBlanca),
                border = BorderStroke(1.dp, BordeSuave),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    FilaDato(Icons.Default.LocalHospital, "Especialidad", especialidad?.nombre ?: "")
                    FilaDato(Icons.Default.CalendarMonth, "Fecha", formatearFecha(cita.fecha))
                    FilaDato(Icons.Default.Schedule, "Hora", rangoHora(cita.hora))
                    FilaDato(Icons.Default.MedicalServices, "Tipo de atención", cita.tipo)
                    FilaDato(Icons.Default.LocationOn, "Dirección", medico?.direccion ?: "")
                    FilaDato(
                        Icons.AutoMirrored.Filled.Notes,
                        "Motivo de consulta",
                        cita.motivo.ifBlank { "Sin motivo registrado" }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = { mostrarDialogo = true },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, RojoTexto),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Icon(imageVector = Icons.Default.EventBusy, contentDescription = null, tint = RojoTexto)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Cancelar cita", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RojoTexto)
            }
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                icon = { Icon(imageVector = Icons.Default.EventBusy, contentDescription = null, tint = RojoTexto) },
                title = { Text(text = "¿Cancelar esta cita?") },
                text = {
                    Text(
                        text = "Tu cita del ${formatearFecha(cita.fecha)} a las ${cita.hora} " +
                            "se eliminará y el horario quedará libre."
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mostrarDialogo = false
                            Repositorio.cancelarCita(cita.id)
                            navController.popBackStack()
                        }
                    ) {
                        Text(text = "Sí, cancelar", color = RojoTexto, fontWeight = FontWeight.SemiBold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text(text = "Volver")
                    }
                }
            )
        }
    }
}

package com.santamaria.saludpluscitas.ui.screens.agendamiento

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.data.repository.Repositorio
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.AvatarMedico
import com.santamaria.saludpluscitas.ui.components.BarraSuperior
import com.santamaria.saludpluscitas.ui.components.BotonPrimario
import com.santamaria.saludpluscitas.ui.components.EstadoVacio
import com.santamaria.saludpluscitas.ui.components.FilaDato
import com.santamaria.saludpluscitas.ui.components.formatearFecha
import com.santamaria.saludpluscitas.ui.components.rangoHora
import com.santamaria.saludpluscitas.ui.theme.BordeSuave
import com.santamaria.saludpluscitas.ui.theme.MoradoMedio
import com.santamaria.saludpluscitas.ui.theme.MoradoOscuro
import com.santamaria.saludpluscitas.ui.theme.MoradoPrimario
import com.santamaria.saludpluscitas.ui.theme.SuperficieBlanca
import com.santamaria.saludpluscitas.ui.theme.TextoSecundario

// Confirmación con diseño de ticket: cabecera morada con el médico, la fecha y
// la hora; debajo de la línea punteada, paciente, sede y teléfonos.
@Composable
fun ConfirmarCitaScreen(
    navController: NavController,
    medicoId: Int,
    fecha: String,
    hora: String
) {
    val medico = Repositorio.obtenerMedico(medicoId)
    val especialidad = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }
    val sede = medico?.let { Repositorio.obtenerSede(it.sedeId) }
    val paciente = Repositorio.usuarioActual

    var motivo by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BarraSuperior(
                titulo = "Confirmar cita",
                onAtras = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        if (medico == null) {
            EstadoVacio(mensaje = "No se encontró al médico", modifier = Modifier.padding(innerPadding))
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 8.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SuperficieBlanca),
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Cabecera morada del ticket.
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.linearGradient(listOf(MoradoMedio, MoradoPrimario, MoradoOscuro)))
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Resumen de tu cita",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AvatarMedico(medico = medico, tamano = 60)
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = medico.nombre,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "${especialidad?.nombre ?: medico.profesion} · CMP ${medico.cmp}",
                                    fontSize = 13.sp,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            ChipTicket(Icons.Default.CalendarMonth, formatearFecha(fecha), Modifier.weight(1.4f))
                            ChipTicket(Icons.Default.Schedule, rangoHora(hora), Modifier.weight(1f))
                        }
                    }

                    LineaPunteada()

                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                        FilaDato(Icons.Default.Person, "Paciente", paciente?.nombre ?: "")
                        FilaDato(Icons.Default.LocalHospital, "Tipo de atención", "Consulta presencial")
                        FilaDato(Icons.Default.LocationOn, sede?.nombre ?: "Sede", sede?.direccion ?: "")
                        FilaDato(Icons.Default.Phone, "Teléfono de la sede", sede?.telefono ?: "")
                        FilaDato(Icons.Default.Phone, "Teléfono del médico", medico.telefono)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Text(
                        text = "Motivo de consulta ",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(text = "(opcional)", fontSize = 15.sp, color = TextoSecundario)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = motivo,
                    // No deja escribir más del máximo.
                    onValueChange = { if (it.length <= Repositorio.MAX_MOTIVO) motivo = it },
                    placeholder = { Text("Ej. Consulta de rutina", color = TextoSecundario) },
                    supportingText = { Text("${motivo.length}/${Repositorio.MAX_MOTIVO}") },
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MoradoPrimario,
                        unfocusedBorderColor = BordeSuave,
                        focusedContainerColor = SuperficieBlanca,
                        unfocusedContainerColor = SuperficieBlanca
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                error?.let {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                }
            }

            BotonPrimario(
                texto = "Confirmar cita",
                onClick = {
                    // validarCita revisa sesión, fecha, hora, horario libre y motivo.
                    val problema = Repositorio.validarCita(medicoId, fecha, hora, motivo)
                    val cita = if (problema == null) Repositorio.agendarCita(medicoId, fecha, hora, motivo) else null
                    if (cita != null) {
                        // Se borra el flujo de agendamiento: Atrás desde el éxito vuelve al Inicio.
                        navController.navigate(Rutas.CitaExitosa.crearRuta(cita.id)) {
                            popUpTo(Rutas.Home.ruta)
                        }
                    } else {
                        error = problema ?: "No se pudo agendar la cita. Inténtalo de nuevo."
                    }
                },
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}

// Fecha u hora en blanco sobre la cabecera morada.
@Composable
private fun ChipTicket(
    icono: ImageVector,
    texto: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.18f))
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icono, contentDescription = null, tint = Color.White, modifier = Modifier.padding(end = 6.dp))
        Text(text = texto, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.White, lineHeight = 16.sp)
    }
}

// Línea punteada que separa las dos partes del ticket.
@Composable
private fun LineaPunteada() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = BordeSuave,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 4f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(18f, 12f))
        )
    }
}

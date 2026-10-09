package com.santamaria.saludpluscitas.ui.screens.agendamiento

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.data.repository.Repositorio
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.BarraSuperior
import com.santamaria.saludpluscitas.ui.components.BotonPrimario
import com.santamaria.saludpluscitas.ui.components.ChipHorario
import com.santamaria.saludpluscitas.ui.components.EstadoVacio
import com.santamaria.saludpluscitas.ui.components.ResumenMedico
import com.santamaria.saludpluscitas.ui.theme.MoradoPrimario
import com.santamaria.saludpluscitas.ui.theme.FondoClaro
import com.santamaria.saludpluscitas.ui.theme.TextoSecundario
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

// Día del calendario: etiqueta corta, número y fecha ISO que viaja en la ruta.
private data class DiaCalendario(
    val etiqueta: String,
    val numero: String,
    val fecha: String
)

private val localePeru: Locale = Locale.forLanguageTag("es-PE")
private val finDeSemana = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

// Se puede agendar hasta 8 semanas hacia adelante.
private const val MAX_SEMANAS = 8

// Fase 2: los próximos 5 días hábiles desde "desde" (incluido), sin sábados ni domingos.
private fun diasHabiles(desde: LocalDate): List<LocalDate> {
    return generateSequence(desde) { it.plusDays(1) }
        .filter { it.dayOfWeek !in finDeSemana }
        .take(5)
        .toList()
}

// Mes y año de los días visibles: "Octubre 2026", o "Octubre / Noviembre 2026"
// si la semana cruza de mes.
private fun tituloMes(dias: List<LocalDate>): String {
    fun mes(fecha: LocalDate) = fecha.month
        .getDisplayName(TextStyle.FULL_STANDALONE, localePeru)
        .replaceFirstChar { it.uppercase() }

    val primero = dias.first()
    val ultimo = dias.last()
    return when {
        primero.month == ultimo.month -> "${mes(primero)} ${primero.year}"
        primero.year == ultimo.year -> "${mes(primero)} / ${mes(ultimo)} ${primero.year}"
        else -> "${mes(primero)} ${primero.year} / ${mes(ultimo)} ${ultimo.year}"
    }
}

// LocalDate → "Mié", "8" y "2026-10-08" (ISO, igual que en la Fase 1).
private fun LocalDate.aDiaCalendario(): DiaCalendario {
    val etiqueta = dayOfWeek.getDisplayName(TextStyle.SHORT, localePeru)
        .removeSuffix(".")
        .replaceFirstChar { it.uppercase() }
    return DiaCalendario(etiqueta, dayOfMonth.toString(), toString())
}

@Composable
fun FechaHoraScreen(
    navController: NavController,
    medicoId: Int
) {
    val medico = Repositorio.obtenerMedico(medicoId)

    // Hoy no cambia mientras la pantalla está abierta.
    val hoy = remember { LocalDate.now() }

    // 0 = semana actual. Las flechas suman o restan una semana; no baja de 0.
    var semana by rememberSaveable { mutableIntStateOf(0) }
    val fechasVisibles = diasHabiles(hoy.plusWeeks(semana.toLong()))
    val dias = fechasVisibles.map { it.aDiaCalendario() }

    // rememberSaveable: al volver de Confirmar se mantiene lo elegido.
    var fecha by rememberSaveable { mutableStateOf<String?>(null) }
    var hora by rememberSaveable { mutableStateOf<String?>(null) }

    // Sin día elegido no se muestran horas; con día, solo las libres.
    val horarios = fecha?.let { Repositorio.horariosDisponibles(medicoId, it) } ?: emptyList()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BarraSuperior(
                titulo = "Seleccionar fecha y hora",
                onAtras = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            if (medico == null) {
                EstadoVacio(mensaje = "No se encontró al médico")
                return@Column
            }

            ResumenMedico(medico = medico)

            Spacer(modifier = Modifier.height(16.dp))

            // Al cambiar de semana se borra lo elegido: ese día ya no está a la vista.
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        semana--
                        fecha = null
                        hora = null
                    },
                    enabled = semana > 0
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Semana anterior")
                }
                Text(
                    text = tituloMes(fechasVisibles),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                IconButton(
                    onClick = {
                        semana++
                        fecha = null
                        hora = null
                    },
                    enabled = semana < MAX_SEMANAS
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Semana siguiente")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dias.forEach { dia ->
                    val seleccionado = dia.fecha == fecha
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (seleccionado) MoradoPrimario else FondoClaro)
                            .clickable {
                                fecha = dia.fecha
                                // La hora elegida puede no existir en el nuevo día.
                                hora = null
                            }
                            .padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = dia.etiqueta,
                            fontSize = 13.sp,
                            color = if (seleccionado) Color.White else TextoSecundario
                        )
                        Text(
                            text = dia.numero,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (seleccionado) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            when {
                fecha == null -> EstadoVacio(
                    mensaje = "Elige un día para ver los horarios",
                    icono = Icons.Default.CalendarMonth,
                    modifier = Modifier.weight(1f)
                )
                horarios.isEmpty() -> EstadoVacio(
                    mensaje = "No hay horarios disponibles este día",
                    modifier = Modifier.weight(1f)
                )
                else -> Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (horarios.size == 1) "1 horario disponible" else "${horarios.size} horarios disponibles",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MoradoPrimario
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(horarios, key = { it }) { h ->
                            ChipHorario(
                                hora = h,
                                seleccionado = h == hora,
                                onClick = { hora = h }
                            )
                        }
                    }
                }
            }

            BotonPrimario(
                texto = "Continuar",
                enabled = fecha != null && hora != null,
                onClick = {
                    val f = fecha
                    val h = hora
                    if (f != null && h != null) {
                        navController.navigate(Rutas.ConfirmarCita.crearRuta(medicoId, f, h))
                    }
                },
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}

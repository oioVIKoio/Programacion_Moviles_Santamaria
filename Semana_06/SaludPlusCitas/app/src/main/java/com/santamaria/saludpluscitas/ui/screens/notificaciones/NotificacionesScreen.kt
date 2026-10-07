package com.santamaria.saludpluscitas.ui.screens.notificaciones

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.data.repository.Repositorio
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.BarraSuperior
import com.santamaria.saludpluscitas.ui.components.EstadoVacio
import com.santamaria.saludpluscitas.ui.components.formatearFecha
import com.santamaria.saludpluscitas.ui.theme.AzulClaro
import com.santamaria.saludpluscitas.ui.theme.AzulPrimario
import com.santamaria.saludpluscitas.ui.theme.BordeSuave
import com.santamaria.saludpluscitas.ui.theme.SuperficieBlanca
import com.santamaria.saludpluscitas.ui.theme.TextoSecundario

// Lo que se muestra de cada recordatorio. Solo lo usa esta pantalla.
private data class Recordatorio(
    val citaId: Int,
    val titulo: String,
    val mensaje: String
)

// Reto 14, vista sin diseño: un recordatorio por cada cita del usuario,
// generado con map. Al tocarlo se abre el Detalle de la cita.
@Composable
fun NotificacionesScreen(
    navController: NavController
) {
    val recordatorios = Repositorio.citasDelUsuario().map { cita ->
        val medico = Repositorio.obtenerMedico(cita.medicoId)
        Recordatorio(
            citaId = cita.id,
            titulo = "Cita con ${medico?.nombre ?: "tu médico"}",
            mensaje = "${formatearFecha(cita.fecha)} a las ${cita.hora}. Llega 15 minutos antes."
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            BarraSuperior(
                titulo = "Notificaciones",
                onAtras = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        if (recordatorios.isEmpty()) {
            EstadoVacio(
                mensaje = "No tienes notificaciones por ahora",
                icono = Icons.Outlined.NotificationsOff,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(recordatorios, key = { it.citaId }) { recordatorio ->
                    TarjetaRecordatorio(
                        recordatorio = recordatorio,
                        onClick = { navController.navigate(Rutas.DetalleCita.crearRuta(recordatorio.citaId)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaRecordatorio(
    recordatorio: Recordatorio,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SuperficieBlanca),
        border = BorderStroke(1.dp, BordeSuave),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AzulClaro),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.NotificationsActive, contentDescription = null, tint = AzulPrimario)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = recordatorio.titulo,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(text = recordatorio.mensaje, fontSize = 14.sp, color = TextoSecundario)
            }
        }
    }
}

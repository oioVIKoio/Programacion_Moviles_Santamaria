package com.santamaria.clinicatecsup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.clinicatecsup.navigation.AppDrawer
import com.santamaria.clinicatecsup.navigation.Screen
import com.santamaria.clinicatecsup.ui.theme.BackgroundLight
import com.santamaria.clinicatecsup.ui.theme.PurplePrimary
import com.santamaria.clinicatecsup.ui.theme.StatusGreenContainer
import com.santamaria.clinicatecsup.ui.theme.StatusGreenText
import com.santamaria.clinicatecsup.ui.theme.StatusGreyContainer
import com.santamaria.clinicatecsup.ui.theme.StatusGreyText
import com.santamaria.clinicatecsup.ui.theme.StatusRedContainer
import com.santamaria.clinicatecsup.ui.theme.StatusRedText

data class CitaItem(
    val id: Int,
    val doctor: String,
    val fechaHora: String,
    val estado: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(
    navController: NavController
) {

    var listaCitas by remember {
        mutableStateOf(
            listOf(
                CitaItem(
                    id = 1,
                    doctor = "Dra. Ana Torres",
                    fechaHora = "Viernes 27, 10:30 am",
                    estado = "Confirmada"
                ),
                CitaItem(
                    id = 2,
                    doctor = "Dr. Luis Vega",
                    fechaHora = "Miércoles 15, 3:00 pm",
                    estado = "Completada"
                )
            )
        )
    }

    var citaACancelar by remember {
        mutableStateOf<CitaItem?>(null)
    }

    AppDrawer(
        navController = navController,
        currentRoute = Screen.Appointments.route
    ) { openDrawer ->

        Scaffold(
            containerColor = BackgroundLight,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Mis citas",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = openDrawer) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BackgroundLight
                    )
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    items(listaCitas) { cita ->

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min)
                            ) {

                                // Franja morada a la izquierda si está confirmada
                                Box(
                                    modifier = Modifier
                                        .width(6.dp)
                                        .fillMaxHeight()
                                        .background(
                                            if (cita.estado == "Confirmada") PurplePrimary
                                            else Color.Transparent
                                        )
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Text(
                                            text = cita.doctor,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 17.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        // Badge de estado
                                        val (containerColor, textColor) = when (cita.estado) {
                                            "Confirmada" -> StatusGreenContainer to StatusGreenText
                                            "Cancelada" -> StatusRedContainer to StatusRedText
                                            else -> StatusGreyContainer to StatusGreyText
                                        }

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(containerColor)
                                                .padding(
                                                    horizontal = 10.dp,
                                                    vertical = 4.dp
                                                )
                                        ) {
                                            Text(
                                                text = cita.estado,
                                                color = textColor,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Text(
                                        text = cita.fechaHora,
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )

                                    // Botón Cancelar cita sólo para citas Confirmadas
                                    if (cita.estado == "Confirmada") {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            TextButton(
                                                onClick = {
                                                    citaACancelar = cita
                                                },
                                                colors = ButtonDefaults.textButtonColors(
                                                    contentColor = StatusRedText
                                                )
                                            ) {
                                                Text(
                                                    text = "Cancelar cita",
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Diálogo de confirmación para cancelar cita
            citaACancelar?.let { cita ->
                AlertDialog(
                    onDismissRequest = {
                        citaACancelar = null
                    },
                    title = {
                        Text(
                            text = "¿Cancelar cita?",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text(
                            text = "¿Estás seguro de que deseas cancelar esta cita con ${cita.doctor}?"
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                listaCitas = listaCitas.map { item ->
                                    if (item.id == cita.id) {
                                        item.copy(estado = "Cancelada")
                                    } else {
                                        item
                                    }
                                }
                                citaACancelar = null
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = StatusRedText
                            )
                        ) {
                            Text(
                                text = "Cancelar cita",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                citaACancelar = null
                            }
                        ) {
                            Text("Volver")
                        }
                    }
                )
            }
        }
    }
}
package com.santamaria.clinica.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.clinica.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(
    navController: NavController,
    doctorId: Int
) {

    var fechaSeleccionada by remember {
        mutableStateOf("")
    }

    var horaSeleccionada by remember {
        mutableStateOf("")
    }

    val fechas = listOf(
        "Jue 26",
        "Vie 27",
        "Sab 28"
    )

    val horas = listOf(
        "9:00",
        "10:30",
        "3:00"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Agendar cita")
                },
                navigationIcon = {
                    TextButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text("←")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Selecciona fecha")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                fechas.forEach { fecha ->

                    FilterChip(
                        selected = fechaSeleccionada == fecha,
                        onClick = {
                            fechaSeleccionada = fecha
                        },
                        label = {
                            Text(fecha)
                        }
                    )
                }
            }

            Text("Selecciona hora")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                horas.forEach { hora ->

                    FilterChip(
                        selected = horaSeleccionada == hora,
                        onClick = {
                            horaSeleccionada = hora
                        },
                        label = {
                            Text(hora)
                        }
                    )
                }
            }

            Button(
                onClick = {
                    navController.navigate(
                        Screen.Confirmation.createRoute(
                            doctorId,
                            fechaSeleccionada.replace(" ", "-"),
                            horaSeleccionada.replace(":", "-")
                        )
                    )
                },
                enabled =
                    fechaSeleccionada.isNotEmpty() &&
                            horaSeleccionada.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar cita")
            }
        }
    }
}
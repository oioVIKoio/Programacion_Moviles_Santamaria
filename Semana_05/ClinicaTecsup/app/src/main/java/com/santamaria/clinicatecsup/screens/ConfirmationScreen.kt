package com.santamaria.clinicatecsup.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.clinicatecsup.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationScreen(
    navController: NavController,
    doctorId: Int,
    fecha: String,
    hora: String
) {

    val medicos = listOf(
        "Dra. Ana Torres",
        "Dr. Luis Vega",
        "Dra. Rosa Díaz"
    )

    val especialidades = listOf(
        "Cardiología",
        "Pediatría",
        "Dermatología"
    )

    val nombre = medicos.getOrElse(doctorId) { "Médico" }
    val especialidad = especialidades.getOrElse(doctorId) { "Especialidad" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Confirmación")
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

            Text("✓ Cita confirmada")

            Text("Tu cita ha sido registrada correctamente.")

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Resumen de la cita")

                    Text("Médico: $nombre")
                    Text("Especialidad: $especialidad")
                    Text("Fecha: ${fecha.replace("-", " ")}")
                    Text("Hora: ${hora.replace("-", ":")}")
                }
            }

            Button(
                onClick = {
                    navController.navigate(Screen.Appointments.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver mis citas")
            }

            Button(
                onClick = {
                    navController.navigate(Screen.Home.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al inicio")
            }
        }
    }
}
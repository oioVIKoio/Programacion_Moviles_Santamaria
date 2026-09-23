package com.santamaria.clinica.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.clinica.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDetailScreen(
    navController: NavController,
    doctorId: Int
) {

    val nombres = listOf(
        "Dra. Ana Torres",
        "Dr. Luis Vega",
        "Dra. Rosa Díaz"
    )

    val especialidades = listOf(
        "Cardiología",
        "Pediatría",
        "Dermatología"
    )

    val calificaciones = listOf(
        "4.9",
        "4.7",
        "4.8"
    )

    val descripciones = listOf(
        "Especialista en arritmias e hipertensión. Formación en la Clínica Mayo.",
        "Especialista en atención pediátrica y cuidado integral de niños.",
        "Especialista en diagnóstico y tratamiento de enfermedades de la piel."
    )

    val nombre = nombres.getOrElse(doctorId) { "Médico" }
    val especialidad = especialidades.getOrElse(doctorId) { "Especialidad" }
    val calificacion = calificaciones.getOrElse(doctorId) { "0.0" }
    val descripcion = descripciones.getOrElse(doctorId) { "Sin información" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Perfil del médico")
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text("✚")

            Text(nombre)

            Text(especialidad)

            Text("★ $calificacion")

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(descripcion)

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    navController.navigate(
                        Screen.Appointment.createRoute(doctorId)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agendar cita")
            }
        }
    }
}
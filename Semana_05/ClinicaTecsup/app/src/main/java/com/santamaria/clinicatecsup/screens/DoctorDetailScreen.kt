package com.santamaria.clinicatecsup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.clinicatecsup.navigation.Screen
import com.santamaria.clinicatecsup.ui.theme.BackgroundLight
import com.santamaria.clinicatecsup.ui.theme.PurpleContainer
import com.santamaria.clinicatecsup.ui.theme.PurplePrimary
import com.santamaria.clinicatecsup.ui.theme.StarGold

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
        containerColor = BackgroundLight,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Perfil del médico",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Avatar circular grande y centrado
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(PurpleContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = PurplePrimary,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre del médico centrado
            Text(
                text = nombre,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Especialidad y valoración
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = especialidad,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = StarGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = calificacion,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Descripción profesional
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón Agendar cita morado, ancho
            Button(
                onClick = {
                    navController.navigate(
                        Screen.Appointment.createRoute(doctorId)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurplePrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Agendar cita",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
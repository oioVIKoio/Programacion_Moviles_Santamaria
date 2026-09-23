package com.santamaria.clinica.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.clinica.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {

    val especialidades = listOf(
        "Cardiología",
        "Pediatría"
    )

    val medicos = listOf(
        "Dra. Ana Torres" to "Cardiología",
        "Dr. Luis Vega" to "Pediatría",
        "Dra. Rosa Díaz" to "Dermatología"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Clínica Salud+")
        Text("Hola, Victor")

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(especialidades) { especialidad ->

                Card {
                    Text(
                        text = especialidad,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Médicos disponibles")

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(medicos.size) { index ->

                val medico = medicos[index]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                Screen.DoctorDetail.createRoute(index)
                            )
                        }
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {
                            Text(medico.first)
                            Text(medico.second)
                        }

                        Text(
                            when (index) {
                                0 -> "★ 4.9"
                                1 -> "★ 4.7"
                                else -> "★ 4.8"
                            }
                        )
                    }
                }
            }
        }
    }
}
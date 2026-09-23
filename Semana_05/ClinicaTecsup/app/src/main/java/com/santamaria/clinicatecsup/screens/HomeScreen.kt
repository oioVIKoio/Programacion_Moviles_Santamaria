package com.santamaria.clinicatecsup.screens

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.clinicatecsup.navigation.AppDrawer
import com.santamaria.clinicatecsup.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {

    val especialidades = listOf(
        "Cardiología",
        "Pediatría"
    )

    var especialidadSeleccionada by remember {
        mutableStateOf<String?>(null)
    }

    val medicos = listOf(
        "Dra. Ana Torres" to "Cardiología",
        "Dr. Luis Vega" to "Pediatría",
        "Dra. Rosa Díaz" to "Dermatología"
    )

    val medicosFiltrados =
        if (especialidadSeleccionada == null) {
            medicos
        } else {
            medicos.filter {
                it.second == especialidadSeleccionada
            }
        }

    AppDrawer(
        navController = navController
    ) { openDrawer ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row {

                IconButton(
                    onClick = openDrawer
                ) {
                    Text("☰")
                }

                Column {
                    Text("Clínica Salud+")
                    Text("Hola, Victor")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(especialidades) { especialidad ->

                    Card(
                        modifier = Modifier.clickable {

                            especialidadSeleccionada =
                                if (
                                    especialidadSeleccionada ==
                                    especialidad
                                ) {
                                    null
                                } else {
                                    especialidad
                                }
                        }
                    ) {

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
                verticalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                items(medicosFiltrados) { medico ->

                    val index = medicos.indexOf(medico)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                navController.navigate(
                                    Screen.DoctorDetail
                                        .createRoute(index)
                                )
                            }
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement =
                                Arrangement.SpaceBetween
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
}
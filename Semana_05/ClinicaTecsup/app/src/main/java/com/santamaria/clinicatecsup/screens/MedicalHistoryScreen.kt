package com.santamaria.clinicatecsup.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.clinicatecsup.navigation.AppDrawer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalHistoryScreen(
    navController: NavController
) {

    AppDrawer(
        navController = navController
    ) { openDrawer ->

        Scaffold(
            topBar = {

                TopAppBar(
                    title = {
                        Text("Historial médico")
                    },
                    navigationIcon = {

                        IconButton(
                            onClick = openDrawer
                        ) {
                            Text("☰")
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
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                Text("Historial médico de Victor")

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(4.dp)
                    ) {

                        Text("15/08/2026")
                        Text("Cardiología")
                        Text("Control general")
                        Text("Dra. Ana Torres")
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(4.dp)
                    ) {

                        Text("10/06/2026")
                        Text("Medicina general")
                        Text("Consulta médica")
                        Text("Dr. Luis Vega")
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(4.dp)
                    ) {

                        Text("22/04/2026")
                        Text("Dermatología")
                        Text("Control de rutina")
                        Text("Dra. Rosa Díaz")
                    }
                }
            }
        }
    }
}
package com.santamaria.clinicatecsup.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.clinicatecsup.navigation.AppDrawer

@Composable
fun AppointmentsScreen(
    navController: NavController
) {

    val citas = listOf(
        Triple(
            "Dra. Ana Torres",
            "Viernes 27, 10:30 am",
            "Confirmada"
        ),
        Triple(
            "Dr. Luis Vega",
            "Miércoles 15, 3:00 pm",
            "Completada"
        )
    )

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

                Text(
                    text = "Mis citas",
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                items(citas) { cita ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(4.dp)
                        ) {

                            Text(cita.first)

                            Text(cita.second)

                            Text(cita.third)
                        }
                    }
                }
            }
        }
    }
}
package com.santamaria.smartfit.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.smartfit.navigation.Screen

@Composable
fun ClassDetailScreen(
    navController: NavController,
    classId: Int
) {

    val clases = listOf(
        Triple("Yoga funcional", "7:00 am · Sala 2", "10 de 12 cupos disponibles"),
        Triple("Cross Training", "6:00 pm · Sala 1", "8 de 12 cupos disponibles"),
        Triple("Spinning", "7:30 pm · Sala 3", "6 de 12 cupos disponibles")
    )

    val clase = clases.getOrElse(classId) {
        clases[0]
    }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("← Detalle de clase")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(clase.first)
            Text(clase.second)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Entrenamiento funcional de alta intensidad. " +
                        "Cupos limitados."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(clase.third)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(
                        Screen.Confirmation.createRoute(classId)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reservar cupo")
            }
        }
    }
}
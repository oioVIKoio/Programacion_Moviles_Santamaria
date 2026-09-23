package com.santamaria.smartfit.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.smartfit.navigation.Screen

@Composable
fun ConfirmationScreen(
    navController: NavController,
    classId: Int
) {

    val clases = listOf(
        Triple("Yoga funcional", "Hoy, 7:00 am", "Sala 2"),
        Triple("Cross Training", "Hoy, 6:00 pm", "Sala 1"),
        Triple("Spinning", "Hoy, 7:30 pm", "Sala 3")
    )

    val clase = clases.getOrElse(classId) {
        clases[0]
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("✓")

        Text("¡Cupo reservado!")

        Text(clase.first)

        Text("${clase.second} · ${clase.third}")

        Button(
            onClick = {
                navController.navigate(Screen.Reservations.route)
            },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Ver mis reservas")
        }
    }
}
package com.santamaria.smartfit.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.smartfit.components.BottomNavigationBar

@Composable
fun RoutinesScreen(navController: NavController) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Text("Mis rutinas")

            Text(
                text = "Aquí podrás consultar tus rutinas de entrenamiento.",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
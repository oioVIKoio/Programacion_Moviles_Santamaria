package com.santamaria.smartfit.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.smartfit.components.BottomNavigationBar
import com.santamaria.smartfit.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {

    val clases = listOf(
        Triple("Yoga funcional", "7:00 am", "Sala 2"),
        Triple("Cross Training", "6:00 pm", "Sala 1"),
        Triple("Spinning", "7:30 pm", "Sala 3")
    )

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

            Text("TECSUP Fit")
            Text("Hola, Victor")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card {
                    Text(
                        text = "Hoy",
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Card {
                    Text(
                        text = "Esta semana",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Clases disponibles")

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(clases) { clase ->

                    val index = clases.indexOf(clase)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.ClassDetail.createRoute(index)
                                )
                            }
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(clase.first)
                            Text("${clase.second} · ${clase.third}")
                        }
                    }
                }
            }
        }
    }
}
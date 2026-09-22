package com.santamaria.navlab.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santamaria.navlab.navigation.Screen

@Composable
fun ListScreen(navController: NavController) {

    val elementos = (1..10).toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("LISTA DE ELEMENTOS")

        LazyColumn {

            items(elementos) { itemId ->

                Text(
                    text = "Elemento número $itemId",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                Screen.Detail.createRoute(itemId)
                            )
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}
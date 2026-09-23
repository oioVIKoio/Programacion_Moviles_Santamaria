package com.santamaria.smartfit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.santamaria.smartfit.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route)
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = "Inicio")
            },
            label = {
                Text("Inicio")
            }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Reservations.route,
            onClick = {
                navController.navigate(Screen.Reservations.route)
            },
            icon = {
                Icon(Icons.Default.Event, contentDescription = "Reservas")
            },
            label = {
                Text("Reservas")
            }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Routines.route,
            onClick = {
                navController.navigate(Screen.Routines.route)
            },
            icon = {
                Icon(Icons.Default.FitnessCenter, contentDescription = "Rutinas")
            },
            label = {
                Text("Rutinas")
            }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Profile.route,
            onClick = {
                navController.navigate(Screen.Profile.route)
            },
            icon = {
                Icon(Icons.Default.Person, contentDescription = "Perfil")
            },
            label = {
                Text("Perfil")
            }
        )
    }
}
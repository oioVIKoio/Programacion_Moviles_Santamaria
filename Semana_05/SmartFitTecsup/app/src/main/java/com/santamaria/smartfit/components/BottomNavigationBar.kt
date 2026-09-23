package com.santamaria.smartfit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.santamaria.smartfit.navigation.Screen
import com.santamaria.smartfit.ui.theme.PetrolGreen
import com.santamaria.smartfit.ui.theme.PetrolGreenLight
import com.santamaria.smartfit.ui.theme.SubText

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {

        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = {
                if (currentRoute != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = "Inicio")
            },
            label = {
                Text(
                    "Inicio",
                    fontWeight = if (currentRoute == Screen.Home.route) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PetrolGreen,
                selectedTextColor = PetrolGreen,
                indicatorColor = PetrolGreenLight,
                unselectedIconColor = SubText,
                unselectedTextColor = SubText
            )
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Reservations.route,
            onClick = {
                if (currentRoute != Screen.Reservations.route) {
                    navController.navigate(Screen.Reservations.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            },
            icon = {
                Icon(Icons.Default.Event, contentDescription = "Reservas")
            },
            label = {
                Text(
                    "Reservas",
                    fontWeight = if (currentRoute == Screen.Reservations.route) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PetrolGreen,
                selectedTextColor = PetrolGreen,
                indicatorColor = PetrolGreenLight,
                unselectedIconColor = SubText,
                unselectedTextColor = SubText
            )
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Routines.route,
            onClick = {
                if (currentRoute != Screen.Routines.route) {
                    navController.navigate(Screen.Routines.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            },
            icon = {
                Icon(Icons.Default.FitnessCenter, contentDescription = "Rutinas")
            },
            label = {
                Text(
                    "Rutinas",
                    fontWeight = if (currentRoute == Screen.Routines.route) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PetrolGreen,
                selectedTextColor = PetrolGreen,
                indicatorColor = PetrolGreenLight,
                unselectedIconColor = SubText,
                unselectedTextColor = SubText
            )
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Profile.route,
            onClick = {
                if (currentRoute != Screen.Profile.route) {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            },
            icon = {
                Icon(Icons.Default.Person, contentDescription = "Perfil")
            },
            label = {
                Text(
                    "Perfil",
                    fontWeight = if (currentRoute == Screen.Profile.route) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PetrolGreen,
                selectedTextColor = PetrolGreen,
                indicatorColor = PetrolGreenLight,
                unselectedIconColor = SubText,
                unselectedTextColor = SubText
            )
        )
    }
}

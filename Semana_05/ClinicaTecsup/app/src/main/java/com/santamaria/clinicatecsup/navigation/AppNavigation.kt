package com.santamaria.clinicatecsup.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.santamaria.clinica.screens.AppointmentScreen
import com.santamaria.clinica.screens.HomeScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.Appointment.route,
            arguments = listOf(
                navArgument("doctorId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val doctorId =
                backStackEntry.arguments?.getInt("doctorId") ?: 0

            AppointmentScreen(
                navController = navController,
                doctorId = doctorId
            )
        }
    }
}
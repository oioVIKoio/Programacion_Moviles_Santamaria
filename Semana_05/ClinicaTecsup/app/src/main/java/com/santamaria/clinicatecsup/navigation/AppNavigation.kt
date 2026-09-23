package com.santamaria.clinicatecsup.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.santamaria.clinicatecsup.screens.AppointmentScreen
import com.santamaria.clinicatecsup.screens.AppointmentsScreen
import com.santamaria.clinicatecsup.screens.ConfirmationScreen
import com.santamaria.clinicatecsup.screens.DoctorDetailScreen
import com.santamaria.clinicatecsup.screens.HomeScreen
import com.santamaria.clinicatecsup.screens.MedicalHistoryScreen

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
            route = Screen.DoctorDetail.route,
            arguments = listOf(
                navArgument("doctorId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val doctorId =
                backStackEntry.arguments
                    ?.getInt("doctorId") ?: 0

            DoctorDetailScreen(
                navController = navController,
                doctorId = doctorId
            )
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
                backStackEntry.arguments
                    ?.getInt("doctorId") ?: 0

            AppointmentScreen(
                navController = navController,
                doctorId = doctorId
            )
        }

        composable(
            route = Screen.Confirmation.route,
            arguments = listOf(

                navArgument("doctorId") {
                    type = NavType.IntType
                },

                navArgument("fecha") {
                    type = NavType.StringType
                },

                navArgument("hora") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val doctorId =
                backStackEntry.arguments
                    ?.getInt("doctorId") ?: 0

            val fecha =
                backStackEntry.arguments
                    ?.getString("fecha") ?: ""

            val hora =
                backStackEntry.arguments
                    ?.getString("hora") ?: ""

            ConfirmationScreen(
                navController = navController,
                doctorId = doctorId,
                fecha = fecha,
                hora = hora
            )
        }

        composable(Screen.Appointments.route) {
            AppointmentsScreen(navController)
        }

        composable(Screen.MedicalHistory.route) {
            MedicalHistoryScreen(navController)
        }
    }
}
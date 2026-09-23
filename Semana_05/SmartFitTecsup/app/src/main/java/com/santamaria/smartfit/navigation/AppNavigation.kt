package com.santamaria.smartfit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.santamaria.smartfit.screens.ClassDetailScreen
import com.santamaria.smartfit.screens.ConfirmationScreen
import com.santamaria.smartfit.screens.HomeScreen
import com.santamaria.smartfit.screens.ProfileScreen
import com.santamaria.smartfit.screens.ReservationsScreen
import com.santamaria.smartfit.screens.RoutinesScreen

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
            route = Screen.ClassDetail.route,
            arguments = listOf(
                navArgument("classId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val classId =
                backStackEntry.arguments?.getInt("classId") ?: 0

            ClassDetailScreen(
                navController = navController,
                classId = classId
            )
        }

        composable(
            route = Screen.Confirmation.route,
            arguments = listOf(
                navArgument("classId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val classId =
                backStackEntry.arguments?.getInt("classId") ?: 0

            ConfirmationScreen(
                navController = navController,
                classId = classId
            )
        }

        composable(Screen.Reservations.route) {
            ReservationsScreen(navController)
        }

        composable(Screen.Routines.route) {
            RoutinesScreen(navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
    }
}
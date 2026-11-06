package com.tecsup.authfirebaseappsantamaria

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController

object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val CURSOS = "cursos"
    const val CURSO_FORM = "curso_form"
}

@Composable
fun AuthApp() {
    val navController = rememberNavController()
    AuthNavGraph(navController)
}

@Composable
fun AuthNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN
    ) {
        composable(Destinations.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Destinations.HOME) {
            HomeScreen(
                onVerCursos = {
                    navController.navigate(Destinations.CURSOS)
                },
                onLogout = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.CURSOS) {
            CursosScreen(
                onAgregar = { navController.navigate(Destinations.CURSO_FORM) },
                onEditar = { id -> navController.navigate("${Destinations.CURSO_FORM}?id=$id") },
                onVolver = { navController.popBackStack() }
            )
        }

        // Mismo formulario para crear (sin id) y editar (con id)
        composable(
            route = "${Destinations.CURSO_FORM}?id={id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            CursoFormScreen(
                cursoId = backStackEntry.arguments?.getString("id"),
                onGuardado = { navController.popBackStack() },
                onVolver = { navController.popBackStack() }
            )
        }
    }
}

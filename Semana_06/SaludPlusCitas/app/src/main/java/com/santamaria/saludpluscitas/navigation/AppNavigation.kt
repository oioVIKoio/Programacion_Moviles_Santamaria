package com.santamaria.saludpluscitas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.santamaria.saludpluscitas.ui.screens.agendamiento.CitaExitosaScreen
import com.santamaria.saludpluscitas.ui.screens.agendamiento.ConfirmarCitaScreen
import com.santamaria.saludpluscitas.ui.screens.agendamiento.EspecialidadesScreen
import com.santamaria.saludpluscitas.ui.screens.agendamiento.FechaHoraScreen
import com.santamaria.saludpluscitas.ui.screens.agendamiento.MedicosScreen
import com.santamaria.saludpluscitas.ui.screens.agendamiento.MedicosSedeScreen
import com.santamaria.saludpluscitas.ui.screens.agendamiento.SedesScreen
import com.santamaria.saludpluscitas.ui.screens.auth.LoginScreen
import com.santamaria.saludpluscitas.ui.screens.auth.RegistroScreen
import com.santamaria.saludpluscitas.ui.screens.auth.SplashScreen
import com.santamaria.saludpluscitas.ui.screens.auth.TerminosScreen
import com.santamaria.saludpluscitas.ui.screens.citas.DetalleCitaScreen
import com.santamaria.saludpluscitas.ui.screens.citas.MisCitasScreen
import com.santamaria.saludpluscitas.ui.screens.home.HomeScreen
import com.santamaria.saludpluscitas.ui.screens.medicos.DoctoresScreen
import com.santamaria.saludpluscitas.ui.screens.notificaciones.NotificacionesScreen
import com.santamaria.saludpluscitas.ui.screens.perfil.PerfilScreen
import com.santamaria.saludpluscitas.ui.screens.resultados.ResultadosScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.Splash.ruta
    ) {

        // auth
        composable(Rutas.Splash.ruta) {
            SplashScreen(navController)
        }

        composable(Rutas.Registro.ruta) {
            RegistroScreen(navController)
        }

        composable(
            route = Rutas.Login.ruta,
            arguments = listOf(
                navArgument("telefono") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val telefono = backStackEntry.arguments?.getString("telefono") ?: ""
            LoginScreen(
                navController = navController,
                telefonoRegistrado = telefono
            )
        }

        composable(Rutas.Terminos.ruta) {
            TerminosScreen(navController)
        }

        // home
        composable(Rutas.Home.ruta) {
            HomeScreen(navController)
        }

        // agendamiento
        composable(Rutas.Sedes.ruta) {
            SedesScreen(navController)
        }

        composable(
            route = Rutas.MedicosSede.ruta,
            arguments = listOf(
                navArgument("sedeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val sedeId = backStackEntry.arguments?.getInt("sedeId") ?: 0
            MedicosSedeScreen(
                navController = navController,
                sedeId = sedeId
            )
        }

        composable(Rutas.Especialidades.ruta) {
            EspecialidadesScreen(navController)
        }

        composable(
            route = Rutas.Medicos.ruta,
            arguments = listOf(
                navArgument("especialidadId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val especialidadId = backStackEntry.arguments?.getInt("especialidadId") ?: 0
            MedicosScreen(
                navController = navController,
                especialidadId = especialidadId
            )
        }

        composable(
            route = Rutas.FechaHora.ruta,
            arguments = listOf(
                navArgument("medicoId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            FechaHoraScreen(
                navController = navController,
                medicoId = medicoId
            )
        }

        composable(
            route = Rutas.ConfirmarCita.ruta,
            arguments = listOf(
                navArgument("medicoId") { type = NavType.IntType },
                navArgument("fecha") { type = NavType.StringType },
                navArgument("hora") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            val fecha = backStackEntry.arguments?.getString("fecha") ?: ""
            val hora = backStackEntry.arguments?.getString("hora") ?: ""
            ConfirmarCitaScreen(
                navController = navController,
                medicoId = medicoId,
                fecha = fecha,
                hora = hora
            )
        }

        composable(
            route = Rutas.CitaExitosa.ruta,
            arguments = listOf(
                navArgument("citaId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val citaId = backStackEntry.arguments?.getInt("citaId") ?: 0
            CitaExitosaScreen(
                navController = navController,
                citaId = citaId
            )
        }

        // citas
        composable(Rutas.MisCitas.ruta) {
            MisCitasScreen(navController)
        }

        composable(
            route = Rutas.DetalleCita.ruta,
            arguments = listOf(
                navArgument("citaId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val citaId = backStackEntry.arguments?.getInt("citaId") ?: 0
            DetalleCitaScreen(
                navController = navController,
                citaId = citaId
            )
        }

        // menú lateral
        composable(Rutas.Doctores.ruta) {
            DoctoresScreen(navController)
        }

        // perfil, resultados y notificaciones
        composable(Rutas.Perfil.ruta) {
            PerfilScreen(navController)
        }

        composable(Rutas.Resultados.ruta) {
            ResultadosScreen(navController)
        }

        composable(Rutas.Notificaciones.ruta) {
            NotificacionesScreen(navController)
        }
    }
}

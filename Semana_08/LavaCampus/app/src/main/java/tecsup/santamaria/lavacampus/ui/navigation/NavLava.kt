package tecsup.santamaria.lavacampus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tecsup.santamaria.lavacampus.LavaApp
import tecsup.santamaria.lavacampus.model.Rol
import tecsup.santamaria.lavacampus.ui.administrador.ServiciosScreen
import tecsup.santamaria.lavacampus.ui.estudiante.SplashScreen
import tecsup.santamaria.lavacampus.ui.theme.LavaTheme
import tecsup.santamaria.lavacampus.viewmodel.LavaViewModelFactory
import tecsup.santamaria.lavacampus.viewmodel.ServiciosViewModel

@Composable
fun NavLava() {
    val app = LocalContext.current.applicationContext as LavaApp
    val nav = rememberNavController()

    // TODO (Paso 6): el tema depende del perfil activo y del modo oscuro guardado en DataStore
    LavaTheme(rol = Rol.ADMIN) {
        NavHost(navController = nav, startDestination = Rutas.SPLASH) {
            composable(Rutas.SPLASH) {
                SplashScreen(onTerminar = {
                    // TODO (Paso 6): ir al login o directo a la pantalla principal del perfil guardado
                    nav.navigate(Rutas.SERVICIOS) { popUpTo(Rutas.SPLASH) { inclusive = true } }
                })
            }
            composable(Rutas.SERVICIOS) {
                val vm: ServiciosViewModel = viewModel(factory = LavaViewModelFactory { ServiciosViewModel(app.repository) })
                ServiciosScreen(vm = vm, onEditar = { /* TODO (Paso 13): abrir FormularioServicio */ })
            }
            // TODO: agregar los destinos de cada paso (Rutas.kt ya los tiene)
        }
    }
}

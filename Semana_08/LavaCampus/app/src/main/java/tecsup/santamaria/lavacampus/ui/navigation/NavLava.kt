package tecsup.santamaria.lavacampus.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tecsup.santamaria.lavacampus.LavaApp
import tecsup.santamaria.lavacampus.model.Rol
import tecsup.santamaria.lavacampus.ui.administrador.ServiciosScreen
import tecsup.santamaria.lavacampus.ui.estudiante.LoginScreen
import tecsup.santamaria.lavacampus.ui.estudiante.SplashScreen
import tecsup.santamaria.lavacampus.ui.theme.LavaTheme
import tecsup.santamaria.lavacampus.viewmodel.LavaViewModelFactory
import tecsup.santamaria.lavacampus.viewmodel.ServiciosViewModel
import tecsup.santamaria.lavacampus.viewmodel.SesionViewModel

@Composable
fun NavLava() {
    val app = LocalContext.current.applicationContext as LavaApp
    val nav = rememberNavController()

    // La sesión vive en toda la app: la comparten el Splash, el login y el tema
    val sesion: SesionViewModel = viewModel(
        factory = LavaViewModelFactory { SesionViewModel(app.repository, app.preferencias) }
    )
    val perfil by sesion.perfilActivo.collectAsState()
    val oscuro by sesion.modoOscuro.collectAsState()

    // El tema depende del perfil activo y del modo oscuro guardado en DataStore
    LavaTheme(rol = if (perfil == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE, oscuro = oscuro) {
        NavHost(navController = nav, startDestination = Rutas.SPLASH) {
            composable(Rutas.SPLASH) {
                SplashScreen(
                    perfilGuardado = perfil,
                    onComenzar = {
                        nav.navigate(Rutas.LOGIN) { popUpTo(Rutas.SPLASH) { inclusive = true } }
                    },
                    onSesionGuardada = { rol -> nav.irAPrincipal(rol, desde = Rutas.SPLASH) }
                )
            }
            composable(Rutas.LOGIN) {
                LoginScreen(vm = sesion, onIngresar = { rol -> nav.irAPrincipal(rol, desde = Rutas.LOGIN) })
            }
            // Pantallas principales: se reemplazan en el Paso 7 (Inicio) y el Paso 11 (Panel)
            composable(Rutas.INICIO) { Pendiente("Inicio del estudiante") }
            composable(Rutas.PANEL) { Pendiente("Panel de la lavandería") }
            composable(Rutas.SERVICIOS) {
                val vm: ServiciosViewModel = viewModel(factory = LavaViewModelFactory { ServiciosViewModel(app.repository) })
                ServiciosScreen(vm = vm, onEditar = { /* TODO (Paso 13): abrir FormularioServicio */ })
            }
            // TODO: agregar los destinos de cada paso (Rutas.kt ya los tiene)
        }
    }
}

// Inicio para el estudiante, Panel para el administrador
private fun NavHostController.irAPrincipal(rol: Rol, desde: String) {
    val destino = if (rol == Rol.ADMIN) Rutas.PANEL else Rutas.INICIO
    navigate(destino) { popUpTo(desde) { inclusive = true } }
}

@Composable
private fun Pendiente(titulo: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(titulo, style = MaterialTheme.typography.titleLarge)
    }
}

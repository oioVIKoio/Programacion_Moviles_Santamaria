package tecsup.santamaria.lavacampus.ui.navigation

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import tecsup.santamaria.lavacampus.LavaApp
import tecsup.santamaria.lavacampus.data.local.entity.Usuario
import tecsup.santamaria.lavacampus.model.Rol
import tecsup.santamaria.lavacampus.ui.administrador.AjustesScreen
import tecsup.santamaria.lavacampus.ui.administrador.BandejaScreen
import tecsup.santamaria.lavacampus.ui.administrador.ClientesScreen
import tecsup.santamaria.lavacampus.ui.administrador.FormularioServicioScreen
import tecsup.santamaria.lavacampus.ui.administrador.GestionPedidoScreen
import tecsup.santamaria.lavacampus.ui.administrador.PanelScreen
import tecsup.santamaria.lavacampus.ui.administrador.ServiciosScreen
import tecsup.santamaria.lavacampus.ui.estudiante.DetallePedidoScreen
import tecsup.santamaria.lavacampus.ui.estudiante.InicioScreen
import tecsup.santamaria.lavacampus.ui.estudiante.LoginScreen
import tecsup.santamaria.lavacampus.ui.estudiante.MisPedidosScreen
import tecsup.santamaria.lavacampus.ui.estudiante.NuevoPedidoScreen
import tecsup.santamaria.lavacampus.ui.estudiante.PerfilScreen
import tecsup.santamaria.lavacampus.ui.estudiante.SplashScreen
import tecsup.santamaria.lavacampus.ui.estudiante.TarifasScreen
import tecsup.santamaria.lavacampus.ui.theme.LavaTheme
import tecsup.santamaria.lavacampus.viewmodel.BandejaViewModel
import tecsup.santamaria.lavacampus.viewmodel.ClientesViewModel
import tecsup.santamaria.lavacampus.viewmodel.DetallePedidoViewModel
import tecsup.santamaria.lavacampus.viewmodel.GestionPedidoViewModel
import tecsup.santamaria.lavacampus.viewmodel.InicioViewModel
import tecsup.santamaria.lavacampus.viewmodel.LavaViewModelFactory
import tecsup.santamaria.lavacampus.viewmodel.MisPedidosViewModel
import tecsup.santamaria.lavacampus.viewmodel.NuevoPedidoViewModel
import tecsup.santamaria.lavacampus.viewmodel.PanelViewModel
import tecsup.santamaria.lavacampus.viewmodel.ServiciosViewModel
import tecsup.santamaria.lavacampus.viewmodel.SesionViewModel

@Composable
fun NavLava() {
    val app = LocalContext.current.applicationContext as LavaApp
    val nav = rememberNavController()

    // La sesión vive en toda la app: la comparten el Splash, el login, el tema, Perfil y Ajustes
    val sesion: SesionViewModel = viewModel(
        factory = LavaViewModelFactory { SesionViewModel(app.repository, app.preferencias) }
    )
    val perfil by sesion.perfilActivo.collectAsState()
    val oscuro by sesion.modoOscuro.collectAsState()
    val usuario by sesion.usuarioActual.collectAsState()

    // Cambiar de perfil o cerrar sesión: se limpia toda la pila
    val cambiarPerfil = { sesion.cambiarPerfil { rol -> nav.irAGrafo(rol) } }
    val cerrarSesion = {
        sesion.cerrarSesion {
            nav.navigate(Rutas.LOGIN) { popUpTo(nav.graph.id) { inclusive = true } }
        }
    }

    // Íconos de la barra de estado: claros en modo oscuro, oscuros en modo claro
    val vista = LocalView.current
    SideEffect {
        val ventana = (vista.context as Activity).window
        WindowCompat.getInsetsController(ventana, vista).isAppearanceLightStatusBars = !oscuro
    }

    // El tema depende del perfil activo y del modo oscuro guardado en DataStore
    LavaTheme(rol = if (perfil == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE, oscuro = oscuro) {
        NavHost(navController = nav, startDestination = Rutas.SPLASH) {
            composable(Rutas.SPLASH) {
                SplashScreen(
                    perfilGuardado = perfil,
                    onComenzar = {
                        nav.navigate(Rutas.LOGIN) { popUpTo(Rutas.SPLASH) { inclusive = true } }
                    },
                    onSesionGuardada = { rol -> nav.irAGrafo(rol) }
                )
            }
            composable(Rutas.LOGIN) {
                LoginScreen(vm = sesion, onIngresar = { rol -> nav.irAGrafo(rol) })
            }

            // Grafo del estudiante
            navigation(startDestination = Rutas.INICIO, route = Rutas.GRAFO_ESTUDIANTE) {
                composable(Rutas.INICIO) {
                    ConUsuario(usuario) { estudiante ->
                        val vm: InicioViewModel = viewModel(
                            key = "inicio_${estudiante.id}",
                            factory = LavaViewModelFactory { InicioViewModel(app.repository, estudiante) }
                        )
                        InicioScreen(
                            vm = vm,
                            barraInferior = { BarraEstudiante(nav, Rutas.INICIO) },
                            onNuevoPedido = { nav.navigate(Rutas.NUEVO_PEDIDO) },
                            onMisPedidos = { nav.irAPestana(Rutas.MIS_PEDIDOS) },
                            onTarifas = { nav.irAPestana(Rutas.TARIFAS) },
                            onPedido = { id -> nav.navigate(Rutas.detallePedido(id)) }
                        )
                    }
                }
                composable(Rutas.NUEVO_PEDIDO) {
                    ConUsuario(usuario) { estudiante ->
                        val vm: NuevoPedidoViewModel = viewModel(
                            factory = LavaViewModelFactory {
                                NuevoPedidoViewModel(app.repository, estudiante.id, app.preferencias.recibirPedidos)
                            }
                        )
                        NuevoPedidoScreen(
                            vm = vm,
                            onAtras = { nav.volver() },
                            // Al confirmar vuelve a Mis pedidos
                            onCreado = {
                                nav.navigate(Rutas.MIS_PEDIDOS) {
                                    popUpTo(Rutas.INICIO)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
                composable(
                    Rutas.DETALLE_PEDIDO,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { entrada ->
                    val id = entrada.arguments?.getInt("id") ?: 0
                    val vm: DetallePedidoViewModel = viewModel(
                        factory = LavaViewModelFactory { DetallePedidoViewModel(app.repository, id) }
                    )
                    DetallePedidoScreen(vm = vm, onAtras = { nav.volver() })
                }
                composable(Rutas.MIS_PEDIDOS) {
                    ConUsuario(usuario) { estudiante ->
                        MisPedidosScreen(
                            vm = misPedidos(app, estudiante),
                            barraInferior = { BarraEstudiante(nav, Rutas.MIS_PEDIDOS) },
                            onAtras = { nav.irAPestana(Rutas.INICIO) },
                            onNuevoPedido = { nav.navigate(Rutas.NUEVO_PEDIDO) },
                            onPedido = { id -> nav.navigate(Rutas.detallePedido(id)) }
                        )
                    }
                }
                composable(Rutas.TARIFAS) {
                    val vm: ServiciosViewModel = viewModel(factory = LavaViewModelFactory { ServiciosViewModel(app.repository) })
                    TarifasScreen(
                        vm = vm,
                        barraInferior = { BarraEstudiante(nav, Rutas.TARIFAS) },
                        onAtras = { nav.irAPestana(Rutas.INICIO) }
                    )
                }
                composable(Rutas.PERFIL) {
                    ConUsuario(usuario) { estudiante ->
                        PerfilScreen(
                            sesion = sesion,
                            pedidos = misPedidos(app, estudiante),
                            barraInferior = { BarraEstudiante(nav, Rutas.PERFIL) },
                            onAtras = { nav.irAPestana(Rutas.INICIO) },
                            onCambiarPerfil = cambiarPerfil,
                            onCerrarSesion = cerrarSesion
                        )
                    }
                }
            }

            // Grafo del administrador
            navigation(startDestination = Rutas.PANEL, route = Rutas.GRAFO_ADMIN) {
                composable(Rutas.PANEL) {
                    val vm: PanelViewModel = viewModel(factory = LavaViewModelFactory { PanelViewModel(app.repository) })
                    PanelScreen(
                        vm = vm,
                        barraInferior = { BarraAdmin(nav, Rutas.PANEL) },
                        onVerListos = { nav.irAPestana(Rutas.BANDEJA, inicio = Rutas.PANEL) }
                    )
                }
                composable(Rutas.BANDEJA) {
                    val vm: BandejaViewModel = viewModel(factory = LavaViewModelFactory { BandejaViewModel(app.repository) })
                    BandejaScreen(
                        vm = vm,
                        barraInferior = { BarraAdmin(nav, Rutas.BANDEJA) },
                        onAtras = { nav.irAPestana(Rutas.PANEL, inicio = Rutas.PANEL) },
                        onGestionar = { id -> nav.navigate(Rutas.gestionPedido(id)) }
                    )
                }
                composable(
                    Rutas.GESTION_PEDIDO,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { entrada ->
                    val id = entrada.arguments?.getInt("id") ?: 0
                    val vm: GestionPedidoViewModel = viewModel(
                        factory = LavaViewModelFactory { GestionPedidoViewModel(app.repository, id) }
                    )
                    GestionPedidoScreen(vm = vm, onAtras = { nav.volver() })
                }
                composable(Rutas.SERVICIOS) {
                    val vm: ServiciosViewModel = viewModel(factory = LavaViewModelFactory { ServiciosViewModel(app.repository) })
                    ServiciosScreen(
                        vm = vm,
                        onEditar = { id -> nav.navigate(Rutas.formularioServicio(id)) },
                        barraInferior = { BarraAdmin(nav, Rutas.SERVICIOS) },
                        onAtras = { nav.irAPestana(Rutas.PANEL, inicio = Rutas.PANEL) }
                    )
                }
                composable(
                    Rutas.FORMULARIO_SERVICIO,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { entrada ->
                    val id = entrada.arguments?.getInt("id") ?: 0
                    val vm: ServiciosViewModel = viewModel(factory = LavaViewModelFactory { ServiciosViewModel(app.repository) })
                    FormularioServicioScreen(vm = vm, id = id, onListo = { nav.volver() })
                }
                composable(Rutas.CLIENTES) {
                    val vm: ClientesViewModel = viewModel(factory = LavaViewModelFactory { ClientesViewModel(app.repository) })
                    ClientesScreen(
                        vm = vm,
                        barraInferior = { BarraAdmin(nav, Rutas.CLIENTES) },
                        onAtras = { nav.irAPestana(Rutas.PANEL, inicio = Rutas.PANEL) }
                    )
                }
                composable(Rutas.AJUSTES) {
                    AjustesScreen(
                        sesion = sesion,
                        barraInferior = { BarraAdmin(nav, Rutas.AJUSTES) },
                        onAtras = { nav.irAPestana(Rutas.PANEL, inicio = Rutas.PANEL) },
                        onCambiarPerfil = cambiarPerfil,
                        onCerrarSesion = cerrarSesion
                    )
                }
            }
        }
    }
}

// Mis pedidos y Perfil comparten el ViewModel de los pedidos del estudiante
@Composable
private fun misPedidos(app: LavaApp, estudiante: Usuario): MisPedidosViewModel = viewModel(
    key = "mis_pedidos_${estudiante.id}",
    factory = LavaViewModelFactory { MisPedidosViewModel(app.repository, estudiante.id) }
)

// Las pantallas del estudiante esperan a que la sesión tenga su usuario
@Composable
private fun ConUsuario(usuario: Usuario?, contenido: @Composable (Usuario) -> Unit) {
    if (usuario == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    } else {
        contenido(usuario)
    }
}

// Grafo del perfil: Inicio para el estudiante, Panel para el administrador. Limpia la pila
private fun NavHostController.irAGrafo(rol: Rol) {
    val grafo = if (rol == Rol.ADMIN) Rutas.GRAFO_ADMIN else Rutas.GRAFO_ESTUDIANTE
    navigate(grafo) { popUpTo(graph.id) { inclusive = true } }
}

// Atrás seguro: con dos toques rápidos no deja la pila vacía (pantalla en blanco)
private fun NavHostController.volver() {
    if (previousBackStackEntry != null) popBackStack()
}

// Cambia de pestaña sin apilar pantallas (inicio: Inicio del estudiante o Panel del admin)
private fun NavHostController.irAPestana(ruta: String, inicio: String = Rutas.INICIO) {
    navigate(ruta) {
        popUpTo(inicio) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

private class Pestana(val ruta: String, val texto: String, val icono: ImageVector, val iconoActivo: ImageVector = icono)

private val pestanasEstudiante = listOf(
    Pestana(Rutas.INICIO, "Inicio", Icons.Outlined.Home, Icons.Filled.Home),
    Pestana(Rutas.MIS_PEDIDOS, "Pedidos", Icons.Outlined.Menu),
    Pestana(Rutas.TARIFAS, "Tarifas", Icons.Outlined.Sell),
    Pestana(Rutas.PERFIL, "Perfil", Icons.Outlined.Person)
)

private val pestanasAdmin = listOf(
    Pestana(Rutas.PANEL, "Panel", Icons.Outlined.BarChart, Icons.Filled.BarChart),
    Pestana(Rutas.BANDEJA, "Pedidos", Icons.Outlined.Menu),
    Pestana(Rutas.SERVICIOS, "Servicios", Icons.Outlined.Sell),
    Pestana(Rutas.CLIENTES, "Clientes", Icons.Outlined.Person),
    Pestana(Rutas.AJUSTES, "Ajustes", Icons.Outlined.Settings)
)

// NavigationBar del estudiante: Inicio, Pedidos, Tarifas, Perfil
@Composable
private fun BarraEstudiante(nav: NavHostController, actual: String) =
    BarraNavegacion(nav, actual, pestanasEstudiante, Rutas.INICIO)

// NavigationBar del administrador: Panel, Pedidos, Servicios, Clientes, Ajustes
@Composable
private fun BarraAdmin(nav: NavHostController, actual: String) =
    BarraNavegacion(nav, actual, pestanasAdmin, Rutas.PANEL)

@Composable
private fun BarraNavegacion(nav: NavHostController, actual: String, pestanas: List<Pestana>, inicio: String) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        pestanas.forEach { p ->
            val activa = p.ruta == actual
            NavigationBarItem(
                selected = activa,
                onClick = { if (!activa) nav.irAPestana(p.ruta, inicio) },
                icon = { Icon(if (activa) p.iconoActivo else p.icono, contentDescription = p.texto) },
                label = { Text(p.texto) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.surface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

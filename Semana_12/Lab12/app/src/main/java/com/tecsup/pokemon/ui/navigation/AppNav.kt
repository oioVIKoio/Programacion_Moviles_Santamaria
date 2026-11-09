package com.tecsup.pokemon.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.pokemon.AppContainer
import com.tecsup.pokemon.ui.AppViewModelFactory
import com.tecsup.pokemon.ui.detalle.DetalleScreen
import com.tecsup.pokemon.ui.detalle.DetalleViewModel
import com.tecsup.pokemon.ui.favoritos.FavoritosScreen
import com.tecsup.pokemon.ui.favoritos.FavoritosViewModel
import com.tecsup.pokemon.ui.lista.ListaScreen
import com.tecsup.pokemon.ui.lista.ListaViewModel

object Rutas {
    const val LISTA = "lista"
    const val FAVORITOS = "favoritos"
    const val DETALLE = "detalle/{id}"
    fun detalle(id: String) = "detalle/$id"
}

@Composable
fun AppNav(container: AppContainer) {
    val nav = rememberNavController()
    val entrada by nav.currentBackStackEntryAsState()
    val rutaActual = entrada?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                PestanaInferior(nav, rutaActual, Rutas.LISTA, "🔥", "Pokémon")
                PestanaInferior(nav, rutaActual, Rutas.FAVORITOS, "★", "Favoritos")
            }
        }
    ) { padding ->
        NavHost(nav, startDestination = Rutas.LISTA, modifier = Modifier.padding(padding)) {

            composable(Rutas.LISTA) {
                val vm: ListaViewModel = viewModel(factory = AppViewModelFactory(container))
                val estado by vm.estado.collectAsStateWithLifecycle()
                ListaScreen(
                    estado = estado,
                    onItemClick = { nav.navigate(Rutas.detalle(it.id)) },
                    onFavorito = { vm.alternarFavorito(it.id) },
                    onReintentar = vm::refrescar
                )
            }

            composable(
                Rutas.DETALLE,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { e ->
                val id = e.arguments?.getString("id").orEmpty()
                val vm: DetalleViewModel = viewModel(factory = AppViewModelFactory(container, id))
                val estado by vm.estado.collectAsStateWithLifecycle()
                DetalleScreen(estado = estado, onReintentar = vm::cargar, onFavorito = vm::alternarFavorito)
            }

            composable(Rutas.FAVORITOS) {
                val vm: FavoritosViewModel = viewModel(factory = AppViewModelFactory(container))
                val estado by vm.estado.collectAsStateWithLifecycle()
                FavoritosScreen(
                    estado = estado,
                    onItemClick = { nav.navigate(Rutas.detalle(it.id)) },
                    onFavorito = { vm.alternarFavorito(it.id) }
                )
            }
        }
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.PestanaInferior(
    nav: NavHostController,
    rutaActual: String?,
    ruta: String,
    icono: String,
    etiqueta: String
) {
    NavigationBarItem(
        selected = rutaActual == ruta,
        onClick = {
            nav.navigate(ruta) {
                popUpTo(Rutas.LISTA) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        },
        icon = { Text(icono) },
        label = { Text(etiqueta) }
    )
}

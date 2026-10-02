package com.santamaria.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    var destinoActual by remember {
        mutableStateOf("Inicio")
    }

    val favoritos = remember {
        mutableStateListOf<String>()
    }

    val morado = Color(0xFF6A1B9A)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            AppDrawer(
                destinoActual = destinoActual,
                onDestinoSeleccionado = { destino ->

                    destinoActual = destino

                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {

        Scaffold(
            topBar = {

                TopAppBar(
                    title = {

                        Column {

                            Text(
                                text = if (destinoActual == "Inicio") {
                                    "TECSUP Store"
                                } else {
                                    destinoActual
                                },
                                fontWeight = FontWeight.Bold
                            )

                            if (destinoActual == "Inicio") {

                                Text(
                                    text = "Más vendidos",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    },
                    navigationIcon = {

                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = morado,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                when (destinoActual) {

                    "Inicio" -> {

                        PantallaTienda(
                            favoritos = favoritos.toSet(),
                            onFavoritoClick = { producto ->

                                if (producto.nombre in favoritos) {
                                    favoritos.remove(producto.nombre)
                                } else {
                                    favoritos.add(producto.nombre)
                                }
                            }
                        )
                    }

                    "Mis pedidos" -> {

                        PantallaSeccion(
                            titulo = "Mis pedidos",
                            mensaje = "Aquí podrás consultar tus pedidos."
                        )
                    }

                    "Favoritos" -> {

                        PantallaSeccion(
                            titulo = "Favoritos",
                            mensaje = "Aquí aparecerán tus productos favoritos."
                        )
                    }

                    "Perfil" -> {

                        PantallaSeccion(
                            titulo = "Perfil",
                            mensaje = "Información del perfil del usuario."
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaSeccion(
    titulo: String,
    mensaje: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = mensaje,
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
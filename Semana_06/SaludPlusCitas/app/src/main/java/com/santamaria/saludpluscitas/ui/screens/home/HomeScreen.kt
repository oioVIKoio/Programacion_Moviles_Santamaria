package com.santamaria.saludpluscitas.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.santamaria.saludpluscitas.ui.components.AvatarPaciente
import com.santamaria.saludpluscitas.ui.theme.MoradoMedio
import com.santamaria.saludpluscitas.ui.theme.MoradoOscuro
import com.santamaria.saludpluscitas.ui.theme.RojoTexto
import com.santamaria.saludpluscitas.ui.theme.SuperficieBlanca
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.data.repository.Repositorio
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.BarraNavegacion
import com.santamaria.saludpluscitas.ui.components.TarjetaAccion
import com.santamaria.saludpluscitas.ui.components.TarjetaEspecialidadDestacada
import com.santamaria.saludpluscitas.ui.theme.MoradoPastel
import com.santamaria.saludpluscitas.ui.theme.MoradoPrimario
import com.santamaria.saludpluscitas.ui.theme.RosaPastel
import com.santamaria.saludpluscitas.ui.theme.RosaTexto
import com.santamaria.saludpluscitas.ui.theme.NaranjaPastel
import com.santamaria.saludpluscitas.ui.theme.NaranjaTexto
import com.santamaria.saludpluscitas.ui.theme.TextoSecundario
import com.santamaria.saludpluscitas.ui.theme.VerdePastel
import com.santamaria.saludpluscitas.ui.theme.VerdeTexto

@Composable
fun HomeScreen(
    navController: NavController
) {
    // Solo el primer nombre para el saludo ("¡Hola, Victor!").
    val nombre = Repositorio.usuarioActual?.nombre?.substringBefore(" ") ?: ""
    val destacadas = Repositorio.especialidadesDestacadas()

    val estadoDrawer = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var confirmarSalida by remember { mutableStateOf(false) }

    // Cierra el menú y luego navega.
    fun irA(ruta: String) {
        scope.launch { estadoDrawer.close() }
        navController.navigate(ruta)
    }

    if (confirmarSalida) {
        AlertDialog(
            onDismissRequest = { confirmarSalida = false },
            title = { Text("Cerrar sesión") },
            text = { Text("¿Seguro que quieres salir de tu cuenta?") },
            confirmButton = {
                TextButton(onClick = {
                    confirmarSalida = false
                    Repositorio.cerrarSesion()
                    // Se limpia todo el historial: Atrás no puede volver a la sesión.
                    navController.navigate(Rutas.Splash.ruta) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }) {
                    Text("Salir", color = RojoTexto)
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmarSalida = false }) {
                    Text("Cancelar", color = MoradoPrimario)
                }
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = estadoDrawer,
        drawerContent = {
            MenuLateral(
                onSedes = { irA(Rutas.Sedes.ruta) },
                onDoctores = { irA(Rutas.Doctores.ruta) },
                onAgenda = { irA(Rutas.MisCitas.ruta) },
                onCerrarSesion = {
                    scope.launch { estadoDrawer.close() }
                    confirmarSalida = true
                }
            )
        }
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            bottomBar = {
                BarraNavegacion(
                    navController = navController,
                    rutaActual = Rutas.Home.ruta
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    IconButton(onClick = { scope.launch { estadoDrawer.open() } }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Abrir menú",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "¡Hola, $nombre!",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "¿Qué deseas hacer hoy?",
                            fontSize = 15.sp,
                            color = TextoSecundario
                        )
                    }
                    IconButton(onClick = { navController.navigate(Rutas.Notificaciones.ruta) }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notificaciones",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Grid 2x2 de accesos rápidos.
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    TarjetaAccion(
                        titulo = "Agendar cita",
                        icono = Icons.Default.CalendarMonth,
                        colorFondo = MoradoPastel,
                        colorContenido = MoradoPrimario,
                        onClick = { navController.navigate(Rutas.Sedes.ruta) },
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaAccion(
                        titulo = "Mis citas",
                        icono = Icons.Default.EventAvailable,
                        colorFondo = VerdePastel,
                        colorContenido = VerdeTexto,
                        onClick = { navController.navigate(Rutas.MisCitas.ruta) },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    TarjetaAccion(
                        titulo = "Mis datos",
                        icono = Icons.Default.Person,
                        colorFondo = RosaPastel,
                        colorContenido = RosaTexto,
                        onClick = { navController.navigate(Rutas.Perfil.ruta) },
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaAccion(
                        titulo = "Resultados",
                        icono = Icons.Default.Description,
                        colorFondo = NaranjaPastel,
                        colorContenido = NaranjaTexto,
                        onClick = { navController.navigate(Rutas.Resultados.ruta) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Especialidades destacadas",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { navController.navigate(Rutas.Especialidades.ruta) }) {
                        Text(text = "Ver todas", color = MoradoPrimario)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(destacadas, key = { it.id }) { especialidad ->
                        TarjetaEspecialidadDestacada(
                            especialidad = especialidad,
                            onClick = {
                                navController.navigate(Rutas.Medicos.crearRuta(especialidad.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

// Menú lateral: encabezado con el paciente y las opciones Sede, Doctores,
// Agenda y Cerrar sesión.
@Composable
private fun MenuLateral(
    onSedes: () -> Unit,
    onDoctores: () -> Unit,
    onAgenda: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val usuario = Repositorio.usuarioActual
    val colores = NavigationDrawerItemDefaults.colors(
        unselectedIconColor = MoradoPrimario,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface
    )

    ModalDrawerSheet(drawerContainerColor = SuperficieBlanca) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(listOf(MoradoMedio, MoradoPrimario, MoradoOscuro)))
                .padding(horizontal = 20.dp, vertical = 28.dp)
        ) {
            AvatarPaciente(nombre = usuario?.nombre ?: "", tamano = 64)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = usuario?.nombre ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = usuario?.telefono ?: "",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        NavigationDrawerItem(
            label = { Text("Sede") },
            icon = { Icon(Icons.Default.LocalHospital, contentDescription = null) },
            selected = false,
            onClick = onSedes,
            colors = colores,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        NavigationDrawerItem(
            label = { Text("Doctores") },
            icon = { Icon(Icons.Default.MedicalServices, contentDescription = null) },
            selected = false,
            onClick = onDoctores,
            colors = colores,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        NavigationDrawerItem(
            label = { Text("Agenda") },
            icon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
            selected = false,
            onClick = onAgenda,
            colors = colores,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))

        NavigationDrawerItem(
            label = { Text("Cerrar sesión", color = RojoTexto) },
            icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = RojoTexto) },
            selected = false,
            onClick = onCerrarSesion,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

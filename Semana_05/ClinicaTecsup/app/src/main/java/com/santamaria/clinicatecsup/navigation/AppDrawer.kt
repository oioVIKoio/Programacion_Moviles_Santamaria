package com.santamaria.clinicatecsup.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.clinicatecsup.ui.theme.PurpleContainer
import com.santamaria.clinicatecsup.ui.theme.PurplePrimary
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    navController: NavController,
    currentRoute: String? = null,
    content: @Composable (openDrawer: () -> Unit) -> Unit
) {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Header VP Victor Paciente
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PurplePrimary)
                        .padding(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "VS",
                                color = PurplePrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Victor Santamaria",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "Paciente",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                NavigationDrawerItem(
                    label = { Text("Inicio", fontWeight = FontWeight.Medium) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    selected = currentRoute == Screen.Home.route,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = PurpleContainer,
                        selectedIconColor = PurplePrimary,
                        selectedTextColor = PurplePrimary
                    ),
                    onClick = {
                        scope.launch { drawerState.close() }
                        if (currentRoute != Screen.Home.route) {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Mis citas", fontWeight = FontWeight.Medium) },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Mis citas") },
                    selected = currentRoute == Screen.Appointments.route,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = PurpleContainer,
                        selectedIconColor = PurplePrimary,
                        selectedTextColor = PurplePrimary
                    ),
                    onClick = {
                        scope.launch { drawerState.close() }
                        if (currentRoute != Screen.Appointments.route) {
                            navController.navigate(Screen.Appointments.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Historial médico", fontWeight = FontWeight.Medium) },
                    icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "Historial médico") },
                    selected = currentRoute == Screen.MedicalHistory.route,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = PurpleContainer,
                        selectedIconColor = PurplePrimary,
                        selectedTextColor = PurplePrimary
                    ),
                    onClick = {
                        scope.launch { drawerState.close() }
                        if (currentRoute != Screen.MedicalHistory.route) {
                            navController.navigate(Screen.MedicalHistory.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Perfil", fontWeight = FontWeight.Medium) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    selected = currentRoute == Screen.Profile.route,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = PurpleContainer,
                        selectedIconColor = PurplePrimary,
                        selectedTextColor = PurplePrimary
                    ),
                    onClick = {
                        scope.launch { drawerState.close() }
                        if (currentRoute != Screen.Profile.route) {
                            navController.navigate(Screen.Profile.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    ) {
        content {
            scope.launch {
                drawerState.open()
            }
        }
    }
}
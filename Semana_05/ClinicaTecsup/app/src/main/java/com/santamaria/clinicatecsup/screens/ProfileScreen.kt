package com.santamaria.clinicatecsup.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.clinicatecsup.navigation.AppDrawer
import com.santamaria.clinicatecsup.navigation.Screen
import com.santamaria.clinicatecsup.ui.theme.BackgroundLight
import com.santamaria.clinicatecsup.ui.theme.PurpleContainer
import com.santamaria.clinicatecsup.ui.theme.PurplePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
    var correo by remember { mutableStateOf("victor.santamaria@tecsup.edu.pe") }
    var telefono by remember { mutableStateOf("+51 987 654 321") }
    var fechaNacimiento by remember { mutableStateOf("15 de marzo de 2006") }
    var direccion by remember { mutableStateOf("Lima, Perú") }

    var isEditing by remember { mutableStateOf(false) }

    // Variables temporales para el modo edición
    var editCorreo by remember { mutableStateOf(correo) }
    var editTelefono by remember { mutableStateOf(telefono) }
    var editDireccion by remember { mutableStateOf(direccion) }

    AppDrawer(
        navController = navController,
        currentRoute = Screen.Profile.route
    ) { openDrawer ->
        Scaffold(
            containerColor = BackgroundLight,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Perfil",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = openDrawer) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BackgroundLight
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Avatar circular con iniciales VS
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .background(PurpleContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "VS",
                        color = PurplePrimary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Victor Santamaria
                Text(
                    text = "Victor Santamaria",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Paciente
                Text(
                    text = "Paciente",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Sección INFORMACIÓN PERSONAL
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "INFORMACIÓN PERSONAL",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurplePrimary,
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            if (!isEditing) {
                                // MODO LECTURA
                                InfoRow(
                                    icon = Icons.Default.Email,
                                    label = "Correo",
                                    value = correo
                                )
                                InfoRow(
                                    icon = Icons.Default.Phone,
                                    label = "Teléfono",
                                    value = telefono
                                )
                                InfoRow(
                                    icon = Icons.Default.Cake,
                                    label = "Fecha de nacimiento",
                                    value = fechaNacimiento
                                )
                                InfoRow(
                                    icon = Icons.Default.LocationOn,
                                    label = "Dirección",
                                    value = direccion
                                )
                            } else {
                                // MODO EDICIÓN
                                OutlinedTextField(
                                    value = editCorreo,
                                    onValueChange = { editCorreo = it },
                                    label = { Text("Correo") },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Email,
                                            contentDescription = null,
                                            tint = PurplePrimary
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = PurplePrimary,
                                        focusedLabelColor = PurplePrimary
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                    value = editTelefono,
                                    onValueChange = { editTelefono = it },
                                    label = { Text("Teléfono") },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Phone,
                                            contentDescription = null,
                                            tint = PurplePrimary
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = PurplePrimary,
                                        focusedLabelColor = PurplePrimary
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                InfoRow(
                                    icon = Icons.Default.Cake,
                                    label = "Fecha de nacimiento",
                                    value = fechaNacimiento
                                )

                                OutlinedTextField(
                                    value = editDireccion,
                                    onValueChange = { editDireccion = it },
                                    label = { Text("Dirección") },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.LocationOn,
                                            contentDescription = null,
                                            tint = PurplePrimary
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = PurplePrimary,
                                        focusedLabelColor = PurplePrimary
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botón EDITAR PERFIL / GUARDAR CAMBIOS
                if (!isEditing) {
                    Button(
                        onClick = {
                            editCorreo = correo
                            editTelefono = telefono
                            editDireccion = direccion
                            isEditing = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PurplePrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "EDITAR PERFIL",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            correo = editCorreo
                            telefono = editTelefono
                            direccion = editDireccion
                            isEditing = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PurplePrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "GUARDAR CAMBIOS",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(PurpleContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PurplePrimary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
package com.santamaria.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    destinoActual: String,
    cantidadFavoritos: Int,
    onDestinoSeleccionado: (String) -> Unit
) {

    val morado = Color(0xFF6A1B9A)
    val lavanda = Color(0xFFF1E4F7)

    ModalDrawerSheet(
        modifier = Modifier
            .width(320.dp)
            .fillMaxHeight()
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = lavanda,
                            shape = CircleShape
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "VS",
                        color = morado,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Column {

                    Text(
                        text = "Victor Santamaria",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = "victor.santamaria.f@tecsup.edu.pe",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            HorizontalDivider()

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Inicio",
                        fontWeight = if (destinoActual == "Inicio") {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )
                },
                selected = destinoActual == "Inicio",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio"
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = lavanda,
                    selectedIconColor = morado,
                    selectedTextColor = morado
                ),
                onClick = {
                    onDestinoSeleccionado("Inicio")
                }
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Mis pedidos",
                        fontWeight = if (destinoActual == "Mis pedidos") {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )
                },
                selected = destinoActual == "Mis pedidos",
                icon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Mis pedidos"
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = lavanda,
                    selectedIconColor = morado,
                    selectedTextColor = morado
                ),
                onClick = {
                    onDestinoSeleccionado("Mis pedidos")
                }
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Favoritos",
                        fontWeight = if (destinoActual == "Favoritos") {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )
                },
                selected = destinoActual == "Favoritos",
                icon = {

                    BadgedBox(
                        badge = {

                            if (cantidadFavoritos > 0) {
                                Badge {
                                    Text(
                                        text = cantidadFavoritos.toString()
                                    )
                                }
                            }
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoritos"
                        )
                    }
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = lavanda,
                    selectedIconColor = morado,
                    selectedTextColor = morado
                ),
                onClick = {
                    onDestinoSeleccionado("Favoritos")
                }
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Perfil",
                        fontWeight = if (destinoActual == "Perfil") {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )
                },
                selected = destinoActual == "Perfil",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil"
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = lavanda,
                    selectedIconColor = morado,
                    selectedTextColor = morado
                ),
                onClick = {
                    onDestinoSeleccionado("Perfil")
                }
            )

            NavigationDrawerItem(
                label = {
                    Text("Cerrar sesión")
                },
                selected = false,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Cerrar sesión"
                    )
                },
                onClick = {
                    // Funcionalidad opcional no implementada
                }
            )
        }
    }
}
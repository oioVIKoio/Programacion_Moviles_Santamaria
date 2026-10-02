package com.santamaria.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer() {

    ModalDrawerSheet(
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight()
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
        ) {

            Text(
                text = "TECSUP Store"
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            HorizontalDivider()

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            NavigationDrawerItem(
                label = {
                    Text("Inicio")
                },
                selected = false,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio"
                    )
                },
                onClick = {
                    // Navegación en el siguiente commit
                }
            )

            NavigationDrawerItem(
                label = {
                    Text("Mis pedidos")
                },
                selected = false,
                icon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Mis pedidos"
                    )
                },
                onClick = {
                    // Navegación en el siguiente commit
                }
            )

            NavigationDrawerItem(
                label = {
                    Text("Favoritos")
                },
                selected = false,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favoritos"
                    )
                },
                onClick = {
                    // Navegación en el siguiente commit
                }
            )

            NavigationDrawerItem(
                label = {
                    Text("Perfil")
                },
                selected = false,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil"
                    )
                },
                onClick = {
                    // Navegación en el siguiente commit
                }
            )
        }
    }
}
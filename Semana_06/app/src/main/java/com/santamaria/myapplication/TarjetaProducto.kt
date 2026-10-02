package com.santamaria.myapplication
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

@Composable
fun TarjetaProducto(
    producto: Producto
) {

    // Estado que posteriormente controlará el DropdownMenu
    var expanded by remember {
        mutableStateOf(false)
    }

    val morado = Color(0xFF6A1B9A)
    val lavanda = Color(0xFFF7F1FA)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = lavanda
        ),
        border = BorderStroke(
            width = 1.dp,
            color = morado
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Representación simple del producto
            Icon(
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = "Producto",
                tint = morado,
                modifier = Modifier.size(36.dp)
            )

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            // Información del producto
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "S/ %.2f".format(producto.precio),
                    style = MaterialTheme.typography.bodyMedium,
                    color = morado
                )
            }

            // Primer requisito del Laboratorio 06:
            Box {

                IconButton(
                    onClick = {
                        expanded = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Opciones del producto"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    DropdownMenuItem(
                        text = {
                            Text("Favoritos")
                        },
                        onClick = {
                            expanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Compartir")
                        },
                        onClick = {
                            expanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Reportar")
                        },
                        onClick = {
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
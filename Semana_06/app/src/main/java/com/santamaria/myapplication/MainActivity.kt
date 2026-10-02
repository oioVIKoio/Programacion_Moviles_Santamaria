package com.santamaria.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PantallaTienda()
            }
        }
    }
}

@Composable
fun PantallaTienda() {

    val productos = remember {
        listOf(
            Producto(
                nombre = "Audífonos",
                precio = 89.00
            ),
            Producto(
                nombre = "Smartwatch",
                precio = 199.00
            ),
            Producto(
                nombre = "Funda celular",
                precio = 25.00
            )
        )
    }

    val morado = Color(0xFF6A1B9A)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // CABECERA DE TECSUP STORE
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = morado
        ) {

            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
            ) {

                Text(
                    text = "TECSUP Store",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Más vendidos",
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // LISTA DE PRODUCTOS
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(productos) { producto ->

                TarjetaProducto(
                    producto = producto
                )
            }
        }
    }
}
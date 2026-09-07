package com.santamaria.lab03registroproducto

import android.os.Bundle

import androidx.compose.ui.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santamaria.lab03registroproducto.ui.theme.Lab03RegistroProductoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Lab03RegistroProductoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    RegistroProductoScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RegistroProductoScreen(modifier: Modifier = Modifier) {

    var nombreProducto by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var mostrarResumen by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Registro de Producto",
            fontSize = 28.sp
        )

        Text(
            text = "Ingrese los datos del producto",
            fontSize = 16.sp
        )

        OutlinedTextField(
            value = nombreProducto,
            onValueChange = { nombreProducto = it },
            label = {
                Text("Nombre del producto")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = {
                    Text("Precio")
                },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = {
                    Text("Cantidad")
                },
                modifier = Modifier.weight(1f)
            )
        }

        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (nombreProducto.isBlank() || precio.isBlank() || cantidad.isBlank()) {
                        mensajeError = "Error: Todos los campos son obligatorios"
                        mostrarResumen = false
                    } else {
                        mensajeError = ""
                        mostrarResumen = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("AGREGAR PRODUCTO")
            }

            Button(
                onClick = {
                    nombreProducto = ""
                    precio = ""
                    cantidad = ""
                    mensajeError = ""
                    mostrarResumen = false
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("LIMPIAR")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mostrarResumen) {
            val precioNum = precio.toDoubleOrNull() ?: 0.0
            val cantidadNum = cantidad.toIntOrNull() ?: 0
            val importe = precioNum * cantidadNum

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = nombreProducto,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Precio: S/ " + String.format("%.2f", precioNum)
                    )

                    Text(
                        text = "Cantidad: $cantidadNum"
                    )

                    Text(
                        text = "Importe: S/ " + String.format("%.2f", importe),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Text(
                text = "✓ Producto registrado correctamente",
                color = Color(0xFF2E7D32)
            )
        }
    }
}
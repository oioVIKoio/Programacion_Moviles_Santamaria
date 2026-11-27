package com.santamaria.avisoscampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.santamaria.avisoscampus.notificacion.Notificador
import com.santamaria.avisoscampus.viewmodel.AvisosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current

    LaunchedEffect(Unit) {
        vm.eventos.collect { aviso ->
            val mostrado = Notificador.mostrar(contexto, aviso)
            // TODO Paso 8: si no se mostró, avisar al ViewModel
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("AvisosCampus", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { relleno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // TODO Paso 2: tarjeta Permiso de notificaciones
            TarjetaPermiso()
            // TODO Paso 3: tarjeta Avisos
            TarjetaAvisos()
            // TODO Paso 5: tarjeta Recordatorio
            TarjetaRecordatorio()
            // TODO Paso 6: tarjeta Receptor de anuncios
            TarjetaReceptor()
            // TODO Paso 7: tarjeta Historial (HistorialAvisos)
        }
    }
}

@Composable
private fun Tarjeta(titulo: String, contenido: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                titulo,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            contenido()
        }
    }
}

@Composable
private fun TarjetaPermiso() = Tarjeta("Permiso de notificaciones") {
    Button(onClick = { /* TODO Paso 2 */ }, modifier = Modifier.fillMaxWidth()) {
        Text("Permitir notificaciones")
    }
}

@Composable
private fun TarjetaAvisos() = Tarjeta("Avisos") {
    Button(onClick = { /* TODO Paso 3 */ }, modifier = Modifier.fillMaxWidth()) {
        Text("Mostrar aviso")
    }
}

@Composable
private fun TarjetaRecordatorio() = Tarjeta("Recordatorio") {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { /* TODO Paso 5 */ }) { Text("Recordar en 10 s") }
        OutlinedButton(onClick = { /* TODO Paso 5 */ }) { Text("Cancelar") }
    }
}

@Composable
private fun TarjetaReceptor() = Tarjeta("Receptor de anuncios") {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Escuchar el modo avión", modifier = Modifier.weight(1f))
        Switch(checked = false, onCheckedChange = { /* TODO Paso 6 */ })
    }
}

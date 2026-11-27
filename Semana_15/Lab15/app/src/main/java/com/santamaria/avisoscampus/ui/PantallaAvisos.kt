package com.santamaria.avisoscampus.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import com.santamaria.avisoscampus.model.PermisoUi
import com.santamaria.avisoscampus.ui.theme.Gris
import com.santamaria.avisoscampus.ui.theme.GrisSuave
import com.santamaria.avisoscampus.ui.theme.ErrorRojo
import com.santamaria.avisoscampus.ui.theme.ErrorSuave
import com.santamaria.avisoscampus.ui.theme.Verde
import com.santamaria.avisoscampus.ui.theme.VerdeSuave
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.santamaria.avisoscampus.notificacion.Notificador
import com.santamaria.avisoscampus.viewmodel.AvisosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current
    val permiso by vm.permiso.collectAsState()
    val faltan by vm.faltan.collectAsState()

    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        vm.permisoRespondido(concedido)
    }

    LaunchedEffect(Unit) {
        if (Notificador.tienePermiso(contexto)) vm.permisoRespondido(true)
    }

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
            TarjetaPermiso(permiso) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    vm.permisoRespondido(true)
                }
            }
            TarjetaAvisos { vm.avisarAhora() }
            TarjetaRecordatorio(faltan, { vm.recordarEn() }, { vm.cancelarRecordatorio() })
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
private fun TarjetaPermiso(permiso: PermisoUi, alPedir: () -> Unit) = Tarjeta("Permiso de notificaciones") {
    val (texto, fondo, color) = when (permiso) {
        PermisoUi.SinPedir -> Triple("Sin pedir", GrisSuave, Gris)
        PermisoUi.Concedido -> Triple("Permitido", VerdeSuave, Verde)
        PermisoUi.Denegado -> Triple("Denegado", ErrorSuave, ErrorRojo)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Estado:  ")
        Text(
            texto,
            color = color,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(fondo)
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
    Button(onClick = alPedir, modifier = Modifier.fillMaxWidth()) {
        Text("Permitir notificaciones")
    }
}

@Composable
private fun TarjetaAvisos(alMostrar: () -> Unit) = Tarjeta("Avisos") {
    Button(onClick = alMostrar, modifier = Modifier.fillMaxWidth()) {
        Text("Mostrar aviso")
    }
}

@Composable
private fun TarjetaRecordatorio(faltan: Int?, alRecordar: () -> Unit, alCancelar: () -> Unit) =
    Tarjeta("Recordatorio") {
        val corriendo = faltan != null
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            Button(onClick = alRecordar, enabled = !corriendo) { Text("Recordar en 10 s") }
            OutlinedButton(onClick = alCancelar, enabled = corriendo) { Text("Cancelar") }
        }
        if (faltan != null) {
            Text(
                "Faltan $faltan s",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }

@Composable
private fun TarjetaReceptor() = Tarjeta("Receptor de anuncios") {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Escuchar el modo avión", modifier = Modifier.weight(1f))
        Switch(checked = false, onCheckedChange = { /* TODO Paso 6 */ })
    }
}

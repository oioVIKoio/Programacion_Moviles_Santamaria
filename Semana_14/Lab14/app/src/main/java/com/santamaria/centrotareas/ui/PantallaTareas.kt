package com.santamaria.centrotareas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santamaria.centrotareas.ui.components.BotonPrincipal
import com.santamaria.centrotareas.ui.components.FranjaVida
import com.santamaria.centrotareas.ui.components.TarjetaPaso
import com.santamaria.centrotareas.ui.theme.FormaBoton
import com.santamaria.centrotareas.ui.theme.NumeroChico
import com.santamaria.centrotareas.ui.theme.NumeroGrande
import com.santamaria.centrotareas.viewmodel.TareasViewModel

@Composable
fun PantallaTareas(vm: TareasViewModel) {
    // Contador de la prueba de vida: sobrevive a rotar la pantalla
    var toques by rememberSaveable { mutableIntStateOf(0) }
    // Acordeón: número de la tarjeta abierta (0 = todas plegadas)
    var abierta by rememberSaveable { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 14.dp)
        ) {
            Text(
                "LABORATORIO DE CONCURRENCIA",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text("CentroTareas", style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp))
        }
        FranjaVida(toques = toques, onToque = { toques++ })
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaPaso(1, "Tarea con progreso", Icons.Outlined.Bolt, abierta == 1, { abierta = if (abierta == 1) 0 else 1 }) {
                val mensaje by vm.mensaje.collectAsState()
                val progreso by vm.progreso.collectAsState()
                val hilo by vm.hilo.collectAsState()
                // Paso 3: mensaje y porcentaje arriba, barra, hilo actual y el botón Iniciar
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        mensaje,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Text("${(progreso * 100).toInt()} %", style = NumeroGrande.copy(fontSize = 24.sp))
                }
                LinearProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier.fillMaxWidth().height(10.dp).clip(FormaBoton),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.outline,
                    drawStopIndicator = {}
                )
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Hilo actual",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Text(hilo, style = NumeroChico.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                BotonPrincipal("Iniciar", onClick = vm::tareaLarga, icono = Icons.Outlined.PlayArrow)
                // TODO Paso 4: Cancelar y bloquear el doble inicio
            }
            TarjetaPaso(2, "Contar números primos", Icons.Outlined.Memory, abierta == 2, { abierta = if (abierta == 2) 0 else 2 }) {
                // TODO Paso 5
                Text("Se construye en el paso 5.", style = MaterialTheme.typography.bodyMedium)
            }
            TarjetaPaso(3, "Dos cargas de red", Icons.Outlined.Sync, abierta == 3, { abierta = if (abierta == 3) 0 else 3 }) {
                // TODO Pasos 6 y 7
                Text("Se construye en los pasos 6 y 7.", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

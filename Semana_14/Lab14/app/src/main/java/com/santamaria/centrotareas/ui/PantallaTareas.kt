package com.santamaria.centrotareas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import com.santamaria.centrotareas.ui.components.BotonPrincipal
import com.santamaria.centrotareas.ui.components.BotonSecundario
import com.santamaria.centrotareas.ui.components.CajaMensaje
import com.santamaria.centrotareas.ui.components.FranjaVida
import com.santamaria.centrotareas.ui.components.TarjetaPaso
import com.santamaria.centrotareas.ui.components.TipoMensaje
import com.santamaria.centrotareas.model.CargaUi
import com.santamaria.centrotareas.model.PrimosUi
import com.santamaria.centrotareas.model.Tiempos
import com.santamaria.centrotareas.ui.theme.Ambar
import com.santamaria.centrotareas.ui.theme.FormaBoton
import com.santamaria.centrotareas.ui.theme.NumeroChico
import com.santamaria.centrotareas.ui.theme.NumeroGrande
import com.santamaria.centrotareas.viewmodel.TareasViewModel

private fun miles(n: Int) = String.format(Locale.US, "%,d", n).replace(',', ' ')

/** Barras con los últimos tiempos de "Una por una" y "A la vez" y cuánto se ahorra en paralelo. */
@Composable
private fun ComparacionTiempos(tiempos: Tiempos) {
    val una = tiempos.unaPorUna
    val vez = tiempos.aLaVez
    if (una == null && vez == null) return
    val maximo = maxOf(una ?: 0L, vez ?: 0L).toFloat()
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("COMPARACIÓN DE TIEMPOS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        FilaTiempo("Una por una", una, maximo, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.55f))
        FilaTiempo("A la vez", vez, maximo, MaterialTheme.colorScheme.primary)
        if (una != null && vez != null && una > vez) {
            val segundos = String.format(Locale.US, "%.1f", (una - vez) / 1000.0).replace('.', ',')
            Text(
                "$segundos s menos al ejecutar en paralelo",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun FilaTiempo(nombre: String, ms: Long?, maximo: Float, color: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(nombre, style = MaterialTheme.typography.bodySmall, modifier = Modifier.width(84.dp))
        Box(
            modifier = Modifier.weight(1f).height(8.dp).clip(FormaBoton).background(MaterialTheme.colorScheme.outline)
        ) {
            if (ms != null && maximo > 0f) {
                Box(Modifier.fillMaxWidth(ms / maximo).height(8.dp).clip(FormaBoton).background(color))
            }
        }
        Text(
            if (ms == null) "—" else String.format(Locale.US, "%.1f s", ms / 1000.0),
            style = NumeroChico.copy(fontSize = 11.sp),
            modifier = Modifier.width(44.dp)
        )
    }
}

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
                val corriendo by vm.corriendo.collectAsState()
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
                    color = if (mensaje == "Cancelado") Ambar else MaterialTheme.colorScheme.primary,
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
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    BotonPrincipal("Iniciar", onClick = vm::tareaLarga, icono = Icons.Outlined.PlayArrow, habilitado = !corriendo, modifier = Modifier.weight(1f))
                    BotonSecundario("Cancelar", onClick = vm::cancelar, icono = Icons.Outlined.StopCircle, habilitado = corriendo, modifier = Modifier.weight(1f))
                }
            }
            TarjetaPaso(2, "Contar números primos", Icons.Outlined.Memory, abierta == 2, { abierta = if (abierta == 2) 0 else 2 }) {
                val primos by vm.primos.collectAsState()
                var texto by rememberSaveable { mutableStateOf("2000000") }
                // Valor por defecto subido de 500 000 a 2 000 000: en el emulador tarda ~3 s (la guía pide entre 2 y 5 s)
                // Ignora textos no numéricos o menores que 2
                val limite = texto.toIntOrNull()?.takeIf { it >= 2 }
                val calculando = primos is PrimosUi.Calculando
                OutlinedTextField(
                    value = texto,
                    onValueChange = { nuevo -> texto = nuevo.filter { it.isDigit() }.take(9) },
                    label = { Text("Hasta el número") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = !calculando,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    BotonPrincipal("Contar", onClick = { limite?.let(vm::contar) }, habilitado = limite != null && !calculando, modifier = Modifier.weight(1f))
                    BotonSecundario("Cancelar", onClick = vm::cancelarPrimos, habilitado = calculando, modifier = Modifier.weight(1f))
                }
                when (val estado = primos) {
                    is PrimosUi.Listo -> CajaMensaje(
                        tipo = TipoMensaje.EXITO,
                        titulo = "Cálculo terminado",
                        texto = "${miles(estado.cantidad)} primos hasta ${miles(limite ?: 0)} · ${estado.milisegundos} ms"
                    )
                    is PrimosUi.Calculando -> {
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text("Calculando…", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
                            Text("${estado.porcentaje} %", style = NumeroChico.copy(fontSize = 14.sp))
                        }
                        LinearProgressIndicator(
                            progress = { estado.porcentaje / 100f },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(FormaBoton),
                            trackColor = MaterialTheme.colorScheme.outline,
                            drawStopIndicator = {}
                        )
                    }
                    PrimosUi.Cancelado -> Text("Cancelado", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    PrimosUi.Inicial -> Unit
                }
            }
            TarjetaPaso(3, "Dos cargas de red", Icons.Outlined.Sync, abierta == 3, { abierta = if (abierta == 3) 0 else 3 }) {
                val carga by vm.carga.collectAsState()
                val simularError by vm.simularError.collectAsState()
                val tiempos by vm.tiempos.collectAsState()
                val cargando = carga is CargaUi.Cargando
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Simular error", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                    Switch(
                        checked = simularError,
                        onCheckedChange = vm::cambiarSimularError,
                        enabled = !cargando,
                        colors = SwitchDefaults.colors(checkedTrackColor = MaterialTheme.colorScheme.error)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    BotonSecundario("Una por una", onClick = vm::cargarUnaPorUna, habilitado = !cargando, modifier = Modifier.weight(1f))
                    BotonPrincipal("A la vez", onClick = vm::cargarALaVez, habilitado = !cargando, modifier = Modifier.weight(1f))
                }
                when (val estado = carga) {
                    CargaUi.Cargando -> Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        CircularProgressIndicator()
                    }
                    is CargaUi.Exito -> CajaMensaje(
                        tipo = TipoMensaje.EXITO,
                        titulo = "Modo: ${estado.modo}",
                        texto = "Perfil: ${estado.perfil}\nPedidos: ${estado.pedidos}\nTiempo: ${estado.milisegundos} ms"
                    )
                    is CargaUi.Error -> CajaMensaje(
                        tipo = TipoMensaje.ERROR,
                        titulo = "No se pudo cargar",
                        texto = estado.mensaje.removePrefix("No se pudo cargar: "),
                        accion = "Reintentar",
                        onAccion = vm::cargarALaVez
                    )
                    CargaUi.Inicial -> Unit
                }
                ComparacionTiempos(tiempos)
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

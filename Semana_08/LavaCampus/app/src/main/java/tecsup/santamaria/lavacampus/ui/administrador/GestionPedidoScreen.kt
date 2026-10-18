package tecsup.santamaria.lavacampus.ui.administrador

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.model.Casilleros
import tecsup.santamaria.lavacampus.model.EstadoPedido
import tecsup.santamaria.lavacampus.ui.components.AppTopBar
import tecsup.santamaria.lavacampus.ui.components.EstadoPedidoChip
import tecsup.santamaria.lavacampus.ui.components.LavaButton
import tecsup.santamaria.lavacampus.ui.components.StepperEstado
import tecsup.santamaria.lavacampus.ui.components.fechaHora
import tecsup.santamaria.lavacampus.viewmodel.GestionPedidoViewModel

@Composable
fun GestionPedidoScreen(vm: GestionPedidoViewModel, onAtras: () -> Unit) {
    val p by vm.pedido.collectAsState()
    val ocupados by vm.casillerosOcupados.collectAsState()
    val elegido by vm.casilleroElegido.collectAsState()
    val mensaje by vm.mensaje.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar(p?.let { "Gestionar pedido #%03d".format(it.pedido.id) } ?: "Gestionar pedido", onAtras = onAtras) }
    ) { padding ->
        val pedido = p
        if (pedido == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            return@Scaffold
        }
        val estado = EstadoPedido.valueOf(pedido.pedido.estado)
        val siguiente = estado.siguiente()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Datos del estudiante
            Tarjeta {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(44.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            iniciales(pedido.usuario.nombre),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(pedido.usuario.nombre, style = MaterialTheme.typography.titleMedium)
                        Text(pedido.usuario.codigo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    EstadoPedidoChip(estado)
                }
            }

            Tarjeta {
                // Solo la hora de registro está guardada; las demás etapas muestran "—"
                StepperEstado(
                    estado = estado,
                    vertical = true,
                    detalles = listOf(fechaHora(pedido.pedido.fechaCreacion), "—", "—", "—")
                )
            }

            if (estado == EstadoPedido.RECIBIDO || estado == EstadoPedido.EN_PROCESO || estado == EstadoPedido.LISTO) {
                Text("Casillero para el recojo", style = MaterialTheme.typography.titleMedium)
                val lista = rememberLazyListState()
                // Que el casillero elegido se vea sin buscarlo
                LaunchedEffect(elegido) {
                    elegido?.let { c -> lista.scrollToItem((Casilleros.todos.indexOf(c) - 1).coerceAtLeast(0)) }
                }
                LazyRow(state = lista, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(Casilleros.todos) { c ->
                        val ocupado = c in ocupados
                        FilterChip(
                            selected = c == elegido,
                            onClick = { vm.elegirCasillero(c) },
                            // LISTO ya tiene su casillero: no se cambia
                            enabled = !ocupado && estado != EstadoPedido.LISTO,
                            label = { Text(c) },
                            shape = RoundedCornerShape(50),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                disabledSelectedContainerColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
                Text(
                    "Los casilleros en gris ya están ocupados",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Total y cobro
            Tarjeta {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Total S/ %.2f".format(pedido.pedido.total), style = MaterialTheme.typography.titleMedium)
                        Text("Se cobra al entregar", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text("Cobrado", style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.width(8.dp))
                    Switch(
                        checked = pedido.pedido.pagado,
                        onCheckedChange = vm::marcarPagado,
                        enabled = !estado.esFinal
                    )
                }
            }

            // Recordatorio de la regla
            Text(
                buildAnnotatedString {
                    append("Regla: no se entrega un pedido sin cobrar,\n")
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)) {
                        append("ni se pasa a Listo sin casillero.")
                    }
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                    .padding(12.dp)
            )

            mensaje?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
            }

            if (siguiente != null) {
                LavaButton(texto = "Marcar como ${siguiente.etiqueta}", onClick = vm::avanzar)
            }
            LavaButton(
                texto = "Cancelar pedido",
                onClick = vm::cancelar,
                enabled = GestionPedidoViewModel.puedeCancelar(estado),
                contorno = true,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

// "Victor Santamaria" -> "VS"
private fun iniciales(nombre: String) =
    nombre.split(" ").filter { it.isNotBlank() }.take(2).joinToString("") { it.first().uppercase() }

@Composable
private fun Tarjeta(contenido: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(Modifier.padding(14.dp)) { contenido() }
    }
}

package tecsup.santamaria.lavacampus.ui.estudiante

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.data.local.relation.PedidoConDetalle
import tecsup.santamaria.lavacampus.model.EstadoPedido
import tecsup.santamaria.lavacampus.model.Unidad
import tecsup.santamaria.lavacampus.ui.components.AppTopBar
import tecsup.santamaria.lavacampus.ui.components.EstadoPedidoChip
import tecsup.santamaria.lavacampus.ui.components.IconoServicio
import tecsup.santamaria.lavacampus.ui.components.LavaButton
import tecsup.santamaria.lavacampus.ui.components.StepperEstado
import tecsup.santamaria.lavacampus.ui.components.cantidadConUnidad
import tecsup.santamaria.lavacampus.ui.components.fechaCorta
import tecsup.santamaria.lavacampus.ui.components.fechaHora
import tecsup.santamaria.lavacampus.viewmodel.DetallePedidoViewModel

@Composable
fun DetallePedidoScreen(vm: DetallePedidoViewModel, onAtras: () -> Unit) {
    val p by vm.pedido.collectAsState()
    val puedeCancelar by vm.puedeCancelar.collectAsState()
    var confirmar by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar(p?.let { "Pedido #%03d".format(it.pedido.id) } ?: "Pedido", onAtras = onAtras) }
    ) { padding ->
        val pedido = p
        if (pedido == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            return@Scaffold
        }
        val estado = EstadoPedido.valueOf(pedido.pedido.estado)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Cabecera(pedido, estado)

            Text("Seguimiento", style = MaterialTheme.typography.titleMedium)
            Tarjeta {
                // Solo se guarda la hora de registro; las demás etapas no tienen hora en la tabla.
                // Cancelado: las etapas ya no van a pasar, así que no dicen "Pendiente"
                val registro = fechaHora(pedido.pedido.fechaCreacion)
                StepperEstado(
                    estado = estado,
                    vertical = true,
                    detalles = if (estado == EstadoPedido.CANCELADO) listOf(registro, "—", "—", "—") else listOf(registro)
                )
            }

            Text("Detalle", style = MaterialTheme.typography.titleMedium)
            Tarjeta {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    pedido.detalles.forEach { d ->
                        Row {
                            Text(
                                "${d.servicio.nombre} · ${cantidadConUnidad(d.detalle.cantidad, Unidad.valueOf(d.servicio.unidad))} × S/ %.2f".format(d.detalle.precioUnitario),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                            Text("S/ %.2f".format(d.detalle.subtotal), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Total", style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f))
                        Text(
                            "S/ %.2f".format(pedido.pedido.total),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            if (estado != EstadoPedido.CANCELADO) RecuadroPago(pedido, estado)

            LavaButton(
                texto = "Cancelar pedido",
                onClick = { confirmar = true },
                enabled = puedeCancelar,
                contorno = true,
                color = MaterialTheme.colorScheme.error
            )
            if (!puedeCancelar && estado != EstadoPedido.CANCELADO) {
                Text(
                    "Solo se cancela antes de empezar el lavado",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(Modifier.height(8.dp))
        }
    }

    if (confirmar) {
        AlertDialog(
            onDismissRequest = { confirmar = false },
            title = { Text("¿Cancelar el pedido?") },
            text = { Text("El pedido pasará a Cancelado y no se podrá recuperar.") },
            confirmButton = {
                TextButton(onClick = { confirmar = false; vm.cancelar() }) {
                    Text("Sí, cancelar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmar = false }) { Text("No") }
            }
        )
    }
}

@Composable
private fun Cabecera(p: PedidoConDetalle, estado: EstadoPedido) {
    Tarjeta {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconoServicio(p.detalles.firstOrNull()?.servicio?.icono ?: "wash", tamano = 48.dp)
            Spacer(Modifier.width(12.dp))
            Column {
                Text("Registrado el ${fechaCorta(p.pedido.fechaRegistro)}", style = MaterialTheme.typography.labelLarge)
                Text(
                    "Entrega estimada: ${fechaCorta(p.pedido.fechaEntrega)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                EstadoPedidoChip(estado)
            }
        }
    }
}

// 'Pago pendiente, se paga al recoger' o 'Pagado', y el casillero
@Composable
private fun RecuadroPago(p: PedidoConDetalle, estado: EstadoPedido) {
    val color = if (p.pedido.pagado) EstadoPedido.LISTO.color else EstadoPedido.RECIBIDO.color
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.14f))
            .padding(12.dp)
    ) {
        Text(
            if (p.pedido.pagado) "Pagado" else "Pago pendiente · se paga al recoger",
            style = MaterialTheme.typography.labelLarge,
            color = color
        )
        Text(
            if (estado == EstadoPedido.LISTO && p.pedido.casillero != null)
                "Casillero: ${p.pedido.casillero}"
            else if (estado == EstadoPedido.ENTREGADO) "Pedido entregado"
            else "Casillero: se asignará al estar listo",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

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

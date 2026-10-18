package tecsup.santamaria.lavacampus.ui.estudiante

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.data.local.relation.PedidoConDetalle
import tecsup.santamaria.lavacampus.model.EstadoPedido
import tecsup.santamaria.lavacampus.ui.components.AppTopBar
import tecsup.santamaria.lavacampus.ui.components.PedidoCard
import tecsup.santamaria.lavacampus.ui.components.SelectorSegmentado
import tecsup.santamaria.lavacampus.ui.components.fechaCorta
import tecsup.santamaria.lavacampus.ui.components.fechaTarjeta
import tecsup.santamaria.lavacampus.ui.components.nombreCorto
import tecsup.santamaria.lavacampus.ui.components.resumenLineas
import tecsup.santamaria.lavacampus.viewmodel.MisPedidosViewModel

@Composable
fun MisPedidosScreen(
    vm: MisPedidosViewModel,
    barraInferior: @Composable () -> Unit,
    onAtras: () -> Unit,
    onNuevoPedido: () -> Unit,
    onPedido: (Int) -> Unit
) {
    val activos by vm.activos.collectAsState()
    val historial by vm.historial.collectAsState()
    val listo by vm.listo.collectAsState()
    var pestana by rememberSaveable { mutableIntStateOf(0) }
    var aCancelar by remember { mutableStateOf<PedidoConDetalle?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar("Mis pedidos", onAtras = onAtras) },
        bottomBar = barraInferior,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNuevoPedido,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) { Icon(Icons.Filled.Add, contentDescription = "Nuevo pedido") }
        }
    ) { padding ->
        val lista = if (pestana == 0) activos else historial
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SelectorSegmentado(
                    opciones = listOf("Activos", "Historial"),
                    seleccionada = pestana,
                    onCambio = { pestana = it }
                )
            }
            if (lista.isEmpty()) {
                item {
                    Text(
                        if (pestana == 0) "No tienes pedidos activos" else "Aún no tienes pedidos en el historial",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
                    )
                }
            }
            items(lista, key = { it.pedido.id }) { p ->
                TarjetaPedido(
                    p = p,
                    puedeCancelar = vm.puedeCancelar(p),
                    onClick = { onPedido(p.pedido.id) },
                    onCancelar = { aCancelar = p }
                )
            }
            // Aviso cuando hay un pedido por recoger
            if (pestana == 0) listo?.let { p ->
                item {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Text("Aviso", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                            Text(
                                "Tu pedido #%03d está listo: recógelo en el casillero indicado.".format(p.pedido.id),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }

    aCancelar?.let { p ->
        AlertDialog(
            onDismissRequest = { aCancelar = null },
            title = { Text("¿Cancelar el pedido #%03d?".format(p.pedido.id)) },
            text = { Text("El pedido pasará a Cancelado y no se podrá recuperar.") },
            confirmButton = {
                TextButton(onClick = { vm.cancelar(p); aCancelar = null }) {
                    Text("Sí, cancelar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { aCancelar = null }) { Text("No") } }
        )
    }
}

@Composable
private fun TarjetaPedido(
    p: PedidoConDetalle,
    puedeCancelar: Boolean,
    onClick: () -> Unit,
    onCancelar: () -> Unit
) {
    val estado = EstadoPedido.valueOf(p.pedido.estado)
    val esListo = estado == EstadoPedido.LISTO
    val forma = RoundedCornerShape(14.dp)
    PedidoCard(
        numero = p.pedido.id,
        resumen = if (esListo) "${resumenLineas(p.detalles)} · ${fechaTarjeta(p.pedido.fechaEntrega)}"
        else "${nombreCorto(p.usuario.nombre)} · ${resumenLineas(p.detalles)}",
        total = p.pedido.total,
        estado = estado,
        icono = p.detalles.firstOrNull()?.servicio?.icono ?: "wash",
        onClick = onClick,
        // LISTO: destaque verde
        modifier = if (esListo) Modifier.border(1.5.dp, estado.color, forma) else Modifier
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            when {
                esListo && p.pedido.casillero != null -> Surface(
                    color = estado.color,
                    contentColor = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "Casillero ${p.pedido.casillero}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }
                puedeCancelar -> Text(
                    "Cancelar",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.clickable(onClick = onCancelar)
                )
                estado == EstadoPedido.EN_PROCESO -> Text(
                    "Entrega ${fechaCorta(p.pedido.fechaEntrega).substringBeforeLast(" ")}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                else -> Spacer(Modifier)
            }
        }
    }
}

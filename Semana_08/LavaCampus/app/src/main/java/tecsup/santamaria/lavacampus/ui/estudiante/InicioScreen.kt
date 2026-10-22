package tecsup.santamaria.lavacampus.ui.estudiante

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.data.local.relation.PedidoConDetalle
import tecsup.santamaria.lavacampus.model.EstadoPedido
import tecsup.santamaria.lavacampus.ui.components.LavaButton
import tecsup.santamaria.lavacampus.ui.components.PedidoCard
import tecsup.santamaria.lavacampus.ui.components.StepperEstado
import tecsup.santamaria.lavacampus.ui.components.fechaCorta
import tecsup.santamaria.lavacampus.ui.components.fechaTarjeta
import tecsup.santamaria.lavacampus.ui.components.resumenLineas
import tecsup.santamaria.lavacampus.viewmodel.InicioViewModel

@Composable
fun InicioScreen(
    vm: InicioViewModel,
    barraInferior: @Composable () -> Unit,
    onNuevoPedido: () -> Unit,
    onMisPedidos: () -> Unit,
    onTarifas: () -> Unit,
    onPedido: (Int) -> Unit
) {
    val activo by vm.pedidoActivo.collectAsState()
    val recientes by vm.recientes.collectAsState()
    val estudiante = vm.estudiante

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = barraInferior
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            "Hola, ${estudiante.nombre.substringBefore(" ")}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            "${estudiante.codigo} · Tecsup",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Avisos")
                    }
                }
            }
            item { TarjetaActivo(activo, onPedido) }
            item { LavaButton(texto = "Nuevo pedido", onClick = onNuevoPedido) }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Acceso(Icons.Outlined.Menu, "Mis pedidos", "Ver estado", onMisPedidos, Modifier.weight(1f))
                    Acceso(Icons.Outlined.Sell, "Tarifas", "Precios por kg", onTarifas, Modifier.weight(1f))
                }
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Últimos pedidos", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    Text(
                        "Ver todos",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable(onClick = onMisPedidos)
                    )
                }
            }
            items(recientes, key = { it.pedido.id }) { p ->
                PedidoCard(
                    numero = p.pedido.id,
                    resumen = "${resumenLineas(p.detalles)} · ${fechaTarjeta(p.pedido.fechaEntrega)}",
                    total = p.pedido.total,
                    estado = EstadoPedido.valueOf(p.pedido.estado),
                    icono = p.detalles.firstOrNull()?.servicio?.icono ?: "wash",
                    casillero = p.pedido.casillero,
                    onClick = { onPedido(p.pedido.id) }
                )
            }
        }
    }
}

// Tarjeta turquesa con el seguimiento del pedido activo
@Composable
private fun TarjetaActivo(p: PedidoConDetalle?, onPedido: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = p != null) { p?.let { onPedido(it.pedido.id) } }
    ) {
        Column(Modifier.padding(16.dp)) {
            if (p == null) {
                Text("Tu pedido activo", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(6.dp))
                Text("No tienes pedidos activos", style = MaterialTheme.typography.titleLarge)
                Text("Cuando hagas un pedido, aquí verás su avance", style = MaterialTheme.typography.bodySmall)
                return@Column
            }
            val estado = EstadoPedido.valueOf(p.pedido.estado)
            Row {
                Text("Tu pedido activo", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                Text("#%03d".format(p.pedido.id), style = MaterialTheme.typography.labelLarge)
            }
            Spacer(Modifier.height(6.dp))
            Text(estado.etiqueta, style = MaterialTheme.typography.titleLarge)
            Text(
                if (estado == EstadoPedido.LISTO && p.pedido.casillero != null)
                    "Recógelo en el casillero ${p.pedido.casillero}"
                else
                    "Entrega estimada: ${fechaCorta(p.pedido.fechaEntrega)}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(12.dp))
            Surface(
                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(12.dp)
            ) {
                StepperEstado(estado, Modifier.padding(horizontal = 8.dp, vertical = 8.dp))
            }
        }
    }
}

@Composable
private fun Acceso(icono: ImageVector, titulo: String, subtitulo: String, onClick: () -> Unit, modifier: Modifier) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(icono, contentDescription = null, modifier = Modifier.padding(9.dp))
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(titulo, style = MaterialTheme.typography.labelLarge)
                Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

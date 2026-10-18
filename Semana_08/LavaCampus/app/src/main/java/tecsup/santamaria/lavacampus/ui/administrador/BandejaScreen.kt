package tecsup.santamaria.lavacampus.ui.administrador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.model.EstadoPedido
import tecsup.santamaria.lavacampus.ui.components.AppTopBar
import tecsup.santamaria.lavacampus.ui.components.LavaTextField
import tecsup.santamaria.lavacampus.ui.components.PedidoCard
import tecsup.santamaria.lavacampus.ui.components.nombreCorto
import tecsup.santamaria.lavacampus.ui.components.resumenLineas
import tecsup.santamaria.lavacampus.viewmodel.BandejaViewModel

private val filtros = listOf(null, EstadoPedido.RECIBIDO, EstadoPedido.EN_PROCESO, EstadoPedido.LISTO)

@Composable
fun BandejaScreen(
    vm: BandejaViewModel,
    barraInferior: @Composable () -> Unit,
    onAtras: () -> Unit,
    onGestionar: (Int) -> Unit
) {
    val pedidos by vm.pedidos.collectAsState()
    val filtro by vm.filtro.collectAsState()
    val busqueda by vm.busqueda.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val avisos = remember { SnackbarHostState() }

    LaunchedEffect(mensaje) {
        mensaje?.let {
            avisos.showSnackbar(it)
            vm.limpiarMensaje()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar("Pedidos", onAtras = onAtras) },
        bottomBar = barraInferior,
        snackbarHost = { SnackbarHost(avisos) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                LavaTextField(
                    valor = busqueda,
                    onCambio = vm::buscar,
                    etiqueta = "Buscar por estudiante o número",
                    icono = Icons.Outlined.Search
                )
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filtros) { e ->
                        FilterChip(
                            selected = filtro == e,
                            onClick = { vm.elegirFiltro(e) },
                            label = { Text(e?.etiqueta ?: "Todos") },
                            shape = RoundedCornerShape(50),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }
            if (pedidos.isEmpty()) item {
                Text(
                    "No hay pedidos con ese filtro",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
                )
            }
            items(pedidos, key = { it.pedido.id }) { p ->
                val estado = EstadoPedido.valueOf(p.pedido.estado)
                PedidoCard(
                    numero = p.pedido.id,
                    resumen = "${nombreCorto(p.usuario.nombre)} · ${resumenLineas(p.detalles)}",
                    total = p.pedido.total,
                    estado = estado,
                    icono = p.detalles.firstOrNull()?.servicio?.icono ?: "wash",
                    onClick = { onGestionar(p.pedido.id) }
                ) {
                    // Acción según el estado: Iniciar, Marcar listo, Entregar
                    val accion = when (estado) {
                        EstadoPedido.RECIBIDO -> "Iniciar"
                        EstadoPedido.EN_PROCESO -> "Marcar listo"
                        EstadoPedido.LISTO -> "Entregar"
                        else -> null
                    }
                    if (accion != null) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                // Marcar listo necesita elegir casillero: abre la gestión
                                onClick = { if (estado == EstadoPedido.EN_PROCESO) onGestionar(p.pedido.id) else vm.accionRapida(p) },
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 18.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier.height(34.dp)
                            ) { Text(accion, style = MaterialTheme.typography.labelLarge) }
                        }
                    }
                }
            }
        }
    }
}

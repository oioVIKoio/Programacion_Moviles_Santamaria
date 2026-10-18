package tecsup.santamaria.lavacampus.ui.estudiante

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.data.local.entity.Servicio
import tecsup.santamaria.lavacampus.model.Unidad
import tecsup.santamaria.lavacampus.ui.components.AppTopBar
import tecsup.santamaria.lavacampus.ui.components.IconoServicio
import tecsup.santamaria.lavacampus.ui.components.LavaButton
import tecsup.santamaria.lavacampus.ui.components.LavaTextField
import tecsup.santamaria.lavacampus.ui.components.SelectorCantidad
import tecsup.santamaria.lavacampus.ui.components.cantidadConUnidad
import tecsup.santamaria.lavacampus.ui.components.fechaCorta
import tecsup.santamaria.lavacampus.ui.components.textoPrecio
import tecsup.santamaria.lavacampus.viewmodel.NuevoPedidoViewModel

@Composable
fun NuevoPedidoScreen(vm: NuevoPedidoViewModel, onAtras: () -> Unit, onCreado: () -> Unit) {
    val servicios by vm.servicios.collectAsState()
    val cantidades by vm.cantidades.collectAsState()
    val nota by vm.nota.collectAsState()
    val total by vm.total.collectAsState()
    val fecha by vm.fechaEntrega.collectAsState()
    val error by vm.error.collectAsState()
    val creado by vm.creado.collectAsState()
    val recibe by vm.recibePedidos.collectAsState()

    LaunchedEffect(creado) { if (creado) onCreado() }

    val elegidos = servicios.filter { (cantidades[it.id] ?: 0.0) > 0 }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar("Nuevo pedido", onAtras = onAtras) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Ajustes del admin: recepción cerrada
            if (!recibe) item {
                Text(
                    "La lavandería no está recibiendo pedidos",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.error.copy(alpha = 0.1f))
                        .padding(12.dp)
                )
            }
            item { Text("¿Qué necesitas?", style = MaterialTheme.typography.titleMedium) }
            items(servicios, key = { it.id }) { s ->
                val unidad = Unidad.valueOf(s.unidad)
                TarjetaServicio(
                    servicio = s,
                    cantidad = cantidades[s.id] ?: 0.0,
                    unidad = unidad,
                    onMenos = { vm.cambiarCantidad(s.id, -unidad.paso) },
                    onMas = { vm.cambiarCantidad(s.id, unidad.paso) }
                )
            }
            item {
                LavaTextField(
                    valor = nota,
                    onCambio = vm::cambiarNota,
                    etiqueta = "Nota (opcional)",
                    unaLinea = false
                )
            }
            item {
                Resumen(elegidos, cantidades, total)
            }
            item {
                Column(
                    Modifier.fillMaxWidth().navigationBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        fecha?.let { "Entrega estimada: ${fechaCorta(it)}" } ?: "Elige al menos un servicio",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    error?.let {
                        Text(
                            it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    LavaButton(
                        texto = "Confirmar pedido",
                        onClick = vm::confirmar,
                        enabled = elegidos.isNotEmpty() && recibe
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun TarjetaServicio(
    servicio: Servicio,
    cantidad: Double,
    unidad: Unidad,
    onMenos: () -> Unit,
    onMas: () -> Unit
) {
    val elegido = cantidad > 0
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(
            if (elegido) 1.5.dp else 1.dp,
            if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconoServicio(
                    servicio.icono,
                    tamano = 44.dp,
                    color = if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    fondo = if (elegido) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(servicio.nombre, style = MaterialTheme.typography.titleMedium)
                    Text(
                        "S/ %.2f %s · %d h".format(servicio.precio, servicio.textoPrecio(), servicio.horasEntrega),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            SelectorCantidad(
                valor = cantidad,
                paso = unidad.paso,
                minimo = unidad.minimo,
                maximo = unidad.maximo,
                onMenos = onMenos,
                onMas = onMas,
                sufijo = if (unidad == Unidad.KG) "kg" else "",
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

// Una línea por servicio elegido y el total
@Composable
private fun Resumen(elegidos: List<Servicio>, cantidades: Map<Int, Double>, total: Double) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                "Resumen",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            elegidos.forEach { s ->
                val cantidad = cantidades[s.id] ?: 0.0
                Row {
                    Text(
                        "${s.nombre}  ${cantidadConUnidad(cantidad, Unidad.valueOf(s.unidad))}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Text("S/ %.2f".format(cantidad * s.precio), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Total", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                Text(
                    "S/ %.2f".format(total),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

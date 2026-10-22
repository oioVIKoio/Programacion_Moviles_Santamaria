package tecsup.santamaria.lavacampus.ui.administrador

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.model.EstadoPedido
import tecsup.santamaria.lavacampus.ui.components.fechaCorta
import tecsup.santamaria.lavacampus.ui.components.graficos.GraficoBarras
import tecsup.santamaria.lavacampus.ui.components.graficos.GraficoDona
import tecsup.santamaria.lavacampus.viewmodel.PanelViewModel
import java.util.Locale

@Composable
fun PanelScreen(vm: PanelViewModel, barraInferior: @Composable () -> Unit, onVerListos: () -> Unit) {
    val hoy by vm.pedidosHoy.collectAsState()
    val enProceso by vm.enProceso.collectAsState()
    val porRecoger by vm.porRecoger.collectAsState()
    val ingresos by vm.ingresosSemana.collectAsState()
    val pagados by vm.pagadosSemana.collectAsState()
    val porEstado by vm.porEstado.collectAsState()
    val porServicio by vm.porServicio.collectAsState()

    // Barras con el color del tema: la más alta fuerte, las demás suaves
    val primario = MaterialTheme.colorScheme.primary
    val maximo = porServicio.maxOfOrNull { it.valor }
    val barras = porServicio.map { it.copy(color = if (it.valor == maximo) primario else primario.copy(alpha = 0.35f)) }

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
                        Text("Panel de la lavandería", style = MaterialTheme.typography.headlineSmall)
                        Text(
                            "Hoy · ${fechaCorta(vm.hoy).replaceFirstChar { it.uppercase() }}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = { }) { Icon(Icons.Filled.Notifications, contentDescription = "Avisos") }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Contador(hoy, "Pedidos hoy", MaterialTheme.colorScheme.primary, Modifier.weight(1f))
                    Contador(enProceso, "En proceso", EstadoPedido.EN_PROCESO.color, Modifier.weight(1f))
                    Contador(porRecoger, "Por recoger", EstadoPedido.LISTO.color, Modifier.weight(1f))
                }
            }
            item {
                // Tarjeta violeta de ingresos de la semana
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Ingresos de la semana", style = MaterialTheme.typography.bodySmall)
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                "S/ %,.2f".format(Locale.US, ingresos),
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.weight(1f)
                            )
                            Text(if (pagados == 1) "1 pedido" else "$pagados pedidos", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            item {
                Text("Pedidos por estado", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Tarjeta { GraficoDona(porEstado, Modifier.fillMaxWidth()) }
            }
            item {
                Text("Ingresos por servicio (S/)", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Tarjeta {
                    if (barras.isEmpty()) {
                        Text(
                            "Aún no hay pedidos pagados esta semana",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        GraficoBarras(barras)
                    }
                }
            }
            // Alerta si hay pedidos LISTO esperando recojo
            if (porRecoger > 0) item {
                val verde = EstadoPedido.LISTO.color
                Surface(color = verde.copy(alpha = 0.12f), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Inventory2, contentDescription = null, tint = verde, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                if (porRecoger == 1) "1 pedido espera recojo" else "$porRecoger pedidos esperan recojo",
                                style = MaterialTheme.typography.labelLarge
                            )
                            Text("Avisa a los estudiantes", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Button(
                            onClick = onVerListos,
                            colors = ButtonDefaults.buttonColors(containerColor = verde, contentColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(50)
                        ) { Text("Ver") }
                    }
                }
            }
        }
    }
}

@Composable
private fun Contador(valor: Int, texto: String, color: Color, modifier: Modifier) {
    Surface(color = color.copy(alpha = 0.12f), shape = RoundedCornerShape(14.dp), modifier = modifier) {
        Column(Modifier.padding(vertical = 14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$valor", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = color)
            Text(texto, style = MaterialTheme.typography.bodySmall, color = color)
        }
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
        Column(Modifier.padding(16.dp)) { contenido() }
    }
}

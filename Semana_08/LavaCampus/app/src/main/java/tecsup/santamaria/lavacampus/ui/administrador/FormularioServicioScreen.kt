package tecsup.santamaria.lavacampus.ui.administrador

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.data.local.entity.Servicio
import tecsup.santamaria.lavacampus.model.Unidad
import tecsup.santamaria.lavacampus.ui.components.AppTopBar
import tecsup.santamaria.lavacampus.ui.components.IconoServicio
import tecsup.santamaria.lavacampus.ui.components.LavaButton
import tecsup.santamaria.lavacampus.ui.components.LavaSwitch
import tecsup.santamaria.lavacampus.ui.components.LavaTextField
import tecsup.santamaria.lavacampus.ui.components.SelectorSegmentado
import tecsup.santamaria.lavacampus.viewmodel.ServiciosViewModel

private val unidades = listOf(Unidad.KG to "Kilo", Unidad.PRENDA to "Prenda", Unidad.UNIDAD to "Unidad")
private val tiempos = listOf(24, 48, 72)
private val iconos = listOf("wash", "iron", "shirt")

// Sirve para crear (id 0) y para editar
@Composable
fun FormularioServicioScreen(vm: ServiciosViewModel, id: Int, onListo: () -> Unit) {
    val servicios by vm.servicios.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val original = servicios.firstOrNull { it.id == id }

    var nombre by rememberSaveable { mutableStateOf("") }
    var unidad by rememberSaveable { mutableStateOf(Unidad.KG) }
    var precio by rememberSaveable { mutableStateOf("") }
    var horas by rememberSaveable { mutableStateOf(24) }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var icono by rememberSaveable { mutableStateOf("wash") }
    var activo by rememberSaveable { mutableStateOf(true) }
    var cargado by rememberSaveable { mutableStateOf(id == 0) }

    // Al editar, se llena una sola vez con los datos guardados
    LaunchedEffect(original) {
        if (!cargado && original != null) {
            nombre = original.nombre
            unidad = Unidad.valueOf(original.unidad)
            precio = "%.2f".format(java.util.Locale.US, original.precio)
            horas = original.horasEntrega
            descripcion = original.descripcion
            icono = original.icono
            activo = original.activo
            cargado = true
        }
    }
    LaunchedEffect(Unit) { vm.limpiarMensaje() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar(if (id == 0) "Nuevo servicio" else "Editar servicio", onAtras = onListo) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LavaTextField(valor = nombre, onCambio = { nombre = it; vm.limpiarMensaje() }, etiqueta = "Nombre del servicio")

            Text("Se cobra por", style = MaterialTheme.typography.titleMedium)
            SelectorSegmentado(
                opciones = unidades.map { it.second },
                seleccionada = unidades.indexOfFirst { it.first == unidad },
                onCambio = { unidad = unidades[it].first }
            )

            LavaTextField(valor = precio, onCambio = { precio = it; vm.limpiarMensaje() }, etiqueta = "Precio (S/)", teclado = KeyboardType.Decimal)

            Text("Tiempo de entrega", style = MaterialTheme.typography.titleMedium)
            SelectorSegmentado(
                opciones = tiempos.map { "$it h" },
                seleccionada = tiempos.indexOf(horas),
                onCambio = { horas = tiempos[it] }
            )

            LavaTextField(valor = descripcion, onCambio = { descripcion = it }, etiqueta = "Descripción", unaLinea = false)

            Text("Ícono", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                iconos.forEach { i ->
                    val elegido = i == icono
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(
                            if (elegido) 2.dp else 1.dp,
                            if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        ),
                        modifier = Modifier.weight(1f).clickable { icono = i }
                    ) {
                        Box(Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.Center) {
                            IconoServicio(
                                i,
                                tamano = 40.dp,
                                color = if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                fondo = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                }
            }

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Servicio activo", style = MaterialTheme.typography.labelLarge)
                        Text("Los estudiantes podrán pedirlo", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    LavaSwitch(checked = activo, onCheckedChange = { activo = it })
                }
            }

            mensaje?.let { Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error) }

            LavaButton(texto = "Guardar servicio", onClick = {
                val servicio = Servicio(
                    id = id,
                    nombre = nombre,
                    descripcion = descripcion.trim(),
                    unidad = unidad.name,
                    precio = precio.replace(",", ".").toDoubleOrNull() ?: 0.0,
                    horasEntrega = horas,
                    activo = activo,
                    icono = icono
                )
                if (vm.guardar(servicio)) onListo()
            })
            LavaButton(texto = "Cancelar", onClick = onListo, contorno = true)

            // Al editar: eliminar (si ya está en pedidos, el ViewModel lo impide)
            if (original != null) {
                TextButton(onClick = { vm.eliminar(original, alEliminar = onListo) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Eliminar servicio", color = MaterialTheme.colorScheme.error)
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

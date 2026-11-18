package com.santamaria.campusgps.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.santamaria.campusgps.model.Lugar
import com.santamaria.campusgps.model.LugarConDistancia
import com.santamaria.campusgps.model.UbicacionUi
import com.santamaria.campusgps.ui.components.BotonPrincipal
import com.santamaria.campusgps.ui.components.BotonSecundario
import com.santamaria.campusgps.ui.theme.Exito
import com.santamaria.campusgps.ui.theme.FormaBoton
import com.santamaria.campusgps.ui.theme.FormaHoja
import com.santamaria.campusgps.ui.theme.NumeroChico
import com.santamaria.campusgps.util.formatearDistancia
import com.santamaria.campusgps.viewmodel.MapaViewModel
import java.util.Locale

// Altura fija de la lista: el mapa se queda con el resto (weight 1f)
private val ALTO_LISTA = 200.dp

// Ejemplo resuelto: pedir la ubicación y mostrar latitud y longitud, ya con el diseño.
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()
    val lugares by vm.lugares.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val masCercano by vm.masCercano.collectAsState()
    val categoria by vm.categoria.collectAsState()
    val contexto = LocalContext.current

    // Si el usuario acepta alguno de los dos permisos buscamos; si niega, avisamos sin cerrar la app
    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        if (resultado.values.any { it }) vm.buscar() else vm.permisoDenegado()
    }

    fun alPulsar() {
        val tienePermiso = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).any { ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED }

        if (tienePermiso) {
            vm.buscar()
        } else {
            lanzador.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(Modifier.fillMaxSize()) {
            // El mapa ocupa el espacio libre; la hoja queda debajo
            MapaUbicacion(
                estado = estado,
                lugares = lugares,
                seleccionado = seleccionado,
                masCercano = masCercano?.id,
                modifier = Modifier.weight(1f)
            )
            HojaInferior(
                estado = estado,
                lugares = lugares,
                seleccionado = seleccionado,
                masCercano = masCercano,
                categorias = vm.categorias,
                categoria = categoria,
                onCategoria = vm::filtrarCategoria,
                onSeleccionar = vm::seleccionar,
                onBuscar = { alPulsar() }
            )
        }

        // Chip de marca flotante sobre el mapa, como en el diseño
        Surface(
            modifier = Modifier.statusBarsPadding().padding(20.dp),
            shape = FormaBoton,
            color = MaterialTheme.colorScheme.surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Row(
                Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Icon(Icons.Outlined.LocationOn, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                Text("CampusGPS", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

// Hoja blanca con esquinas redondeadas que se monta 28 dp sobre el mapa
@Composable
private fun HojaInferior(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    masCercano: Lugar?,
    categorias: List<String>,
    categoria: String?,
    onCategoria: (String?) -> Unit,
    onSeleccionar: (Int) -> Unit,
    onBuscar: () -> Unit
) {
    val solape = 28.dp
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .layout { medida, restricciones ->
                val pieza = medida.measure(restricciones)
                val px = solape.roundToPx()
                layout(pieza.width, pieza.height - px) { pieza.place(0, -px) }
            },
        shape = FormaHoja,
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            Modifier.navigationBarsPadding().padding(start = 20.dp, end = 20.dp, top = 11.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                Modifier.align(Alignment.CenterHorizontally).size(width = 42.dp, height = 4.dp)
                    .background(MaterialTheme.colorScheme.outline, CircleShape)
            )
            BotonPrincipal(
                texto = if (estado is UbicacionUi.Buscando) "Buscando…" else "Obtener mi ubicación",
                icono = Icons.Outlined.NearMe,
                habilitado = estado !is UbicacionUi.Buscando,
                onClick = onBuscar
            )
            // Deshabilitado hasta que haya un lugar con distancia; al pulsarlo se selecciona y la cámara va a él
            BotonSecundario(
                texto = "Ir al más cercano",
                icono = Icons.Outlined.Flag,
                habilitado = masCercano != null,
                onClick = { masCercano?.let { onSeleccionar(it.id) } }
            )
            when (val e = estado) {
                UbicacionUi.Inicial -> Column(
                    Modifier.fillMaxWidth().padding(vertical = 18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("Encuentra el lugar más cercano", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Activa tu ubicación para ordenar los sitios del campus por distancia.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
                UbicacionUi.Buscando -> {
                    Text(
                        "Buscando señal GPS…",
                        style = NumeroChico,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    // Todavía sin ubicación: la lista sale con "Sin calcular"
                    ListaLugares(lugares, seleccionado, masCercano?.id, onSeleccionar, Modifier.height(ALTO_LISTA))
                }
                is UbicacionUi.Lista -> {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Tu ubicación", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            String.format(Locale.US, "%.4f, %.4f", e.posicion.latitud, e.posicion.longitud),
                            style = NumeroChico
                        )
                    }
                    // "Más cercano: nombre (distancia)"
                    val cercano = lugares.firstOrNull { it.lugar.id == masCercano?.id }
                    if (cercano?.metros != null) {
                        Text(
                            "Más cercano: ${cercano.lugar.nombre} (${formatearDistancia(cercano.metros)})",
                            style = MaterialTheme.typography.labelMedium,
                            color = Exito
                        )
                    }
                    FiltroCategorias(categorias, categoria, onCategoria)
                    ListaLugares(lugares, seleccionado, masCercano?.id, onSeleccionar, Modifier.height(ALTO_LISTA))
                }
                is UbicacionUi.Error -> MensajeError(e, onReintentar = onBuscar)
            }
        }
    }
}

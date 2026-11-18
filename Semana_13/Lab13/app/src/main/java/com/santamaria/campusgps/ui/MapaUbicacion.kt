package com.santamaria.campusgps.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.santamaria.campusgps.R
import com.santamaria.campusgps.model.LugarConDistancia
import com.santamaria.campusgps.model.TipoMapa
import com.santamaria.campusgps.model.UbicacionUi
import com.santamaria.campusgps.ui.theme.Azul
import com.santamaria.campusgps.ui.theme.Exito

// Solo dibuja lo que recibe: no consulta ningún Repository
@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    masCercano: Int?,
    radio: Int?,
    onSeleccionar: (Int?) -> Unit,
    tipoMapa: TipoMapa,
    soloCampus: Boolean,
    ubicacionNativa: Boolean,
    modifier: Modifier = Modifier
) {
    // La cámara arranca en el centro de los lugares (el campus) y no en un punto fijo de Lima
    val camara = rememberCameraPositionState {
        val centro = if (lugares.isEmpty()) LatLng(-12.0464, -77.0428)
        else LatLng(lugares.map { it.lugar.latitud }.average(), lugares.map { it.lugar.longitud }.average())
        position = CameraPosition.fromLatLngZoom(centro, 17f)
    }

    // Cuando llega la ubicación, la cámara viaja hasta ella
    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            camara.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(estado.posicion.latitud, estado.posicion.longitud),
                    17f
                )
            )
        }
    }

    // Al elegir un lugar en la lista, la cámara se acerca a él (zoom 17)
    LaunchedEffect(seleccionado) {
        lugares.firstOrNull { it.lugar.id == seleccionado }?.let { item ->
            camara.animate(
                CameraUpdateFactory.newLatLngZoom(LatLng(item.lugar.latitud, item.lugar.longitud), 17f)
            )
        }
    }

    val contexto = LocalContext.current
    // "Solo campus": estilo que apaga los puntos de interés y el transporte de Google; si no, el mapa normal
    val estilo = remember(soloCampus) {
        if (soloCampus) MapStyleOptions.loadRawResourceStyle(contexto, R.raw.mapa_solo_campus) else null
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = camara,
        // Tocar un lugar vacío del mapa quita la selección
        onMapClick = { onSeleccionar(null) },
        properties = MapProperties(
            mapType = when (tipoMapa) {
                TipoMapa.NORMAL -> MapType.NORMAL
                TipoMapa.SATELITE -> MapType.SATELLITE
                TipoMapa.HIBRIDO -> MapType.HYBRID
            },
            mapStyleOptions = estilo,
            // Punto azul nativo: solo si el permiso ya fue concedido (sin permiso lanzaría SecurityException)
            isMyLocationEnabled = ubicacionNativa
        ),
        // Sin botones de zoom (el diseño no los lleva) y con margen para que la hoja no tape el logo de Google
        uiSettings = MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = ubicacionNativa),
        // Margen arriba para que el botón de mi ubicación no quede bajo la barra de estado, y abajo para el logo de Google
        contentPadding = PaddingValues(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(), bottom = 28.dp)
    ) {
        // Radio de cercanía alrededor de tu posición
        if (estado is UbicacionUi.Lista && radio != null) {
            Circle(
                center = LatLng(estado.posicion.latitud, estado.posicion.longitud),
                radius = radio.toDouble(),
                strokeColor = Azul,
                strokeWidth = 3f,
                fillColor = Azul.copy(alpha = 0.12f)
            )
        }
        // Tu posición sigue en rojo (marcador por defecto), como pide la guía
        if (estado is UbicacionUi.Lista) {
            Marker(
                state = rememberUpdatedMarkerState(
                    position = LatLng(estado.posicion.latitud, estado.posicion.longitud)
                ),
                title = "Estás aquí"
            )
        }
        // Un marcador por lugar con el ícono de su categoría: azul, y verde el más cercano. key(id) evita que se mezclen al reordenar.
        // Solo el lugar elegido (al tocar su marcador o su tarjeta) resalta: se agranda y sube por encima de los demás.
        lugares.forEach { item ->
            key(item.lugar.id) {
                val esCercano = item.lugar.id == masCercano
                val esElegido = item.lugar.id == seleccionado
                MarkerComposable(
                    item.lugar.id, item.lugar.categoria, esCercano, esElegido,
                    state = rememberUpdatedMarkerState(position = LatLng(item.lugar.latitud, item.lugar.longitud)),
                    title = item.lugar.nombre,
                    snippet = item.lugar.categoria,
                    anchor = Offset(0.5f, 0.5f),
                    zIndex = if (esElegido) 2f else 0f,
                    // Tocar el marcador lo elige; devolver false deja que Maps muestre además su nombre y categoría
                    onClick = {
                        onSeleccionar(item.lugar.id)
                        false
                    }
                ) {
                    Surface(
                        modifier = Modifier.size(if (esElegido) 58.dp else 40.dp),
                        shape = CircleShape,
                        color = if (esCercano) Exito else Azul,
                        border = BorderStroke(if (esElegido) 4.dp else 2.dp, Color.White),
                        shadowElevation = if (esElegido) 10.dp else 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                iconoDe(item.lugar.categoria),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(if (esElegido) 34.dp else 22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

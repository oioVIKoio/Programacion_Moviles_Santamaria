package com.santamaria.campusgps.ui

import androidx.compose.foundation.BorderStroke
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
    tipoMapa: TipoMapa,
    soloCampus: Boolean,
    ubicacionNativa: Boolean,
    modifier: Modifier = Modifier
) {
    // La cámara arranca en el centro de los lugares (el campus) y no en un punto fijo de Lima
    val camara = rememberCameraPositionState {
        val centro = if (lugares.isEmpty()) LatLng(-12.0464, -77.0428)
        else LatLng(lugares.map { it.lugar.latitud }.average(), lugares.map { it.lugar.longitud }.average())
        position = CameraPosition.fromLatLngZoom(centro, 16f)
    }

    // Cuando llega la ubicación, la cámara viaja hasta ella
    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            camara.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(estado.posicion.latitud, estado.posicion.longitud),
                    16f
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
        lugares.forEach { item ->
            key(item.lugar.id) {
                val esCercano = item.lugar.id == masCercano
                MarkerComposable(
                    item.lugar.id, item.lugar.categoria, esCercano,
                    state = rememberUpdatedMarkerState(position = LatLng(item.lugar.latitud, item.lugar.longitud)),
                    title = item.lugar.nombre,
                    snippet = item.lugar.categoria,
                    anchor = Offset(0.5f, 0.5f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (esCercano) Exito else Azul,
                        border = BorderStroke(2.dp, Color.White),
                        shadowElevation = 4.dp
                    ) {
                        Icon(
                            iconoDe(item.lugar.categoria),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp).size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

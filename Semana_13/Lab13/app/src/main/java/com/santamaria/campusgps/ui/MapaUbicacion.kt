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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay
import androidx.compose.ui.unit.Dp
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
import com.google.maps.android.compose.CameraMoveStartedReason
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
import com.santamaria.campusgps.ui.theme.Referencia

// Solo dibuja lo que recibe: no consulta ningún Repository
@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    masCercano: Int?,
    radio: Int?,
    onSeleccionar: (Int?) -> Unit,
    referencia: Int?,
    tipoMapa: TipoMapa,
    soloCampus: Boolean,
    ubicacionNativa: Boolean,
    siguiendo: Boolean = false,
    enfoque: Int = 0,
    recentrar: Int = 0,
    oscuro: Boolean = false,
    modoRumbo: Boolean = false,
    rumbo: Float? = null,
    modifier: Modifier = Modifier,
    // Alto que tapa la hoja inferior: el mapa se centra en la parte que queda a la vista
    relleno: Dp = 0.dp
) {
    // La cámara arranca en el centro de los lugares (el campus) y no en un punto fijo de Lima
    val camara = rememberCameraPositionState {
        val centro = if (lugares.isEmpty()) LatLng(-12.0464, -77.0428)
        else LatLng(lugares.map { it.lugar.latitud }.average(), lugares.map { it.lugar.longitud }.average())
        position = CameraPosition.fromLatLngZoom(centro, 17f)
    }

    // Cuando llega la ubicación, la cámara viaja hasta ella
    // La cámara te sigue mientras caminas (como en Google Maps). Deja de seguirte si tocas un lugar o mueves el mapa con el dedo,
    // y vuelve a seguirte al pulsar «Obtener mi ubicación» o el botón de mi ubicación del mapa.
    var camaraSigue by remember { mutableStateOf(true) }
    LaunchedEffect(recentrar) {
        camaraSigue = true
        (estado as? UbicacionUi.Lista)?.let { camara.animate(CameraUpdateFactory.newLatLng(LatLng(it.posicion.latitud, it.posicion.longitud))) }
    }
    LaunchedEffect(camara.isMoving) {
        if (camara.isMoving && camara.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE) camaraSigue = false
    }

    // Cuando llega la ubicación, la cámara viaja hasta ella; siguiendo en vivo solo se desplaza (respeta el zoom que elijas)
    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista && (!siguiendo || camaraSigue)) {
            val destino = LatLng(estado.posicion.latitud, estado.posicion.longitud)
            camara.animate(
                if (siguiendo) CameraUpdateFactory.newLatLng(destino) else CameraUpdateFactory.newLatLngZoom(destino, 17f)
            )
        }
    }

    // Al elegir un lugar en la lista, la cámara se acerca a él (zoom 17)
    // También se repite en cada toque (enfoque), no solo cuando cambia el lugar elegido
    LaunchedEffect(seleccionado, enfoque) {
        if (seleccionado != null) camaraSigue = false
        lugares.firstOrNull { it.lugar.id == seleccionado }?.let { item ->
            camara.animate(
                CameraUpdateFactory.newLatLngZoom(LatLng(item.lugar.latitud, item.lugar.longitud), 17f)
            )
        }
    }

    // Si la hoja sube o baja, el mapa vuelve a centrarse en lo que queda a la vista (el lugar elegido o tu posición).
    // La pausa deja terminar la animación de la hoja y solo se actúa con el último valor.
    LaunchedEffect(relleno) {
        delay(250)
        val lugar = lugares.firstOrNull { it.lugar.id == seleccionado }
        val tuPosicion = (estado as? UbicacionUi.Lista)?.let { LatLng(it.posicion.latitud, it.posicion.longitud) }
        // Si la cámara te sigue, el destino eres tú; si no, el lugar elegido; si moviste el mapa a mano sin elegir nada, no se toca
        val destino = when {
            camaraSigue -> tuPosicion
            lugar != null -> LatLng(lugar.lugar.latitud, lugar.lugar.longitud)
            else -> null
        }
        destino?.let { camara.animate(CameraUpdateFactory.newLatLng(it)) }
    }

    val contexto = LocalContext.current
    // "Solo campus": estilo que apaga los puntos de interés y el transporte de Google; si no, el mapa normal
    // Estilo del mapa: normal, solo campus, oscuro, u oscuro con solo campus
    val estilo = remember(soloCampus, oscuro) {
        val recurso = when {
            oscuro && soloCampus -> R.raw.mapa_oscuro_solo_campus
            oscuro -> R.raw.mapa_oscuro
            soloCampus -> R.raw.mapa_solo_campus
            else -> null
        }
        recurso?.let { MapStyleOptions.loadRawResourceStyle(contexto, it) }
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
            // El punto azul es propio (PuntoUsuario), así que se apaga el nativo
            isMyLocationEnabled = false
        ),
        // Sin botones de zoom (el diseño no los lleva) y con margen para que la hoja no tape el logo de Google
        uiSettings = MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = false),
        // Margen arriba: deja libre la fila del chip y los botones (así la brújula del mapa queda debajo y no tapada), y abajo el alto que tapa la hoja (así el mapa se centra en lo que se ve y el logo de Google queda a la vista)
        contentPadding = PaddingValues(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 76.dp, bottom = relleno)
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
        // Tu posición: punto azul con flecha hacia donde miras (y abanico con «Mi rumbo»). Es plano: gira junto con el mapa.
        if (estado is UbicacionUi.Lista) {
            MarkerComposable(
                modoRumbo, rumbo != null,
                state = rememberUpdatedMarkerState(position = LatLng(estado.posicion.latitud, estado.posicion.longitud)),
                anchor = Offset(0.5f, 0.5f),
                flat = true,
                rotation = rumbo ?: 0f,
                zIndex = 3f
            ) {
                PuntoUsuario(conAbanico = modoRumbo && rumbo != null, conFlecha = rumbo != null)
            }
        }
        // Un marcador por lugar con el ícono de su categoría: azul, y verde el más cercano. key(id) evita que se mezclen al reordenar.
        // Solo el lugar elegido (al tocar su marcador o su tarjeta) resalta: se agranda y sube por encima de los demás.
        lugares.forEach { item ->
            key(item.lugar.id) {
                val esCercano = item.lugar.id == masCercano
                val esElegido = item.lugar.id == seleccionado
                val esReferencia = item.lugar.id == referencia
                MarkerComposable(
                    item.lugar.id, item.lugar.categoria, esCercano, esElegido, esReferencia,
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
                        // Tu referencia guardada lleva el borde ámbar; el resto, blanco
                        border = BorderStroke(if (esElegido) 4.dp else if (esReferencia) 3.dp else 2.dp, if (esReferencia) Referencia else Color.White),
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

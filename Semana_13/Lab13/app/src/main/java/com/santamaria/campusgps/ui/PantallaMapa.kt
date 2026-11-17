package com.santamaria.campusgps.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.santamaria.campusgps.model.UbicacionUi
import com.santamaria.campusgps.ui.components.BotonPrincipal
import com.santamaria.campusgps.ui.components.CajaMensaje
import com.santamaria.campusgps.ui.components.TipoMensaje
import com.santamaria.campusgps.ui.theme.FormaBoton
import com.santamaria.campusgps.ui.theme.FormaHoja
import com.santamaria.campusgps.ui.theme.NumeroChico
import com.santamaria.campusgps.viewmodel.MapaViewModel
import java.util.Locale

// Ejemplo resuelto: pedir la ubicación y mostrar latitud y longitud, ya con el diseño.
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        // Chip de marca flotante, como en el diseño (aquí todavía sin mapa debajo)
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

        // Hoja inferior
        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
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
                    onClick = { vm.buscar() }
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
                    UbicacionUi.Buscando -> Text(
                        "Buscando señal GPS…",
                        style = NumeroChico,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    is UbicacionUi.Lista -> Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Tu ubicación", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            String.format(Locale.US, "%.4f, %.4f", e.posicion.latitud, e.posicion.longitud),
                            style = NumeroChico
                        )
                    }
                    is UbicacionUi.Error -> CajaMensaje(
                        tipo = TipoMensaje.ERROR,
                        titulo = "No pudimos ubicarte",
                        texto = e.mensaje
                    )
                }
            }
        }
    }
}

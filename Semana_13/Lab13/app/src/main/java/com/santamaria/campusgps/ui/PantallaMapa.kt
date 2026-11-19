package com.santamaria.campusgps.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.compose.foundation.background
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Spacer
import com.santamaria.campusgps.model.TipoMapa
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.santamaria.campusgps.model.Lugar
import com.santamaria.campusgps.model.LugarConDistancia
import com.santamaria.campusgps.model.UbicacionUi
import com.santamaria.campusgps.ui.components.BotonPrincipal
import com.santamaria.campusgps.ui.components.BotonSecundario
import com.santamaria.campusgps.ui.theme.FormaBoton
import com.santamaria.campusgps.ui.theme.FormaHoja
import com.santamaria.campusgps.ui.theme.NumeroChico
import com.santamaria.campusgps.ui.theme.ReferenciaTexto
import com.santamaria.campusgps.util.formatearDistancia
import com.santamaria.campusgps.viewmodel.MapaViewModel

// Alto de la hoja bajada: asa (24) + botón (52) + separación (10) + botón (52) + respiro (2)
private val ALTO_BOTONES = 140.dp

// Ejemplo resuelto: pedir la ubicación y mostrar latitud y longitud, ya con el diseño.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val estado by vm.estado.collectAsState()
    val lugares by vm.lugares.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val masCercano by vm.masCercano.collectAsState()
    val categoria by vm.categoria.collectAsState()
    val radio by vm.radio.collectAsState()
    val referencia by vm.referencia.collectAsState()
    val ocultos by vm.ocultos.collectAsState()
    val siguiendo by vm.siguiendo.collectAsState()
    val dentro by vm.dentroDelRadio.collectAsState()
    val tipoMapa by vm.tipoMapa.collectAsState()
    val soloCampus by vm.soloCampus.collectAsState()
    var capasAbierto by remember { mutableStateOf(false) }
    val contexto = LocalContext.current

    // Al salir de la app (pantalla apagada o cambio de app) se detiene el seguimiento para no gastar batería, y al volver se retoma
    val ciclo = (contexto as? androidx.activity.ComponentActivity)?.lifecycle
    DisposableEffect(ciclo) {
        val observador = androidx.lifecycle.LifecycleEventObserver { _, evento ->
            when (evento) {
                androidx.lifecycle.Lifecycle.Event.ON_STOP -> vm.detenerSeguimiento()
                androidx.lifecycle.Lifecycle.Event.ON_START -> vm.alVolver()
                else -> Unit
            }
        }
        ciclo?.addObserver(observador)
        onDispose { ciclo?.removeObserver(observador) }
    }

    // Punto azul nativo del mapa: solo si el permiso ya está concedido (se recalcula cuando cambia el estado tras pedirlo)
    val permisoConcedido = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ).any { ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED }

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

    // Hoja arrastrable: abajo solo se ven los dos botones; al subirla aparecen los filtros y la lista
    val estadoHoja = rememberBottomSheetScaffoldState(
        rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded, skipHiddenState = true)
    )
    val navegacion = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val densidad = LocalDensity.current
    var altoContenido by remember { mutableStateOf(0.dp) }
    val hojaArriba = estadoHoja.bottomSheetState.targetValue == SheetValue.Expanded
    // El mapa se centra en lo que no tapa la hoja; con la hoja bajada, el espacio de la barra de navegación queda vacío
    val relleno by animateDpAsState(if (hojaArriba) altoContenido + 24.dp else ALTO_BOTONES + navegacion, label = "relleno")
    val hueco by animateDpAsState(if (hojaArriba) 0.dp else navegacion, label = "hueco")
    // Cuando llega la ubicación (o un error) la hoja sube sola para mostrar el resultado
    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista || estado is UbicacionUi.Error) estadoHoja.bottomSheetState.expand()
    }

    BottomSheetScaffold(
        scaffoldState = estadoHoja,
        sheetPeekHeight = ALTO_BOTONES + navegacion,
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetShape = FormaHoja,
        sheetDragHandle = {
            Box(
                Modifier.padding(vertical = 10.dp).size(width = 42.dp, height = 4.dp)
                    .background(MaterialTheme.colorScheme.outline, CircleShape)
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            HojaInferior(

                estado = estado,
                lugares = lugares,
                seleccionado = seleccionado,
                masCercano = masCercano,
                todos = vm.todosLosLugares,
                ocultos = ocultos,
                onAlternarLugar = vm::alternarLugar,
                onMostrarTodos = vm::mostrarTodosLosLugares,
                categorias = vm.categorias,
                categoria = categoria,
                onCategoria = vm::filtrarCategoria,
                radios = vm.radios,
                radio = radio,
                onRadio = vm::elegirRadio,
                dentro = dentro,
                referencia = referencia,
                onSeleccionar = vm::seleccionar,
                onBuscar = { alPulsar() },
                siguiendo = siguiendo,
                onSeguimiento = vm::alternarSeguimiento,
                hueco = hueco,
                modifier = Modifier.onSizeChanged { altoContenido = with(densidad) { it.height.toDp() } }
            )
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            MapaUbicacion(

                estado = estado,
                lugares = lugares,
                seleccionado = seleccionado,
                masCercano = masCercano?.id,
                radio = radio,
                onSeleccionar = vm::seleccionar,
                referencia = referencia?.lugar?.id,
                tipoMapa = tipoMapa,
                soloCampus = soloCampus,
                ubicacionNativa = permisoConcedido,
                siguiendo = siguiendo,
                modifier = Modifier.fillMaxSize(),
                relleno = relleno
            )

        // Arriba a la izquierda: chip de marca y el botón de capas del mapa
        Row(
            Modifier.statusBarsPadding().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
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
            Box {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    IconButton(onClick = { capasAbierto = true }) {
                        Icon(Icons.Outlined.Layers, contentDescription = "Capas del mapa", tint = MaterialTheme.colorScheme.primary)
                    }
                }
                // Visor del mapa y vista "Solo lugares del campus" (apaga los puntos de Google)
                DropdownMenu(expanded = capasAbierto, onDismissRequest = { capasAbierto = false }) {
                    TipoMapa.entries.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo.etiqueta) },
                            leadingIcon = { if (tipo == tipoMapa) Icon(Icons.Outlined.Check, null) else Spacer(Modifier.size(24.dp)) },
                            onClick = { vm.cambiarTipoMapa(tipo); capasAbierto = false }
                        )
                    }
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Solo lugares del campus") },
                        leadingIcon = { if (soloCampus) Icon(Icons.Outlined.Check, null) else Spacer(Modifier.size(24.dp)) },
                        onClick = { vm.alternarSoloCampus(); capasAbierto = false }
                    )
                }
            }
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
    todos: List<Lugar>,
    ocultos: Set<Int>,
    onAlternarLugar: (Int) -> Unit,
    onMostrarTodos: () -> Unit,
    categorias: List<String>,
    categoria: String?,
    onCategoria: (String?) -> Unit,
    radios: List<Int>,
    radio: Int?,
    onRadio: (Int?) -> Unit,
    dentro: Set<Int>,
    referencia: LugarConDistancia?,
    onSeleccionar: (Int) -> Unit,
    onBuscar: () -> Unit,
    siguiendo: Boolean,
    onSeguimiento: () -> Unit,
    hueco: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        // La hoja nunca pasa del 58 % de la pantalla (así el mapa siempre se ve). Los botones quedan arriba y solo la lista se desplaza
        modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .heightIn(max = (LocalConfiguration.current.screenHeightDp * 0.58f).dp)
            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
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
            // Con la hoja bajada empuja el resto fuera de la vista (queda tapado por la barra de navegación)
            Spacer(Modifier.height(hueco))
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
                    // Tu referencia: el último lugar que tocaste, guardado aunque cierres la app
                    referencia?.let { ref ->
                        Text(
                            "Tu referencia: ${ref.lugar.nombre}",
                            style = MaterialTheme.typography.labelMedium,
                            color = ReferenciaTexto
                        )
                    }
                }
                UbicacionUi.Buscando -> {
                    Text(
                        "Buscando señal GPS…",
                        style = NumeroChico,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    // Todavía sin ubicación: la lista sale con "Sin calcular"
                    ListaLugares(lugares, seleccionado, masCercano?.id, onSeleccionar, Modifier.weight(1f, fill = false), referencia = referencia?.lugar?.id)
                }
                is UbicacionUi.Lista -> {
                    ListaLugares(
                        lugares, seleccionado, masCercano?.id, onSeleccionar,
                        Modifier.weight(1f, fill = false), dentro, referencia?.lugar?.id,
                        conRadio = radio != null,
                        cabecera = {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text("Seguimiento en vivo", style = MaterialTheme.typography.titleMedium)
                                        Text(
                                            if (siguiendo) "Actualizando mientras caminas" else "En pausa",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Switch(
                                        checked = siguiendo,
                                        onCheckedChange = { onSeguimiento() },
                                        colors = SwitchDefaults.colors(
                                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                                            checkedThumbColor = Color.White,
                                            uncheckedTrackColor = MaterialTheme.colorScheme.surface,
                                            uncheckedBorderColor = MaterialTheme.colorScheme.outline,
                                            uncheckedThumbColor = MaterialTheme.colorScheme.outline
                                        )
                                    )
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    SelectorLugares(todos, ocultos, onAlternarLugar, onMostrarTodos)
                                    FiltroCategorias(categorias, categoria, onCategoria, Modifier.weight(1f))
                                }
                                FiltroRadio(radios, radio, onRadio)
                                // Resumen del radio: cuántos lugares caen dentro (los de afuera se ven atenuados en la lista)
                                if (radio != null) {
                                    Text(
                                        when (dentro.size) {
                                            0 -> "Ningún lugar dentro de $radio m"
                                            1 -> "1 lugar dentro de $radio m"
                                            else -> "${dentro.size} lugares dentro de $radio m"
                                        },
                                        style = MaterialTheme.typography.labelMedium,
                                        color = if (dentro.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant else com.santamaria.campusgps.ui.theme.Exito
                                    )
                                }
                                if (lugares.isEmpty()) {
                                    Text(
                                        "No hay lugares visibles. Márcalos en «Lugares».",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    )
                }
                is UbicacionUi.Error -> MensajeError(e, onReintentar = onBuscar)
            }
    }
}

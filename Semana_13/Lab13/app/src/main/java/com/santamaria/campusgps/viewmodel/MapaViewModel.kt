package com.santamaria.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santamaria.campusgps.data.GpsApagadoException
import com.santamaria.campusgps.data.LugaresRepository
import com.santamaria.campusgps.data.ReferenciaRepository
import com.santamaria.campusgps.data.UbicacionRepository
import com.santamaria.campusgps.model.Lugar
import com.santamaria.campusgps.model.LugarConDistancia
import com.santamaria.campusgps.model.Posicion
import com.santamaria.campusgps.model.TipoError
import com.santamaria.campusgps.model.TipoMapa
import com.santamaria.campusgps.model.UbicacionUi
import com.santamaria.campusgps.util.distanciaMetros
import com.santamaria.campusgps.util.rumboHacia
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val DISTANCIA_LLEGADA = 30f

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository,
    private val referenciaRepo: ReferenciaRepository
) : ViewModel() {

    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado: StateFlow<UbicacionUi> = _estado.asStateFlow()

    // _lugares guarda todos los lugares con su distancia; lugares es lo que se ve (filtrado)
    private val _lugares = MutableStateFlow(
        lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) }
    )

    // Mejora 1: filtro por categoría (null = todas)
    val categorias: List<String> = lugaresRepo.obtenerLugares().map { it.categoria }.distinct()
    private val _categoria = MutableStateFlow<String?>(null)
    val categoria: StateFlow<String?> = _categoria.asStateFlow()

    // Mejora 2: radio de cercanía en metros (null = sin radio). No oculta nada: solo marca los lugares que caen dentro.
    val radios: List<Int> = listOf(100, 200, 500)
    private val _radio = MutableStateFlow<Int?>(null)
    val radio: StateFlow<Int?> = _radio.asStateFlow()

    // Mejora 5: elegir cuáles lugares se ven. Los ocultos salen del mapa, de la lista y del cálculo del más cercano.
    val todosLosLugares: List<Lugar> = lugaresRepo.obtenerLugares()
    private val _ocultos = MutableStateFlow<Set<Int>>(emptySet())
    val ocultos: StateFlow<Set<Int>> = _ocultos.asStateFlow()

    val lugares: StateFlow<List<LugarConDistancia>> = combine(_lugares, _categoria, _ocultos) { todos, cat, ocultos ->
        todos.filter { (cat == null || it.lugar.categoria == cat) && it.lugar.id !in ocultos }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _lugares.value)

    // Ids de los lugares visibles que están dentro del radio elegido (vacío si no hay radio o aún no hay distancias)
    val dentroDelRadio: StateFlow<Set<Int>> = combine(lugares, _radio) { lista, radio ->
        if (radio == null) emptySet()
        else lista.filter { it.metros != null && it.metros <= radio }.map { it.lugar.id }.toSet()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    // Mejora 3: visor del mapa. "Solo campus" oculta los puntos de interés y el transporte de Google (apagado por defecto)
    private val _tipoMapa = MutableStateFlow(TipoMapa.NORMAL)
    val tipoMapa: StateFlow<TipoMapa> = _tipoMapa.asStateFlow()
    private val _soloCampus = MutableStateFlow(false)
    val soloCampus: StateFlow<Boolean> = _soloCampus.asStateFlow()

    fun cambiarTipoMapa(tipo: TipoMapa) {
        _tipoMapa.value = tipo
    }

    fun alternarSoloCampus() {
        _soloCampus.value = !_soloCampus.value
    }

    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado: StateFlow<Int?> = _seleccionado.asStateFlow()

    // Cada vez que pulsas «Obtener mi ubicación» suma 1: el mapa vuelve a seguirte
    private val _recentrar = MutableStateFlow(0)
    val recentrar: StateFlow<Int> = _recentrar.asStateFlow()

    fun buscar() {
        // Pedir tu ubicación te lleva a ti: se quita el lugar elegido para que la cámara no tenga dos destinos
        _seleccionado.value = null
        _recentrar.value += 1
        if (!ubicacionRepo.playServicesDisponible()) {
            _estado.value = UbicacionUi.Error("Este teléfono no tiene Google Play Services", TipoError.SIN_PLAY_SERVICES)
            return
        }
        _estado.value = UbicacionUi.Buscando
        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess {
                    _estado.value = UbicacionUi.Lista(it)
                    actualizarDistancias(it)
                    // Ya hay ubicación y permiso: el seguimiento arranca solo
                    if (quiereSeguir && !_siguiendo.value) iniciarSeguimiento()
                }
                .onFailure { _estado.value = errorDe(it) }
        }
    }

    // Mejora 7: modo oscuro (cambia el tema de la app y el estilo del mapa)
    private val _oscuro = MutableStateFlow(false)
    val oscuro: StateFlow<Boolean> = _oscuro.asStateFlow()

    fun alternarOscuro() {
        _oscuro.value = !_oscuro.value
    }

    // Mejora 8: "Mi rumbo". El mapa gira hacia donde apunta el celular, como un GPS normal.
    private val _modoRumbo = MutableStateFlow(false)
    val modoRumbo: StateFlow<Boolean> = _modoRumbo.asStateFlow()
    private val _rumbo = MutableStateFlow<Float?>(null)
    val rumbo: StateFlow<Float?> = _rumbo.asStateFlow()
    private var trabajoRumbo: Job? = null

    // El sensor de orientación va con el seguimiento (la flecha siempre apunta); «Mi rumbo» solo decide si el mapa gira
    fun alternarRumbo() {
        _modoRumbo.value = !_modoRumbo.value
    }

    // Vuelve a centrar la cámara en ti (botón de mi ubicación)
    fun recentrarCamara() {
        _seleccionado.value = null
        _recentrar.value += 1
    }

    private fun iniciarRumbo() {
        trabajoRumbo?.cancel()
        trabajoRumbo = viewModelScope.launch {
            ubicacionRepo.seguirRumbo()
                .catch { _modoRumbo.value = false }
                .collect { _rumbo.value = it }
        }
    }

    // Mejora 6: seguimiento en vivo. Solo se puede encender cuando ya hay una ubicación (el permiso ya fue concedido).
    private val _siguiendo = MutableStateFlow(false)
    val siguiendo: StateFlow<Boolean> = _siguiendo.asStateFlow()
    private var trabajoSeguimiento: Job? = null

    // Mientras usas la app el seguimiento está encendido; solo se apaga si lo pausas tú o al salir de la app
    private var quiereSeguir = true

    fun alternarSeguimiento() {
        if (_siguiendo.value) {
            quiereSeguir = false
            detenerSeguimiento()
        } else {
            quiereSeguir = true
            iniciarSeguimiento()
        }
    }

    // Al volver a la app se retoma el seguimiento si ya te habías ubicado y no lo pausaste
    fun alVolver() {
        if (_estado.value is UbicacionUi.Lista && quiereSeguir && !_siguiendo.value) iniciarSeguimiento()
    }

    private fun iniciarSeguimiento() {
        trabajoSeguimiento?.cancel()
        _siguiendo.value = true
        iniciarRumbo()
        trabajoSeguimiento = viewModelScope.launch {
            ubicacionRepo.seguirPosicion()
                .catch {
                    _siguiendo.value = false
                    _estado.value = errorDe(it)
                }
                .collect { posicion ->
                    _estado.value = UbicacionUi.Lista(posicion)
                    actualizarDistancias(posicion)
                }
        }
    }

    // Se llama al salir de la app para no gastar batería (no cambia lo que prefiere el usuario)
    fun detenerSeguimiento() {
        trabajoSeguimiento?.cancel()
        trabajoSeguimiento = null
        _siguiendo.value = false
        // El sensor de orientación también se apaga (y se retoma con el seguimiento)
        trabajoRumbo?.cancel()
        trabajoRumbo = null
        _rumbo.value = null
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error("Sin permiso de ubicación no puedo mostrar dónde estás", TipoError.SIN_PERMISO)
    }

    // Tocar un lugar lo selecciona y lo deja guardado como tu referencia (no se pierde al cerrar la app)
    // Cada toque suma 1 a "enfoque": así la cámara viaja al lugar aunque ya estuviera elegido
    private val _enfoque = MutableStateFlow(0)
    val enfoque: StateFlow<Int> = _enfoque.asStateFlow()

    fun seleccionar(id: Int?) {
        _seleccionado.value = id
        if (id != null) {
            _enfoque.value += 1
            viewModelScope.launch { referenciaRepo.guardar(id) }
        }
    }

    // Marca o desmarca un lugar; si era el elegido, se quita la selección porque deja de verse
    fun alternarLugar(id: Int) {
        val oculto = id in _ocultos.value
        _ocultos.value = if (oculto) _ocultos.value - id else _ocultos.value + id
        if (!oculto && _seleccionado.value == id) _seleccionado.value = null
    }

    fun mostrarTodosLosLugares() {
        _ocultos.value = emptySet()
    }

    fun elegirRadio(metros: Int?) {
        _radio.value = metros
    }

    // Al cambiar de categoría se quita la selección: el lugar elegido puede haber quedado fuera de la lista
    fun filtrarCategoria(categoria: String?) {
        _categoria.value = categoria
        _seleccionado.value = null
    }

    // El más cercano se deriva de la lista visible (ya ordenada): el primero que ya tiene distancia
    val masCercano: StateFlow<Lugar?> = lugares
        .map { lista -> lista.firstOrNull { it.metros != null }?.lugar }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)


    // Mejora 4: la referencia guardada, con su distancia actual. Sale de todos los lugares, así un filtro no la esconde.
    val referencia: StateFlow<LugarConDistancia?> = combine(_lugares, referenciaRepo.ultimoLugarId) { todos, id ->
        todos.firstOrNull { it.lugar.id == id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Mejora 9: "Llegaste". Si estás a menos de 30 m de tu referencia, la pantalla lo avisa.
    val llegaste: StateFlow<Lugar?> = referencia
        .map { ref -> if (ref?.metros != null && ref.metros <= DISTANCIA_LLEGADA) ref.lugar else null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Se calcula solo cuando llega la ubicación (dentro de buscar) y deja la lista ordenada de menor a mayor
    private fun actualizarDistancias(posicion: Posicion) {
        _lugares.value = lugaresRepo.obtenerLugares()
            .map { LugarConDistancia(it, distanciaMetros(posicion, it), rumboHacia(posicion, it)) }
            .sortedBy { it.metros }
    }

    private fun errorDe(e: Throwable): UbicacionUi.Error = when (e) {
        is SecurityException -> UbicacionUi.Error("Falta el permiso de ubicación", TipoError.SIN_PERMISO)
        is GpsApagadoException -> UbicacionUi.Error("Activa el GPS del teléfono para ubicarte", TipoError.GPS_APAGADO)
        else -> UbicacionUi.Error(e.message ?: "No se pudo obtener la ubicación", TipoError.OTRO)
    }
}

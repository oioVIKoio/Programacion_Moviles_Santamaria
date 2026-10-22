package tecsup.santamaria.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tecsup.santamaria.lavacampus.data.local.entity.Servicio
import tecsup.santamaria.lavacampus.data.repository.LavaRepository
import tecsup.santamaria.lavacampus.model.LineaPedido
import tecsup.santamaria.lavacampus.model.Unidad
import java.time.DayOfWeek
import java.time.LocalDate

class NuevoPedidoViewModel(
    private val repo: LavaRepository,
    private val usuarioId: Int,
    recibiendo: Flow<Boolean>,
    repetirId: Int = 0  // > 0: carga las líneas de ese pedido
) : ViewModel() {

    // Ajustes del admin: si está apagado, no se pueden crear pedidos
    val recibePedidos: StateFlow<Boolean> = recibiendo
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    // Solo los activos se pueden pedir
    val servicios: StateFlow<List<Servicio>> = repo.observarServiciosActivos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // servicioId -> cantidad (0 = apagado)
    private val _cantidades = MutableStateFlow<Map<Int, Double>>(emptyMap())
    val cantidades: StateFlow<Map<Int, Double>> = _cantidades.asStateFlow()

    private val _nota = MutableStateFlow("")
    val nota: StateFlow<String> = _nota.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // true cuando se guardó: la pantalla vuelve a Mis pedidos
    private val _creado = MutableStateFlow(false)
    val creado: StateFlow<Boolean> = _creado.asStateFlow()

    // Se recalcula en cada cambio: suma de cantidad × precio
    val subtotal: StateFlow<Double> = combine(servicios, _cantidades) { lista, cant ->
        lista.sumOf { s -> (cant[s.id] ?: 0.0) * s.precio }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Kilos de lavado: solo los servicios que se cobran por kg
    private val kilos: StateFlow<Double> = combine(servicios, _cantidades) { lista, cant ->
        lista.filter { it.unidad == Unidad.KG.name }.sumOf { cant[it.id] ?: 0.0 }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Descuento por volumen: 10 % si el lavado supera 5 kg
    val descuento: StateFlow<Double> = combine(subtotal, kilos) { sub, kg ->
        if (kg > KILOS_DESCUENTO) Math.round(sub * PORCENTAJE_DESCUENTO * 100) / 100.0 else 0.0
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val total: StateFlow<Double> = combine(subtotal, descuento) { sub, desc -> sub - desc }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Hoy + el mayor horasEntrega de lo elegido; null si no hay nada elegido
    val fechaEntrega: StateFlow<String?> = combine(servicios, _cantidades) { lista, cant ->
        val horas = lista.filter { (cant[it.id] ?: 0.0) > 0 }.maxOfOrNull { it.horasEntrega }
        horas?.let { calcularEntrega(it).toString() }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        if (repetirId > 0) cargarPedido(repetirId)
    }

    // Pedir de nuevo: mismas líneas y cantidades, con los precios actuales de cada servicio
    private fun cargarPedido(id: Int) {
        viewModelScope.launch {
            val anterior = repo.observarPedido(id).first() ?: return@launch
            val activos = repo.observarServiciosActivos().first().map { it.id }.toSet()
            val (disponibles, retirados) = anterior.detalles.partition { it.servicio.id in activos }
            _cantidades.value = disponibles.associate { it.servicio.id to it.detalle.cantidad }
            if (retirados.isNotEmpty()) {
                _error.value = "Ya no está disponible: " + retirados.joinToString { it.servicio.nombre }
            }
        }
    }

    fun cambiarCantidad(servicioId: Int, delta: Double) {
        val servicio = servicios.value.firstOrNull { it.id == servicioId } ?: return
        val unidad = Unidad.valueOf(servicio.unidad)
        val actual = _cantidades.value[servicioId] ?: 0.0
        val nueva = when {
            delta > 0 && actual == 0.0 -> unidad.minimo                  // al activar: 1.0 kg o 1 prenda
            delta < 0 && actual <= unidad.minimo -> 0.0                  // por debajo del mínimo se apaga
            else -> (actual + delta).coerceIn(unidad.minimo, unidad.maximo)
        }
        _cantidades.value = _cantidades.value + (servicioId to nueva)
        _error.value = null
    }

    fun cambiarNota(texto: String) {
        _nota.value = texto
    }

    fun confirmar() {
        val lineas = _cantidades.value.filter { it.value > 0 }.map { LineaPedido(it.key, it.value) }
        val fecha = fechaEntrega.value
        if (lineas.isEmpty() || fecha == null) return
        if (!recibePedidos.value) {
            _error.value = "La lavandería no está recibiendo pedidos"
            return
        }
        viewModelScope.launch {
            if (repo.contarActivos(usuarioId) >= LIMITE_ACTIVOS) {
                _error.value = "Ya tienes 3 pedidos activos"
                return@launch
            }
            val porcentaje = if (kilos.value > KILOS_DESCUENTO) PORCENTAJE_DESCUENTO else 0.0
            repo.crearPedido(usuarioId, lineas, fecha, _nota.value.trim(), porcentaje)
            _creado.value = true
        }
    }

    // La lavandería no atiende los domingos
    private fun calcularEntrega(horas: Int): LocalDate {
        var f = LocalDate.now().plusDays((horas / 24).toLong())
        if (f.dayOfWeek == DayOfWeek.SUNDAY) f = f.plusDays(1)
        return f
    }

    companion object {
        const val LIMITE_ACTIVOS = 3
        const val KILOS_DESCUENTO = 5.0
        const val PORCENTAJE_DESCUENTO = 0.10
    }
}

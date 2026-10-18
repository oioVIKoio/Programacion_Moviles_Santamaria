package tecsup.santamaria.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
    private val usuarioId: Int
) : ViewModel() {

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
    val total: StateFlow<Double> = combine(servicios, _cantidades) { lista, cant ->
        lista.sumOf { s -> (cant[s.id] ?: 0.0) * s.precio }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Hoy + el mayor horasEntrega de lo elegido; null si no hay nada elegido
    val fechaEntrega: StateFlow<String?> = combine(servicios, _cantidades) { lista, cant ->
        val horas = lista.filter { (cant[it.id] ?: 0.0) > 0 }.maxOfOrNull { it.horasEntrega }
        horas?.let { calcularEntrega(it).toString() }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

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
        viewModelScope.launch {
            if (repo.contarActivos(usuarioId) >= LIMITE_ACTIVOS) {
                _error.value = "Ya tienes 3 pedidos activos"
                return@launch
            }
            repo.crearPedido(usuarioId, lineas, fecha, _nota.value.trim())
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
    }
}

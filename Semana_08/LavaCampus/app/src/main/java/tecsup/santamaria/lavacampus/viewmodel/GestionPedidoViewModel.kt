package tecsup.santamaria.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tecsup.santamaria.lavacampus.data.local.relation.PedidoConDetalle
import tecsup.santamaria.lavacampus.data.repository.LavaRepository
import tecsup.santamaria.lavacampus.model.EstadoPedido

class GestionPedidoViewModel(
    private val repo: LavaRepository,
    private val pedidoId: Int
) : ViewModel() {

    val pedido: StateFlow<PedidoConDetalle?> = repo.observarPedido(pedidoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Ocupados por otros pedidos LISTO: el casillero del propio pedido no cuenta
    val casillerosOcupados: StateFlow<List<String>> = combine(repo.observarCasillerosOcupados(), pedido) { ocupados, p ->
        ocupados - setOfNotNull(p?.pedido?.casillero)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _casillero = MutableStateFlow<String?>(null)
    val casilleroElegido: StateFlow<String?> = _casillero.asStateFlow()

    // Motivo cuando una regla no deja avanzar o cancelar
    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    init {
        // Si ya tiene casillero (LISTO), aparece elegido
        viewModelScope.launch {
            _casillero.value = pedido.filterNotNull().first().pedido.casillero
        }
    }

    fun elegirCasillero(c: String) {
        if (c in casillerosOcupados.value) return
        _casillero.value = c
        _mensaje.value = null
    }

    fun marcarPagado(b: Boolean) {
        viewModelScope.launch { repo.marcarPagado(pedidoId, b) }
        _mensaje.value = null
    }

    // Solo al estado siguiente, con las reglas de casillero y cobro
    fun avanzar() {
        val p = pedido.value?.pedido ?: return
        val actual = EstadoPedido.valueOf(p.estado)
        val siguiente = actual.siguiente() ?: return
        val motivo = motivoParaNoAvanzar(siguiente, _casillero.value, casillerosOcupados.value, p.pagado)
        if (motivo != null) {
            _mensaje.value = motivo
            return
        }
        val casillero = if (siguiente == EstadoPedido.LISTO) _casillero.value else p.casillero
        viewModelScope.launch { repo.cambiarEstado(pedidoId, siguiente, casillero) }
        _mensaje.value = null
    }

    // Solo desde RECIBIDO o EN_PROCESO
    fun cancelar() {
        val p = pedido.value?.pedido ?: return
        if (!puedeCancelar(EstadoPedido.valueOf(p.estado))) {
            _mensaje.value = "Solo se cancela un pedido Recibido o En proceso"
            return
        }
        viewModelScope.launch { repo.cambiarEstado(pedidoId, EstadoPedido.CANCELADO, null) }
        _mensaje.value = null
    }

    companion object {
        fun puedeCancelar(estado: EstadoPedido) =
            estado == EstadoPedido.RECIBIDO || estado == EstadoPedido.EN_PROCESO

        // Reglas de avance (también las usa la bandeja). null = se puede
        fun motivoParaNoAvanzar(
            siguiente: EstadoPedido,
            casillero: String?,
            ocupados: List<String>,
            pagado: Boolean
        ): String? = when {
            siguiente == EstadoPedido.LISTO && casillero == null -> "Elige un casillero para pasar a Listo"
            siguiente == EstadoPedido.LISTO && casillero in ocupados -> "El casillero $casillero ya está ocupado"
            siguiente == EstadoPedido.ENTREGADO && !pagado -> "No se entrega un pedido sin cobrar: marca Cobrado"
            else -> null
        }
    }
}

package tecsup.santamaria.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tecsup.santamaria.lavacampus.data.local.relation.PedidoConDetalle
import tecsup.santamaria.lavacampus.data.repository.LavaRepository
import tecsup.santamaria.lavacampus.model.EstadoPedido

class MisPedidosViewModel(
    private val repo: LavaRepository,
    usuarioId: Int
) : ViewModel() {

    private val pedidos = repo.observarPedidosDeUsuario(usuarioId)

    // Activos = RECIBIDO, EN_PROCESO y LISTO
    val activos: StateFlow<List<PedidoConDetalle>> = pedidos
        .map { lista -> lista.filter { estado(it).esActivo } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Historial = ENTREGADO y CANCELADO
    val historial: StateFlow<List<PedidoConDetalle>> = pedidos
        .map { lista -> lista.filter { estado(it).esFinal } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Para el aviso: el pedido que ya se puede recoger
    val listo: StateFlow<PedidoConDetalle?> = activos
        .map { lista -> lista.firstOrNull { estado(it) == EstadoPedido.LISTO } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun puedeCancelar(p: PedidoConDetalle) = estado(p) == EstadoPedido.RECIBIDO

    // Misma regla del Paso 9: solo RECIBIDO
    fun cancelar(p: PedidoConDetalle) {
        if (!puedeCancelar(p)) return
        viewModelScope.launch { repo.cambiarEstado(p.pedido.id, EstadoPedido.CANCELADO, null) }
    }

    private fun estado(p: PedidoConDetalle) = EstadoPedido.valueOf(p.pedido.estado)
}

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

class DetallePedidoViewModel(
    private val repo: LavaRepository,
    private val pedidoId: Int
) : ViewModel() {

    // Flow de Room: si el administrador cambia el estado, se actualiza solo
    val pedido: StateFlow<PedidoConDetalle?> = repo.observarPedido(pedidoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // El estudiante solo cancela antes de que empiece el lavado
    val puedeCancelar: StateFlow<Boolean> = pedido
        .map { it != null && EstadoPedido.valueOf(it.pedido.estado) == EstadoPedido.RECIBIDO }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun cancelar() {
        if (!puedeCancelar.value) return
        viewModelScope.launch { repo.cambiarEstado(pedidoId, EstadoPedido.CANCELADO, null) }
    }
}

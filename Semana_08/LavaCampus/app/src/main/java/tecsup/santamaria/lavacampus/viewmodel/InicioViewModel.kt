package tecsup.santamaria.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import tecsup.santamaria.lavacampus.data.local.entity.Usuario
import tecsup.santamaria.lavacampus.data.local.relation.PedidoConDetalle
import tecsup.santamaria.lavacampus.data.repository.LavaRepository
import tecsup.santamaria.lavacampus.model.EstadoPedido

class InicioViewModel(repo: LavaRepository, val estudiante: Usuario) : ViewModel() {

    // Llegan del más nuevo al más antiguo
    private val pedidos = repo.observarPedidosDeUsuario(estudiante.id)

    // El más reciente en RECIBIDO, EN_PROCESO o LISTO
    val pedidoActivo: StateFlow<PedidoConDetalle?> = pedidos
        .map { lista -> lista.firstOrNull { esActivo(it) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Los 2 últimos, sin repetir el que ya sale arriba como activo (como el mockup)
    val recientes: StateFlow<List<PedidoConDetalle>> = pedidos
        .map { lista ->
            val activo = lista.firstOrNull { esActivo(it) }
            lista.filter { it != activo }.take(2)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private fun esActivo(p: PedidoConDetalle) = EstadoPedido.valueOf(p.pedido.estado).esActivo
}

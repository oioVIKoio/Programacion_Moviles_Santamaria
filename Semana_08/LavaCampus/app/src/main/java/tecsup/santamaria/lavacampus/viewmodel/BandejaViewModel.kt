package tecsup.santamaria.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tecsup.santamaria.lavacampus.data.local.relation.PedidoConDetalle
import tecsup.santamaria.lavacampus.data.repository.LavaRepository
import tecsup.santamaria.lavacampus.model.EstadoPedido
import java.time.LocalDate

class BandejaViewModel(private val repo: LavaRepository) : ViewModel() {

    // null = Todos
    private val _filtro = MutableStateFlow<EstadoPedido?>(null)
    val filtro: StateFlow<EstadoPedido?> = _filtro.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    // Filtrado por chip y por buscador (nombre del estudiante o número)
    val pedidos: StateFlow<List<PedidoConDetalle>> = combine(repo.observarPedidos(), _filtro, _busqueda) { lista, filtro, texto ->
        val q = texto.trim().removePrefix("#").lowercase()
        lista.filter { p ->
            (filtro == null || p.pedido.estado == filtro.name) &&
                (q.isEmpty() || p.usuario.nombre.lowercase().contains(q) ||
                    p.pedido.id.toString() == q.trimStart('0').ifEmpty { "0" })
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Números de los pedidos atrasados (entrega vencida y sin entregar ni cancelar)
    val atrasados: StateFlow<Set<Int>> = repo.observarAtrasados(LocalDate.now().toString())
        .map { it.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    fun elegirFiltro(e: EstadoPedido?) {
        _filtro.value = e
    }

    fun buscar(texto: String) {
        _busqueda.value = texto
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }

    // Botón rápido de la tarjeta. Pasar a LISTO necesita casillero: eso se hace en la gestión
    fun accionRapida(p: PedidoConDetalle) {
        val siguiente = EstadoPedido.valueOf(p.pedido.estado).siguiente() ?: return
        val motivo = GestionPedidoViewModel.motivoParaNoAvanzar(siguiente, p.pedido.casillero, emptyList(), p.pedido.pagado)
        if (motivo != null) {
            _mensaje.value = motivo
            return
        }
        viewModelScope.launch { repo.cambiarEstado(p.pedido.id, siguiente, p.pedido.casillero) }
    }
}

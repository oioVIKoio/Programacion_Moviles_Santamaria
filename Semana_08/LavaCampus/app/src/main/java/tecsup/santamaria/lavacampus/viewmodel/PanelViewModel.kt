package tecsup.santamaria.lavacampus.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import tecsup.santamaria.lavacampus.data.repository.LavaRepository
import tecsup.santamaria.lavacampus.model.EstadoPedido
import tecsup.santamaria.lavacampus.ui.components.graficos.DatoGrafico
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class PanelViewModel(repo: LavaRepository) : ViewModel() {

    val hoy: String = LocalDate.now().toString()

    // Lunes de esta semana (si hoy es domingo, el lunes anterior)
    val lunes: String = LocalDate.now()
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toString()

    val pedidosHoy: StateFlow<Int> = repo.observarPedidosDeFecha(hoy).enEstado(0)
    val enProceso: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.EN_PROCESO).enEstado(0)
    val porRecoger: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.LISTO).enEstado(0)
    val ingresosSemana: StateFlow<Double> = repo.observarIngresosDesde(lunes).enEstado(0.0)

    // Cuántos pedidos pagados suman esos ingresos (el mockup lo muestra en la tarjeta)
    val pagadosSemana: StateFlow<Int> = repo.observarPedidos()
        .map { lista -> lista.count { it.pedido.pagado && it.pedido.fechaRegistro >= lunes } }
        .enEstado(0)

    // Dona: los 4 estados activos o entregado, cada uno con su color
    val porEstado: StateFlow<List<DatoGrafico>> = combine(
        repo.observarCantidadPorEstado(EstadoPedido.RECIBIDO),
        repo.observarCantidadPorEstado(EstadoPedido.EN_PROCESO),
        repo.observarCantidadPorEstado(EstadoPedido.LISTO),
        repo.observarCantidadPorEstado(EstadoPedido.ENTREGADO)
    ) { recibido, proceso, listo, entregado ->
        listOf(
            EstadoPedido.RECIBIDO to recibido,
            EstadoPedido.EN_PROCESO to proceso,
            EstadoPedido.LISTO to listo,
            EstadoPedido.ENTREGADO to entregado
        ).map { (estado, n) -> DatoGrafico(estado.etiqueta, n.toFloat(), estado.color) }
    }.enEstado(emptyList())

    // Barras: ingresos por servicio de esta semana. El color lo pone la pantalla con el tema
    val porServicio: StateFlow<List<DatoGrafico>> = repo.observarIngresosPorServicio(lunes)
        .map { lista -> lista.map { DatoGrafico(it.nombre, it.total.toFloat(), Color.Unspecified) } }
        .enEstado(emptyList())

    private fun <T> kotlinx.coroutines.flow.Flow<T>.enEstado(inicial: T): StateFlow<T> =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), inicial)
}

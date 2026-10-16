package tecsup.santamaria.lavacampus.data.repository

import kotlinx.coroutines.flow.Flow
import tecsup.santamaria.lavacampus.data.local.entity.Servicio

// El contrato: los ViewModels solo conocen esta interface
interface LavaRepository {
    // Usuarios
    // TODO (Paso 4): login(correo, clave) y observarClientes()

    // Servicios (ejemplo resuelto)
    fun observarServicios(): Flow<List<Servicio>>
    fun observarServiciosActivos(): Flow<List<Servicio>>
    suspend fun guardarServicio(s: Servicio)
    suspend fun eliminarServicio(s: Servicio)
    suspend fun cambiarActivo(id: Int, activo: Boolean)

    // Pedidos
    // TODO (Paso 4): crearPedido, contarActivos, observarPedidos..., cambiarEstado, marcarPagado

    // Panel
    // TODO (Paso 4): observarPedidosDeFecha, observarCantidadPorEstado, observarIngresos...
}

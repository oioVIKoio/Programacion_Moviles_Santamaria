package tecsup.santamaria.lavacampus.data.repository

import kotlinx.coroutines.flow.Flow
import tecsup.santamaria.lavacampus.data.local.entity.Servicio
import tecsup.santamaria.lavacampus.data.local.entity.Usuario
import tecsup.santamaria.lavacampus.data.local.relation.PedidoConDetalle
import tecsup.santamaria.lavacampus.model.ClienteResumen
import tecsup.santamaria.lavacampus.model.EstadoPedido
import tecsup.santamaria.lavacampus.model.IngresoServicio
import tecsup.santamaria.lavacampus.model.LineaPedido
import tecsup.santamaria.lavacampus.model.Rol

// El contrato: los ViewModels solo conocen esta interface
interface LavaRepository {
    // Usuarios
    suspend fun login(correo: String, clave: String): Usuario?
    suspend fun obtenerUsuario(id: Int): Usuario?
    suspend fun primerUsuarioDeRol(rol: Rol): Usuario?
    fun observarClientes(): Flow<List<ClienteResumen>>

    // Servicios (ejemplo resuelto)
    fun observarServicios(): Flow<List<Servicio>>
    fun observarServiciosActivos(): Flow<List<Servicio>>
    suspend fun guardarServicio(s: Servicio)
    suspend fun eliminarServicio(s: Servicio)
    suspend fun cambiarActivo(id: Int, activo: Boolean)
    suspend fun servicioTieneDetalles(id: Int): Boolean

    // Pedidos
    suspend fun crearPedido(
        usuarioId: Int, lineas: List<LineaPedido>,
        fechaEntrega: String, nota: String,
        porcentajeDescuento: Double = 0.0
    ): Long
    suspend fun contarActivos(usuarioId: Int): Int
    fun observarPedidosDeUsuario(usuarioId: Int): Flow<List<PedidoConDetalle>>
    fun observarPedidos(): Flow<List<PedidoConDetalle>>
    fun observarPedido(id: Int): Flow<PedidoConDetalle?>
    suspend fun cambiarEstado(id: Int, estado: EstadoPedido, casillero: String?)
    suspend fun marcarPagado(id: Int, pagado: Boolean)
    fun observarCasillerosOcupados(): Flow<List<String>>

    // Panel
    fun observarPedidosDeFecha(fecha: String): Flow<Int>
    fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int>
    fun observarIngresosDesde(fechaInicio: String): Flow<Double>
    fun observarIngresosPorServicio(fechaInicio: String): Flow<List<IngresoServicio>>
    fun observarAtrasados(hoy: String): Flow<List<Int>>
}

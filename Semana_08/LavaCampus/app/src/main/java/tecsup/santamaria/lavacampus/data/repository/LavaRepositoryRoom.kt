package tecsup.santamaria.lavacampus.data.repository

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import tecsup.santamaria.lavacampus.data.local.LavaDatabase
import tecsup.santamaria.lavacampus.data.local.entity.DetallePedido
import tecsup.santamaria.lavacampus.data.local.entity.Pedido
import tecsup.santamaria.lavacampus.data.local.entity.Servicio
import tecsup.santamaria.lavacampus.data.local.entity.Usuario
import tecsup.santamaria.lavacampus.data.local.relation.PedidoConDetalle
import tecsup.santamaria.lavacampus.model.ClienteResumen
import tecsup.santamaria.lavacampus.model.EstadoPedido
import tecsup.santamaria.lavacampus.model.IngresoServicio
import tecsup.santamaria.lavacampus.model.LineaPedido
import tecsup.santamaria.lavacampus.model.Rol
import java.time.LocalDate

class LavaRepositoryRoom(private val db: LavaDatabase) : LavaRepository {

    private val servicioDao = db.servicioDao()
    private val usuarioDao = db.usuarioDao()
    private val pedidoDao = db.pedidoDao()
    private val detalleDao = db.detalleDao()

    // Usuarios
    override suspend fun login(correo: String, clave: String): Usuario? = usuarioDao.login(correo, clave)

    override suspend fun obtenerUsuario(id: Int): Usuario? = usuarioDao.obtener(id)

    override suspend fun primerUsuarioDeRol(rol: Rol): Usuario? = usuarioDao.primeroDeRol(rol.name)

    override fun observarClientes(): Flow<List<ClienteResumen>> = usuarioDao.observarClientes()

    // Servicios
    override fun observarServicios(): Flow<List<Servicio>> = servicioDao.observarTodos()

    override fun observarServiciosActivos(): Flow<List<Servicio>> = servicioDao.observarActivos()

    override suspend fun guardarServicio(s: Servicio) {
        servicioDao.insertar(s)
    }

    override suspend fun eliminarServicio(s: Servicio) = servicioDao.eliminar(s)

    override suspend fun cambiarActivo(id: Int, activo: Boolean) = servicioDao.cambiarActivo(id, activo)

    override suspend fun servicioTieneDetalles(id: Int): Boolean = detalleDao.contarPorServicio(id) > 0

    // Pedidos
    // Pedido y detalles en una sola transacción: si falla un detalle, no queda el pedido
    override suspend fun crearPedido(
        usuarioId: Int, lineas: List<LineaPedido>,
        fechaEntrega: String, nota: String,
        porcentajeDescuento: Double
    ): Long = db.withTransaction {
        // el precio se copia del servicio (queda congelado en el detalle)
        val detalles = lineas.map { linea ->
            val servicio = servicioDao.obtener(linea.servicioId)
                ?: error("Servicio ${linea.servicioId} no existe")
            DetallePedido(
                pedidoId = 0,
                servicioId = servicio.id,
                cantidad = linea.cantidad,
                precioUnitario = servicio.precio,
                subtotal = linea.cantidad * servicio.precio
            )
        }
        val subtotal = detalles.sumOf { it.subtotal }
        val descuento = Math.round(subtotal * porcentajeDescuento * 100) / 100.0
        val pedidoId = pedidoDao.insertar(
            Pedido(
                usuarioId = usuarioId,
                fechaRegistro = LocalDate.now().toString(),
                fechaEntrega = fechaEntrega,
                estado = EstadoPedido.RECIBIDO.name,
                total = subtotal - descuento,
                descuento = descuento,
                pagado = false,
                nota = nota
            )
        )
        detalleDao.insertarTodos(detalles.map { it.copy(pedidoId = pedidoId.toInt()) })
        pedidoId
    }

    override suspend fun contarActivos(usuarioId: Int): Int = pedidoDao.contarActivos(usuarioId)

    override fun observarPedidosDeUsuario(usuarioId: Int): Flow<List<PedidoConDetalle>> =
        pedidoDao.observarPorUsuario(usuarioId)

    override fun observarPedidos(): Flow<List<PedidoConDetalle>> = pedidoDao.observarTodos()

    override fun observarPedido(id: Int): Flow<PedidoConDetalle?> = pedidoDao.observarUno(id)

    // Sin reglas: las valida el ViewModel
    override suspend fun cambiarEstado(id: Int, estado: EstadoPedido, casillero: String?) =
        pedidoDao.actualizarEstado(id, estado.name, casillero)

    override suspend fun marcarPagado(id: Int, pagado: Boolean) = pedidoDao.actualizarPagado(id, pagado)

    override fun observarCasillerosOcupados(): Flow<List<String>> = pedidoDao.observarCasillerosOcupados()

    // Panel
    override fun observarPedidosDeFecha(fecha: String): Flow<Int> = pedidoDao.contarPorFecha(fecha)

    override fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int> =
        pedidoDao.contarPorEstado(estado.name)

    override fun observarIngresosDesde(fechaInicio: String): Flow<Double> = pedidoDao.ingresosDesde(fechaInicio)

    override fun observarAtrasados(hoy: String): Flow<List<Int>> = pedidoDao.observarAtrasados(hoy)

    override fun observarIngresosPorServicio(fechaInicio: String): Flow<List<IngresoServicio>> =
        detalleDao.observarIngresosPorServicio(fechaInicio)
}

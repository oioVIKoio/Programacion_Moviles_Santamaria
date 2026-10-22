package tecsup.santamaria.lavacampus.data.local

import tecsup.santamaria.lavacampus.data.local.entity.DetallePedido
import tecsup.santamaria.lavacampus.data.local.entity.Pedido
import tecsup.santamaria.lavacampus.data.local.entity.Servicio
import tecsup.santamaria.lavacampus.data.local.entity.Usuario
import tecsup.santamaria.lavacampus.model.EstadoPedido
import tecsup.santamaria.lavacampus.model.Rol
import java.time.DayOfWeek
import java.time.LocalDate
import kotlinx.coroutines.flow.first

object DatosIniciales {

    val servicios = listOf(
        Servicio(nombre = "Lavado y secado", descripcion = "Ropa del día a día, lavada y secada", unidad = "KG", precio = 8.00, horasEntrega = 24, icono = "wash"),
        Servicio(nombre = "Planchado", descripcion = "Camisas, pantalones y polos", unidad = "PRENDA", precio = 2.50, horasEntrega = 48, icono = "iron"),
        Servicio(nombre = "Lavado en seco", descripcion = "Ternos, sacos y prendas delicadas", unidad = "PRENDA", precio = 12.00, horasEntrega = 48, icono = "shirt"),
        Servicio(nombre = "Edredón / frazada", descripcion = "Edredones, frazadas y cobertores", unidad = "UNIDAD", precio = 20.00, horasEntrega = 48, activo = false, icono = "wash")
    )

    // Solo correos de Tecsup. Victor con sus datos; el resto ficticios (Ley 29733), nombres y códigos del mockup
    val usuarios = listOf(
        Usuario(nombre = "Victor Santamaria", codigo = "120732", correo = "victor.santamaria.f@tecsup.edu.pe", clave = "1234", rol = Rol.CLIENTE.name),
        Usuario(nombre = "Luis Ramos", codigo = "U202310877", correo = "luis.ramos@tecsup.edu.pe", clave = "1234", rol = Rol.CLIENTE.name),
        Usuario(nombre = "Marta Paredes", codigo = "U202411022", correo = "marta.paredes@tecsup.edu.pe", clave = "1234", rol = Rol.CLIENTE.name),
        Usuario(nombre = "Administración", codigo = "", correo = "admin@tecsup.edu.pe", clave = "admin", rol = Rol.ADMIN.name),
        Usuario(nombre = "Juan León Suyon", codigo = "", correo = "jleon@tecsup.edu.pe", clave = "admin", rol = Rol.ADMIN.name)
    )

    // Pedido de ejemplo: líneas = (posición del servicio, cantidad)
    private class Semilla(
        val usuario: Int,
        val diasAtras: Long,
        val estado: EstadoPedido,
        val lineas: List<Pair<Int, Double>>,
        val pagado: Boolean = false,
        val casillero: String? = null
    )

    private val pedidos = listOf(
        Semilla(0, 4, EstadoPedido.ENTREGADO, listOf(0 to 3.5, 1 to 4.0), pagado = true),
        Semilla(1, 3, EstadoPedido.ENTREGADO, listOf(2 to 2.0), pagado = true),
        Semilla(2, 2, EstadoPedido.ENTREGADO, listOf(0 to 5.0), pagado = true),
        Semilla(2, 1, EstadoPedido.ENTREGADO, listOf(1 to 6.0), pagado = true),
        Semilla(0, 2, EstadoPedido.LISTO, listOf(0 to 4.0), casillero = "C-05"),
        Semilla(2, 1, EstadoPedido.LISTO, listOf(2 to 1.0, 1 to 3.0), pagado = true, casillero = "C-07"),
        Semilla(1, 1, EstadoPedido.EN_PROCESO, listOf(0 to 2.5)),
        Semilla(2, 0, EstadoPedido.EN_PROCESO, listOf(0 to 3.0, 1 to 5.0)),
        Semilla(0, 0, EstadoPedido.RECIBIDO, listOf(1 to 8.0)),
        Semilla(1, 0, EstadoPedido.RECIBIDO, listOf(0 to 6.0))
    )

    // Hoy + el mayor tiempo de entrega; si cae domingo pasa al lunes
    private fun fechaEntrega(registro: LocalDate, horas: Int): LocalDate {
        val fecha = registro.plusDays((horas / 24).toLong())
        return if (fecha.dayOfWeek == DayOfWeek.SUNDAY) fecha.plusDays(1) else fecha
    }

    // Se carga solo la primera vez
    suspend fun sembrar(db: LavaDatabase) {
        if (db.usuarioDao().contar() > 0) return

        if (db.servicioDao().contar() == 0) {
            db.servicioDao().insertarTodos(servicios)
        }
        db.usuarioDao().insertarTodos(usuarios)

        val serviciosBd = db.servicioDao().observarTodos().first().sortedBy { it.id }
        val clientes = db.usuarioDao().observarClientes().first().sortedBy { it.id }

        for (semilla in pedidos) {
            val registro = LocalDate.now().minusDays(semilla.diasAtras)
            val lineas = semilla.lineas.map { (pos, cantidad) -> serviciosBd[pos] to cantidad }
            val total = lineas.sumOf { (s, cantidad) -> s.precio * cantidad }
            val horas = lineas.maxOf { (s, _) -> s.horasEntrega }

            val pedidoId = db.pedidoDao().insertar(
                Pedido(
                    usuarioId = clientes[semilla.usuario].id,
                    fechaRegistro = registro.toString(),
                    fechaEntrega = fechaEntrega(registro, horas).toString(),
                    estado = semilla.estado.name,
                    total = total,
                    casillero = semilla.casillero,
                    pagado = semilla.pagado
                )
            ).toInt()

            db.detalleDao().insertarTodos(lineas.map { (s, cantidad) ->
                DetallePedido(
                    pedidoId = pedidoId,
                    servicioId = s.id,
                    cantidad = cantidad,
                    precioUnitario = s.precio,
                    subtotal = s.precio * cantidad
                )
            })
        }
    }
}

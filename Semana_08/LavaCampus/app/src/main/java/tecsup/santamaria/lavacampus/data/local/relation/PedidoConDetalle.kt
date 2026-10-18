package tecsup.santamaria.lavacampus.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import tecsup.santamaria.lavacampus.data.local.entity.DetallePedido
import tecsup.santamaria.lavacampus.data.local.entity.Pedido
import tecsup.santamaria.lavacampus.data.local.entity.Usuario

data class PedidoConDetalle(
    @Embedded val pedido: Pedido,
    @Relation(parentColumn = "usuarioId", entityColumn = "id")
    val usuario: Usuario,
    // relación anidada: cada detalle trae su servicio
    @Relation(entity = DetallePedido::class, parentColumn = "id", entityColumn = "pedidoId")
    val detalles: List<DetalleConServicio>
)

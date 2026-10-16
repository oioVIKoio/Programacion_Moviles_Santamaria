package tecsup.santamaria.lavacampus.data.local

import tecsup.santamaria.lavacampus.data.local.entity.Servicio

object DatosIniciales {

    val servicios = listOf(
        Servicio(nombre = "Lavado y secado", descripcion = "Ropa del día a día, lavada y secada", unidad = "KG", precio = 8.00, horasEntrega = 24, icono = "wash"),
        Servicio(nombre = "Planchado", descripcion = "Camisas, pantalones y polos", unidad = "PRENDA", precio = 2.50, horasEntrega = 48, icono = "iron"),
        Servicio(nombre = "Lavado en seco", descripcion = "Ternos, sacos y prendas delicadas", unidad = "PRENDA", precio = 12.00, horasEntrega = 48, icono = "shirt"),
        Servicio(nombre = "Edredón / frazada", descripcion = "Edredones, frazadas y cobertores", unidad = "UNIDAD", precio = 20.00, horasEntrega = 48, activo = false, icono = "wash")
    )

    // Se carga solo la primera vez
    suspend fun sembrar(db: LavaDatabase) {
        // TODO (Paso 3): usar usuarioDao().contar() == 0 y sembrar usuarios, pedidos y detalles
        if (db.servicioDao().contar() == 0) {
            db.servicioDao().insertarTodos(servicios)
        }
    }
}

package tecsup.santamaria.lavacampus.data.repository

import kotlinx.coroutines.flow.Flow
import tecsup.santamaria.lavacampus.data.local.LavaDatabase
import tecsup.santamaria.lavacampus.data.local.entity.Servicio

class LavaRepositoryRoom(private val db: LavaDatabase) : LavaRepository {

    private val servicioDao = db.servicioDao()

    // Servicios
    override fun observarServicios(): Flow<List<Servicio>> = servicioDao.observarTodos()

    override fun observarServiciosActivos(): Flow<List<Servicio>> = servicioDao.observarActivos()

    override suspend fun guardarServicio(s: Servicio) {
        servicioDao.insertar(s)
    }

    override suspend fun eliminarServicio(s: Servicio) = servicioDao.eliminar(s)

    override suspend fun cambiarActivo(id: Int, activo: Boolean) = servicioDao.cambiarActivo(id, activo)

    // TODO (Paso 4): usuarios, pedidos y panel
}

package tecsup.santamaria.lavacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tecsup.santamaria.lavacampus.data.local.entity.Usuario
import tecsup.santamaria.lavacampus.model.ClienteResumen

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave LIMIT 1")
    suspend fun login(correo: String, clave: String): Usuario?

    @Insert suspend fun insertarTodos(lista: List<Usuario>)

    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun contar(): Int

    // pedidos = no cancelados, gastado = suma de los pagados
    @Query(
        """
        SELECT u.id, u.nombre, u.codigo, u.correo,
               COUNT(CASE WHEN p.estado != 'CANCELADO' THEN p.id END) AS pedidos,
               COALESCE(SUM(CASE WHEN p.pagado = 1 THEN p.total END), 0.0) AS gastado
        FROM usuarios u
        LEFT JOIN pedidos p ON p.usuarioId = u.id
        WHERE u.rol = 'CLIENTE'
        GROUP BY u.id
        ORDER BY u.nombre
        """
    )
    fun observarClientes(): Flow<List<ClienteResumen>>
}

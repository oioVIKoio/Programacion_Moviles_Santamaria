package tecsup.santamaria.lavacampus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import tecsup.santamaria.lavacampus.data.local.dao.DetalleDao
import tecsup.santamaria.lavacampus.data.local.dao.PedidoDao
import tecsup.santamaria.lavacampus.data.local.dao.ServicioDao
import tecsup.santamaria.lavacampus.data.local.dao.UsuarioDao
import tecsup.santamaria.lavacampus.data.local.entity.DetallePedido
import tecsup.santamaria.lavacampus.data.local.entity.Pedido
import tecsup.santamaria.lavacampus.data.local.entity.Servicio
import tecsup.santamaria.lavacampus.data.local.entity.Usuario

@Database(
    entities = [Servicio::class, Usuario::class, Pedido::class, DetallePedido::class],
    version = 2,
    exportSchema = false
)
abstract class LavaDatabase : RoomDatabase() {
    abstract fun servicioDao(): ServicioDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun detalleDao(): DetalleDao

    companion object {
        @Volatile private var INSTANCE: LavaDatabase? = null

        fun obtener(context: Context): LavaDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                LavaDatabase::class.java,
                "lavacampus.db"
            ).addMigrations(MIGRACION_1_2).build().also { INSTANCE = it }
        }

        // v2: descuento por volumen en pedidos
        private val MIGRACION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE pedidos ADD COLUMN descuento REAL NOT NULL DEFAULT 0")
            }
        }
    }
}

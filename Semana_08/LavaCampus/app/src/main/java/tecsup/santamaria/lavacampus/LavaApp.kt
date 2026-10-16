package tecsup.santamaria.lavacampus

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tecsup.santamaria.lavacampus.data.local.DatosIniciales
import tecsup.santamaria.lavacampus.data.local.LavaDatabase
import tecsup.santamaria.lavacampus.data.repository.LavaRepository
import tecsup.santamaria.lavacampus.data.repository.LavaRepositoryRoom

class LavaApp : Application() {

    val database: LavaDatabase by lazy { LavaDatabase.obtener(this) }
    val repository: LavaRepository by lazy { LavaRepositoryRoom(database) }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            DatosIniciales.sembrar(database)
        }
    }
}

package com.santamaria.campusgps.data

import com.santamaria.campusgps.model.Posicion
import kotlinx.coroutines.flow.Flow

interface UbicacionRepository {
    suspend fun obtenerPosicion(): Result<Posicion>

    // Mejora 6: seguimiento en vivo. Emite una posición cada vez que el GPS se mueve y se detiene al cancelar el Flow
    fun seguirPosicion(): Flow<Posicion>

    // El ViewModel no recibe Context: por eso esta comprobación vive en el Repository
    fun playServicesDisponible(): Boolean
}

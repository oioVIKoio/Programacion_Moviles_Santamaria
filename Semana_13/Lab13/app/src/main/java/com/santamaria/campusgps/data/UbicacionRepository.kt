package com.santamaria.campusgps.data

import com.santamaria.campusgps.model.Posicion
import kotlinx.coroutines.flow.Flow

interface UbicacionRepository {
    suspend fun obtenerPosicion(): Result<Posicion>

    // Mejora 6: seguimiento en vivo. Emite una posición cada vez que el GPS se mueve y se detiene al cancelar el Flow
    fun seguirPosicion(): Flow<Posicion>

    // Hacia dónde apunta el celular (grados desde el norte), con el sensor de orientación de Google
    fun seguirRumbo(): Flow<Float>

    // El ViewModel no recibe Context: por eso esta comprobación vive en el Repository
    fun playServicesDisponible(): Boolean
}

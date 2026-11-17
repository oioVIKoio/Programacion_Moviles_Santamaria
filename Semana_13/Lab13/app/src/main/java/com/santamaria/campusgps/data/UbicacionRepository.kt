package com.santamaria.campusgps.data

import com.santamaria.campusgps.model.Posicion

interface UbicacionRepository {
    suspend fun obtenerPosicion(): Result<Posicion>

    // El ViewModel no recibe Context: por eso esta comprobación vive en el Repository
    fun playServicesDisponible(): Boolean
}

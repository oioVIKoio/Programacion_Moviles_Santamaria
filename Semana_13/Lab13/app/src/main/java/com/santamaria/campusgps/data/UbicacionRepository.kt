package com.santamaria.campusgps.data

import com.santamaria.campusgps.model.Posicion

interface UbicacionRepository {
    suspend fun obtenerPosicion(): Result<Posicion>
}

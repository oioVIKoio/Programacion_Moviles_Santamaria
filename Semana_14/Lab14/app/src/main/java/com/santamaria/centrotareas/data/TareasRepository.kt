package com.santamaria.centrotareas.data

import com.santamaria.centrotareas.model.Avance
import com.santamaria.centrotareas.model.AvancePrimos
import kotlinx.coroutines.flow.Flow

interface TareasRepository {
    /** Tarea simulada de ≈ 4 s que va emitiendo su avance. Cancelar al que la recoge la detiene. */
    fun tareaConProgreso(): Flow<Avance>
    /** Cuenta los primos de 2 a [hasta] emitiendo el avance; la última emisión trae el total (100 %). */
    fun contarPrimos(hasta: Int): Flow<AvancePrimos>
    suspend fun cargarPerfil(): String
    suspend fun cargarPedidos(simularError: Boolean): List<String>
}

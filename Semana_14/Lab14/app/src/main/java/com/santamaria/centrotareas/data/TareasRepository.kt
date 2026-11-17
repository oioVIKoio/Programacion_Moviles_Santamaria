package com.santamaria.centrotareas.data

import com.santamaria.centrotareas.model.Avance
import kotlinx.coroutines.flow.Flow

interface TareasRepository {
    /** Tarea simulada de ≈ 4 s que va emitiendo su avance. Cancelar al que la recoge la detiene. */
    fun tareaConProgreso(): Flow<Avance>
    suspend fun contarPrimos(hasta: Int): Int
    suspend fun cargarPerfil(): String
    suspend fun cargarPedidos(simularError: Boolean): List<String>
}

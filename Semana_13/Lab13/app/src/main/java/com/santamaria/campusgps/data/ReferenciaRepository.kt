package com.santamaria.campusgps.data

import kotlinx.coroutines.flow.Flow

// La referencia es el último lugar que el usuario tocó: se guarda para que siga ahí al volver a abrir la app
interface ReferenciaRepository {
    val ultimoLugarId: Flow<Int?>
    suspend fun guardar(id: Int)
}

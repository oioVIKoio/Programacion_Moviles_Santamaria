package com.santamaria.campusgps.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.almacen by preferencesDataStore(name = "campusgps")

/** Guarda la referencia en DataStore (un archivo del propio teléfono, sin internet). El Context vive solo aquí. */
class ReferenciaRepositoryDataStore(private val contexto: Context) : ReferenciaRepository {
    private val clave = intPreferencesKey("ultimo_lugar_id")

    override val ultimoLugarId: Flow<Int?> = contexto.almacen.data.map { it[clave] }

    override suspend fun guardar(id: Int) {
        contexto.almacen.edit { it[clave] = id }
    }
}

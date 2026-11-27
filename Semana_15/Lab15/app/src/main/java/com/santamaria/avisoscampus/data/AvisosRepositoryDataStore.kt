package com.santamaria.avisoscampus.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.santamaria.avisoscampus.model.Aviso
import com.santamaria.avisoscampus.model.Origen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

// El DataStore se crea una sola vez por app. El Repository recibe el DataStore ya creado,
// no un Context.
val Context.avisosStore: DataStore<Preferences> by preferencesDataStore(name = "avisos")

class AvisosRepositoryDataStore(private val store: DataStore<Preferences>) : AvisosRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val clave = stringPreferencesKey("historial")

    // Los ids no se repiten entre ejecuciones: así una notificación vieja no se pisa con una nueva.
    private val siguiente = AtomicInteger((System.currentTimeMillis() / 1000).toInt())

    override val avisos: StateFlow<List<Aviso>> = store.data
        .map { prefs -> leer(prefs[clave]) }
        .stateIn(scope, SharingStarted.Eagerly, emptyList())

    override fun registrar(titulo: String, texto: String, origen: Origen, urgente: Boolean): Aviso {
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val aviso = Aviso(siguiente.incrementAndGet(), titulo, texto, origen, hora, urgente)
        scope.launch {
            store.edit { prefs ->
                val actual = leer(prefs[clave])
                prefs[clave] = escribir((listOf(aviso) + actual).take(MAXIMO))
            }
        }
        return aviso
    }

    private fun leer(json: String?): List<Aviso> {
        if (json.isNullOrEmpty()) return emptyList()
        val lista = JSONArray(json)
        return (0 until lista.length()).map { i ->
            val o = lista.getJSONObject(i)
            Aviso(
                id = o.getInt("id"),
                titulo = o.getString("titulo"),
                texto = o.getString("texto"),
                origen = Origen.valueOf(o.getString("origen")),
                hora = o.getString("hora"),
                urgente = o.getBoolean("urgente")
            )
        }
    }

    private fun escribir(avisos: List<Aviso>): String {
        val lista = JSONArray()
        avisos.forEach { a ->
            lista.put(
                JSONObject()
                    .put("id", a.id)
                    .put("titulo", a.titulo)
                    .put("texto", a.texto)
                    .put("origen", a.origen.name)
                    .put("hora", a.hora)
                    .put("urgente", a.urgente)
            )
        }
        return lista.toString()
    }

    private companion object {
        const val MAXIMO = 50
    }
}

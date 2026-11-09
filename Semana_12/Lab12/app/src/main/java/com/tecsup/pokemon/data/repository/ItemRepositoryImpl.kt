package com.tecsup.pokemon.data.repository

import com.tecsup.pokemon.data.mapper.aModelo
import com.tecsup.pokemon.data.remote.ApiService
import com.tecsup.pokemon.domain.model.Item
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

/**
 * Por ahora guarda lo descargado en memoria. Cuando entre Room (C09),
 * la lista saldrá de la base de datos y Retrofit solo la actualizará.
 */
class ItemRepositoryImpl(private val api: ApiService) : ItemRepository {

    private val cache = MutableStateFlow<List<Item>>(emptyList())

    override val items: Flow<List<Item>> = cache
    override val favoritos: Flow<List<Item>> = cache.map { lista -> lista.filter { it.esFavorito } }

    override suspend fun refrescar(): Result<Int> = conErrores {
        val favoritos = idsFavoritos()
        val nuevos = api.listar().pokemon
            .map { it.pokemon.aModelo(esFavorito = it.pokemon.name in favoritos) }
            .sortedBy { it.titulo }
        cache.value = nuevos
        nuevos.size
    }

    override suspend fun obtenerDetalle(id: String): Result<Item> = conErrores {
        api.detalle(id).aModelo(esFavorito = id in idsFavoritos())
    }

    override suspend fun alternarFavorito(id: String) = cache.update { lista ->
        lista.map { if (it.id == id) it.copy(esFavorito = !it.esFavorito) else it }
    }

    override suspend fun cantidadGuardada() = cache.value.size
    override suspend fun cantidadFavoritos() = cache.value.count { it.esFavorito }
    override suspend fun limpiarCache() { cache.value = emptyList() }

    private fun idsFavoritos() = cache.value.filter { it.esFavorito }.map { it.id }.toSet()

    /** IOException: sin internet. HttpException: 404, 500... SerializationException: JSON distinto al DTO. */
    private inline fun <T> conErrores(bloque: () -> T): Result<T> = try {
        Result.success(bloque())
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: HttpException) {
        Result.failure(e)
    } catch (e: SerializationException) {
        Result.failure(e)
    }
}

package com.tecsup.pokemon.ui.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.pokemon.data.repository.ItemRepository
import com.tecsup.pokemon.domain.model.Item
import com.tecsup.pokemon.ui.UiState
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ListaViewModel(private val repo: ItemRepository) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<List<Item>>>(UiState.Cargando)
    val estado: StateFlow<UiState<List<Item>>> = _estado.asStateFlow()

    // Evita mostrar "vacío" antes de que responda la API la primera vez
    private var refrescoTerminado = false

    init {
        viewModelScope.launch {
            repo.items.collect { lista ->
                if (lista.isNotEmpty() || refrescoTerminado) _estado.value = UiState.Exito(lista)
            }
        }
        refrescar()
    }

    fun refrescar() {
        if (_estado.value is UiState.Error) _estado.value = UiState.Cargando
        viewModelScope.launch {
            val resultado = repo.refrescar()
            refrescoTerminado = true
            resultado
                .onSuccess {
                    if (_estado.value is UiState.Cargando) _estado.value = UiState.Exito(emptyList())
                }
                .onFailure { e ->
                    if (repo.cantidadGuardada() == 0) _estado.value = UiState.Error(mensajeDe(e))
                }
        }
    }

    fun alternarFavorito(id: String) {
        viewModelScope.launch { repo.alternarFavorito(id) }
    }

    private fun mensajeDe(e: Throwable) = when (e) {
        is IOException -> "No hay conexión a internet"
        is HttpException -> "El servidor respondió con error ${e.code()}"
        else -> "Ocurrió un error inesperado"
    }
}

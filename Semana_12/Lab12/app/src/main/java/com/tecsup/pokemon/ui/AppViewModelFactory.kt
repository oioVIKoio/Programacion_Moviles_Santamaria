package com.tecsup.pokemon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.pokemon.AppContainer
import com.tecsup.pokemon.ui.detalle.DetalleViewModel
import com.tecsup.pokemon.ui.favoritos.FavoritosViewModel
import com.tecsup.pokemon.ui.lista.ListaViewModel

/** Crea los ViewModel con lo que necesitan del AppContainer. [id] solo lo usa el detalle. */
class AppViewModelFactory(
    private val container: AppContainer,
    private val id: String = ""
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ListaViewModel::class.java -> ListaViewModel(container.itemRepository)
        DetalleViewModel::class.java -> DetalleViewModel(container.itemRepository, id)
        FavoritosViewModel::class.java -> FavoritosViewModel(container.itemRepository)
        else -> error("ViewModel desconocido: ${modelClass.name}")
    } as T
}

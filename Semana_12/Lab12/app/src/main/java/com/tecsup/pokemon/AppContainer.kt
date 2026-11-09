package com.tecsup.pokemon

import com.tecsup.pokemon.data.remote.RetrofitClient
import com.tecsup.pokemon.data.repository.ItemRepository
import com.tecsup.pokemon.data.repository.ItemRepositoryImpl

/** Crea una sola vez la API y los repositorios. Los ViewModel los reciben por la Factory. */
class AppContainer {
    val itemRepository: ItemRepository by lazy { ItemRepositoryImpl(RetrofitClient.api) }
}

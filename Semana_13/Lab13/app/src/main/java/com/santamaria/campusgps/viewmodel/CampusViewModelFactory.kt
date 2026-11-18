package com.santamaria.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.santamaria.campusgps.data.LugaresRepository
import com.santamaria.campusgps.data.ReferenciaRepository
import com.santamaria.campusgps.data.UbicacionRepository

class CampusViewModelFactory(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository,
    private val referenciaRepo: ReferenciaRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MapaViewModel(ubicacionRepo, lugaresRepo, referenciaRepo) as T
}

package com.santamaria.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santamaria.campusgps.data.GpsApagadoException
import com.santamaria.campusgps.data.LugaresRepository
import com.santamaria.campusgps.data.UbicacionRepository
import com.santamaria.campusgps.model.Lugar
import com.santamaria.campusgps.model.LugarConDistancia
import com.santamaria.campusgps.model.Posicion
import com.santamaria.campusgps.model.TipoError
import com.santamaria.campusgps.model.UbicacionUi
import com.santamaria.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository
) : ViewModel() {

    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado: StateFlow<UbicacionUi> = _estado.asStateFlow()

    private val _lugares = MutableStateFlow(
        lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) }
    )
    val lugares: StateFlow<List<LugarConDistancia>> = _lugares.asStateFlow()

    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado: StateFlow<Int?> = _seleccionado.asStateFlow()

    fun buscar() {
        if (!ubicacionRepo.playServicesDisponible()) {
            _estado.value = UbicacionUi.Error("Este teléfono no tiene Google Play Services", TipoError.SIN_PLAY_SERVICES)
            return
        }
        _estado.value = UbicacionUi.Buscando
        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess {
                    _estado.value = UbicacionUi.Lista(it)
                    actualizarDistancias(it)
                }
                .onFailure { _estado.value = errorDe(it) }
        }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error("Sin permiso de ubicación no puedo mostrar dónde estás", TipoError.SIN_PERMISO)
    }

    fun seleccionar(id: Int?) {
        _seleccionado.value = id
    }

    // El más cercano se deriva de la lista (ya ordenada): el primero que ya tiene distancia
    val masCercano: StateFlow<Lugar?> = _lugares
        .map { lista -> lista.firstOrNull { it.metros != null }?.lugar }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)


    // Se calcula solo cuando llega la ubicación (dentro de buscar) y deja la lista ordenada de menor a mayor
    private fun actualizarDistancias(posicion: Posicion) {
        _lugares.value = lugaresRepo.obtenerLugares()
            .map { LugarConDistancia(it, distanciaMetros(posicion, it)) }
            .sortedBy { it.metros }
    }

    private fun errorDe(e: Throwable): UbicacionUi.Error = when (e) {
        is SecurityException -> UbicacionUi.Error("Falta el permiso de ubicación", TipoError.SIN_PERMISO)
        is GpsApagadoException -> UbicacionUi.Error("Activa el GPS del teléfono para ubicarte", TipoError.GPS_APAGADO)
        else -> UbicacionUi.Error(e.message ?: "No se pudo obtener la ubicación", TipoError.OTRO)
    }
}

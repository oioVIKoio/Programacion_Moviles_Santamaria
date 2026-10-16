package tecsup.santamaria.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tecsup.santamaria.lavacampus.data.local.entity.Servicio
import tecsup.santamaria.lavacampus.data.repository.LavaRepository

class ServiciosViewModel(private val repo: LavaRepository) : ViewModel() {

    val servicios: StateFlow<List<Servicio>> = repo.observarServicios()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun alternar(s: Servicio) {
        viewModelScope.launch { repo.cambiarActivo(s.id, !s.activo) }
    }

    // TODO (Paso 13): guardar(servicio) y eliminar(servicio)
}

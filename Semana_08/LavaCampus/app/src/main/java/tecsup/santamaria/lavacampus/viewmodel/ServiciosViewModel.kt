package tecsup.santamaria.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tecsup.santamaria.lavacampus.data.local.entity.Servicio
import tecsup.santamaria.lavacampus.data.repository.LavaRepository

class ServiciosViewModel(private val repo: LavaRepository) : ViewModel() {

    val servicios: StateFlow<List<Servicio>> = repo.observarServicios()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Para Tarifas: solo los que el estudiante puede pedir
    val activos: StateFlow<List<Servicio>> = repo.observarServiciosActivos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun alternar(s: Servicio) {
        viewModelScope.launch { repo.cambiarActivo(s.id, !s.activo) }
    }

    // Motivo cuando no se puede guardar o eliminar
    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun limpiarMensaje() {
        _mensaje.value = null
    }

    // Nuevo (id 0) o editado. Devuelve false si no pasa las validaciones
    fun guardar(servicio: Servicio): Boolean {
        _mensaje.value = when {
            servicio.nombre.isBlank() -> "El nombre es obligatorio"
            servicio.precio <= 0.0 -> "El precio debe ser mayor que 0"
            else -> null
        }
        if (_mensaje.value != null) return false
        viewModelScope.launch { repo.guardarServicio(servicio.copy(nombre = servicio.nombre.trim())) }
        return true
    }

    // Un servicio que ya está en algún pedido no se elimina: solo se desactiva
    fun eliminar(servicio: Servicio, alEliminar: () -> Unit) {
        viewModelScope.launch {
            if (repo.servicioTieneDetalles(servicio.id)) {
                _mensaje.value = "No se puede eliminar: ya está en pedidos. Desactívalo"
            } else {
                repo.eliminarServicio(servicio)
                alEliminar()
            }
        }
    }
}

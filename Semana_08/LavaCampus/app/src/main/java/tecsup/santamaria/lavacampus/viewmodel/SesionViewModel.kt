package tecsup.santamaria.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tecsup.santamaria.lavacampus.data.local.entity.Usuario
import tecsup.santamaria.lavacampus.data.prefs.PreferenciasStore
import tecsup.santamaria.lavacampus.data.repository.LavaRepository
import tecsup.santamaria.lavacampus.model.Rol

class SesionViewModel(
    private val repo: LavaRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()



    private val _aviso = MutableStateFlow<String?>(null)
    val aviso: StateFlow<String?> = _aviso.asStateFlow()



    val modoOscuro: StateFlow<Boolean> = prefs.modoOscuro
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val notificaciones: StateFlow<Boolean> = prefs.notificaciones
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val recibirPedidos: StateFlow<Boolean> = prefs.recibirPedidos
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    // Perfil guardado en DataStore: null mientras carga, "" si no hay sesión
    val perfilActivo: StateFlow<String?> = prefs.perfilActivo
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        // Sesión guardada: se recupera el usuario para no pedir login otra vez
        viewModelScope.launch {
            val id = prefs.usuarioId.first()
            if (id > 0 && prefs.perfilActivo.first().isNotEmpty()) {
                _usuarioActual.value = repo.obtenerUsuario(id)
            }
        }
    }

    fun login(correo: String, clave: String, rolSeleccionado: Rol, confirmacion: String = clave) {
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Ingresa tu correo y tu contraseña"
            return
        }
        // Solo se aceptan correos institucionales
        if (!correo.trim().lowercase().endsWith(DOMINIO)) {
            _error.value = "Usa tu correo $DOMINIO"
            return
        }
        // Soporte confirma su contraseña al iniciar sesión
        if (rolSeleccionado == Rol.SOPORTE && clave != confirmacion) {
            _error.value = "Las contraseñas no coinciden"
            return
        }
        viewModelScope.launch {
            val usuario = repo.login(correo.trim(), clave)
            when {
                usuario == null -> _error.value = "Correo o contraseña incorrectos"
                usuario.rol != rolSeleccionado.name ->
                    _error.value = "Esta cuenta no es de ${rolSeleccionado.etiqueta}. Cambia de pestaña"
                else -> {
                    _error.value = null
                    prefs.guardarSesion(usuario.rol, usuario.id)
                    _usuarioActual.value = usuario
                }
            }
        }
    }

    // Registro de soporte: valida, guarda en Room y vuelve al login
    fun registrarSoporte(
        codigo: String, nombre: String, apellido: String,
        correo: String, clave: String, confirmacion: String,
        alTerminar: () -> Unit
    ) {
        val correoLimpio = correo.trim()
        when {
            listOf(codigo, nombre, apellido, correoLimpio, clave, confirmacion).any { it.isBlank() } -> {
                _error.value = "Completa todos los campos"
                return
            }
            !correoLimpio.lowercase().endsWith(DOMINIO) -> {
                _error.value = "Usa tu correo $DOMINIO"
                return
            }
            clave != confirmacion -> {
                _error.value = "Las contraseñas no coinciden"
                return
            }
        }
        viewModelScope.launch {
            if (repo.correoRegistrado(correoLimpio)) {
                _error.value = "Ese correo ya está registrado"
                return@launch
            }
            repo.registrarUsuario(
                Usuario(
                    nombre = nombre.trim(),
                    apellido = apellido.trim(),
                    codigo = codigo.trim(),
                    correo = correoLimpio,
                    clave = clave,
                    rol = Rol.SOPORTE.name
                )
            )
            _error.value = null
            _aviso.value = "Cuenta creada. Ingresa con tu correo y contraseña"
            alTerminar()
        }
    }

    fun limpiarError() {
        _error.value = null
        _aviso.value = null
    }

    // Preferencias: se guardan en DataStore y el tema se entera solo
    fun cambiarModoOscuro(b: Boolean) {
        viewModelScope.launch { prefs.guardarModoOscuro(b) }
    }

    fun cambiarNotificaciones(b: Boolean) {
        viewModelScope.launch { prefs.guardarNotificaciones(b) }
    }

    fun cambiarRecibirPedidos(b: Boolean) {
        viewModelScope.launch { prefs.guardarRecibirPedidos(b) }
    }

    // Modo práctica: pasa al otro rol con su primer usuario, sin login
    fun cambiarPerfil(alTerminar: (Rol) -> Unit) {
        val actual = _usuarioActual.value ?: return
        val otro = if (actual.rol == Rol.ADMIN.name) Rol.CLIENTE else Rol.ADMIN
        viewModelScope.launch {
            val usuario = repo.primerUsuarioDeRol(otro) ?: return@launch
            prefs.guardarSesion(usuario.rol, usuario.id)
            _usuarioActual.value = usuario
            alTerminar(otro)
        }
    }

    fun cerrarSesion(alTerminar: () -> Unit) {
        viewModelScope.launch {
            prefs.cerrarSesion()
            _usuarioActual.value = null
            alTerminar()
        }
    }

    companion object {
        const val DOMINIO = "@tecsup.edu.pe"
    }
}

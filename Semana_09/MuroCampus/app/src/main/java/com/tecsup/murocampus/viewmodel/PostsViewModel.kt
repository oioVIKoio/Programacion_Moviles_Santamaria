package com.tecsup.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.murocampus.data.PostRepository
import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.NuevoPost
import com.tecsup.murocampus.model.Post
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetalleUi(val post: Post, val comentarios: List<Comentario>)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista: StateFlow<UiState<List<Post>>> = _lista.asStateFlow()

    private val _usuarioFiltro = MutableStateFlow<Int?>(null)   // null significa Todos
    val usuarioFiltro: StateFlow<Int?> = _usuarioFiltro.asStateFlow()

    init { cargar() }

    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _lista.value = UiState.Cargando
            val filtro = _usuarioFiltro.value
            (if (filtro == null) repo.listar() else repo.porUsuario(filtro))
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle: StateFlow<UiState<DetalleUi>> = _detalle.asStateFlow()

    fun abrir(id: Int) {
        viewModelScope.launch {
            _detalle.value = UiState.Cargando
            // Las dos llamadas salen a la vez; si una falla, todo el detalle muestra el error
            val resultado = coroutineScope {
                val post = async { repo.detalle(id) }
                val comentarios = async { repo.comentarios(id) }
                post.await().mapCatching { p -> DetalleUi(p, comentarios.await().getOrThrow()) }
            }
            resultado
                .onSuccess { _detalle.value = UiState.Exito(it) }
                .onFailure { _detalle.value = UiState.Error(mensajeDe(it)) }
        }
    }

    private val _publicacion = MutableStateFlow<UiState<Post>?>(null)   // null = aún no se envió
    val publicacion: StateFlow<UiState<Post>?> = _publicacion.asStateFlow()

    fun publicar(titulo: String, texto: String) {
        viewModelScope.launch {
            _publicacion.value = UiState.Cargando
            repo.crear(NuevoPost(userId = 1, title = titulo, body = texto))
                .onSuccess { _publicacion.value = UiState.Exito(it) }
                .onFailure { _publicacion.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun reiniciarPublicacion() {
        _publicacion.value = null
    }

    private val _eliminacion = MutableStateFlow<UiState<Unit>?>(null)   // null = aún no se pidió
    val eliminacion: StateFlow<UiState<Unit>?> = _eliminacion.asStateFlow()

    fun eliminar(id: Int) {
        viewModelScope.launch {
            _eliminacion.value = UiState.Cargando
            repo.borrar(id)
                .onSuccess {
                    // JSONPlaceholder no borra de verdad: se quita de la lista que ya está en pantalla
                    val actual = _lista.value
                    if (actual is UiState.Exito) {
                        _lista.value = UiState.Exito(actual.datos.filter { it.id != id })
                    }
                    _eliminacion.value = UiState.Exito(Unit)
                }
                .onFailure { _eliminacion.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun reiniciarEliminacion() {
        _eliminacion.value = null
    }
}

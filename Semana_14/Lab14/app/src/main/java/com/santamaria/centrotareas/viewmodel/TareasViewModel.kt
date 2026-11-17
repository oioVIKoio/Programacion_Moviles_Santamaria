package com.santamaria.centrotareas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santamaria.centrotareas.data.TareasRepository
import com.santamaria.centrotareas.model.CargaUi
import com.santamaria.centrotareas.model.PrimosUi
import com.santamaria.centrotareas.model.Tiempos
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TareasViewModel(private val repo: TareasRepository) : ViewModel() {

    // ----- Tarea con progreso (pasos 1 a 4) -----
    private val _mensaje = MutableStateFlow("Listo")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    // Avance de 0f a 1f; lo emite el Repository (Flow), ≈ 4 s
    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso.asStateFlow()

    // Extra del diseño: hilo en el que corre el trabajo (el mismo dato que va al Logcat)
    private val _hilo = MutableStateFlow("main")
    val hilo: StateFlow<String> = _hilo.asStateFlow()

    // Paso 4: el Job permite cancelar; corriendo bloquea el doble inicio
    private var trabajo: Job? = null
    private val _corriendo = MutableStateFlow(false)
    val corriendo: StateFlow<Boolean> = _corriendo.asStateFlow()

    fun tareaLarga() {
        if (_corriendo.value) return
        _corriendo.value = true
        trabajo = viewModelScope.launch {
            try {
                _progreso.value = 0f
                _mensaje.value = "Trabajando..."
                Log.d("HILO", "Antes: ${Thread.currentThread().name}")
                // El Repository emite el avance desde Default; aquí se recoge en el hilo principal
                repo.tareaConProgreso().collect { avance ->
                    _hilo.value = avance.hilo
                    _progreso.value = avance.progreso
                }
                Log.d("HILO", "Despues: ${Thread.currentThread().name}")
                _mensaje.value = "Terminó"
            } finally {
                // Termine bien o cancelada: vuelve a habilitar Iniciar
                _hilo.value = "main"
                _corriendo.value = false
            }
        }
    }

    fun cancelar() {
        trabajo?.cancel()
        _mensaje.value = "Cancelado"
    }

    // ----- Contar primos (paso 5) -----
    private val _primos = MutableStateFlow<PrimosUi>(PrimosUi.Inicial)
    val primos: StateFlow<PrimosUi> = _primos.asStateFlow()

    private var trabajoPrimos: Job? = null

    fun contar(hasta: Int) {
        if (_primos.value is PrimosUi.Calculando) return
        _primos.value = PrimosUi.Calculando(0)
        trabajoPrimos = viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                var cantidad = 0
                repo.contarPrimos(hasta).collect { avance ->
                    cantidad = avance.cantidad
                    _primos.value = PrimosUi.Calculando(avance.porcentaje)
                }
                _primos.value = PrimosUi.Listo(cantidad, System.currentTimeMillis() - inicio)
            } catch (e: CancellationException) {
                _primos.value = PrimosUi.Cancelado
                throw e // sin relanzarla, la cancelación se rompe
            }
        }
    }

    fun cancelarPrimos() {
        trabajoPrimos?.cancel()
    }

    // ----- Dos cargas (pasos 6 y 7) -----
    private val _carga = MutableStateFlow<CargaUi>(CargaUi.Inicial)
    val carga: StateFlow<CargaUi> = _carga.asStateFlow()

    private var trabajoCarga: Job? = null

    // Mejora: se guardan los tiempos de cada modo para compararlos en pantalla
    private val _tiempos = MutableStateFlow(Tiempos())
    val tiempos: StateFlow<Tiempos> = _tiempos.asStateFlow()

    // Paso 7: con el interruptor activo, los pedidos fallan con IOException("Sin conexión")
    private val _simularError = MutableStateFlow(false)
    val simularError: StateFlow<Boolean> = _simularError.asStateFlow()

    fun cambiarSimularError(valor: Boolean) {
        _simularError.value = valor
    }

    // Una por una: espera el perfil (2 s) y después los pedidos (3 s) ≈ 5 s
    fun cargarUnaPorUna() {
        if (_carga.value is CargaUi.Cargando) return
        _carga.value = CargaUi.Cargando
        trabajoCarga = viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val perfil = repo.cargarPerfil()
                val pedidos = repo.cargarPedidos(_simularError.value)
                val ms = System.currentTimeMillis() - inicio
                _tiempos.update { it.copy(unaPorUna = ms) }
                _carga.value = CargaUi.Exito("Una por una", perfil, pedidos.size, ms)
            } catch (e: CancellationException) {
                throw e // primero deja pasar la cancelación
            } catch (e: Exception) {
                _carga.value = CargaUi.Error("No se pudo cargar: " + e.message)
            }
        }
    }

    // A la vez: lanza las dos con async y espera ambas con await() ≈ max(2 s, 3 s) = 3 s
    fun cargarALaVez() {
        if (_carga.value is CargaUi.Cargando) return
        _carga.value = CargaUi.Cargando
        trabajoCarga = viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = coroutineScope {
                    val a = async { repo.cargarPerfil() }
                    val b = async { repo.cargarPedidos(_simularError.value) }
                    Pair(a.await(), b.await())
                }
                val ms = System.currentTimeMillis() - inicio
                _tiempos.update { it.copy(aLaVez = ms) }
                _carga.value = CargaUi.Exito("A la vez", perfil, pedidos.size, ms)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _carga.value = CargaUi.Error("No se pudo cargar: " + e.message)
            }
        }
    }
}

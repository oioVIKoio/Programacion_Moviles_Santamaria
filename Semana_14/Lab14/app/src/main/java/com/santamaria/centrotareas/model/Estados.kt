package com.santamaria.centrotareas.model

sealed interface PrimosUi {
    data object Inicial : PrimosUi
    data class Calculando(val porcentaje: Int) : PrimosUi
    data class Listo(val cantidad: Int, val milisegundos: Long) : PrimosUi
    data object Cancelado : PrimosUi
}

sealed interface CargaUi {
    data object Inicial : CargaUi
    data object Cargando : CargaUi
    data class Exito(
        val modo: String,
        val perfil: String,
        val pedidos: Int,
        val milisegundos: Long
    ) : CargaUi
    data class Error(val mensaje: String) : CargaUi
}

/** Un avance de la tarea con progreso: valor de 0f a 1f y el hilo que lo produjo. */
data class Avance(val progreso: Float, val hilo: String)

/** Un avance del conteo de primos: porcentaje (0 a 100) y primos encontrados hasta ahora. */
data class AvancePrimos(val porcentaje: Int, val cantidad: Int)

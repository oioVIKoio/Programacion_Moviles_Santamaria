package com.santamaria.saludpluscitas.data.model

// Reto 13: modelo propio. fecha en formato ISO, como en Cita.
// listo = false significa que el examen aún está en proceso.
data class Resultado(
    val id: Int,
    val examen: String,
    val medicoId: Int,
    val fecha: String,
    val listo: Boolean,
    val detalle: String
)

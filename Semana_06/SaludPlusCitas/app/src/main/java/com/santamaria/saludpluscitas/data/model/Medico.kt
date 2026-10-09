package com.santamaria.saludpluscitas.data.model

// profesion es el texto que se muestra bajo el nombre ("Ginecóloga").
// sedeId indica dónde atiende; la dirección sale de la Sede.
data class Medico(
    val id: Int,
    val nombre: String,
    val especialidadId: Int,
    val profesion: String,
    val cmp: String,
    val calificacion: Double,
    val resenas: Int,
    val disponibilidad: String,
    val sedeId: Int,
    val telefono: String
)

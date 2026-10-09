package com.santamaria.saludpluscitas.data.model

// Local de la clínica. El paciente elige la sede antes que al médico.
data class Sede(
    val id: Int,
    val nombre: String,
    val distrito: String,
    val direccion: String,
    val telefono: String,
    val horario: String
)

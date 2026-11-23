package com.tecsup.murocampus.model

import kotlinx.serialization.Serializable

// [E] Ejemplo resuelto: Usuario

@Serializable
data class Usuario(
    val id: Int,
    val name: String,
    val email: String
)

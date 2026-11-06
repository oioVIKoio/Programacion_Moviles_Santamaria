package com.tecsup.authfirebaseappsantamaria

// Documento de la colección "cursos" en Firestore
data class Curso(
    val id: String = "",
    val nombre: String = "",
    val docente: String = "",
    val creditos: Int = 0,
    val userId: String = ""
)

package com.santamaria.navlab.models


data class Student(
    val id: Int,
    val nombre: String,
    val carrera: String,
    val correo: String,
    val facultad: String,
    val biografia: String
)

object StudentProvider {
    val students = listOf(
        Student(
            id = 1,
            nombre = "Juan León",
            carrera = "Ingeniería de Sistemas",
            correo = "juan.leon@example.com",
            facultad = "Ingeniería y Tecnología",
            biografia = "Estudiante destacado con interés en desarrollo Android."
        ),
        Student(
            id = 2,
            nombre = "María García",
            carrera = "Arquitectura",
            correo = "maria.garcia@example.com",
            facultad = "Arquitectura y Diseño",
            biografia = "Estudiante interesada en diseño y planificación urbana."
        ),
        Student(
            id = 3,
            nombre = "Carlos Pérez",
            carrera = "Medicina",
            correo = "carlos.perez@example.com",
            facultad = "Ciencias de la Salud",
            biografia = "Estudiante interesado en investigación y salud."
        ),
        Student(
            id = 4,
            nombre = "Ana López",
            carrera = "Derecho",
            correo = "ana.lopez@example.com",
            facultad = "Derecho",
            biografia = "Estudiante interesada en derecho corporativo."
        ),
        Student(
            id = 5,
            nombre = "Luis Ramírez",
            carrera = "Administración",
            correo = "luis.ramirez@example.com",
            facultad = "Negocios",
            biografia = "Estudiante interesado en gestión empresarial."
        )
    )
}

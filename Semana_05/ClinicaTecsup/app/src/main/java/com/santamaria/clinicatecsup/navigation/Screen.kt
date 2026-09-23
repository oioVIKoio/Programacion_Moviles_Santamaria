package com.santamaria.clinicatecsup.navigation


sealed class Screen(val route: String) {

    object Home : Screen("home")

    object DoctorDetail : Screen("doctor/{doctorId}") {
        fun createRoute(doctorId: Int): String = "doctor/$doctorId"
    }

    object Appointment : Screen("appointment/{doctorId}") {
        fun createRoute(doctorId: Int): String = "appointment/$doctorId"
    }

    object Confirmation : Screen(
        "confirmation/{doctorId}/{fecha}/{hora}"
    ) {
        fun createRoute(
            doctorId: Int,
            fecha: String,
            hora: String
        ): String {
            return "confirmation/$doctorId/$fecha/$hora"
        }
    }

    object Appointments : Screen("appointments")

    object MedicalHistory : Screen("medical_history")
}
package com.santamaria.smartfit.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object ClassDetail : Screen("class_detail/{classId}") {
        fun createRoute(classId: Int): String {
            return "class_detail/$classId"
        }
    }

    object Confirmation : Screen("confirmation/{classId}") {
        fun createRoute(classId: Int): String {
            return "confirmation/$classId"
        }
    }

    object Reservations : Screen("reservations")

    object Routines : Screen("routines")

    object Profile : Screen("profile")
}
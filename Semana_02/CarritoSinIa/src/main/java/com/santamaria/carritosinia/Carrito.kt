package com.santamaria.carritosinia

data class Producto(
    val id: Int,
    var nombre: String,
    var precio: Double,
    var stock: Int
)

fun main() {
    println("=== CARRITO DE PRODUCTOS ===")
}
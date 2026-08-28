package com.santamaria.carritosinia

import java.util.Scanner

data class Producto(
    val nombre: String,
    val precio: Double,
    val cantidad: Int
)

fun main() {

    val scanner = Scanner(System.`in`)

    val productos = mutableListOf<Producto>()

    println("========================================")
    println("     CARRITO DE COMPRAS - TIENDA TECSUP")
    println("========================================")

    print("Cliente: ")
    val cliente = scanner.nextLine()

    print("¿Cuántos productos desea registrar?: ")

    if (scanner.hasNextInt()) {

        val cantidadProductos = scanner.nextInt()
        scanner.nextLine()

        for (i in 1..cantidadProductos) {

            println("\nProducto $i")

            print("Nombre: ")
            val nombre = scanner.nextLine()

            print("Precio: ")

            if (!scanner.hasNextDouble()) {
                println("Precio inválido.")
                scanner.close()
                return
            }

            val precio = scanner.nextDouble()
            scanner.nextLine()

            print("Cantidad: ")

            if (!scanner.hasNextInt()) {
                println("Cantidad inválida.")
                scanner.close()
                return
            }

            val cantidad = scanner.nextInt()
            scanner.nextLine()

            productos.add(
                Producto(nombre, precio, cantidad)
            )

            println("Producto agregado: $nombre")
        }

        println("\nCliente: $cliente")
        println("Productos registrados: ${productos.size}")

    } else {
        println("Cantidad inválida. Debe ingresar un número entero.")
        scanner.nextLine()
    }

    scanner.close()
}
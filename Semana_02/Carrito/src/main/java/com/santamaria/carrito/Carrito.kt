package com.santamaria.carrito

import java.util.Scanner

data class Producto(
    val id: Int,
    var nombre: String,
    var precio: Double,
    var stock: Int
)

class ProductoService {

    private val productos = mutableListOf<Producto>()

    fun agregar(producto: Producto) {
        productos.add(producto)
    }

    fun listar(): List<Producto> {
        return productos
    }

    fun buscar(id: Int): Producto? {
        return productos.find { it.id == id }
    }

    fun eliminar(id: Int): Boolean {
        return productos.removeIf { it.id == id }
    }
}

fun main() {

    val scanner = Scanner(System.`in`)
    val service = ProductoService()

    var opcion: Int

    do {
        println(
            """
            === PRODUCTOS ===
            1. Agregar
            2. Listar
            3. Buscar
            4. Eliminar
            0. Salir
            """.trimIndent()
        )

        println("Opción: ")

        if (scanner.hasNextInt()) {
            opcion = scanner.nextInt()
            scanner.nextLine()
        } else {
            scanner.nextLine()
            opcion = -1
            println("Por favor, ingrese un número válido.")
            continue
        }

        when (opcion) {

            1 -> {
                println("ID: ")
                val id = scanner.nextInt()
                scanner.nextLine()

                println("Nombre: ")
                val nombre = scanner.nextLine()

                println("Precio: ")
                val precio = scanner.nextDouble()

                println("Stock: ")
                val stock = scanner.nextInt()
                scanner.nextLine()

                service.agregar(
                    Producto(id, nombre, precio, stock)
                )
            }

            2 -> {
                service.listar().forEach {
                    println(it)
                }
            }

            3 -> {
                println("ID: ")
                val id = scanner.nextInt()

                println(
                    service.buscar(id)
                        ?: "Producto no encontrado"
                )
            }

            4 -> {
                println("ID: ")
                val id = scanner.nextInt()

                if (service.eliminar(id)) {
                    println("Producto eliminado")
                } else {
                    println("Producto no encontrado")
                }
            }

            0 -> {
                println("Saliendo...")
            }

            else -> {
                println("Opción inválida.")
            }
        }

    } while (opcion != 0)
}
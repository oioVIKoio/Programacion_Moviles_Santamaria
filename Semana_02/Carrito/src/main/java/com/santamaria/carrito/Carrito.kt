package com.santamaria.carrito

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

        val entrada = readln().toIntOrNull()

        if (entrada != null) {
            opcion = entrada
        } else {
            opcion = -1
            println("Por favor, ingrese un número válido.")
            continue
        }

        when (opcion) {

            1 -> {
                println("ID: ")
                val id = readln().toInt()

                println("Nombre: ")
                val nombre = readln()

                println("Precio: ")
                val precio = readln().toDouble()

                println("Stock: ")
                val stock = readln().toInt()

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
                val id = readln().toInt()

                println(
                    service.buscar(id)
                        ?: "Producto no encontrado"
                )
            }

            4 -> {
                println("ID: ")
                val id = readln().toInt()

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
package com.santamaria.carritosinia

import java.util.Scanner

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun calcularSubtotal(productos: List<Producto>): Double {

    var subtotal = 0.0

    for (producto in productos) {
        subtotal += producto.precio * producto.cantidad
    }

    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun mostrarDetalle(productos: List<Producto>) {

    println("\n--------- DETALLE DEL CARRITO ---------")

    var i = 1

    for (producto in productos) {

        val importe = producto.precio * producto.cantidad

        println(
            String.format(
                "%d. %-20s x%d S/ %8.2f",
                i,
                producto.nombre,
                producto.cantidad,
                importe
            )
        )

        i++
    }

    println("---------------------------------------")
}

fun calcularDescuento(total: Double): Double {

    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

fun main() {

    val scanner = Scanner(System.`in`)
    val carrito = mutableListOf<Producto>()

    println("=".repeat(50))
    println("     CARRITO DE COMPRAS - TIENDA TECSUP")
    println("=".repeat(50))

    print("Cliente: ")
    val nombreCliente = scanner.nextLine()

    print("¿Cuántos productos desea registrar?: ")
    val cantidadProductos = scanner.nextLine().toIntOrNull()

    if (cantidadProductos == null || cantidadProductos <= 0) {
        println("Cantidad inválida.")
        scanner.close()
        return
    }

    for (i in 1..cantidadProductos) {

        println("\nProducto $i")

        print("Nombre: ")
        val nombre = scanner.nextLine()

        print("Precio: ")
        val precio = scanner.nextLine().toDoubleOrNull()

        if (precio == null || precio <= 0) {
            println("Precio inválido.")
            scanner.close()
            return
        }

        print("Cantidad: ")
        val cantidad = scanner.nextLine().toIntOrNull()

        if (cantidad == null || cantidad <= 0) {
            println("Cantidad inválida.")
            scanner.close()
            return
        }

        val producto = Producto(
            nombre,
            precio,
            cantidad
        )

        carrito.add(producto)

        println("Producto agregado: ${producto.nombre}")
    }

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    mostrarDetalle(carrito)

    println("Cantidad de productos : ${carrito.size}")
    println(String.format("Subtotal              : S/ %8.2f", subtotal))
    println(String.format("IGV (18%%)             : S/ %8.2f", igv))
    println(String.format("TOTAL A PAGAR         : S/ %8.2f", total))

    println("---------------------------------------")

    val masCaro = carrito.maxByOrNull { it.precio }

    if (masCaro != null) {
        println(
            "Producto más caro: ${masCaro.nombre} " +
                    String.format("(S/ %.2f)", masCaro.precio)
        )
    }

    val descuento = calcularDescuento(total)
    val totalConDescuento = total - descuento

    when {
        total > 5000 -> {
            println("Descuento aplicado    : 10%")
            println(String.format("Descuento             : S/ %8.2f", descuento))
        }

        total > 3000 -> {
            println("Descuento aplicado    : 5%")
            println(String.format("Descuento             : S/ %8.2f", descuento))
        }

        else -> {
            println("Descuento aplicado    : No aplica")
        }
    }

    println(
        String.format(
            "TOTAL CON DESCUENTO   : S/ %8.2f",
            totalConDescuento
        )
    )

    println("=".repeat(50))
    println("Gracias por su compra, $nombreCliente!")
    println("=".repeat(50))

    scanner.close()
}
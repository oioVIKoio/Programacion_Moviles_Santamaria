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

    println("-".repeat(50))
    println("     CARRITO DE COMPRAS - TIENDA TECSUP")
    println("-".repeat(50))

    print("Cliente: ")
    val cliente = scanner.nextLine()

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

        val producto = Producto(nombre, precio, cantidad)
        productos.add(producto)

        println("Producto agregado: $nombre")
    }

    println("\n--------- DETALLE DEL CARRITO ---------")

    var subtotal = 0.0
    var cantidadTotal = 0

    for (producto in productos) {

        val importe = producto.precio * producto.cantidad

        subtotal = subtotal + importe
        cantidadTotal = cantidadTotal + producto.cantidad

        println(
            "${producto.nombre} x${producto.cantidad}  S/ %.2f".format(importe)
        )
    }

    val igv = subtotal * 0.18
    val total = subtotal + igv
    var productoMasCaro = productos[0]

    for (producto in productos) {
        if (producto.precio > productoMasCaro.precio) {
            productoMasCaro = producto
        }
    }

    var descuento = 0.0

    if (total > 3000) {
        descuento = total * 0.05
    }

    val totalConDescuento = total - descuento
    println("=".repeat(50))
    println("Cliente               : $cliente")
    println("Cantidad de productos : $cantidadTotal")
    println("Subtotal              : S/ %.2f".format(subtotal))
    println("IGV (18%)             : S/ ${"%.2f".format(igv)}")
    println("TOTAL A PAGAR         : S/ %.2f".format(total))
    println("=".repeat(50))
    println("Producto más caro     : ${productoMasCaro.nombre}")
    println("Precio                : S/ %.2f".format(productoMasCaro.precio))

    if (descuento > 0) {
        println("Descuento aplicado    : 5%")
        println("Descuento             : S/ %.2f".format(descuento))
    } else {
        println("Descuento aplicado    : No aplica")
    }

    println("TOTAL CON DESCUENTO   : S/ %.2f".format(totalConDescuento))
    println("=".repeat(50))
    println("Gracias por su compra, $cliente!")
    println("=".repeat(50))

    scanner.close()
}
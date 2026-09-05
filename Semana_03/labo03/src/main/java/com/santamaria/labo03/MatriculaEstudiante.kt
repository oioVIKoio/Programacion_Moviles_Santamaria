package com.santamaria.labo03

fun main() {

    println("=====================================")
    println("       SISTEMA DE MATRÍCULA")
    println("=====================================")

    // =========================================
    // DATOS DEL ESTUDIANTE
    // =========================================

    print("Nombre del estudiante: ")
    val estudiante = readln()

    if (estudiante.isBlank()) {
        println("Nombre incorrecto")
        return
    }

    // =========================================
    // TURNO
    // =========================================

    println("\n---------- TURNO ----------")
    println("1. Mañana - 10% descuento")
    println("2. Tarde  - 15% descuento")
    println("3. Noche  - 20% descuento")

    print("Seleccione el turno: ")
    val opcionTurno = readln().toIntOrNull()

    if (opcionTurno == null || opcionTurno !in 1..3) {
        println("Turno incorrecto")
        return
    }

    val turno: String
    val porcentajeDescuento: Double

    if (opcionTurno == 1) {
        turno = "MAÑANA"
        porcentajeDescuento = 0.10
    } else if (opcionTurno == 2) {
        turno = "TARDE"
        porcentajeDescuento = 0.15
    } else {
        turno = "NOCHE"
        porcentajeDescuento = 0.20
    }

    // =========================================
    // VALOR DEL CRÉDITO
    // =========================================

    print("\nValor de cada crédito: S/ ")
    val valorCredito = readln().toDoubleOrNull()

    if (valorCredito == null || valorCredito <= 0) {
        println("Valor del crédito incorrecto")
        return
    }

    // =========================================
    // CURSOS
    // =========================================

    print("\nCantidad de cursos: ")
    val cantidadCursos = readln().toIntOrNull()

    if (cantidadCursos == null || cantidadCursos <= 0) {
        println("Cantidad de cursos incorrecta")
        return
    }

    val nombresCursos = Array(cantidadCursos) { "" }
    val creditosCursos = IntArray(cantidadCursos)
    val costosCursos = DoubleArray(cantidadCursos)

    var totalCreditos = 0
    var totalCursos = 0.0

    for (i in 1..cantidadCursos) {

        println("\n--- CURSO $i ---")

        print("Nombre del curso: ")
        nombresCursos[i - 1] = readln()

        if (nombresCursos[i - 1].isBlank()) {
            println("Nombre del curso incorrecto")
            return
        }

        print("Cantidad de créditos: ")
        val creditos = readln().toIntOrNull()

        if (creditos == null || creditos <= 0) {
            println("Cantidad de créditos incorrecta")
            return
        }

        creditosCursos[i - 1] = creditos

        costosCursos[i - 1] =
            creditosCursos[i - 1] * valorCredito

        totalCreditos += creditosCursos[i - 1]
        totalCursos += costosCursos[i - 1]
    }

    // =========================================
    // CARGA ACADÉMICA
    // =========================================

    val cargaAcademica: String

    if (totalCreditos <= 12) {
        cargaAcademica = "M.R"
    } else if (totalCreditos <= 18) {
        cargaAcademica = "CARGA COMPLETA"
    } else {
        cargaAcademica = "REQUIERE AUTORIZACION"
    }

    // =========================================
    // MATRÍCULA
    // =========================================

    val matricula = 500.00

    // =========================================
    // DESCUENTO DE MATRÍCULA
    // =========================================

    val descuento = matricula * porcentajeDescuento

    val matriculaDescuento = matricula - descuento

    // =========================================
    // SUBTOTAL
    // =========================================

    val subtotal = totalCursos + matriculaDescuento

    // =========================================
    // RESULTADO
    // =========================================

    println("\n=====================================")
    println("          RESULTADO")
    println("=====================================")

    println("NOMBRE: $estudiante")
    println("TURNO: $turno")

    println("-------------------------------------")
    println("CURSO\t\tCREDITOS\tCOSTO")
    println("-------------------------------------")

    for (i in 1..cantidadCursos) {

        println(
            "${nombresCursos[i - 1]}\t\t" +
                    "${creditosCursos[i - 1]}\t\t" +
                    "S/ ${"%.2f".format(costosCursos[i - 1])}"
        )
    }

    println("-------------------------------------")
    println("CURSOS MATRICULADOS: $cantidadCursos")
    println("TOTAL CREDITOS: $totalCreditos")
    println("CARGA ACADÉMICA: $cargaAcademica")
    println("MATRÍCULA: S/ ${"%.2f".format(matricula)}")
    println("DESCUENTO: S/ ${"%.2f".format(descuento)}")
    println("SUBTOTAL: S/ ${"%.2f".format(subtotal)}")
    println("TOTAL A PAGAR: S/ ${"%.2f".format(subtotal)}")
    println("=====================================")
}
package com.yldefonso.estudiante

import java.util.Scanner

fun main() {

    val scanner = Scanner(System.`in`)

    // DATOS DE ENTRADA

    print("Nombre del estudiante: ")
    val estudiante = scanner.nextLine()

    print("Cantidad de cursos: ")
    val cantidadCursos = scanner.nextInt()

    print("Valor de cada credito: S/ ")
    val valorCredito = scanner.nextDouble()
    scanner.nextLine()

    val nombresCursos = Array(cantidadCursos) { "" }
    val creditosCursos = IntArray(cantidadCursos)
    val costosCursos = DoubleArray(cantidadCursos)

    var totalCreditos = 0
    var totalPagar = 0.0

    // REGISTRO DE CURSOS

    for (i in 1..cantidadCursos) {

        println("\n--- CURSO $i ---")

        print("Nombre del curso: ")
        nombresCursos[i - 1] = scanner.nextLine()

        print("Cantidad de creditos: ")
        creditosCursos[i - 1] = scanner.nextInt()
        scanner.nextLine()

        costosCursos[i - 1] = creditosCursos[i - 1] * valorCredito

        totalCreditos += creditosCursos[i - 1]
        totalPagar += costosCursos[i - 1]
    }

    // CALCULOS

    val cargaAcademica: String

    if (totalCreditos <= 12) {
        cargaAcademica = "M.R"
    } else if (totalCreditos <= 18) {
        cargaAcademica = "CARGA COMPLETA"
    } else {
        cargaAcademica = "REQUIERE AUTORIZACION"
    }

    val cantidadCuotas: Int

    if (totalPagar > 2300) {
        cantidadCuotas = 3
    } else {
        cantidadCuotas = 2
    }

    val valorCuota = totalPagar / cantidadCuotas
}
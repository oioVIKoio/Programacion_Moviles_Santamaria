package com.yldefonso.estudiante

fun main() {

    print("Nombre del estudiante: ")
    val estudiante = readln()

    if (estudiante.isBlank()) {
        println("Nombre incorrecto")
        return
    }

    print("Cantidad de cursos: ")
    val cantidadCursos = readln().toIntOrNull()

    if (cantidadCursos == null || cantidadCursos <= 0) {
        println("Cantidad de cursos incorrecta")
        return
    }

    print("Valor de cada credito: S/ ")
    val valorCredito = readln().toDoubleOrNull()

    if (valorCredito == null || valorCredito <= 0) {
        println("Valor del credito incorrecto")
        return
    }

    val nombresCursos = Array(cantidadCursos) { "" }
    val creditosCursos = IntArray(cantidadCursos)
    val costosCursos = DoubleArray(cantidadCursos)

    var totalCreditos = 0
    var totalPagar = 0.0

    for (i in 1..cantidadCursos) {

        println("\n--- CURSO $i ---")

        print("Nombre del curso: ")
        nombresCursos[i - 1] = readln()

        if (nombresCursos[i - 1].isBlank()) {
            println("Nombre del curso incorrecto")
            return
        }

        print("Cantidad de creditos: ")
        val creditos = readln().toIntOrNull()

        if (creditos == null || creditos <= 0) {
            println("Cantidad de creditos incorrecta")
            return
        }

        creditosCursos[i - 1] = creditos

        costosCursos[i - 1] = creditosCursos[i - 1] * valorCredito

        totalCreditos += creditosCursos[i - 1]
        totalPagar += costosCursos[i - 1]
    }

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

    println("\n========== RESULTADO FINAL ==========")
    println("ESTUDIANTE: $estudiante")
    println("-------------------------------------")
    println("CURSO\t\tCREDITOS\tCOSTO")
    println("-------------------------------------")

    for (i in 1..cantidadCursos) {
        println(
            "${nombresCursos[i - 1]}\t\t${creditosCursos[i - 1]}\t\tS/ ${"%.2f".format(costosCursos[i - 1])}"
        )
    }

    println("-------------------------------------")
    println("CURSOS MATRICULADOS: $cantidadCursos")
    println("TOTAL CREDITOS: $totalCreditos")
    println("TOTAL A PAGAR: S/ ${"%.2f".format(totalPagar)}")
    println("CARGA ACADEMICA: $cargaAcademica")
    println("FORMA DE PAGO: $cantidadCuotas CUOTAS DE S/ ${"%.2f".format(valorCuota)}")
    println("=====================================")
}
package com.santamaria.labo03

fun main() {
    println("=====================================")
    println("       SISTEMA DE MATRÍCULA")
    println("=====================================")

    print("Aforo máximo: ")
    val aforoMaximo = readln().toIntOrNull()

    if (aforoMaximo == null || aforoMaximo <= 0) {
        println("Aforo incorrecto")
        return
    }

    print("Cantidad de estudiantes a registrar: ")
    val cantidadEstudiantes = readln().toIntOrNull()

    if (cantidadEstudiantes == null || cantidadEstudiantes <= 0) {
        println("Cantidad de estudiantes incorrecta")
        return
    }

    if (cantidadEstudiantes > aforoMaximo) {
        println("No se puede realizar el registro.")
        println("La cantidad de estudiantes supera el aforo máximo.")
        return
    }

    var estudiantesRegistrados = 0

    for (numeroEstudiante in 1..cantidadEstudiantes) {

        println("\n=====================================")
        println("       ESTUDIANTE $numeroEstudiante")
        println("=====================================")

        print("Nombre del estudiante: ")
        val estudiante = readln()

        if (estudiante.isBlank()) {
            println("Nombre incorrecto")
            return
        }

        println("\n------ CATEGORÍA DE ESTUDIANTE ------")
        println("1. Ordinario")
        println("2. Becado")
        print("Seleccione la categoría: ")

        val opcionCategoria = readln().toIntOrNull()

        if (opcionCategoria == null || opcionCategoria !in 1..2) {
            println("Categoría incorrecta")
            return
        }

        val categoria: String
        val matricula: Double

        if (opcionCategoria == 1) {
            categoria = "ORDINARIO"
            matricula = 500.00
        } else {
            categoria = "BECADO"
            matricula = 0.00
        }

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

        print("\nValor de cada crédito: S/ ")
        val valorCredito = readln().toDoubleOrNull()

        if (valorCredito == null || valorCredito <= 0) {
            println("Valor del crédito incorrecto")
            return
        }

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
            costosCursos[i - 1] = creditos * valorCredito

            totalCreditos += creditos
            totalCursos += costosCursos[i - 1]
        }

        val cargaAcademica: String

        if (totalCreditos <= 12) {
            cargaAcademica = "M.R"
        } else if (totalCreditos <= 18) {
            cargaAcademica = "CARGA COMPLETA"
        } else {
            cargaAcademica = "REQUIERE AUTORIZACION"
        }

        val descuento = matricula * porcentajeDescuento
        val matriculaDescuento = matricula - descuento

        val subtotal = totalCursos + matriculaDescuento

        val igv = subtotal * 0.18

        val totalPagar = subtotal + igv

        println("\n-------------------------------------")
        println("           RESUMEN")
        println("-------------------------------------")
        println("NOMBRE: $estudiante")
        println("CATEGORÍA: $categoria")
        println("TURNO: $turno")

        for (i in 1..cantidadCursos) {
            println(
                "${nombresCursos[i - 1]} - " +
                        "${creditosCursos[i - 1]} créditos - " +
                        "S/ ${"%.2f".format(costosCursos[i - 1])}"
            )
        }

        println("-------------------------------------")
        println("TOTAL CREDITOS: $totalCreditos")
        println("CARGA ACADÉMICA: $cargaAcademica")
        println("MATRÍCULA: S/ ${"%.2f".format(matricula)}")
        println("DESCUENTO: S/ ${"%.2f".format(descuento)}")
        println("MATRÍCULA CON DESCUENTO: S/ ${"%.2f".format(matriculaDescuento)}")
        println("SUBTOTAL: S/ ${"%.2f".format(subtotal)}")
        println("IGV (18%): S/ ${"%.2f".format(igv)}")
        println("TOTAL A PAGAR: S/ ${"%.2f".format(totalPagar)}")

        estudiantesRegistrados++

        println("-------------------------------------")
        println("ESTUDIANTE REGISTRADO CORRECTAMENTE")
    }

    val vacantes = aforoMaximo - estudiantesRegistrados

    println("\n=====================================")
    println("          REPORTE DE AFORO")
    println("=====================================")
    println("AFORO MÁXIMO: $aforoMaximo")
    println("ESTUDIANTES REGISTRADOS: $estudiantesRegistrados")
    println("VACANTES DISPONIBLES: $vacantes")
    println("=====================================")
}
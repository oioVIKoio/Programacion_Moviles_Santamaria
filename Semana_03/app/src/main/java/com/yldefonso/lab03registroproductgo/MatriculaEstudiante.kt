package com.yldefonso.lab03registroproductgo

fun main() {

    println("=====================================")
    println("       SISTEMA DE MATRÍCULA")
    println("             Y AFORO")
    println("=====================================")

    print("Aforo máximo: ")
    val aforo = readln().toIntOrNull()

    if (aforo == null || aforo <= 0) {
        println("Aforo incorrecto")
        return
    }

    print("Cantidad de estudiantes a matricular: ")
    val cantidadEstudiantes = readln().toIntOrNull()

    if (cantidadEstudiantes == null || cantidadEstudiantes <= 0) {
        println("Cantidad de estudiantes incorrecta")
        return
    }

    if (cantidadEstudiantes > aforo) {
        println("La cantidad de estudiantes supera el aforo")
        return
    }

    print("Valor de cada crédito: S/ ")
    val valorCredito = readln().toDoubleOrNull()

    if (valorCredito == null || valorCredito <= 0) {
        println("Valor del crédito incorrecto")
        return
    }

    // =========================================
    // DATOS PARA EL REPORTE
    // =========================================

    val estudiantes = Array(cantidadEstudiantes) { "" }
    val turnos = Array(cantidadEstudiantes) { "" }
    val categorias = Array(cantidadEstudiantes) { "" }
    val totalesPagar = DoubleArray(cantidadEstudiantes)

    val reporteFinal = StringBuilder()

    // =========================================
    // REGISTRO DE ESTUDIANTES
    // =========================================

    for (estudianteActual in 1..cantidadEstudiantes) {

        val posicion = estudianteActual - 1

        println("\n=====================================")
        println("          MATRÍCULA $estudianteActual")
        println("=====================================")

        // =====================================
        // DATOS DEL ESTUDIANTE
        // =====================================

        print("Nombre del estudiante: ")
        val estudiante = readln()

        if (estudiante.isBlank()) {
            println("Nombre incorrecto")
            return
        }

        estudiantes[posicion] = estudiante

        // =====================================
        // TURNO
        // =====================================

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

        turnos[posicion] = turno

        // =====================================
        // CATEGORÍA
        // =====================================

        println("\n-------- CATEGORÍA --------")
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

        categorias[posicion] = categoria

        // =====================================
        // CURSOS
        // =====================================

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

        // =====================================
        // CARGA ACADÉMICA
        // =====================================

        val cargaAcademica: String

        if (totalCreditos <= 12) {
            cargaAcademica = "M.R"
        } else if (totalCreditos <= 18) {
            cargaAcademica = "CARGA COMPLETA"
        } else {
            cargaAcademica = "REQUIERE AUTORIZACION"
        }

        // =====================================
        // DESCUENTO DE MATRÍCULA
        // =====================================

        val descuento = matricula * porcentajeDescuento

        val matriculaDescuento = matricula - descuento

        // =====================================
        // SUBTOTAL
        // =====================================

        /*
         * El becado no paga matrícula,
         * pero SÍ paga sus cursos.
         */

        val subtotal = totalCursos + matriculaDescuento

        // =====================================
        // IGV
        // =====================================

        val igv = subtotal * 0.18

        val totalPagar = subtotal + igv

        totalesPagar[posicion] = totalPagar

        // =====================================
        // CUOTAS
        // =====================================

        val cantidadCuotas: Int

        if (totalPagar > 2300) {
            cantidadCuotas = 3
        } else {
            cantidadCuotas = 2
        }

        val valorCuota = totalPagar / cantidadCuotas

        // =====================================
        // GUARDAR REPORTE DEL ESTUDIANTE
        // =====================================

        reporteFinal.append("\n")
        reporteFinal.append("=====================================\n")
        reporteFinal.append("          ESTUDIANTE ${estudianteActual}\n")
        reporteFinal.append("=====================================\n")
        reporteFinal.append("NOMBRE: $estudiante\n")
        reporteFinal.append("TURNO: $turno\n")
        reporteFinal.append("CATEGORÍA: $categoria\n")
        reporteFinal.append("-------------------------------------\n")
        reporteFinal.append("CURSO\t\tCREDITOS\tCOSTO\n")
        reporteFinal.append("-------------------------------------\n")

        for (i in 1..cantidadCursos) {

            reporteFinal.append(
                "${nombresCursos[i - 1]}\t\t" +
                        "${creditosCursos[i - 1]}\t\t" +
                        "S/ ${"%.2f".format(costosCursos[i - 1])}\n"
            )
        }

        reporteFinal.append("-------------------------------------\n")
        reporteFinal.append("CURSOS MATRICULADOS: $cantidadCursos\n")
        reporteFinal.append("TOTAL CREDITOS: $totalCreditos\n")
        reporteFinal.append(
            "MATRÍCULA: S/ ${"%.2f".format(matricula)}\n"
        )
        reporteFinal.append(
            "DESCUENTO: S/ ${"%.2f".format(descuento)}\n"
        )
        reporteFinal.append(
            "SUBTOTAL: S/ ${"%.2f".format(subtotal)}\n"
        )
        reporteFinal.append(
            "IGV (18%): S/ ${"%.2f".format(igv)}\n"
        )
        reporteFinal.append(
            "TOTAL A PAGAR: S/ ${"%.2f".format(totalPagar)}\n"
        )
        reporteFinal.append(
            "CARGA ACADÉMICA: $cargaAcademica\n"
        )
        reporteFinal.append(
            "FORMA DE PAGO: $cantidadCuotas CUOTAS DE " +
                    "S/ ${"%.2f".format(valorCuota)}\n"
        )

        println("\nMatrícula $estudianteActual registrada correctamente.")
    }

    // =========================================
    // REPORTE FINAL
    // =========================================

    println("\n\n=====================================")
    println("          REPORTE FINAL")
    println("=====================================")

    print(reporteFinal)

    // =========================================
    // AFORO FINAL
    // =========================================

    println("\n=====================================")
    println("              AFORO")
    println("=====================================")
    println("AFORO MÁXIMO: $aforo")
    println("MATRICULADOS: $cantidadEstudiantes")
    println("VACANTES DISPONIBLES: ${aforo - cantidadEstudiantes}")
    println("=====================================")
}
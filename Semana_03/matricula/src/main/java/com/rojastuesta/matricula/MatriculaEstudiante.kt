package com.rojastuesta.matricula

fun main() {

    println("=====================================")
    println("       SISTEMA DE MATRÍCULA")
    println("=====================================")

    print("Cantidad de estudiantes a matricular: ")
    val cantidadEstudiantes = readln().toIntOrNull()

    if (cantidadEstudiantes == null || cantidadEstudiantes <= 0) {
        println("Cantidad de estudiantes incorrecta")
        return
    }

    val nombres = Array(cantidadEstudiantes) { "" }
    val turnos = Array(cantidadEstudiantes) { "" }
    val totalesPagar = DoubleArray(cantidadEstudiantes)

    for (posicion in 0 until cantidadEstudiantes) {

        println("\n=====================================")
        println("       ESTUDIANTE ${posicion + 1}")
        println("=====================================")

        print("Ingrese nombre del estudiante: ")
        val nombre = readln()

        nombres[posicion] = nombre

        println("\n-------- TURNO --------")
        println("1. Mañana  - 10% de descuento")
        println("2. Tarde   - 15% de descuento")
        println("3. Noche   - 20% de descuento")

        print("Seleccione el turno: ")
        val opcionTurno = readln().toIntOrNull()

        if (opcionTurno == null || opcionTurno !in 1..3) {
            println("Turno incorrecto")
            return
        }

        val turno: String
        val porcentajeDescuento: Double

        when (opcionTurno) {
            1 -> {
                turno = "MAÑANA"
                porcentajeDescuento = 0.10
            }

            2 -> {
                turno = "TARDE"
                porcentajeDescuento = 0.15
            }

            else -> {
                turno = "NOCHE"
                porcentajeDescuento = 0.20
            }
        }

        turnos[posicion] = turno

        println("\n-------- CURSOS --------")

        print("Cantidad de cursos: ")
        val cantidadCursos = readln().toIntOrNull()

        if (cantidadCursos == null || cantidadCursos <= 0) {
            println("Cantidad de cursos incorrecta")
            return
        }

        var totalCreditos = 0
        var totalCursos = 0.0

        for (curso in 1..cantidadCursos) {

            println("\nCurso $curso")

            print("Nombre del curso: ")
            val nombreCurso = readln()

            print("Cantidad de créditos: ")
            val creditos = readln().toIntOrNull()

            if (creditos == null || creditos <= 0) {
                println("Cantidad de créditos incorrecta")
                return
            }

            print("Costo por crédito: S/ ")
            val costoCredito = readln().toDoubleOrNull()

            if (costoCredito == null || costoCredito < 0) {
                println("Costo por crédito incorrecto")
                return
            }

            val costoCurso = creditos * costoCredito

            totalCreditos += creditos
            totalCursos += costoCurso

            println("$nombreCurso: $creditos créditos - S/ ${"%.2f".format(costoCurso)}")
        }

        println("\n-------- CARGA ACADÉMICA --------")

        val cargaAcademica = when {
            totalCreditos <= 10 -> "CARGA BAJA"
            totalCreditos <= 20 -> "CARGA MEDIA"
            else -> "CARGA ALTA"
        }

        println("Total de créditos: $totalCreditos")
        println("Carga académica: $cargaAcademica")

        val descuento = totalCursos * porcentajeDescuento
        val subtotal = totalCursos - descuento

        val totalPagar = subtotal

        totalesPagar[posicion] = totalPagar

        println("\n-------- FORMA DE PAGO --------")

        val cantidadCuotas: Int

        if (totalPagar > 2300) {
            cantidadCuotas = 3
        } else {
            cantidadCuotas = 2
        }

        val valorCuota = totalPagar / cantidadCuotas

        println("Cantidad de cuotas: $cantidadCuotas")
        println("Valor de cada cuota: S/ ${"%.2f".format(valorCuota)}")

        println("\n=====================================")
        println("         REPORTE DE MATRÍCULA")
        println("=====================================")

        val reporteFinal = StringBuilder()

        reporteFinal.append("ESTUDIANTE: $nombre\n")
        reporteFinal.append("TURNO: $turno\n")
        reporteFinal.append("TOTAL DE CRÉDITOS: $totalCreditos\n")
        reporteFinal.append("CARGA ACADÉMICA: $cargaAcademica\n")
        reporteFinal.append("TOTAL CURSOS: S/ ${"%.2f".format(totalCursos)}\n")
        reporteFinal.append("DESCUENTO: S/ ${"%.2f".format(descuento)}\n")
        reporteFinal.append("SUBTOTAL: S/ ${"%.2f".format(subtotal)}\n")
        reporteFinal.append("TOTAL A PAGAR: S/ ${"%.2f".format(totalPagar)}\n")
        reporteFinal.append("CUOTAS: $cantidadCuotas\n")
        reporteFinal.append("VALOR DE CUOTA: S/ ${"%.2f".format(valorCuota)}\n")

        println(reporteFinal.toString())
        println("=====================================")
    }

    println("\n=====================================")
    println("       RESUMEN DE ESTUDIANTES")
    println("=====================================")

    for (posicion in 0 until cantidadEstudiantes) {
        println(
            "${posicion + 1}. ${nombres[posicion]} | " +
                    "Turno: ${turnos[posicion]} | " +
                    "Total: S/ ${"%.2f".format(totalesPagar[posicion])}"
        )
    }

    println("=====================================")
}
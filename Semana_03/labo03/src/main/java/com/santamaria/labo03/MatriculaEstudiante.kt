package com.santamaria.labo03

fun main() {

    println("=====================================")
    println("       SISTEMA DE MATRÍCULA")
    println("=====================================")


    val aforo: Int

    while (true) {
        print("Ingrese el aforo máximo: ")

        val valor = readln().toIntOrNull()

        if (valor != null && valor > 0) {
            aforo = valor
            break
        }

        println("Aforo incorrecto. Ingrese un número mayor a 0.")
    }


    val cantidadEstudiantes: Int

    while (true) {
        print("Ingrese la cantidad de estudiantes a matricular: ")

        val valor = readln().toIntOrNull()

        if (valor != null && valor > 0 && valor <= aforo) {
            cantidadEstudiantes = valor
            break
        }

        if (valor != null && valor > aforo) {
            println("La cantidad supera el aforo disponible.")
        } else {
            println("Cantidad incorrecta. Ingrese un número mayor a 0.")
        }
    }


    val nombres = Array(cantidadEstudiantes) { "" }
    val categorias = Array(cantidadEstudiantes) { "" }
    val turnos = Array(cantidadEstudiantes) { "" }
    val totalesPagar = DoubleArray(cantidadEstudiantes)

    var estudiantesRegistrados = 0


    while (estudiantesRegistrados < cantidadEstudiantes) {

        val posicion = estudiantesRegistrados

        println()
        println("=====================================")
        println("       ESTUDIANTE ${posicion + 1}")
        println("=====================================")

        // NOMBRE

        while (true) {
            print("Nombre del estudiante: ")

            val nombre = readln().trim()

            if (nombre.isNotBlank()) {
                nombres[posicion] = nombre
                break
            }

            println("El nombre no puede estar vacío.")
        }


        val categoria: String
        val matricula: Double

        while (true) {

            println()
            println("1. Ordinario - Matrícula S/ 500.00")
            println("2. Becado    - Matrícula S/ 0.00")
            print("Seleccione la categoría: ")

            val opcion = readln().toIntOrNull()

            if (opcion == 1) {
                categoria = "ORDINARIO"
                matricula = 500.00
                break
            }

            if (opcion == 2) {
                categoria = "BECADO"
                matricula = 0.00
                break
            }

            println("Categoría incorrecta. Seleccione 1 o 2.")
        }

        categorias[posicion] = categoria


        val turno: String
        val porcentajeDescuento: Double

        while (true) {

            println()
            println("1. Mañana - 10% descuento")
            println("2. Tarde  - 15% descuento")
            println("3. Noche  - 20% descuento")
            print("Seleccione el turno: ")

            val opcion = readln().toIntOrNull()

            if (opcion == 1) {
                turno = "MAÑANA"
                porcentajeDescuento = 0.10
                break
            }

            if (opcion == 2) {
                turno = "TARDE"
                porcentajeDescuento = 0.15
                break
            }

            if (opcion == 3) {
                turno = "NOCHE"
                porcentajeDescuento = 0.20
                break
            }

            println("Turno incorrecto. Seleccione 1, 2 o 3.")
        }

        turnos[posicion] = turno


        val valorCredito: Double

        while (true) {
            print("\nValor de cada crédito: S/ ")

            val valor = readln().toDoubleOrNull()

            if (valor != null && valor > 0) {
                valorCredito = valor
                break
            }

            println("Valor del crédito incorrecto.")
        }


        val cantidadCursos: Int

        while (true) {
            print("Cantidad de cursos: ")

            val valor = readln().toIntOrNull()

            if (valor != null && valor > 0) {
                cantidadCursos = valor
                break
            }

            println("Cantidad de cursos incorrecta.")
        }


        val nombresCursos = Array(cantidadCursos) { "" }
        val creditosCursos = IntArray(cantidadCursos)
        val costosCursos = DoubleArray(cantidadCursos)

        var totalCreditos = 0
        var totalCursos = 0.0


        for (curso in 0 until cantidadCursos) {

            println()
            println("--- CURSO ${curso + 1} ---")


            while (true) {

                print("Nombre del curso: ")

                val nombreCurso = readln().trim()

                if (nombreCurso.isBlank()) {
                    println("El nombre del curso no puede estar vacío.")
                    continue
                }


                var repetido = false

                for (i in 0 until curso) {
                    if (nombresCursos[i].equals(nombreCurso, ignoreCase = true)) {
                        repetido = true
                        break
                    }
                }

                if (repetido) {
                    println("Ese curso ya fue registrado.")
                    continue
                }

                nombresCursos[curso] = nombreCurso
                break
            }

            while (true) {

                print("Cantidad de créditos: ")

                val creditos = readln().toIntOrNull()

                if (creditos != null && creditos > 0) {

                    creditosCursos[curso] = creditos
                    totalCreditos += creditos

                    break
                }

                println("Cantidad de créditos incorrecta.")
            }

            costosCursos[curso] =
                creditosCursos[curso] * valorCredito

            totalCursos += costosCursos[curso]
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

        totalesPagar[posicion] = totalPagar


        val cantidadCuotas: Int

        if (totalPagar > 2300) {
            cantidadCuotas = 3
        } else {
            cantidadCuotas = 2
        }

        val valorCuota = totalPagar / cantidadCuotas


        println()
        println("=====================================")
        println("          RESUMEN DE MATRÍCULA")
        println("=====================================")

        println("NOMBRE: ${nombres[posicion]}")
        println("CATEGORÍA: ${categorias[posicion]}")
        println("TURNO: ${turnos[posicion]}")

        println("-------------------------------------")
        println("CURSO\t\tCRÉDITOS\tCOSTO")
        println("-------------------------------------")

        for (curso in 0 until cantidadCursos) {

            println(
                "${nombresCursos[curso]}\t\t" +
                        "${creditosCursos[curso]}\t\t" +
                        "S/ ${"%.2f".format(costosCursos[curso])}"
            )
        }

        println("-------------------------------------")
        println("CURSOS MATRICULADOS: $cantidadCursos")
        println("TOTAL CRÉDITOS: $totalCreditos")
        println("CARGA ACADÉMICA: $cargaAcademica")
        println("MATRÍCULA: S/ ${"%.2f".format(matricula)}")
        println("DESCUENTO: S/ ${"%.2f".format(descuento)}")
        println(
            "MATRÍCULA CON DESCUENTO: " +
                    "S/ ${"%.2f".format(matriculaDescuento)}"
        )
        println("SUBTOTAL: S/ ${"%.2f".format(subtotal)}")
        println("IGV (18%): S/ ${"%.2f".format(igv)}")
        println("TOTAL A PAGAR: S/ ${"%.2f".format(totalPagar)}")
        println("FORMA DE PAGO: $cantidadCuotas cuotas")
        println("VALOR DE CADA CUOTA: S/ ${"%.2f".format(valorCuota)}")
        println("=====================================")

        estudiantesRegistrados++

        println()
        println("Estudiante registrado correctamente.")
        println(
            "Matriculados: $estudiantesRegistrados / $aforo"
        )
        println(
            "Vacantes disponibles: ${aforo - estudiantesRegistrados}"
        )
    }


    println()
    println("=====================================")
    println("          REPORTE FINAL")
    println("=====================================")
    println("AFORO MÁXIMO: $aforo")
    println("ESTUDIANTES MATRICULADOS: $estudiantesRegistrados")
    println("VACANTES DISPONIBLES: ${aforo - estudiantesRegistrados}")
    println("=====================================")
}
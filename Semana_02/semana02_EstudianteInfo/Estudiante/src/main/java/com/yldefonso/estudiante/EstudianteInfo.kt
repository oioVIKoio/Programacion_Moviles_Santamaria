package com.yldefonso.semana02_estudianteinfo

import java.util.Scanner

fun main() {

    val scanner = Scanner(System.`in`)

    print("Nombre del estudiante: ")
    val estudiante = scanner.nextLine()

    print("Cantidad de cursos: ")
    val cantidadCursos = scanner.nextInt()

    print("Valor de cada credito: S/ ")
    val valorCredito = scanner.nextDouble()
    scanner.nextLine()

    val nombresCursos = Array(cantidadCursos) { "" }
    val creditosCursos = IntArray(cantidadCursos)

    for (i in 1..cantidadCursos) {

        println("\n--- CURSO $i ---")

        print("Nombre del curso: ")
        nombresCursos[i - 1] = scanner.nextLine()

        print("Cantidad de creditos: ")
        creditosCursos[i - 1] = scanner.nextInt()
        scanner.nextLine()
    }
}
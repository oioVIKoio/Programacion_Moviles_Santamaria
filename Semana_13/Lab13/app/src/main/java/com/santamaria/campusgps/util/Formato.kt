package com.santamaria.campusgps.util

import java.util.Locale
import kotlin.math.roundToInt

/** Menos de 1000 m se muestra en metros ("350 m"); desde 1000 m, en kilómetros ("1.5 km"). */
fun formatearDistancia(metros: Float): String =
    if (metros < 1000f) "${metros.roundToInt()} m"
    else String.format(Locale.US, "%.1f km", metros / 1000f)

/** Extra del diseño: minutos a pie, a unos 80 m por minuto y como mínimo 1. */
fun formatearCaminata(metros: Float): String =
    "${maxOf(1, (metros / 80f).roundToInt())} min a pie"

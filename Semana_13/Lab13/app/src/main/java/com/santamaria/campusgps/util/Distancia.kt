package com.santamaria.campusgps.util

import android.location.Location
import com.santamaria.campusgps.model.Lugar
import com.santamaria.campusgps.model.Posicion

/** Distancia en metros entre dos puntos, en línea recta sobre la superficie de la Tierra. */
fun distanciaMetros(desde: Posicion, hasta: Lugar): Float {
    val resultado = FloatArray(1)
    Location.distanceBetween(desde.latitud, desde.longitud, hasta.latitud, hasta.longitud, resultado)
    return resultado[0]
}

/** Rumbo (0 a 360 grados, desde el norte hacia el este) que hay que seguir desde tu posición para llegar al lugar. */
fun rumboHacia(desde: Posicion, hasta: Lugar): Float {
    val resultado = FloatArray(2)
    Location.distanceBetween(desde.latitud, desde.longitud, hasta.latitud, hasta.longitud, resultado)
    return (resultado[1] + 360f) % 360f
}

/** Convierte grados en una dirección en palabras: norte, noreste, este... */
fun direccionCardinal(grados: Float): String {
    val nombres = listOf("norte", "noreste", "este", "sureste", "sur", "suroeste", "oeste", "noroeste")
    return nombres[((grados + 22.5f) / 45f).toInt() % 8]
}

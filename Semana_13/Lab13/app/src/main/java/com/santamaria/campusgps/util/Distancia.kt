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

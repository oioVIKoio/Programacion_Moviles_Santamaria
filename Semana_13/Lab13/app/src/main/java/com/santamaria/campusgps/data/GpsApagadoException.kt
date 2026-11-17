package com.santamaria.campusgps.data

/** El servicio de ubicación del teléfono está apagado: sin él no hay ubicación aunque exista el permiso. */
class GpsApagadoException : Exception("El GPS está apagado")

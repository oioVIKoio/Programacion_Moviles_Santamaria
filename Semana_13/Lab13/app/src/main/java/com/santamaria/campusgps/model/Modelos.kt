package com.santamaria.campusgps.model

data class Posicion(val latitud: Double, val longitud: Double)

data class Lugar(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val latitud: Double,
    val longitud: Double
)

// metros y rumbo (grados desde el norte hacia el lugar) son null mientras todavía no se conoce la ubicación del usuario
data class LugarConDistancia(val lugar: Lugar, val metros: Float?, val rumbo: Float? = null)

// Qué salió mal: la pantalla decide el botón según el tipo (Abrir ajustes, Reintentar o ninguno)
enum class TipoError { SIN_PERMISO, GPS_APAGADO, SIN_PLAY_SERVICES, OTRO }

sealed interface UbicacionUi {
    data object Inicial : UbicacionUi
    data object Buscando : UbicacionUi
    data class Lista(val posicion: Posicion) : UbicacionUi
    data class Error(val mensaje: String, val tipo: TipoError) : UbicacionUi
}

// Mejora 3: tipo de mapa que se ve (el visor del mapa)
enum class TipoMapa(val etiqueta: String) { NORMAL("Normal"), SATELITE("Satélite"), HIBRIDO("Híbrido") }

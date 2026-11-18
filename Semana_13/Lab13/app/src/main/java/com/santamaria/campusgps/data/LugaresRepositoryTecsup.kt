package com.santamaria.campusgps.data

import com.santamaria.campusgps.model.Lugar

// Lugares REALES del campus de Tecsup (Av. Cascanueces 2221, Santa Anita).
// Fuente: fichas de Google Maps consultadas el 18/11/2026; solo entran los sitios que Maps nombra como Tecsup.
// La categoría la da el tipo de lugar que indica la propia ficha de Maps.
class LugaresRepositoryTecsup : LugaresRepository {

    override fun obtenerLugares(): List<Lugar> = listOf(
        Lugar(1, "Pabellón 10", "Estudio", -12.0437270, -76.9528260),
        Lugar(2, "Pabellón 11", "Estudio", -12.0436924, -76.9524764),
        Lugar(3, "Pabellón 15", "Servicios", -12.0434870, -76.9531418),
        Lugar(4, "Pabellón 16", "Estudio", -12.0433980, -76.9527886),
        Lugar(5, "Canchas Deportivas", "Deporte", -12.0425876, -76.9524681),
        Lugar(6, "Polideportivo", "Deporte", -12.0434832, -76.9538417),
        Lugar(7, "Informes Tecsup", "Servicios", -12.0448633, -76.9515031)
    )
}

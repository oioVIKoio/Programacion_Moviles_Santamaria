package com.santamaria.campusgps.data

import com.santamaria.campusgps.model.Lugar

interface LugaresRepository {
    fun obtenerLugares(): List<Lugar>
}

package com.santamaria.avisoscampus

import android.app.Application
import com.santamaria.avisoscampus.notificacion.Notificador

class AvisosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Notificador.crearCanal(this)
    }
}

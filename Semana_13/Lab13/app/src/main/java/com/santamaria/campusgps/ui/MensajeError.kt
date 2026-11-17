package com.santamaria.campusgps.ui

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.santamaria.campusgps.model.TipoError
import com.santamaria.campusgps.model.UbicacionUi
import com.santamaria.campusgps.ui.components.CajaMensaje
import com.santamaria.campusgps.ui.components.TipoMensaje

/** Error de ubicación con el botón que corresponde a cada causa. */
@Composable
fun MensajeError(error: UbicacionUi.Error, onReintentar: () -> Unit, modifier: Modifier = Modifier) {
    val contexto = LocalContext.current
    when (error.tipo) {
        TipoError.SIN_PERMISO -> CajaMensaje(
            tipo = TipoMensaje.ERROR,
            titulo = "Sin permiso de ubicación",
            texto = error.mensaje,
            modifier = modifier,
            accion = "Abrir ajustes",
            onAccion = {
                // Lleva a los ajustes de ESTA app, donde se puede dar el permiso a mano
                contexto.startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.fromParts("package", contexto.packageName, null))
                )
            }
        )
        TipoError.GPS_APAGADO -> CajaMensaje(
            tipo = TipoMensaje.ERROR,
            titulo = "El GPS está apagado",
            texto = error.mensaje,
            modifier = modifier,
            accion = "Reintentar",
            onAccion = onReintentar
        )
        // Sin botón: reintentar no arregla un teléfono sin Play Services
        TipoError.SIN_PLAY_SERVICES -> CajaMensaje(
            tipo = TipoMensaje.ERROR,
            titulo = "Sin Google Play Services",
            texto = error.mensaje,
            modifier = modifier
        )
        TipoError.OTRO -> CajaMensaje(
            tipo = TipoMensaje.ERROR,
            titulo = "No pudimos ubicarte",
            texto = error.mensaje,
            modifier = modifier,
            accion = "Reintentar",
            onAccion = onReintentar
        )
    }
}

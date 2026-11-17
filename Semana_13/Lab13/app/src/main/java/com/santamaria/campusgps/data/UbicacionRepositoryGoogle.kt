package com.santamaria.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.santamaria.campusgps.model.Posicion
import kotlinx.coroutines.tasks.await

class UbicacionRepositoryGoogle(context: Context) : UbicacionRepository {

    private val contexto = context.applicationContext
    private val cliente = LocationServices.getFusedLocationProviderClient(contexto)

    // El permiso lo pide la pantalla antes de llamar al ViewModel
    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> = runCatching {
        val servicio = contexto.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!LocationManagerCompat.isLocationEnabled(servicio)) throw GpsApagadoException()
        val ubicacion = cliente
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
            .await()
            ?: error("No se pudo obtener la ubicación")
        Posicion(ubicacion.latitude, ubicacion.longitude)
    }

    override fun playServicesDisponible(): Boolean =
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(contexto) == ConnectionResult.SUCCESS
}

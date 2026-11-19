package com.santamaria.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.DeviceOrientationListener
import com.google.android.gms.location.DeviceOrientationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.santamaria.campusgps.model.Posicion
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    // requestLocationUpdates convertido en Flow: al cancelar la colección se quitan las actualizaciones (awaitClose)
    @SuppressLint("MissingPermission")
    override fun seguirPosicion(): Flow<Posicion> = callbackFlow {
        val servicio = contexto.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!LocationManagerCompat.isLocationEnabled(servicio)) throw GpsApagadoException()
        val pedido = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000L)
            .setMinUpdateDistanceMeters(2f)
            .build()
        val callback = object : LocationCallback() {
            override fun onLocationResult(resultado: LocationResult) {
                resultado.lastLocation?.let { trySend(Posicion(it.latitude, it.longitude)) }
            }
        }
        cliente.requestLocationUpdates(pedido, callback, Looper.getMainLooper())
        awaitClose { cliente.removeLocationUpdates(callback) }
    }

    // El sensor de orientación convertido en Flow: al cancelar se quita el listener (awaitClose)
    override fun seguirRumbo(): Flow<Float> = callbackFlow {
        val orientacion = LocationServices.getFusedOrientationProviderClient(contexto)
        val pedido = DeviceOrientationRequest.Builder(DeviceOrientationRequest.OUTPUT_PERIOD_DEFAULT).build()
        val listener = DeviceOrientationListener { trySend(it.headingDegrees) }
        orientacion.requestOrientationUpdates(pedido, ContextCompat.getMainExecutor(contexto), listener)
        awaitClose { orientacion.removeOrientationUpdates(listener) }
    }

    override fun playServicesDisponible(): Boolean =
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(contexto) == ConnectionResult.SUCCESS
}

package com.santamaria.centrotareas.data

import android.util.Log
import com.santamaria.centrotareas.model.Avance
import com.santamaria.centrotareas.model.AvancePrimos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class TareasRepositoryLocal : TareasRepository {

    // 100 vueltas de 40 ms ≈ 4 s. flowOn mueve el trabajo a Default: quien recoge sigue en su hilo.
    override fun tareaConProgreso(): Flow<Avance> = flow {
        Log.d("HILO", "Dentro: ${Thread.currentThread().name}")
        for (i in 1..100) {
            delay(40)
            emit(Avance(i / 100f, Thread.currentThread().name))
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun cargarPerfil(): String {
        delay(2000)
        return "Ana Torres (ficticia)"
    }

    override suspend fun cargarPedidos(simularError: Boolean): List<String> {
        delay(3000)
        if (simularError) throw IOException("Sin conexión")
        return listOf("Pedido 101", "Pedido 102", "Pedido 103")
    }

    // Cálculo pesado en Default y cancelable (ensureActive); emite el porcentaje cada ~1 %
    override fun contarPrimos(hasta: Int): Flow<AvancePrimos> = flow {
        var cantidad = 0
        val paso = maxOf(1, hasta / 100)
        for (n in 2..hasta) {
            currentCoroutineContext().ensureActive()
            if (esPrimo(n)) cantidad++
            if (n % paso == 0) emit(AvancePrimos((n.toLong() * 100 / hasta).toInt(), cantidad))
        }
        emit(AvancePrimos(100, cantidad))
    }.flowOn(Dispatchers.Default)

    private fun esPrimo(n: Int): Boolean {
        var d = 2
        // d.toLong() * d evita el desborde de Int que daría calcular la raíz con d * d
        while (d.toLong() * d <= n) {
            if (n % d == 0) return false
            d++
        }
        return true
    }
}

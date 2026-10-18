package tecsup.santamaria.lavacampus.ui.components

import tecsup.santamaria.lavacampus.data.local.relation.DetalleConServicio
import tecsup.santamaria.lavacampus.model.Unidad
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val es = Locale.forLanguageTag("es-PE")
private val formatoCorto = DateTimeFormatter.ofPattern("EEE d MMM", es)
private val formatoHora = DateTimeFormatter.ofPattern("d MMM HH:mm", es)

// "2026-10-19" -> "lun 19 oct"
fun fechaCorta(iso: String): String = LocalDate.parse(iso).format(formatoCorto).replace(".", "")

// milisegundos -> "20 oct 10:15"
fun fechaHora(millis: Long): String =
    Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).format(formatoHora).replace(".", "")

// "Lun 19 oct", como en las tarjetas de pedido
fun fechaTarjeta(iso: String): String = fechaCorta(iso).replaceFirstChar { it.uppercase() }

// 3.5 -> "3.5", 5.0 -> "5"
fun cantidadTexto(cantidad: Double, unidad: Unidad): String =
    if (unidad == Unidad.KG) "%.1f".format(Locale.US, cantidad) else cantidad.toInt().toString()

// "3.5 kg", "1 prenda", "5 prendas"
fun cantidadConUnidad(cantidad: Double, unidad: Unidad): String {
    val texto = cantidadTexto(cantidad, unidad)
    return when {
        unidad == Unidad.KG -> "$texto kg"
        cantidad == 1.0 -> "$texto ${unidad.etiqueta}"
        else -> "$texto ${unidad.etiqueta}s"
    }
}

// "3.5 kg + 5 prendas"
fun resumenLineas(detalles: List<DetalleConServicio>): String =
    detalles.joinToString(" + ") { d ->
        cantidadConUnidad(d.detalle.cantidad, Unidad.valueOf(d.servicio.unidad))
    }

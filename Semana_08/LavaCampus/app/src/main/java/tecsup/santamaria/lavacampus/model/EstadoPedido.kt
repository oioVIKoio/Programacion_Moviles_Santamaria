package tecsup.santamaria.lavacampus.model

import androidx.compose.ui.graphics.Color

enum class EstadoPedido(val etiqueta: String, val color: Color) {
    RECIBIDO("Recibido", Color(0xFFD97706)),
    EN_PROCESO("En proceso", Color(0xFF2563EB)),
    LISTO("Listo", Color(0xFF16A34A)),
    ENTREGADO("Entregado", Color(0xFF475569)),
    CANCELADO("Cancelado", Color(0xFFDC2626));

    val esActivo: Boolean get() = this == RECIBIDO || this == EN_PROCESO || this == LISTO
    val esFinal: Boolean get() = this == ENTREGADO || this == CANCELADO

    // Solo se avanza un paso a la vez: RECIBIDO -> EN_PROCESO -> LISTO -> ENTREGADO
    fun siguiente(): EstadoPedido? = when (this) {
        RECIBIDO -> EN_PROCESO
        EN_PROCESO -> LISTO
        LISTO -> ENTREGADO
        ENTREGADO, CANCELADO -> null
    }

    companion object {
        val etapas = listOf(RECIBIDO, EN_PROCESO, LISTO, ENTREGADO)
    }
}

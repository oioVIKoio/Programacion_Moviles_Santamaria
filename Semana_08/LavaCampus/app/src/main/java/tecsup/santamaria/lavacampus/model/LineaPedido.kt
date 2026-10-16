package tecsup.santamaria.lavacampus.model

// Lo que elige el estudiante: un servicio y su cantidad (kilos o prendas)
data class LineaPedido(
    val servicioId: Int,
    val cantidad: Double
)

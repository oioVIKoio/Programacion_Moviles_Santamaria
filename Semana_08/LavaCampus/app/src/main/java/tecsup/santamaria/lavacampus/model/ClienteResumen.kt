package tecsup.santamaria.lavacampus.model

data class ClienteResumen(
    val id: Int,
    val nombre: String,
    val codigo: String,
    val correo: String,
    val pedidos: Int,
    val gastado: Double
)

package tecsup.santamaria.lavacampus.ui.navigation

object Rutas {
    const val SPLASH = "splash"
    const val LOGIN = "login"

    // Estudiante
    const val INICIO = "inicio"
    const val NUEVO_PEDIDO = "nuevo_pedido"
    const val NUEVO_PEDIDO_REPETIR = "nuevo_pedido?repetir={repetir}"  // repetir = 0: pedido en blanco
    const val MIS_PEDIDOS = "mis_pedidos"
    const val DETALLE_PEDIDO = "detalle_pedido/{id}"
    const val TARIFAS = "tarifas"
    const val PERFIL = "perfil"

    // Administrador
    const val PANEL = "panel"
    const val BANDEJA = "bandeja"
    const val GESTION_PEDIDO = "gestion_pedido/{id}"
    const val SERVICIOS = "servicios"
    const val FORMULARIO_SERVICIO = "formulario_servicio/{id}"
    const val CLIENTES = "clientes"
    const val AJUSTES = "ajustes"

    // Grafos de cada perfil
    const val GRAFO_ESTUDIANTE = "grafo_estudiante"
    const val GRAFO_ADMIN = "grafo_admin"

    fun detallePedido(id: Int) = "detalle_pedido/$id"
    fun repetirPedido(id: Int) = "nuevo_pedido?repetir=$id"
    fun gestionPedido(id: Int) = "gestion_pedido/$id"
    fun formularioServicio(id: Int) = "formulario_servicio/$id"   // 0 = nuevo
}

package tecsup.santamaria.lavacampus.model

enum class Unidad(val etiqueta: String, val paso: Double, val minimo: Double, val maximo: Double) {
    KG("kg", 0.5, 1.0, 10.0),
    PRENDA("prenda", 1.0, 1.0, 30.0),
    UNIDAD("unidad", 1.0, 1.0, 30.0)
}

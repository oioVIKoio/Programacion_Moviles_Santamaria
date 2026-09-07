# Lab 03 - Registro de Producto

**Nombre:** Victor Santamaria

## Descripción

Aplicación desarrollada con Kotlin y Jetpack Compose para registrar
un producto mediante un formulario.

La aplicación permite ingresar el nombre del producto, precio y
cantidad, y posteriormente muestra un resumen con el importe
calculado.

## Evidencias

### Pantalla vacía

![Pantalla vacía](img.png)

### Producto registrado

![Producto registrado](img_1.png)


### Mejora con IA

Durante el desarrollo se utilizó Gemini como herramienta de apoyo para mejorar el código existente, manteniendo su estructura y funcionalidad original.

| Prompt que usé | Qué generó Gemini | Qué acepté o corregí (y por qué) |
|---|---|---|
| Mejora este código de Jetpack Compose manteniendo su estructura y funcionalidad actual. Trabaja directamente sobre donde me encuentro, en esta carpeta laboratorio_93_jetpack. Necesito agregar validación de campos vacíos al presionar "AGREGAR PRODUCTO" y un botón "Limpiar" que vacíe los campos, limpie el estado de validación y oculte el resumen. Mantén el diseño actual, Jetpack Compose y el uso de remember y mutableStateOf. No agregues funcionalidades adicionales. | Gemini agregó validaciones para comprobar que el nombre del producto, precio y cantidad no estuvieran vacíos. También incorporó el botón "Limpiar" para restablecer los campos y ocultar el resumen. | Acepté la estructura propuesta porque mantenía el funcionamiento original de la aplicación y cumplía con las mejoras solicitadas. Posteriormente corregí la validación para controlar también los valores numéricos ingresados en precio y cantidad. |

## Pregunta

### ¿Qué pasaría si declaras las variables de los campos SIN `remember`?

Al probar la app sin usar `remember`, los valores de los campos no se conservan cuando ocurre una recomposición. Entonces por ello es la importancia de usar remember para que conserve el estado aun si redibuja la variable en pantalla.

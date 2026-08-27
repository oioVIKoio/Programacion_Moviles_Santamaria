# Semana 02 — CRUD de Productos con Kotlin

Segundo laboratorio del curso de **Desarrollo de Aplicaciones Móviles**, enfocado en Kotlin, Programación Orientada a Objetos y operaciones CRUD mediante una aplicación de consola ejecutada desde Android Studio.

## Objetivo

Desarrollar una aplicación para gestionar productos aplicando conceptos básicos de Kotlin y Programación Orientada a Objetos.

## Funcionalidades

- Agregar productos.
- Listar productos registrados.
- Buscar productos por ID.
- Eliminar productos por ID.
- Validar las opciones ingresadas en el menú.
- Salir de la aplicación desde consola.

## Modelo de datos

La `data class Producto` representa cada producto mediante:

- ID.
- Nombre.
- Precio.
- Stock.

## Lógica de negocio

La clase `ProductoService` administra una colección mutable de productos y proporciona las operaciones:

- `agregar()`
- `listar()`
- `buscar()`
- `eliminar()`

Esto permite separar la representación de los productos de las operaciones realizadas sobre ellos.

## Interacción por consola

La función `main()` utiliza `Scanner` para recibir información desde la entrada estándar y presenta un menú interactivo para realizar las operaciones CRUD.

## Estructura del proyecto

```text
Semana_02/
├── app/
├── Carrito/
│   ├── build.gradle.kts
│   └── src/main/java/com/santamaria/carrito/
│       └── Carrito.kt
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
└── README.md

```
### Prompt utilizado

> Crea un programa de carrito de compras en Kotlin que funcione por consola utilizando POO básico.
>
> Crea una `data class Producto` con los atributos `id`, `nombre`, `precio` y `stock`, y una clase `ProductoService` que gestione los productos mediante una `MutableList`.
>
> El programa debe permitir:
> 1. Agregar un producto ingresando sus datos por teclado.
> 2. Listar los productos registrados.
> 3. Buscar un producto por su ID.
> 4. Eliminar un producto por su ID.
> 5. Salir del programa.
>
> Utiliza `Scanner(System.in)` para ingresar los datos y un `when` para controlar las opciones. El menú debe repetirse hasta que el usuario elija salir.
>
> No utilices herencia, interfaces, patrones de diseño, bases de datos, archivos ni conceptos avanzados. Los productos deben almacenarse temporalmente en una lista mutable.

### Resultado del uso de IA

A partir del prompt se obtuvo una implementación base del carrito. Posteriormente se revisó el código generado, se realizaron ajustes y se comprobó su funcionamiento desde Android Studio.

## Prueba de funcionamiento

Se verificó el funcionamiento del CRUD realizando el registro, listado, búsqueda y eliminación de un producto desde la consola. La aplicación finalizó correctamente mediante la opción `0`.

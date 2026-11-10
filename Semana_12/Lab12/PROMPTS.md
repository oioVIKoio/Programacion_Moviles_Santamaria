# PROMPTS — Laboratorio 12 (Pokédex)

Pareja: Victor y Diego Panez · Fase 2, rama `con-ia`.

En esta fase se pidió a la IA que revisara el proyecto de la Fase 1 y lo dejara con la estructura que pide la guía. Estos son los prompts de trabajo.

## Prompt 1 — Arquitectura MVVM

> Tengo una app Android en Kotlin con Jetpack Compose (Material 3) que muestra los Pokémon de tipo fuego de PokeAPI (`GET type/fire` y `GET pokemon/{nombre}`). Organízala con MVVM: capas `data` (remote, local, mapper, repository), `domain` y `ui`. El Repository debe ser una interface, el ViewModel no debe recibir Context, y debe haber tres modelos distintos: `ItemDto`, `ItemEntity` e `Item`, con mappers. Cada ViewModel expone un `StateFlow<UiState<T>>` con Cargando, Exito y Error, y se crea con un `ViewModelProvider.Factory` que recibe el `AppContainer`.

## Prompt 2 — Room como fuente de verdad y modo sin conexión

> Haz que Room sea la fuente única de verdad: la lista sale siempre de Room y Retrofit solo la actualiza con `refrescar()`. Si no hay internet y hay datos guardados, muestra la lista con un aviso de sin conexión; si no hay datos, muestra una pantalla de error con botón Reintentar. Los favoritos no se deben perder al sincronizar.

## Prompt 3 — Favoritos y panel Admin

> Agrega favoritos (estrella en la lista y pantalla Favoritos, guardados en Room) y un panel Admin que solo aparezca si la sesión tiene rol `ADMIN`, con los totales, un botón Sincronizar y un botón Limpiar caché.

## Prompt 4 — Firebase y notificación

> Integra Firebase Authentication (correo y contraseña, sesión guardada y cerrar sesión). El rol se lee de Firestore en `usuarios/{uid}`, campo `rol`; no se decide en la app. Al sincronizar, muestra una notificación local «Se guardaron N elementos» con canal y permiso `POST_NOTIFICATIONS`, pedida desde el ViewModel mediante la interface `Notificador`.

## Prompt 5 — Pruebas unitarias

> Escribe pruebas unitarias de los ViewModel (lista, detalle, favoritos y Admin) usando un repositorio falso en memoria (`FakeItemRepository`) y un notificador falso, sin red ni Room. Usa `kotlinx-coroutines-test`.

## Prompt 6 — Rediseño (Material 3)

> Mejora la interfaz con Material 3: tarjetas, colores coherentes con el tema fuego, y mensajes de error claros para cada caso (sin internet, error del servidor, sin datos).

## Qué se revisó y corrigió a mano

- Se agregó el manejo de `HttpException` en `DetalleViewModel` para mostrar el error del servidor.
- Se comprobó que los favoritos se conservan al sincronizar.
- Se verificó que `google-services.json` no se sube al repositorio.
- Las pruebas se ejecutan con `./gradlew :app:testDebugUnitTest`.

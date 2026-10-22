# Prompts utilizados

Fase 2 de LavaCampus (rama `con-ia`): 3 mejoras de la guía hechas con IA sobre la app de la Fase 1, sin romper MVVM + Repository.

## Prompt 1

**Texto enviado a Gemini:**

```
Tengo una app Android llamada LavaCampus (lavandería universitaria) hecha en Kotlin con
Jetpack Compose, Material 3, MVVM, Room, DataStore y Navigation Compose. La arquitectura es:
View (Composable) -> ViewModel (StateFlow + reglas) -> LavaRepository (interface)
-> LavaRepositoryRoom -> DAO -> SQLite.

Reglas que no se pueden romper:
- La View no usa DAO ni Repository: solo lee StateFlow del ViewModel y le avisa los clics.
- El ViewModel solo conoce la interface LavaRepository, nunca LavaRepositoryRoom ni los DAO.
- Si agregas una función al Repository, primero la declaras en la interface LavaRepository
  y luego la implementas en LavaRepositoryRoom.
- Si cambias o agregas una entidad, subes la version de LavaDatabase.
- Lecturas que actualizan la pantalla con Flow (sin suspend); escrituras con suspend.
- Nada de XML ni librerías externas. Textos de la app en español con tildes.

Agrega estas 3 mejoras, cada una en un commit aparte:

1) Pedidos atrasados (DAO + ViewModel + View)
   - Un pedido está atrasado si su fechaEntrega es menor que hoy y su estado es distinto
     de ENTREGADO y CANCELADO.
   - La consulta va en PedidoDao (devuelve los id de los atrasados como Flow), la función
     observarAtrasados(hoy) en LavaRepository y su implementación en LavaRepositoryRoom.
   - BandejaViewModel expone los atrasados como StateFlow<Set<Int>> y PanelViewModel
     expone cuántos son como StateFlow<Int>.
   - En la bandeja del administrador, la tarjeta del pedido atrasado tiene borde rojo y el
     texto "Atrasado". En el panel aparece una alerta roja "N pedidos atrasados · Su fecha
     de entrega ya pasó" cuando hay al menos uno.

2) Descuento por volumen (Entidad + Repository + ViewModel + View)
   - Si el lavado supera 5 kg (suma de las cantidades de los servicios que se cobran por
     KG; 5.0 kg exactos no cuenta) se aplica 10 % de descuento sobre el total del pedido.
   - Agrega a la entidad Pedido la columna descuento (monto en soles, default 0) y deja en
     total el monto ya con el descuento restado.
   - Sube LavaDatabase a version 2 con una Migration(1, 2) que haga
     ALTER TABLE pedidos ADD COLUMN descuento, para no perder los pedidos que ya existen.
   - La regla (más de 5 kg y 10 %) vive en NuevoPedidoViewModel, que expone subtotal,
     descuento y total como StateFlow. crearPedido de LavaRepository recibe el porcentaje y
     LavaRepositoryRoom calcula y guarda el descuento redondeado a 2 decimales.
   - En el resumen de Nuevo pedido, cuando aplica, se ve "Subtotal", "Descuento por volumen
     (10 %)" en verde y el "Total". En el detalle del pedido también se ve el descuento.

3) Repetir un pedido (ViewModel + View)
   - En Mis pedidos, pestaña Historial (ENTREGADO y CANCELADO), cada tarjeta tiene un botón
     "Pedir de nuevo".
   - El botón navega a Nuevo pedido con la ruta nuevo_pedido?repetir={id} (argumento Int,
     default 0 = pedido en blanco).
   - NuevoPedidoViewModel recibe repetirId y, si es mayor que 0, carga las mismas líneas y
     cantidades de ese pedido usando las funciones que ya existen en LavaRepository
     (observarPedido y observarServiciosActivos). Los precios son los actuales de cada
     servicio, no los guardados en el pedido viejo.
   - Si algún servicio del pedido viejo ya no está activo, no se carga y se muestra
     "Ya no está disponible: <nombre>".

Muéstrame solo los archivos que cambian en cada mejora.
```

**Qué generó la IA:**
- Mejora 1: consulta en `PedidoDao`, `observarAtrasados` en la interface y en `LavaRepositoryRoom`, `StateFlow` en `BandejaViewModel` y `PanelViewModel`, parámetro `atrasado` en `PedidoCard` (borde rojo y texto) y la alerta en `PanelScreen`.
- Mejora 2: columna `descuento` en `Pedido`, `LavaDatabase` en versión 2 con `MIGRACION_1_2`, `crearPedido` con el porcentaje, `subtotal`/`descuento`/`total` en `NuevoPedidoViewModel` y las filas del descuento en `NuevoPedidoScreen` y `DetallePedidoScreen`.
- Mejora 3: ruta `NUEVO_PEDIDO_REPETIR` con su argumento en `NavLava`, `cargarPedido` en `NuevoPedidoViewModel` y el botón "Pedir de nuevo" en `MisPedidosScreen`.

**Qué tuve que corregir yo:**
- Mejora 1: en la primera versión la bandeja marcaba los atrasados con una función normal del ViewModel (`estaAtrasado(p)`) y la consulta del DAO solo contaba. Se cambió para que el DAO devuelva los id y `BandejaViewModel` los exponga como `StateFlow`, como pide la guía.
- Mejora 2: se revisó que la migración conserve los datos (la base quedó en versión 2 con los 11 pedidos) y que el `defaultValue` de la entidad coincida con el `DEFAULT 0` del `ALTER TABLE`; si no coinciden, Room falla al validar el esquema.
- Se probaron en el emulador los casos límite: 5.0 kg sin descuento, 5.5 kg con descuento (S/ 49.00 − S/ 4.90 = S/ 44.10, guardado en la base) y "Pedir de nuevo" con un precio cambiado (toma el precio nuevo).

**¿Respeta MVVM?:** Sí. Las pantallas solo leen `StateFlow` y llaman funciones del ViewModel; los ViewModel solo usan la interface `LavaRepository`; la única función nueva del Repository (`observarAtrasados`) y el cambio de `crearPedido` se declararon primero en la interface; el cambio de la entidad subió la versión de la base.

## Reflexión (5 líneas)
- **¿Qué mejoró la IA y qué no?** Hizo rápido lo repetitivo (consultas, `StateFlow`, filas de la UI) y respetó las capas cuando el prompt las nombraba; no decidió las reglas que el PDF deja abiertas ("supera 5 kg", "10 % sobre qué"), esas las definí yo.
- **¿Qué error cometió?** Tomó el camino corto: marcó los atrasados con una función suelta en el ViewModel en vez del `StateFlow` que pedía la guía, y lo tuve que corregir.
- **¿Qué aprendí sobre MVVM?** Que la View solo pinta y avisa clics, y que el ViewModel solo conoce la interface del Repository; así cada mejora toca solo las capas que le corresponden.
- **¿Qué aprendí sobre Room?** Que subir la versión de la base no basta: sin una migración se pierden los datos o la app se cae al abrir.
- **¿Qué aprendí sobre reglas de negocio?** Que viven en el ViewModel y el Repository solo guarda; por eso la bandeja, el panel y el resumen no se contradicen.

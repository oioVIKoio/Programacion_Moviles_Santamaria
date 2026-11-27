# PROMPTS — Laboratorio 15 (AvisosCampus)

Fase 2, rama `con-ia`. Esta fase se trabajó con **Claude Code** (no con Gemini en Android Studio). La IA propuso las mejoras a partir de la lista de la guía y se probó cada una en el emulador (Android 16) antes de cada commit; la elección final de mejoras y la revisión del código son de Victor.

Reglas que se pidieron en todos los prompts: mantener MVVM + Repository, ningún `Context`, `NotificationManager` ni `BroadcastReceiver` dentro del `ViewModel`, y todo receptor registrado se quita en `onDispose`.

| Prompt que escribí | Qué respondió la IA | Qué cambié o descarté | Por qué |
|---|---|---|---|
| «Agrega un canal Urgentes con `IMPORTANCE_HIGH` (emergente) además de Avisos, y que el recordatorio use el urgente. Que el Repository y el ViewModel decidan cuál es urgente, no la pantalla.» | `Aviso` ganó el campo `urgente`; `AvisosRepository.registrar` recibe `urgente`; el ViewModel registra el recordatorio con `urgente = true` y `Notificador` elige `CANAL_URGENTE` o `CANAL` según ese campo (con `PRIORITY_HIGH` para versiones anteriores a Android 8). | El campo tiene valor por defecto `false` para no tocar los demás avisos. El canal se sigue creando una sola vez en `AvisosApp`. | La decisión de qué es urgente es del ViewModel; el Notificador solo traduce eso a un canal. Se comprobó con `dumpsys`: `urgentes` importancia 4 y `avisos` importancia 3, y el recordatorio sale emergente. |
| «Que la notificación muestre el texto completo al expandirla con `BigTextStyle`.» | Propuso `setStyle(NotificationCompat.BigTextStyle().bigText(aviso.texto))`. | Como los textos de la guía son cortos y el cambio no se notaba, el texto expandido agrega una segunda línea con el origen y la hora del aviso. | Solo toca el `Notificador` (capa de notificación). Se comprobó expandiendo la notificación: se ve el texto, el origen y la hora. |
| «Guarda el historial en DataStore para que no se pierda al cerrar la app. El Repository no debe recibir un `Context`.» | `AvisosRepositoryDataStore` recibe un `DataStore<Preferences>` ya creado, guarda el historial como JSON en una sola clave y lo expone como `StateFlow` con `stateIn`. `MainActivity` crea el repositorio con `remember`. | Se cambió el id de las notificaciones a uno basado en la hora (los contadores desde 1 se pisarían con notificaciones viejas tras reiniciar la app). Se limitó el historial a 50 avisos. No se usó Room: para una lista de 50 elementos es demasiado. Se mantiene `AvisosRepositoryMemoria` sin cambios. | El `Context` queda fuera del Repository: solo `MainActivity` toca el `DataStore` y se lo pasa. El ViewModel no cambió su forma de usar el Repository. Se comprobó: se muestran dos avisos, se cierra la app con `force-stop` y al abrirla el historial sigue ahí. |

## Revisión del código generado

- `grep` en `viewmodel/`: no hay `Context`, `NotificationManager` ni `BroadcastReceiver` (solo un comentario).
- El receptor del modo avión sigue registrándose y quitándose en el `DisposableEffect` de la pantalla.
- Qué quedó sin hacer, a propósito: botón **Posponer 10 s** en la notificación, **receptor del cargador** y **push de Firebase** (la guía pide mínimo 3 mejoras; se hicieron 3).

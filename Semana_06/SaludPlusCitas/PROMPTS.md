# PROMPTS — Fase 2 (rama `con-ia`)

**Mejora obligatoria:** calendario dinámico en la Pantalla 6 (Fecha y hora) con `java.time.LocalDate`.
**Asistente:** Claude (Claude Code).

Cada prompt sigue el formato de la guía: **prompt → respuesta resumida → qué se corrigió**.

---

## Prompt 1: días hábiles con LocalDate

**Prompt:**
> En `FechaHoraScreen.kt` los días están fijos (lunes 12 a viernes 16 de octubre). Reemplázalos por los próximos 5 días hábiles a partir de hoy usando `java.time.LocalDate`: sin sábados, domingos ni días pasados. La fecha que viaja en la ruta y se guarda en `Cita` tiene que seguir en formato ISO (`"2026-10-08"`) para no romper `horariosDisponibles` ni el bloqueo de horarios reservados. No cambies nombres ni parámetros de las funciones.

**Respuesta resumida:**
- Función `diasHabiles(desde)` con `generateSequence(desde) { it.plusDays(1) }`, `filter { it.dayOfWeek !in setOf(SATURDAY, SUNDAY) }` y `take(5)`.
- Extensión `LocalDate.aDiaCalendario()`: arma la etiqueta corta del día con `getDisplayName(TextStyle.SHORT, es-PE)`, el número con `dayOfMonth` y la fecha ISO con `toString()`.
- `val hoy = remember { LocalDate.now() }` dentro de la pantalla, y la lista fija `dias` se borra.

**Qué se corrigió / revisó:**
- `Locale("es", "PE")` está deprecado en las versiones nuevas de Java. Se usó `Locale.forLanguageTag("es-PE")`.
- `getDisplayName` devuelve la abreviatura en minúscula y con punto (`"mié."`). Se quitó el punto y se puso la primera letra en mayúscula para que quede como en el diseño (`"Mié"`).
- No hizo falta *desugaring*: el proyecto tiene `minSdk 32` y `java.time` existe desde la API 26.
- Probado el miércoles 7/10/2026: muestra Mié 7, Jue 8, Vie 9, Lun 12, Mar 13 (salta el fin de semana). Se agendó Ana Torres el Vie 9 a las 10:00: ese horario desaparece para ella y sigue libre para Claudia Rojas. El bloqueo funciona con las fechas nuevas.

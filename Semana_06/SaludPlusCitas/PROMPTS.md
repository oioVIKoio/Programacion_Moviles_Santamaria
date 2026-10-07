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

**Qué se corrigió / decidió / revisó:**
- `Locale("es", "PE")` está deprecado en las versiones nuevas de Java. Se usó `Locale.forLanguageTag("es-PE")`.
- `getDisplayName` devuelve la abreviatura en minúscula y con punto (`"mié."`). Se quitó el punto y se puso la primera letra en mayúscula para que quede como en el diseño (`"Mié"`).
- No hizo falta *desugaring*: el proyecto tiene `minSdk 32` y `java.time` existe desde la API 26.
- Probado el miércoles 7/10/2026: muestra Mié 7, Jue 8, Vie 9, Lun 12, Mar 13 (salta el fin de semana). Se agendó Ana Torres el Vie 9 a las 10:00: ese horario desaparece para ella y sigue libre para Claudia Rojas. El bloqueo funciona con las fechas nuevas.

---

## Prompt 2: flechas por semana y mes dinámico

**Prompt:**
> Activa las flechas `<` y `>` del calendario: cada una avanza o retrocede una semana y no se puede retroceder antes de la semana actual. El título del mes y el año ("Octubre 2026") tiene que cambiar según la semana que se muestra. Mantén que al cambiar de día se recalculan los horarios y se reinicia la hora.

**Respuesta resumida:**
- Estado `semana` con `rememberSaveable { mutableIntStateOf(0) }`. Los días visibles salen de `diasHabiles(hoy.plusWeeks(semana))`.
- La flecha `<` resta 1 y está deshabilitada con `enabled = semana > 0`. La flecha `>` suma 1.
- `tituloMes(dias)` arma el título con `month.getDisplayName(TextStyle.FULL_STANDALONE, es-PE)` y el año del primer día visible.

**Qué se corrigió / decidió / revisó:**
- Caso que la guía no aclara: una semana que cruza de mes (28 de octubre a 3 de noviembre). Con solo el mes del primer día diría "Octubre 2026" aunque la mitad de los días son de noviembre. Se decidió mostrar "Octubre / Noviembre 2026" (y los dos años si cruza de año).
- Decisión: al cambiar de semana se reinician `fecha` y `hora`. Si no, el día elegido dejaría de verse y Continuar seguiría habilitado con él.
- `FULL` devuelve el mes en minúscula ("octubre"); se pone la primera letra en mayúscula.
- Probado el 7/10/2026: semana 0 = Mié 7–Mar 13 "Octubre 2026" (con `<` deshabilitada), +1 = 14–20, +3 = 28 oct–3 nov "Octubre / Noviembre 2026", +4 = 4–10 "Noviembre 2026". Al volver con `<` se detiene en la semana actual. Con Jue 8 09:00 elegido, avanzar de semana borra la selección.

---

## Prompt 3: fecha en texto en español (Pantalla 7)

**Prompt:**
> La Pantalla 7 (Confirmar cita) tiene que mostrar la fecha en texto en español, como "Martes 16 de setiembre 2026". Hoy `formatearFecha` arma el texto a mano y solo conoce los días de la semana fija de la Fase 1. Cámbiala para que use `java.time` y sirva para cualquier fecha ISO, sin cambiar su nombre ni sus parámetros.

**Respuesta resumida:**
- `DateTimeFormatter.ofPattern("EEEE d 'de' MMMM yyyy", Locale.forLanguageTag("es-PE"))` sobre `LocalDate.parse(fecha)`, con la primera letra en mayúscula.
- Se borran las listas a mano `nombresMes` y `nombresDia`.
- Si la fecha no es ISO, `DateTimeParseException` y se devuelve el texto tal cual.

**Qué se corrigió / decidió / revisó:**
- El formato de la Fase 1 ponía "de" antes del año ("13 de octubre de 2026"). Se dejó como en la guía: "… de octubre 2026", sin "de" antes del año.
- Se revisó que con `es-PE` el mes 9 sale "setiembre" (como en la guía) y no "septiembre": en Resultados aparece "Viernes 25 de setiembre 2026", así que no hizo falta reemplazarlo a mano.
- Como `formatearFecha` se usa en Confirmar, Cita agendada, Mis citas, Detalle, Notificaciones y Resultados, el cambio se ve en todas sin tocar esas pantallas.
- Probado: Ana Torres, Vie 16/10 09:00 → Confirmar, Cita agendada, Mis citas, Detalle (y su `AlertDialog`) y Notificaciones muestran "Viernes 16 de octubre 2026".

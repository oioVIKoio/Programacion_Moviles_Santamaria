# Clínica SaludPlus — App Paciente

**Curso:** Desarrollo de Aplicaciones Móviles · Tecsup
**Semana 06:** Tarea complementaria al Laboratorio 6
**Alumno:** Victor Manuel Santamaria Fabian

App de agendamiento de citas médicas en Jetpack Compose: registro e inicio de sesión, Inicio con `NavigationBar`, especialidades, médicos, fecha y hora, confirmación, mis citas con detalle y cancelación, resultados, notificaciones y términos. No usa base de datos: los datos viven en colecciones del `object Repositorio` y se pierden al cerrar la app.

## Punto de partida: esqueleto
La guía indica partir del proyecto `SaludPlusCitas.zip`, pero **no se recibió** (tampoco estaba en Canvas). El esqueleto se recreó respetando lo que define la guía:

- Árbol de paquetes: `data/model`, `data/repository`, `navigation`, `ui/theme`, `ui/components` y `ui/screens/<módulo>`.
- Completos: `MainActivity`, los 4 modelos (`Usuario`, `Especialidad`, `Medico`, `Cita`), el tema, `Rutas.kt` y `AppNavigation.kt`.
- Esqueleto: `Repositorio.kt` (colecciones + funciones con `TODO`) y las 15 pantallas con `PantallaEnConstruccion`.
- Por crear: los componentes de `ui/components` (sugeridos en `Componentes.kt`).

El paquete es `com.santamaria.saludpluscitas` para identificar al autor. La configuración de Gradle se tomó del proyecto de la Semana 05 (`ClinicaTecsup`).

**Usuario de prueba:** Victor Santamaria · teléfono `987654321` (o `victor.santamaria@gmail.com`) · contraseña `123456`.

## Requerimientos funcionales
Todos probados en el emulador (ver [Cómo probar](#cómo-probar-cada-criterio-de-la-rúbrica)).

| ID | Requerimiento | Criterio de aceptación |
|---|---|---|
| RF-01 | Registro e inicio de sesión | El paciente se registra con nombre y apellido, celular (9 dígitos, empieza con 9), correo opcional y contraseña (6 a 20, sin espacios, confirmada). Al registrarse vuelve al Login con un mensaje de confirmación y el teléfono lleno, y entra con teléfono o correo. Los errores se muestran bajo cada campo y no se permite un teléfono repetido. |
| RF-02 | Búsqueda de especialidades y médicos | La lista de especialidades se filtra mientras se escribe; los médicos de una especialidad salen ordenados por calificación y también se pueden buscar por nombre. |
| RF-03 | Agendar una cita | El paciente elige sede, médico, día y hora; Continuar solo se habilita con día y hora elegidos. Al confirmar se crea la cita y Atrás ya no vuelve al flujo de agendamiento. |
| RF-04 | Evitar reservas duplicadas | Un horario reservado desaparece para ese médico y fecha, pero sigue libre para los demás médicos y días. |
| RF-05 | Consultar y cancelar citas | Mis citas lista las del paciente ordenadas por fecha y hora (o un mensaje si no hay). El detalle muestra todos los datos y cancelar pide confirmación y libera el horario. |
| RF-06 | Sesión y perfil | El Inicio saluda con el nombre del paciente; Perfil muestra sus datos y número de citas; cerrar sesión vuelve al Splash y limpia el historial. |

## Ramas
| Rama | Fase |
|---|---|
| `sin-ia` | Fase 1: desarrollo sin asistente de IA (la guía la llama `main`) |
| `con-ia` | Fase 2: calendario dinámico con IA + `PROMPTS.md` (la guía la llama `mejora-ia`) |

## Avance Fase 1 (`sin-ia`)
| Paso de la guía | Qué se hizo | Commit |
|---|---|---|
| Esqueleto | Proyecto que compila y navega con `PantallaEnConstruccion` | `53d84c2` |
| a. Repositorio: usuarios | `registrarUsuario`, `iniciarSesion`, `cerrarSesion` | `392bfd2` |
| b. Splash + Registro + Login | Validaciones, login por teléfono o correo | `bf35904`, `aac3533` |
| c. Repositorio: especialidades y médicos | `buscarEspecialidades`, `especialidadesDestacadas`, `obtener*`, `medicosPorEspecialidad`, `buscarMedicos` | `1ae269c` |
| d. Inicio | Saludo, campana, 4 tarjetas, `LazyRow` de destacadas | `5548d4c` |
| e. `NavigationBar` | Inicio, Citas, Resultados, Perfil | `2cbffba` |
| f. Especialidades + Médicos | Búsqueda en vivo, `especialidadId`, lupa en Médicos | `0931412` |
| g. Fecha y hora | `horariosDisponibles`, 5 días fijos, `LazyVerticalGrid` | `469b2d7` |
| h. Confirmar + Cita agendada | `agendarCita`, `popUpTo` al confirmar | `d8e69fe` |
| i. Mis citas + Perfil | `citasDelUsuario`, lista vacía, cerrar sesión | `5aec530` |
| Reto 12. Detalle de cita | `cancelarCita` con `removeIf`, `AlertDialog` de confirmación | `3a16d4e` |
| Reto 13. Resultados | Modelo propio `Resultado`, lista fija en `LazyColumn` | `7584d55` |
| Reto 14. Notificaciones | Recordatorios con `map` sobre `citasDelUsuario` | `d5c106a` |
| Reto 15. Términos | Texto con `verticalScroll`; se borra `PantallaEnConstruccion` | Este commit |

**Fase 1 completa:** las 15 pantallas y el `Repositorio` terminados, sin `TODO` ni `PantallaEnConstruccion`. La Fase 2 continúa en la rama `con-ia`.

## Avance Fase 2 (`con-ia`): calendario dinámico con IA
La rama parte del código de la Fase 1 (`git checkout sin-ia -- Semana_06/SaludPlusCitas`). Los prompts, las respuestas y lo que se corrigió están en [`PROMPTS.md`](PROMPTS.md).

| Requisito de la guía | Qué se hizo | Commit |
|---|---|---|
| Partir de la Fase 1 | Se trae el proyecto de `sin-ia` como base | `304c9dc` |
| 5 días hábiles desde hoy | `LocalDate.now()` + `generateSequence` + `filter` (sin sábados ni domingos) + `take(5)` | `790dac7` |
| Flechas por semana y mes dinámico | `plusWeeks(semana)`; `<` deshabilitada en la semana actual; "Octubre 2026" (u "Octubre / Noviembre 2026" si la semana cruza de mes) | `9560839` |
| Fecha en español en la Pantalla 7 | `DateTimeFormatter` `"EEEE d 'de' MMMM yyyy"` con `es-PE` → "Viernes 16 de octubre 2026" | Este commit |
| Horarios recalculados y bloqueo | Sin cambios: la fecha sigue en ISO, así que `horariosDisponibles` y `agendarCita` funcionan igual | — |

**Fase 2 completa.**

## Cambios pedidos en clase (09/10)
| Pedido | Qué se hizo | Dónde |
|---|---|---|
| Registro → Login | El registro ya no inicia sesión: vuelve al Login con "¡Cuenta creada con éxito!" y el teléfono lleno | `RegistroScreen`, `LoginScreen`, `Rutas.Login` (`telefono` opcional) |
| Menú lateral | `ModalNavigationDrawer` en el Inicio (☰): Sede, Doctores, Agenda y Cerrar sesión (con confirmación) | `HomeScreen` |
| Flujo por sede | Agendar cita → Sedes → médicos de la sede (agrupados por especialidad, con chips y buscador) → Fecha y hora → Confirmación | `SedesScreen`, `MedicosSedeScreen` |
| Sede y teléfono del médico | Modelo `Sede` (dirección, teléfono, horario); `Medico` con `sedeId` y `telefono`, visibles en tarjetas, resumen, confirmación y detalle | `Sede.kt`, `Medico.kt`, `Repositorio` |
| Doctores por especialidad | Todos los médicos agrupados por especialidad, con buscador; tocar uno abre su agenda | `DoctoresScreen` |
| Solo horas disponibles | Al elegir el día salen solo las horas libres (mañana y tarde). Si el Dr. Ramírez tiene cita el lunes 15:00, esa hora ya no aparece para él, pero sí para los demás médicos | `horariosDisponibles` |
| Nueva confirmación | Diseño de ticket: cabecera morada con médico, fecha y hora; abajo paciente, sede y teléfonos | `ConfirmarCitaScreen` |
| Color morado | Paleta del tema en morado; avatares de los médicos con iniciales sobre el degradado de su especialidad e insignia con el ícono | `Color.kt`, `Componentes.kt` |

**Validaciones agregadas**
- Agendar (`validarCita`): sesión activa, médico existente, fecha válida, no pasada ni sábado/domingo, hora dentro de los turnos, hora ya pasada si es hoy, horario del médico libre, el paciente sin otra cita a la misma hora y motivo de máx. 200 caracteres (con contador).
- Fecha y hora: hasta 8 semanas hacia adelante; hoy no muestra horas que ya pasaron; contador de horarios disponibles.
- Login: si son solo dígitos, debe ser un teléfono de 9; si no, un correo válido.

## Cómo probar cada criterio de la rúbrica
Entrar con el usuario de prueba y seguir cada fila.

| Criterio (pts) | Prueba | Resultado esperado |
|---|---|---|
| Registro, login y sesión (1) | Registrar un usuario con campos vacíos; luego uno válido. Cerrar sesión desde Perfil. | Errores bajo cada campo; el registro vuelve al Login con "¡Cuenta creada con éxito!" y al ingresar sale "¡Hola, <nombre>!"; cerrar sesión vuelve al Splash y Atrás sale de la app. |
| NavigationBar (2) | En Inicio tocar Citas, Resultados y Perfil; luego Atrás. | Cada pestaña abre su pantalla; Atrás vuelve al Inicio. |
| LazyRow / LazyColumn (2) | Inicio → destacadas. Especialidades → escribir "car" y luego "carxyz". Mis citas sin citas. | 3 destacadas en fila; "car" deja solo Cardiología; "carxyz" muestra mensaje; Mis citas vacía muestra "Aún no tienes citas agendadas". |
| Flujo de agendamiento (3) | Agendar cita → SaludPlus Los Olivos → Dra. Ana Torres → un día → 09:30 → Continuar → Confirmar cita → Atrás. | Cada pantalla recibe su parámetro (`sedeId`, `medicoId`, `fecha`, `hora`); Confirmar muestra la fecha en texto (en `sin-ia`, "Martes 13 de octubre de 2026"; en `con-ia`, p. ej. "Martes 13 de octubre 2026") y "09:30 a 10:00"; Atrás desde Cita agendada vuelve al Inicio, no a Confirmar. |
| Horarios reactivos (2) | Volver a Ana Torres, mismo día. Luego Dra. Claudia Rojas, mismo día. Probar Continuar sin elegir. | 09:30 ya no aparece para Ana ese día pero sí para Claudia; Continuar deshabilitado sin día y hora; al cambiar de día se borra la hora. |
| Repositorio (2) | Mis citas con 2 citas agendadas en desorden. | Salen ordenadas por fecha y hora. |
| Calendario IA (1) — `con-ia` | Abrir Fecha y hora; tocar `>` varias veces y luego `<`. | Salen los 5 días hábiles desde hoy; `<` está deshabilitada en la semana actual; el mes cambia (p. ej. "Octubre / Noviembre 2026" y luego "Noviembre 2026"); al cambiar de semana o de día se borra la hora. |

### Retos extra
| Reto | Prueba | Resultado esperado |
|---|---|---|
| 12. Detalle de cita | Mis citas → tocar una cita → Cancelar cita → Volver; repetir y elegir "Sí, cancelar". | Volver no borra nada; al confirmar, la cita sale de Mis citas y su horario vuelve a estar libre en Fecha y hora. |
| 13. Resultados | Pestaña Resultados de la `NavigationBar`. | 5 exámenes del más reciente al más antiguo, con estado Listo o En proceso y "3 de 5 resultados listos". |
| 14. Notificaciones | Campana del Inicio, sin citas y luego con 2 citas. | Sin citas muestra un mensaje; con citas, un recordatorio por cita ordenado por fecha. Al tocarlo se abre su detalle. |
| 15. Términos | Registro → escribir un nombre → "Términos y Condiciones" → Entendido. | Texto con scroll; al volver, lo escrito en el Registro sigue ahí. |

## Dónde está cada cosa (mapa de la guía)
| Tema de la guía | Archivo | Función / clave |
|---|---|---|
| Datos en memoria | `data/repository/Repositorio.kt` | `object` + `mutableStateListOf` / `mutableStateOf` |
| Operaciones de colecciones | `Repositorio.kt` | `filter`, `find`, `any`, `take`, `map`, `sortedByDescending`, `sortedWith(compareBy(...))`, `maxOfOrNull` |
| Rutas con parámetros | `navigation/Rutas.kt` + `AppNavigation.kt` | `crearRuta(...)` y `navArgument` |
| `popUpTo` | `ConfirmarCitaScreen.kt`, `PerfilScreen.kt`, `LoginScreen.kt` | Borrar el flujo al confirmar / la sesión al salir |
| `NavigationBar` | `ui/components/Componentes.kt` | `BarraNavegacion(navController, rutaActual)` |
| `LazyRow` | `ui/screens/home/HomeScreen.kt` | Especialidades destacadas |
| `LazyColumn` | `EspecialidadesScreen.kt`, `MedicosScreen.kt`, `MisCitasScreen.kt`, `ResultadosScreen.kt`, `NotificacionesScreen.kt` | Listas + `EstadoVacio` |
| `LazyVerticalGrid` | `FechaHoraScreen.kt` | `GridCells.Fixed(3)` con `ChipHorario` |
| Estado en pantalla | Todas las pantallas | `remember` / `rememberSaveable` + `mutableStateOf` |
| `AlertDialog` | `citas/DetalleCitaScreen.kt` | Confirmar la cancelación (`cancelarCita`) |
| `map` sobre colecciones | `notificaciones/NotificacionesScreen.kt` | Cita → recordatorio |
| Modelo propio | `data/model/Resultado.kt` | Lista fija `Repositorio.resultados` |
| Scroll | `auth/TerminosScreen.kt` | `verticalScroll(rememberScrollState())` |
| Componentes reutilizables | `ui/components/Componentes.kt` | `BotonPrimario`, `CampoTexto`, `BarraSuperior`, `TarjetaMedico`, `FilaDato`, etc. |

## Decisiones y diferencias con la guía
- **Sin imágenes:** la ilustración y las fotos de los médicos se reemplazaron por íconos de Material 3 (avatar con ícono).
- **Datos de la Figura 1:** 7 especialidades, horarios de 08:00 a 12:00 cada 30 min, los 4 médicos de Ginecología del diseño.
- **Login** con teléfono **o** correo; el registro deja la sesión iniciada.
- **Fecha en formato ISO** (`"2026-10-13"`) en la ruta y en `Cita`, para que en la Fase 2 solo cambie cómo se generan los días.
- **Fase 1:** semana fija Lun 12 – Vie 16 de octubre de 2026; las flechas del mes están deshabilitadas hasta la Fase 2. El texto de la fecha se arma a mano (`formatearFecha`, `rangoHora`).
- **Fase 2:** los días salen de `LocalDate` y `formatearFecha` usa `DateTimeFormatter` con el formato de la guía, sin "de" antes del año. Como todas las pantallas usan esa misma función, la fecha en español sale igual en Confirmar, Cita agendada, Mis citas, Detalle, Notificaciones y Resultados. Con `es-PE` el mes sale como "setiembre".
- **Semana que cruza de mes:** la guía no lo aclara; el título muestra los dos meses ("Octubre / Noviembre 2026").
- **Menú hamburguesa del Inicio:** no se incluyó; la guía no pide drawer en esta app.
- **Resultados:** la lista es fija e igual para cualquier paciente, como indica la guía.
- **`PantallaEnConstruccion`** se borró al terminar la última pantalla, porque ya no se usaba.
- **Registro** guarda los campos con `rememberSaveable`, para no perder lo escrito al abrir Términos y volver.
- **minSdk 32:** `java.time.LocalDate` funciona sin desugaring en la Fase 2.

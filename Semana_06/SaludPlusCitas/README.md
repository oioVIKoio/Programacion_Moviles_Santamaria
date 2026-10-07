# Clínica SaludPlus — App Paciente

**Curso:** Desarrollo de Aplicaciones Móviles · Tecsup
**Semana 06:** Tarea complementaria al Laboratorio 6
**Alumno:** Victor Manuel Santamaria Fabian

App de agendamiento de citas médicas en Jetpack Compose: registro e inicio de sesión, Inicio con `NavigationBar`, especialidades, médicos, fecha y hora, y confirmación de la cita. No usa base de datos: los datos viven en colecciones del `object Repositorio` y se pierden al cerrar la app.

## Punto de partida: esqueleto
La guía indica partir del proyecto `SaludPlusCitas.zip`, pero **no se recibió** (tampoco estaba en Canvas). El esqueleto se recreó respetando lo que define la guía:

- Árbol de paquetes: `data/model`, `data/repository`, `navigation`, `ui/theme`, `ui/components` y `ui/screens/<módulo>`.
- Completos: `MainActivity`, los 4 modelos (`Usuario`, `Especialidad`, `Medico`, `Cita`), el tema, `Rutas.kt` y `AppNavigation.kt`.
- Esqueleto: `Repositorio.kt` (colecciones + funciones con `TODO`) y las 15 pantallas con `PantallaEnConstruccion`.
- Por crear: los componentes de `ui/components` (sugeridos en `Componentes.kt`).

El paquete es `com.santamaria.saludpluscitas` para identificar al autor. La configuración de Gradle se tomó del proyecto de la Semana 05 (`ClinicaTecsup`).

**Usuario de prueba:** Victor Santamaria · teléfono `987654321` (o `victor.santamaria@gmail.com`) · contraseña `123456`.

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

**Pendiente:**
- [ ] Retos 12–15: Detalle de cita (`cancelarCita` + `AlertDialog`), Resultados, Notificaciones, Términos.
- [ ] Fase 2 en `con-ia`: calendario con `LocalDate` (mínimo 3 commits) + `PROMPTS.md`.
- [ ] Informe: preguntas de reflexión, observaciones y conclusiones (individual).

## Cómo probar cada criterio de la rúbrica
Entrar con el usuario de prueba y seguir cada fila.

| Criterio (pts) | Prueba | Resultado esperado |
|---|---|---|
| Registro, login y sesión (1) | Registrar un usuario con campos vacíos; luego uno válido. Cerrar sesión desde Perfil. | Errores bajo cada campo; el registro entra al Inicio con "¡Hola, <nombre>!"; cerrar sesión vuelve al Splash y Atrás sale de la app. |
| NavigationBar (2) | En Inicio tocar Citas, Resultados y Perfil; luego Atrás. | Cada pestaña abre su pantalla; Atrás vuelve al Inicio. |
| LazyRow / LazyColumn (2) | Inicio → destacadas. Especialidades → escribir "car" y luego "carxyz". Mis citas sin citas. | 3 destacadas en fila; "car" deja solo Cardiología; "carxyz" muestra mensaje; Mis citas vacía muestra "Aún no tienes citas agendadas". |
| Flujo de agendamiento (3) | Agendar cita → Ginecología → Dra. Ana Torres → Mar 13 → 09:30 → Continuar → Agendar cita → Atrás. | Cada pantalla recibe su parámetro (`especialidadId`, `medicoId`, `fecha`, `hora`); Confirmar muestra "Martes 13 de octubre de 2026" y "09:30 a 10:00"; Atrás desde Cita agendada vuelve al Inicio, no a Confirmar. |
| Horarios reactivos (2) | Volver a Ana Torres, Mar 13. Luego Dra. Claudia Rojas, Mar 13. Probar Continuar sin elegir. | 09:30 ya no aparece para Ana ese día pero sí para Claudia; Continuar deshabilitado sin día y hora; al cambiar de día se borra la hora. |
| Repositorio (2) | Mis citas con 2 citas agendadas en desorden. | Salen ordenadas por fecha y hora. |

## Dónde está cada cosa (mapa de la guía)
| Tema de la guía | Archivo | Función / clave |
|---|---|---|
| Datos en memoria | `data/repository/Repositorio.kt` | `object` + `mutableStateListOf` / `mutableStateOf` |
| Operaciones de colecciones | `Repositorio.kt` | `filter`, `find`, `any`, `take`, `map`, `sortedByDescending`, `sortedWith(compareBy(...))`, `maxOfOrNull` |
| Rutas con parámetros | `navigation/Rutas.kt` + `AppNavigation.kt` | `crearRuta(...)` y `navArgument` |
| `popUpTo` | `ConfirmarCitaScreen.kt`, `PerfilScreen.kt`, `LoginScreen.kt` | Borrar el flujo al confirmar / la sesión al salir |
| `NavigationBar` | `ui/components/Componentes.kt` | `BarraNavegacion(navController, rutaActual)` |
| `LazyRow` | `ui/screens/home/HomeScreen.kt` | Especialidades destacadas |
| `LazyColumn` | `EspecialidadesScreen.kt`, `MedicosScreen.kt`, `MisCitasScreen.kt` | Listas + `EstadoVacio` |
| `LazyVerticalGrid` | `FechaHoraScreen.kt` | `GridCells.Fixed(3)` con `ChipHorario` |
| Estado en pantalla | Todas las pantallas | `remember` / `rememberSaveable` + `mutableStateOf` |
| Componentes reutilizables | `ui/components/Componentes.kt` | `BotonPrimario`, `CampoTexto`, `BarraSuperior`, `TarjetaMedico`, `FilaDato`, etc. |

## Decisiones y diferencias con la guía
- **Sin imágenes:** la ilustración y las fotos de los médicos se reemplazaron por íconos de Material 3 (avatar con ícono).
- **Datos de la Figura 1:** 7 especialidades, horarios de 08:00 a 12:00 cada 30 min, los 4 médicos de Ginecología del diseño.
- **Login** con teléfono **o** correo; el registro deja la sesión iniciada.
- **Fecha en formato ISO** (`"2026-10-13"`) en la ruta y en `Cita`, para que en la Fase 2 solo cambie cómo se generan los días.
- **Fase 1:** semana fija Lun 12 – Vie 16 de octubre de 2026; las flechas del mes están deshabilitadas hasta la Fase 2. El texto de la fecha se arma a mano (`formatearFecha`, `rangoHora`).
- **Menú hamburguesa del Inicio:** no se incluyó; la guía no pide drawer en esta app.
- **minSdk 32:** `java.time.LocalDate` funciona sin desugaring en la Fase 2.

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

**Usuario de prueba:** teléfono `987654321` (o `juan@correo.com`) · contraseña `123456`.

## Ramas
| Rama | Fase |
|---|---|
| `sin-ia` | Fase 1: desarrollo sin asistente de IA |
| `con-ia` | Fase 2: calendario dinámico con IA + `PROMPTS.md` |

## Avance Fase 1
- [x] Esqueleto (primer commit)
- [x] a. Repositorio: usuarios
- [ ] b. Splash + Registro + Login
- [ ] c. Repositorio: especialidades y médicos
- [ ] d. Inicio con tarjetas, saludo y `LazyRow`
- [ ] e. `NavigationBar` con 4 destinos
- [ ] f. Especialidades con búsqueda + Médicos
- [ ] g. Fecha y hora con `LazyVerticalGrid`
- [ ] h. Confirmar cita + Cita agendada + `popUpTo`
- [ ] i. Mis citas + Perfil

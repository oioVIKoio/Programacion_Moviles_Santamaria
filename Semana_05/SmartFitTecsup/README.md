# TECSUP Fit - SIN IA

Aplicación Android desarrollada con **Kotlin, Jetpack Compose, Material 3 y Navigation Compose**.

Esta carpeta corresponde a la versión **SIN IA** de la actividad TECSUP Fit. La aplicación implementa un flujo básico para consultar clases disponibles, visualizar el detalle de una clase, reservar un cupo y acceder a diferentes secciones mediante una barra de navegación inferior.

---

## Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Android Studio

---

## Requerimientos funcionales

### RF01 - Pantalla principal

La aplicación debe mostrar la pantalla principal de **TECSUP Fit** con el saludo al usuario y las clases disponibles.

Se muestran:

- Yoga funcional.
- Cross Training.
- Spinning.
- Horario de cada clase.
- Sala correspondiente.

También se muestran las opciones **Hoy** y **Esta semana**.

**Evidencia:**

![img.png](img.png)

---

### RF02 - Detalle de clase

Al seleccionar una clase desde Inicio, la aplicación debe navegar hacia su detalle utilizando `classId`.

La pantalla muestra:

- Nombre de la clase.
- Horario.
- Sala.
- Descripción.
- Cupos disponibles.
- Botón **Reservar cupo**.

**Evidencia:**

![img_1.png](img_1.png)

---

### RF03 - Reserva de cupo

Desde el detalle de una clase, el usuario debe poder seleccionar:

**Reservar cupo**

La aplicación utiliza el `classId` recibido para conservar la información de la clase seleccionada y continuar hacia la confirmación.

**Evidencia:**

![img_2.png](img_2.png)

---

### RF04 - Confirmación de reserva

Después de reservar un cupo, la aplicación muestra una pantalla de confirmación con:

- Mensaje **¡Cupo reservado!**
- Nombre de la clase.
- Horario.
- Sala.
- Botón **Ver mis reservas**.

**Evidencia:**

![img_2.png](img_2.png)

---

### RF05 - Mis reservas

La aplicación debe disponer de una sección para consultar las reservas del usuario.

Cada reserva muestra:

- Nombre de la clase.
- Fecha u horario.
- Estado.

Se utilizan estados como:

- Confirmada.
- Completada.

**Evidencia:**

![img_3.png](img_3.png)

---

### RF06 - Rutinas

La barra inferior incluye acceso a la sección **Rutinas**.

La pantalla permite acceder a la sección destinada a las rutinas de entrenamiento del usuario.

**Evidencia:**

![img_4.png](img_4.png)

---

### RF07 - Perfil

La aplicación incluye una pantalla de perfil accesible desde la barra inferior.

Muestra:

- Diego Ramos.
- Plan Premium.
- 14 clases.
- 3 rachas.

**Evidencia:**

![img_5.png](img_5.png)

---

### RF08 - Barra de navegación inferior

La aplicación utiliza una barra de navegación inferior para permitir el acceso a las principales secciones:

- Inicio.
- Reservas.
- Rutinas.
- Perfil.

La opción correspondiente a la pantalla actual se muestra seleccionada.

**Evidencia:**

![img_6.png](img_6.png)

---

## Navegación

La navegación fue implementada mediante:

- `NavController`
- `NavHost`
- `composable()`
- `navigate()`
- `popBackStack()`
- `navArgument()`
- `NavType`

Para navegar al detalle y confirmación de una clase se utiliza el argumento:

```text
classId
```

El flujo principal es:

```text
INICIO
  │
  └── Seleccionar clase
          │
          ▼
    DETALLE DE CLASE
          │
          └── Reservar cupo
                  │
                  ▼
             CONFIRMACIÓN
                  │
                  └── Ver mis reservas
                            │
                            ▼
                        RESERVAS
```

La navegación inferior permite:

```text
BottomBar
├── Inicio
├── Reservas
├── Rutinas
└── Perfil
```

---

## Estructura principal

```text
com.santamaria.smartfit/
├── MainActivity.kt
├── components/
│   └── BottomNavigationBar.kt
├── navigation/
│   ├── Screen.kt
│   └── AppNavigation.kt
└── screens/
    ├── HomeScreen.kt
    ├── ClassDetailScreen.kt
    ├── ConfirmationScreen.kt
    ├── ReservationsScreen.kt
    ├── RoutinesScreen.kt
    └── ProfileScreen.kt
```

---

## Ejecución

1. Abrir el proyecto SmartFit en Android Studio.
2. Sincronizar las dependencias de Gradle.
3. Seleccionar un emulador o dispositivo Android.
4. Ejecutar la aplicación.
5. Probar el flujo de reserva y las opciones de la barra inferior.

---

## Autor

**Victor Santamaria**

Proyecto desarrollado para el curso de **Desarrollo de Aplicaciones Móviles - Tecsup**.
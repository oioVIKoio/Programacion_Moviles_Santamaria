# Prompt utilizado - NavLab CON IA

## Objetivo

El siguiente prompt fue utilizado para transformar la versión base de **NavLab** en un **Portal Académico**, manteniendo la navegación existente con Jetpack Compose y adaptando principalmente la interfaz visual y el flujo solicitado.

La IA utilizada fue **Gemini**.

---

## Prompt

```text
Quiero que modifiques este proyecto Android EXISTENTE desarrollado en Kotlin + Jetpack Compose.

ESTE ES EL ÚNICO PROMPT QUE RECIBIRÁS.

No recibirás imágenes de referencia.
No habrá una segunda ronda de correcciones.
No esperes que después te indique qué arreglar.

Debes completar EN UNA SOLA PASADA:

1. Análisis del proyecto existente.
2. Navegación.
3. Funcionalidad.
4. Diseño visual.
5. Manejo correcto de WindowInsets / edge-to-edge.
6. Compilación.
7. Corrección de errores.
8. Validación funcional y visual final.

LEE TODO ESTE PROMPT ANTES DE MODIFICAR ARCHIVOS.

==================================================
0. OBJETIVO GENERAL
==================================================

Transforma el proyecto existente en una aplicación llamada:

PORTAL ACADÉMICO

La aplicación debe representar un pequeño portal académico para un estudiante.

El usuario principal/autenticado del portal es:

Victor Santamaria

IMPORTANTE:

NO reconstruyas el proyecto desde cero.

NO reemplaces innecesariamente la arquitectura existente.

NO hagas una reinterpretación creativa.

NO quiero simplemente "una aplicación moderna".

NO quiero una interfaz Material 3 genérica.

Quiero una interfaz violeta/lila compacta, académica, limpia y muy cercana a la composición y proporciones especificadas en este prompt.

==================================================
1. PRIORIDADES
==================================================

Trabaja en este orden de prioridad:

1. Que compile.
2. Que toda la navegación funcione.
3. Que se conserve la arquitectura existente.
4. Que el diseño siga MUY DE CERCA estas especificaciones.
5. Que el código siga siendo fácil de entender.
6. Que sea fácil modificarlo durante una evaluación.

IMPORTANTE:

"COMPACTO" NO significa:

- texto diminuto;
- TopAppBar diminuta;
- títulos escondidos;
- elementos difíciles de leer.

"COMPACTO" significa:

- menos espacio desperdiciado;
- paddings razonables;
- Cards proporcionadas;
- componentes agrupados;
- evitar Spacers gigantes.

Si necesitas ahorrar espacio:

1. reduce Spacer;
2. reduce padding;
3. reduce altura innecesaria;
4. reduce márgenes;
5. SOLO AL FINAL reduce fontSize.

Nunca sacrifiques legibilidad primero.

==================================================
2. INSPECCIONA EL PROYECTO ANTES DE EDITAR
==================================================

Antes de modificar cualquier cosa, inspecciona los archivos reales del proyecto.

Revisa especialmente:

- MainActivity.kt
- Screen.kt
- AppNavigation.kt
- HomeScreen.kt
- ListScreen.kt
- DetailScreen.kt
- ProfileScreen.kt
- build.gradle.kts
- archivos de ui.theme relevantes

Identifica:

- packages reales;
- namespace;
- applicationId;
- dependencias;
- rutas actuales;
- navegación existente;
- parámetros;
- código que ya funciona.

NO empieces modificando archivos sin entender primero el flujo actual.

==================================================
3. ARQUITECTURA EXISTENTE
==================================================

El proyecto utiliza aproximadamente:

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose

Estructura aproximada:

com.santamaria.navlab
│
├── navigation/
│   ├── Screen.kt
│   └── AppNavigation.kt
│
├── screens/
│   ├── HomeScreen.kt
│   ├── ListScreen.kt
│   ├── DetailScreen.kt
│   └── ProfileScreen.kt
│
├── ui.theme/
│
└── MainActivity.kt

Actualmente existe aproximadamente:

Home
├── List
│    └── Detail/{itemId}
└── Profile

Se utilizan conceptos como:

- NavController
- NavHost
- composable()
- Screen.route
- navigate()
- popBackStack()
- navArgument()
- NavType.IntType
- itemId

CONSERVA TODO LO QUE YA FUNCIONE.

No refactorices únicamente por preferencia personal.

==================================================
4. ESTRUCTURA FINAL
==================================================

La estructura final debe permanecer sencilla:

com.santamaria.navlab
│
├── navigation/
│   ├── Screen.kt
│   └── AppNavigation.kt
│
├── screens/
│   ├── LoginScreen.kt
│   ├── HomeScreen.kt
│   ├── ListScreen.kt
│   ├── DetailScreen.kt
│   └── ProfileScreen.kt
│
├── ui.theme/
│
└── MainActivity.kt

Puedes crear como máximo algún archivo sencillo adicional para datos mock si realmente mejora la claridad.

NO crear innecesariamente:

- ViewModel
- Repository
- UseCase
- Clean Architecture
- Hilt
- Retrofit
- Room
- Firebase
- APIs
- backend
- autenticación real

Este es un ejercicio académico de Jetpack Compose y Navigation Compose.

Debe seguir siendo entendible por un estudiante.

==================================================
5. USUARIO PRINCIPAL DEL PORTAL
==================================================

El usuario autenticado es:

Nombre:
Victor Santamaria

Nombre completo:
Victor Santamaria

Correo:
victor.santamaria@tecsup.edu.pe

Teléfono:
+51 987 654 321

Carrera:
Diseño y Desarrollo de Software

Ciclo actual:
IV Ciclo

IMPORTANTE:

Victor Santamaria es el USUARIO DEL PORTAL.

Por lo tanto:

HOME debe mostrar:

Bienvenido,
Victor Santamaria

PROFILE debe mostrar:

Victor Santamaria

y sus datos personales/académicos.

NO utilizar Juan León como usuario autenticado.

Juan León es solamente uno de los estudiantes del Directorio de Alumnos.

Por lo tanto:

Juan León puede aparecer en:

- ListScreen
- DetailScreen

pero NO debe aparecer como:

- usuario autenticado;
- propietario de ProfileScreen;
- nombre de bienvenida del Home.

==================================================
6. NAVEGACIÓN FINAL
==================================================

Screen.kt debe seguir centralizando las rutas.

Debe existir conceptualmente:

Login
Home
List
Profile
Detail/{itemId}

CONSERVAR:

Screen.Detail.createRoute(itemId)

Flujo final:

LOGIN
   ↓
 HOME
  ├──────────────→ DIRECTORIO
  │                     ↓
  │                DETAIL/{itemId}
  │
  └──────────────→ PERFIL

También:

HOME → Cerrar Sesión → LOGIN

PROFILE → Cerrar Sesión → LOGIN

AppNavigation.kt debe continuar utilizando:

rememberNavController()
NavHost()
composable()
navArgument()
NavType.IntType

El startDestination final debe ser:

Screen.Login.route

NO reemplazar Navigation Compose.

==================================================
7. BACK STACK
==================================================

Para volver normalmente:

navController.popBackStack()

Login → Home:

debe navegar a Home evitando que presionar Atrás regrese absurdamente al Login.

Logout:

debe regresar a Login y limpiar las pantallas anteriores del back stack.

Utiliza mecanismos normales de Navigation Compose como popUpTo cuando corresponda.

NO crear un sistema manual de navegación.

==================================================
8. ESCALA VISUAL GLOBAL
==================================================

Diseña pensando aproximadamente en una pantalla de:

360 dp de ancho
800 dp de alto

La interfaz debe ser:

- compacta;
- legible;
- académica;
- limpia.

ESCALA APROXIMADA:

Texto auxiliar:
10.sp - 11.sp

Labels:
10.sp - 11.sp

Texto normal:
11.sp - 13.sp

Valores:
11.sp - 13.sp

Nombre de estudiante:
13.sp - 14.sp

Subtítulos:
13.sp - 15.sp

Título TopAppBar:
15.sp - 16.sp

Título principal:
18.sp - 21.sp

Bienvenida Home:
20.sp - 22.sp

Separación pequeña:
4.dp - 6.dp

Separación normal:
8.dp - 12.dp

Separación grande:
16.dp - 24.dp

Margen horizontal:
12.dp - 16.dp

NO utilices texto de 8.sp para información importante.

NO conviertas los títulos de TopAppBar en microtexto.

==================================================
9. PALETA VISUAL
==================================================

Mantén una identidad violeta/lila.

Colores aproximados:

Violeta principal:
#5B4AA8

Violeta oscuro:
#493B8F

Violeta acción:
#6547C7

Lila:
#8173C7

Lila claro:
#DCD6EE

Lila muy claro:
#EEEAF8

Fondo claro:
#F4F3F8

Card clara:
#EEEFF3

Texto principal:
#292633

Texto secundario:
#66616F

Logout:
#A94A55

Logout background:
#F1DADB

Puedes ajustar ligeramente los colores si el Theme existente ya posee equivalentes cercanos.

No reemplaces esta identidad por colores Material predeterminados completamente diferentes.

==================================================
10. STATUS BAR, EDGE-TO-EDGE Y WINDOW INSETS
==================================================

ESTA SECCIÓN ES OBLIGATORIA.

NINGÚN contenido debe quedar detrás de la StatusBar.

NINGÚN título debe quedar cortado por:

- barra de estado;
- cámara/notch;
- edge-to-edge.

Especialmente deben verse completos:

← Directorio de Alumnos

← Expediente Académico

← Configuración de Perfil

Si MainActivity utiliza:

enableEdgeToEdge()

NO necesariamente lo elimines.

Maneja correctamente los WindowInsets desde Compose.

Utiliza una solución estándar y coherente de Material 3 / Compose.

Por ejemplo, según corresponda:

Scaffold(
    contentWindowInsets = WindowInsets.safeDrawing
)

o WindowInsets adecuados para TopAppBar/Scaffold.

NO soluciones el problema agregando:

padding(top = 30.dp)

o valores fijos arbitrarios para simular la StatusBar.

La solución debe adaptarse al dispositivo.

El resultado correcto debe verse conceptualmente:

┌────────────────────────────────┐
│ 10:20        STATUS BAR        │
├────────────────────────────────┤
│ ← Directorio de Alumnos       │
├────────────────────────────────┤
│ contenido...                   │
└────────────────────────────────┘

NO:

┌────────────────────────────────┐
│10:20 ← Directori... superpuesto│
├────────────────────────────────┤
│ contenido...                   │
└────────────────────────────────┘

También evita que componentes importantes queden detrás de la NavigationBar inferior.

==================================================
11. REGLA GLOBAL DE TOPAPPBAR
==================================================

ListScreen, DetailScreen y ProfileScreen DEBEN mostrar una TopAppBar claramente visible.

NO quiero solamente una flecha diminuta.

NO ocultes el título.

NO permitas que el título quede debajo de la StatusBar.

Altura visual aproximada:

48.dp - 56.dp

Título:

15.sp - 16.sp

SemiBold.

ArrowBack:

20.dp - 24.dp

Los títulos deben ser:

LIST:

← Directorio de Alumnos

DETAIL:

← Expediente Académico

PROFILE:

← Configuración de Perfil

Los tres deben ser perfectamente legibles.

==================================================
12. LOGINSCREEN
==================================================

Crear LoginScreen.kt.

Debe ser la pantalla inicial.

FONDO:

claro con ligero tono gris/lila.

En el centro debe existir UNA Card de acceso.

NO debe ocupar toda la pantalla.

Composición:

┌────────────────────────────────┐
│                                │
│                                │
│     ┌──────────────────────┐   │
│     │   Portal Académico   │   │
│     │  Accede a tu cuenta  │   │
│     │                      │   │
│     │ [✉ Correo          ] │   │
│     │                      │   │
│     │ [🔒 Contraseña   👁] │   │
│     │                      │   │
│     │ [ INICIAR SESIÓN   ] │   │
│     │                      │   │
│     │ ¿Olvidaste tu       │   │
│     │ contraseña?         │   │
│     └──────────────────────┘   │
│                                │
│                                │
└────────────────────────────────┘

CARD:

Ancho:
86% - 88%.

Altura aproximada:
310.dp - 340.dp.

Padding:
16.dp.

RoundedCornerShape:
12.dp - 14.dp.

Elevation:
6.dp - 8.dp.

Color:
gris/lila muy claro.

TÍTULO:

Portal Académico

19.sp - 20.sp.

SemiBold/Bold.

Violeta oscuro.

Centrado.

SUBTÍTULO:

Accede a tu cuenta

11.sp - 12.sp.

Texto secundario.

Centrado.

TEXTFIELDS:

Correo institucional
Contraseña

fillMaxWidth().

Altura visual aproximada:
50.dp - 54.dp.

Texto:
12.sp.

Correo:
Email.

Contraseña:
Lock.

Derecha:
Visibility / VisibilityOff.

Contraseña oculta inicialmente.

Separación:
10.dp - 12.dp.

BOTÓN:

INICIAR SESIÓN

fillMaxWidth().

Altura:
44.dp - 46.dp.

CornerRadius:
6.dp - 8.dp.

Violeta fuerte.

Texto:
11.sp - 12.sp.

NO utilizar forma de píldora exagerada.

RECUPERACIÓN:

¿Olvidaste tu contraseña?

10.sp.

Violeta.

Centrado.

FUNCIONALIDAD:

Utilizar remember para:

correo
contraseña
mostrarContraseña

No necesita autenticación real.

Al pulsar:

INICIAR SESIÓN

navegar a Home y limpiar Login del historial.

==================================================
13. HOMESCREEN
==================================================

Home debe ser visualmente diferente al Login.

NO utilizar fondo blanco dominante.

La pantalla debe estar dominada por VIOLETA/LILA.

Puede utilizarse:

Brush.verticalGradient

sin dependencias externas.

Fondo aproximado:

arriba:
violeta oscuro/medio.

centro:
violeta/lila.

abajo:
lila grisáceo.

COMPOSICIÓN:

┌────────────────────────────────┐
│████████████████████████████████│
│████████████████████████████████│
│                                │
│          Bienvenido,           │
│       Victor Santamaria        │
│                                │
│   ¿Qué deseas gestionar hoy?   │
│                                │
│  ┌──────────────────────────┐  │
│  │ [👥] Directorio Alumnos │  │
│  │      Ver y gestionar    │  │
│  │      estudiantes        │  │
│  └──────────────────────────┘  │
│                                │
│  ┌──────────────────────────┐  │
│  │ [👤] Mi Perfil Académico│  │
│  │      Datos personales   │  │
│  │      y progreso         │  │
│  └──────────────────────────┘  │
│                                │
│                                │
│                                │
│       ↪ Cerrar Sesión Segura   │
└────────────────────────────────┘

BIENVENIDA:

Bienvenido,
Victor Santamaria

20.sp - 22.sp.

Bold.

Blanco.

Centrada.

PREGUNTA:

¿Qué deseas gestionar hoy?

11.sp - 12.sp.

Blanco con transparencia.

CARDS:

Dos Cards.

Ancho:
86% - 90%.

Altura:
64.dp - 70.dp.

CornerRadius:
10.dp - 12.dp.

Color:
blanco/gris muy claro.

Elevation:
suave.

Dentro:

icono dentro de pequeño Box lila.

Título:
12.sp - 13.sp.

SemiBold.

Descripción:
10.sp - 11.sp.

CARD 1:

Directorio de Alumnos
Ver y gestionar estudiantes

→ Screen.List.route

CARD 2:

Mi Perfil Académico
Datos personales y progreso

→ Screen.Profile.route

LOGOUT:

Debe estar cerca del fondo.

Utiliza Spacer(weight(1f)) si corresponde.

Icono Logout.

Texto:

Cerrar Sesión Segura

10.sp - 11.sp.

Color rojizo.

Al pulsar:

volver a Login limpiando back stack.

==================================================
14. LISTSCREEN / DIRECTORIO
==================================================

Conservar ListScreen.

Usar:

Scaffold
TopAppBar
LazyColumn

TOPAPPBAR:

Debe cumplir las reglas globales.

Título:

Directorio de Alumnos

Debe verse completo y debajo de StatusBar.

FONDO:

gris/lila extremadamente claro.

LISTA:

Padding horizontal:
10.dp - 12.dp.

Padding vertical:
6.dp - 8.dp.

Mostrar cinco estudiantes:

1.
Juan León
Ingeniería de Sistemas

2.
María García
Arquitectura

3.
Carlos Pérez
Medicina

4.
Ana López
Derecho

5.
Luis Ramírez
Administración

Cada estudiante debe ser una CARD INDEPENDIENTE.

NO debe parecer una tabla continua.

Composición:

   ┌────────────────────────────┐
   │ (avatar) Juan León       > │
   │          Ingeniería...     │
   └────────────────────────────┘

   espacio pequeño

   ┌────────────────────────────┐
   │ (avatar) María García    > │
   │          Arquitectura      │
   └────────────────────────────┘

CARD:

Altura:
60.dp - 64.dp.

fillMaxWidth().

CornerRadius:
10.dp - 12.dp.

Separación vertical:
4.dp - 5.dp.

Elevation:
2.dp - 4.dp.

Color:
gris/lila claro.

AVATAR:

38.dp - 40.dp.

CircleShape.

Si NO existen fotografías locales:

NO descargues imágenes.

NO agregues Coil.

NO uses internet.

Utiliza:

Person

o una inicial dentro de CircleShape.

NOMBRE:

12.sp - 13.sp.

SemiBold.

CARRERA:

10.sp.

Color secundario/violeta.

ChevronRight:

18.dp - 20.dp.

Gris.

Los cinco estudiantes deben verse completos o prácticamente completos en una pantalla aproximada de 360x800.

Cada estudiante debe navegar mediante:

navController.navigate(
    Screen.Detail.createRoute(id)
)

NO utilizar variables globales para seleccionar al alumno.

==================================================
15. DATOS SIMULADOS DEL DIRECTORIO
==================================================

Puedes crear una estructura sencilla como:

data class Student(
    val id: Int,
    val nombre: String,
    val carrera: String,
    val correo: String,
    val facultad: String,
    val biografia: String
)

y una lista local.

NO Repository.

NO ViewModel.

NO base de datos.

Datos:

ID 1

Juan León
Ingeniería de Sistemas
juan.leon@example.com
Ingeniería y Tecnología
Estudiante destacado con interés en desarrollo Android.

ID 2

María García
Arquitectura
maria.garcia@example.com
Arquitectura y Diseño
Estudiante interesada en diseño y planificación urbana.

ID 3

Carlos Pérez
Medicina
carlos.perez@example.com
Ciencias de la Salud
Estudiante interesado en investigación y salud.

ID 4

Ana López
Derecho
ana.lopez@example.com
Derecho
Estudiante interesada en derecho corporativo.

ID 5

Luis Ramírez
Administración
luis.ramirez@example.com
Negocios
Estudiante interesado en gestión empresarial.

==================================================
16. DETAILSCREEN / EXPEDIENTE ACADÉMICO
==================================================

Conservar estrictamente:

detail/{itemId}

Screen.Detail.createRoute(itemId)

navArgument("itemId")

NavType.IntType

DetailScreen(itemId)

El estudiante mostrado DEBE depender del itemId.

Ejemplo:

id=1
→ Juan León.

id=2
→ María García.

id=3
→ Carlos Pérez.

NO mostrar siempre al mismo estudiante.

Usar Scaffold.

TOPAPPBAR:

← Expediente Académico

Debe verse COMPLETA.

No debe quedar debajo de StatusBar.

Altura:
48.dp - 56.dp.

Título:
15.sp - 16.sp.

Debajo:

HEADER VIOLETA OSCURO.

Altura:

115.dp - 125.dp.

NO hacerlo demasiado alto.

fillMaxWidth().

Esquinas inferiores:

bottomStart:
20.dp - 24.dp.

bottomEnd:
20.dp - 24.dp.

AVATAR:

Debe aparecer SUPERPUESTO parcialmente sobre el límite inferior del header.

Utiliza Box si hace falta.

NO colocarlo simplemente como elemento normal debajo del header.

Tamaño:

70.dp - 76.dp.

CircleShape.

Borde:
3.dp claro.

Debajo:

Nombre del estudiante.

Ejemplo:

María García

16.sp - 17.sp.

Bold.

Carrera:

Arquitectura

10.sp - 11.sp.

Violeta.

CARD INFORMATIVA:

Una sola Card.

NO una Card diferente por cada dato.

Ancho:
86% - 90%.

Padding:
12.dp - 14.dp.

CornerRadius:
10.dp - 12.dp.

Elevation:
suave.

Mostrar:

[icono] ID Estudiante
        2024-000X

[icono] Correo
        correo del estudiante

[icono] Facultad
        facultad del estudiante

HorizontalDivider

Biografía

biografía correspondiente.

Labels:

10.sp - 11.sp.

Valores:

11.sp - 12.sp.

Biografía:

10.sp - 11.sp.

No agregar grandes huecos entre filas.

VOLVER:

ArrowBack debe utilizar:

navController.popBackStack()

==================================================
17. PROFILESCREEN
==================================================

ESTA PANTALLA REPRESENTA AL USUARIO DEL PORTAL:

VICTOR SANTAMARIA.

NO representa al estudiante seleccionado en el directorio.

Usar Scaffold.

TOPAPPBAR:

← Configuración de Perfil

Debe verse COMPLETA.

No debe quedar debajo de StatusBar.

Altura:
48.dp - 56.dp.

Título:
15.sp - 16.sp.

Debajo:

HEADER VIOLETA.

Altura:
120.dp - 130.dp.

Puede utilizar gradiente sencillo.

Dentro:

Avatar circular:

68.dp - 72.dp.

Centrado.

No se necesita fotografía real.

Puede utilizar icono Person.

Debajo:

Victor Santamaria

13.sp - 14.sp.

SemiBold.

Blanco.

CONTENIDO:

Fondo claro.

NO meter toda la información dentro de una Card gigante.

NO distribuir los datos por toda la altura.

NO utilizar grandes huecos entre las filas.

SECCIÓN:

INFORMACIÓN PERSONAL

10.sp - 11.sp.

Violeta.

SemiBold/Bold.

Después:

[icono] Nombre Completo
        Victor Santamaria

[icono] Correo
        victor.santamaria@tecsup.edu.pe

[icono] Teléfono
        +51 987 654 321

Cada fila:

altura aproximada:
42.dp - 48.dp.

Icono dentro de pequeño Box lila.

Label:

10.sp - 11.sp.

Valor:

11.sp - 12.sp.

Separación entre filas:

8.dp - 10.dp.

Después:

aproximadamente 16.dp - 20.dp.

SECCIÓN:

ACADÉMICO

10.sp - 11.sp.

Violeta.

SemiBold/Bold.

Filas:

[icono] Carrera
        Diseño y Desarrollo de Software

[icono] Ciclo Actual
        IV Ciclo

Mismas proporciones.

IMPORTANTE:

Toda esta información debe quedar AGRUPADA.

NO quiero:

Nombre

gran hueco

Correo

gran hueco

Teléfono

gran hueco

Académico

Quiero bloques visuales compactos y legibles.

Después de toda la información sí puedes utilizar:

Spacer(weight(1f))

para empujar el logout al fondo.

LOGOUT:

Botón ancho.

Margen:
14.dp - 16.dp.

Altura:
40.dp - 44.dp.

Fondo:
rosa/rojo muy claro.

Texto e icono:
rojo oscuro.

Texto:

Cerrar Sesión

11.sp aproximadamente.

Al pulsar:

volver a Login limpiando back stack.

==================================================
18. SCAFFOLD E INNERPADDING
==================================================

Cuando utilices:

Scaffold { innerPadding ->

el contenido debe respetar correctamente:

Modifier.padding(innerPadding)

o una solución equivalente apropiada.

Esto aplica especialmente a:

- ListScreen
- DetailScreen
- ProfileScreen

No permitas que:

- TopAppBar;
- StatusBar;
- NavigationBar

se superpongan con el contenido.

CUIDADO:

No apliques WindowInsets duplicados.

No agregues safeDrawing y statusBarsPadding simultáneamente de manera que produzcan un espacio enorme arriba.

Debe existir UNA estrategia coherente de Insets.

==================================================
19. COMPONENTES PERMITIDOS / RECOMENDADOS
==================================================

Prioriza componentes sencillos:

MaterialTheme
Scaffold
TopAppBar
Card
CardDefaults
RoundedCornerShape
Column
Row
Box
Spacer
LazyColumn
Text
Button
TextButton
OutlinedButton
OutlinedTextField
Icon
IconButton
HorizontalDivider
Modifier
padding
fillMaxSize
fillMaxWidth
height
width
size
weight
Arrangement
Alignment

Puedes crear pequeños componentes reutilizables si son fáciles de entender.

Ejemplo:

StudentCard()

ProfileInfoRow()

Pero NO escondas toda la interfaz detrás de una arquitectura compleja.

==================================================
20. ICONOS
==================================================

Utiliza Material Icons si la dependencia ya existe.

Ejemplos:

Person
People
School
Email
Phone
Lock
Visibility
VisibilityOff
ArrowBack
ChevronRight
Logout
Badge

Si ya existe:

implementation(
    "androidx.compose.material:material-icons-extended"
)

conservar.

NO agregar librerías nuevas de iconos.

==================================================
21. IMPORTS
==================================================

CUIDADO con imports automáticos incorrectos.

La clase Screen correcta es:

com.santamaria.navlab.navigation.Screen

NO importar accidentalmente:

androidx.compose.ui.graphics.BlendMode.Companion.Screen

ni ninguna otra clase llamada Screen.

Después de modificar:

revisa imports de TODOS los archivos tocados.

Elimina imports incorrectos o no utilizados cuando corresponda.

==================================================
22. NO HACER
==================================================

NO:

- cambiar packages sin necesidad;
- cambiar namespace;
- cambiar applicationId;
- reconstruir el proyecto;
- eliminar Navigation Compose;
- eliminar Screen.kt;
- eliminar AppNavigation.kt;
- romper Detail/{itemId};
- eliminar createRoute(itemId);
- utilizar variables globales para navegación;
- usar readln();
- usar println() como interfaz;
- agregar backend;
- agregar Firebase;
- agregar API;
- agregar Room;
- agregar Retrofit;
- agregar Hilt;
- agregar Coil;
- agregar ViewModel innecesario;
- agregar Repository;
- agregar arquitectura empresarial;
- cambiar Gradle sin necesidad;
- cambiar versiones de Compose sin necesidad;
- descargar imágenes;
- agregar dependencias innecesarias.

==================================================
23. VALIDACIÓN FUNCIONAL OBLIGATORIA
==================================================

Antes de terminar verifica:

1.
Abrir aplicación
→ Login.

2.
Correo editable
→ funciona.

3.
Contraseña editable
→ funciona.

4.
Mostrar/ocultar contraseña
→ funciona.

5.
Login
→ Home.

6.
Home debe mostrar:

Bienvenido,
Victor Santamaria

7.
Home
→ Directorio.

8.
Directorio debe mostrar cinco estudiantes.

9.
Juan León
→ Detail de Juan León.

10.
Volver
→ Directorio.

11.
María García
→ Detail de María García.

IMPORTANTE:

Detail debe cambiar realmente según itemId.

12.
Volver
→ Directorio.

13.
Volver
→ Home.

14.
Home
→ Profile.

15.
Profile debe mostrar:

Victor Santamaria

NO Juan León.

16.
Profile
→ volver
→ Home.

17.
Home
→ Cerrar Sesión
→ Login.

18.
Login
→ Home
→ Profile
→ Cerrar Sesión
→ Login.

==================================================
24. VALIDACIÓN VISUAL OBLIGATORIA
==================================================

NO termines simplemente porque compile.

Realiza una revisión visual final.

--------------------
LOGIN
--------------------

Debe existir:

- fondo claro/lila;
- Card centrada;
- Card 310-340dp aproximadamente;
- título legible;
- campos compactos;
- botón violeta;
- recuperación pequeña;
- ningún elemento detrás de StatusBar.

--------------------
HOME
--------------------

Debe existir:

- fondo violeta/lila dominante;
- "Bienvenido, Victor Santamaria";
- pregunta;
- dos Cards;
- Cards legibles;
- logout cerca del fondo;
- ningún contenido importante detrás de StatusBar.

--------------------
LIST
--------------------

DEBE VERSE CLARAMENTE:

← Directorio de Alumnos

Si únicamente se ve:

←

o el título aparece cortado detrás de la StatusBar:

ESTÁ MAL.

Además:

- cinco Cards;
- Cards independientes;
- 60-64dp aproximadamente;
- avatar 38-40dp;
- nombre 12-13sp;
- carrera 10sp;
- chevron;
- separación de 4-5dp.

--------------------
DETAIL
--------------------

DEBE VERSE CLARAMENTE:

← Expediente Académico

Si el título queda cortado:

ESTÁ MAL.

Debe existir:

- TopAppBar;
- header violeta 115-125dp;
- avatar superpuesto;
- nombre;
- carrera;
- Card informativa;
- ID;
- correo;
- facultad;
- biografía.

Los datos deben cambiar según itemId.

--------------------
PROFILE
--------------------

DEBE VERSE CLARAMENTE:

← Configuración de Perfil

Si únicamente aparece la flecha o el título queda cortado:

ESTÁ MAL.

Debe existir:

- header violeta 120-130dp;
- avatar;
- Victor Santamaria;
- Información Personal;
- Nombre;
- Correo;
- Teléfono;
- Académico;
- Carrera;
- Ciclo;
- Logout inferior.

La información debe estar AGRUPADA.

No debe existir media pantalla de huecos entre cada fila.

==================================================
25. VALIDACIÓN DE WINDOW INSETS
==================================================

Antes de finalizar comprueba específicamente:

LIST:

¿"Directorio de Alumnos" está completamente debajo de StatusBar?

DETAIL:

¿"Expediente Académico" está completamente debajo de StatusBar?

PROFILE:

¿"Configuración de Perfil" está completamente debajo de StatusBar?

Si alguno está:

- cortado;
- superpuesto;
- parcialmente escondido;
- demasiado cerca del notch;

NO finalices.

Corrige WindowInsets correctamente.

También verifica que el logout inferior no quede detrás de la NavigationBar.

==================================================
26. PREPARACIÓN PARA EVALUACIÓN DEL PROFESOR
==================================================

El profesor puede pedir modificaciones en vivo como:

- quitar una Screen;
- agregar una Screen;
- cambiar startDestination;
- quitar Profile;
- agregar botón;
- quitar botón;
- agregar alumno;
- eliminar alumno;
- modificar ruta;
- cambiar itemId;
- agregar Checkbox;
- agregar Switch;
- modificar LazyColumn;
- quitar TopAppBar;
- modificar Detail.

Por eso:

Screen.kt debe ser sencillo.

AppNavigation.kt debe ser sencillo.

Una Screen principal por archivo.

Nombres claros.

Navegación explícita.

Estados fáciles de localizar.

Datos mock fáciles de modificar.

NO ocultes la lógica importante detrás de abstracciones complejas.

==================================================
27. ORDEN DE EJECUCIÓN
==================================================

Realiza TODO en esta única ejecución.

PASO 1:
Inspecciona el proyecto.

PASO 2:
Identifica packages, navegación y dependencias.

PASO 3:
Identifica qué ya funciona.

PASO 4:
Conserva navegación existente.

PASO 5:
Agrega LoginScreen.

PASO 6:
Actualiza Screen.kt mínimamente.

PASO 7:
Actualiza AppNavigation.kt mínimamente.

PASO 8:
Adapta HomeScreen.

PASO 9:
Adapta ListScreen.

PASO 10:
Adapta DetailScreen.

PASO 11:
Adapta ProfileScreen.

PASO 12:
Comprueba que Victor Santamaria sea el usuario del portal.

PASO 13:
Comprueba que los alumnos del directorio sean independientes del perfil.

PASO 14:
Revisa TopAppBars.

PASO 15:
Revisa WindowInsets.

PASO 16:
Revisa escala visual.

PASO 17:
Revisa imports.

PASO 18:
Compila el proyecto.

PASO 19:
Corrige TODOS los errores de compilación introducidos.

PASO 20:
Verifica navegación completa.

PASO 21:
Verifica itemId con al menos dos estudiantes diferentes.

PASO 22:
Realiza validación visual final.

NO termines inmediatamente después de que compile.

==================================================
28. CRITERIO FINAL
==================================================

El resultado debe sentirse como un Portal Académico diseñado específicamente para este ejercicio.

NO como una interfaz Material genérica.

Debe quedar:

LOGIN
→ Card compacta centrada.

HOME
→ fondo violeta + Victor Santamaria + dos Cards + logout inferior.

LIST
→ TopAppBar completamente visible + cinco alumnos.

DETAIL
→ TopAppBar completamente visible + header violeta corto + avatar superpuesto + información según itemId.

PROFILE
→ TopAppBar completamente visible + header violeta + Victor Santamaria + datos agrupados + logout inferior.

Flujo:

Login
  ↓
Home
  ├── List
  │    └── Detail/{itemId}
  │
  └── Profile

==================================================
29. RESPUESTA FINAL
==================================================

Trabaja DIRECTAMENTE sobre los archivos reales del proyecto.

NO me entregues solamente fragmentos de ejemplo.

NO me des un tutorial antes de modificar.

NO me preguntes cómo debe verse algo si ya está especificado aquí.

Al terminar responde brevemente indicando:

- archivos creados;
- archivos modificados;
- rutas finales;
- flujo final;
- confirmación de compilación;
- decisiones importantes.

RECUERDA:

ESTE ES EL ÚNICO PROMPT.

NO habrá un segundo prompt para corregir la interfaz.

Debes dejar:

NAVEGACIÓN
+
FUNCIONALIDAD
+
DISEÑO
+
WINDOW INSETS
+
COMPILACIÓN

terminados en esta única ejecución.
```

---

## Resultado esperado del prompt

El prompt solicitó conservar la arquitectura y funcionalidad existente mientras se realizaban las siguientes mejoras:

- Incorporación de una pantalla de Login.
- Adaptación del Home al Portal Académico.
- Directorio de alumnos mediante `LazyColumn`.
- Navegación al expediente utilizando `itemId`.
- Expediente académico dependiente del estudiante seleccionado.
- Pantalla de perfil académico.
- Cierre de sesión.
- Mejora visual de las pantallas tomando como referencia el diseño solicitado.
- Conservación de `Screen.kt`, `AppNavigation.kt` y Navigation Compose.
- Revisión final de rutas, argumentos e imports.

---

## Validación

Después de aplicar el prompt se verificó manualmente el siguiente flujo:

    Login
      ↓
    Home
      ├── Directorio
      │      ↓
      │   Detail/{itemId}
      │
      └── Perfil
             ↓
        Cerrar sesión
             ↓
           Login

También se revisó que el proyecto compilara correctamente y que las rutas existentes continuaran funcionando.
# Prompt utilizado - TECSUP Fit CON IA

Este documento contiene el prompt utilizado para mejorar la versión **CON IA** de TECSUP Fit.

La IA recibió como base la implementación funcional desarrollada previamente. El objetivo fue conservar la arquitectura y navegación existentes, aproximar la interfaz al diseño de referencia y agregar una mejora funcional sin incorporar backend ni persistencia.

## Prompt

```text

Quiero que MODIFIQUES este proyecto Android existente desarrollado en Kotlin + Jetpack Compose.

IMPORTANTE:
Trabaja DIRECTAMENTE sobre el proyecto actual.

NO reconstruyas el proyecto desde cero.
NO crees otro proyecto.
NO reemplaces innecesariamente la arquitectura existente.
NO elimines funcionalidades que actualmente funcionan.

Antes de modificar cualquier archivo, inspecciona el proyecto completo y entiende su funcionamiento actual.

==================================================
1. CONTEXTO DEL PROYECTO
==================================================

El proyecto representa una aplicación móvil llamada TECSUP Fit para consultar clases de gimnasio, visualizar sus detalles, reservar cupos y consultar información del usuario.

Está desarrollado utilizando:

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose

El package principal existente es:

com.santamaria.smartfit

Actualmente existe aproximadamente esta estructura:

components/
- BottomNavigationBar.kt

navigation/
- Screen.kt
- AppNavigation.kt

screens/
- HomeScreen.kt
- ClassDetailScreen.kt
- ConfirmationScreen.kt
- ReservationsScreen.kt
- RoutinesScreen.kt
- ProfileScreen.kt

MainActivity.kt

La aplicación YA FUNCIONA.

Actualmente ya se implementó:

- Pantalla principal.
- Lista de clases.
- Navegación al detalle de una clase.
- Envío de classId mediante Navigation Compose.
- Detalle de clase.
- Reserva de cupo.
- Pantalla de confirmación.
- Pantalla Mis reservas.
- Pantalla Rutinas.
- Pantalla Perfil.
- Bottom Navigation Bar.
- Navegación mediante NavController.
- NavHost.
- composable().
- navigate().
- popBackStack().
- navArgument().
- NavType.

QUIERO CONSERVAR TODO LO QUE YA FUNCIONA.

==================================================
2. OBJETIVO
==================================================

Mejora el proyecto existente para que visualmente se aproxime LO MÁS FIELMENTE POSIBLE a la interfaz de referencia de TECSUP Fit.

NO quiero un rediseño libre.

La prioridad es reproducir:

- distribución
- proporciones
- ubicación de elementos
- tamaños relativos
- colores
- cards
- espaciados
- bordes redondeados
- jerarquía tipográfica
- Bottom Navigation Bar

La identidad visual debe utilizar principalmente:

- verde petróleo / verde oscuro
- verde claro para elementos secundarios
- fondos gris muy claro
- cards claras
- esquinas redondeadas
- tipografía oscura
- botones verdes

La interfaz debe seguir siendo sencilla y entendible para un estudiante de Jetpack Compose.

==================================================
3. HOME
==================================================

Mejora HomeScreen para reproducir la referencia.

En la parte superior debe existir un encabezado verde de ancho completo.

Mostrar:

TECSUP Fit
Hola, Victor

IMPORTANTE:
Reemplaza cualquier saludo "Hola, Diego" por:

Hola, Victor

Debajo deben aparecer dos opciones compactas:

Hoy
Esta semana

La opción seleccionada debe resaltarse en verde.

Después:

Clases disponibles

Mostrar las siguientes Cards:

Yoga funcional
7:00 am · Sala 2

Cross Training
6:00 pm · Sala 1

Spinning
7:30 pm · Sala 3

Cada Card debe tener:

- pequeño icono relacionado con entrenamiento a la izquierda
- nombre de la clase
- horario
- sala
- fondo gris/verde muy claro
- bordes redondeados

Al pulsar una clase debe CONSERVARSE la navegación actual mediante:

Screen.ClassDetail.createRoute(classId)

NO reemplaces classId por variables globales.

==================================================
4. DETALLE DE CLASE
==================================================

Mejora ClassDetailScreen manteniendo la navegación actual.

La información debe depender del classId recibido.

Para Cross Training, por ejemplo, mostrar:

← Detalle de clase

[ área visual con icono de entrenamiento ]

Cross Training
6:00 pm · Sala 1 · 45 min

Entrenamiento funcional de alta intensidad.
Cupos limitados.

8 de 12 cupos disponibles

En la parte inferior:

[ RESERVAR CUPO ]

El botón debe ser ancho, verde y con bordes redondeados.

Debe continuar navegando hacia ConfirmationScreen enviando classId.

Debe existir la opción para regresar mediante:

navController.popBackStack()

==================================================
5. CONFIRMACIÓN
==================================================

Mejora ConfirmationScreen para reproducir la referencia.

El contenido debe estar principalmente centrado.

Mostrar:

[ círculo verde claro con check ]

¡Cupo reservado!

Nombre de la clase

Hoy, horario · sala

[ VER MIS RESERVAS ]

Ejemplo:

¡Cupo reservado!

Cross Training

Hoy, 6:00 pm · Sala 1

El botón "Ver mis reservas" debe continuar navegando hacia ReservationsScreen.

NO hardcodees siempre Cross Training si el classId permite identificar la clase seleccionada.

==================================================
6. MIS RESERVAS
==================================================

Mejora ReservationsScreen siguiendo la referencia.

Título:

Mis reservas

Mostrar Cards verticales.

Ejemplo:

Cross Training
Hoy, 6:00 pm
Confirmada

Yoga funcional
Ayer, 7:00 am
Completada

La reserva Confirmada debe mostrar:

- detalle verde vertical a la izquierda
- etiqueta verde suave "Confirmada"

La reserva Completada debe mostrar:

- etiqueta gris "Completada"

Mantén LazyColumn si ya existe.

==================================================
7. MEJORA FUNCIONAL CON IA: CANCELAR RESERVA
==================================================

Agrega UNA mejora funcional sencilla sobre la versión existente:

PERMITIR CANCELAR UNA RESERVA CONFIRMADA.

IMPORTANTE:
Debe ser una funcionalidad LOCAL y sencilla.

NO agregar backend.
NO agregar base de datos.
NO agregar Firebase.
NO agregar ViewModel únicamente para esta función.

En ReservationsScreen:

Una reserva cuyo estado sea "Confirmada" debe ofrecer una acción:

Cancelar reserva

Al pulsarla:

1. Mostrar un AlertDialog de Material 3.

Título:

¿Cancelar reserva?

Mensaje aproximado:

¿Estás seguro de que deseas cancelar esta reserva?

Opciones:

Volver
Cancelar reserva

2. Si el usuario confirma:

Cambiar localmente:

Confirmada
→
Cancelada

3. La interfaz debe actualizarse inmediatamente mediante estado de Jetpack Compose.

Puedes utilizar:

- remember
- mutableStateOf

o una solución equivalente sencilla.

4. Una reserva Cancelada NO debe poder cancelarse nuevamente.

5. Una reserva Completada NO debe poder cancelarse.

6. El estado Cancelada debe mostrarse mediante una etiqueta rojo suave.

NO conviertas esto en un sistema complejo de gestión de reservas.

==================================================
8. RUTINAS
==================================================

Conserva RoutinesScreen.

La referencia visual no define detalladamente esta pantalla, por lo que NO quiero que inventes una funcionalidad compleja.

Mantén una pantalla sencilla y funcional, integrada visualmente con TECSUP Fit.

Puede mostrar:

Mis rutinas

y una pequeña sección o Card indicando las rutinas de entrenamiento disponibles.

NO agregues backend ni funcionalidades innecesarias.

==================================================
9. PERFIL
==================================================

Mejora ProfileScreen para reproducir la referencia.

IMPORTANTE:

El perfil debe corresponder a:

Victor Santamaria

NO Diego Ramos.

Mostrar:

Mi perfil

[ avatar circular ]

VS

Victor Santamaria
Plan Premium

Debajo mostrar dos Cards pequeñas horizontales:

14
Clases

3
Rachas

Mantén el diseño compacto.

La referencia NO muestra edición de perfil.

Por lo tanto:

NO agregues edición de perfil.
NO agregues formularios.
NO agregues correo.
NO agregues teléfono.
NO agregues dirección.
NO agregues información médica.
NO agregues backend.
NO agregues base de datos.

El objetivo es reproducir fielmente la pantalla de perfil proporcionada.

==================================================
10. BOTTOM NAVIGATION BAR
==================================================

CONSERVA BottomNavigationBar.kt.

La barra inferior debe permanecer visible en las pantallas principales correspondientes.

Debe contener exactamente:

Inicio
Reservas
Rutinas
Perfil

Cada opción debe tener:

- icono
- texto
- indicador visual de selección

La opción seleccionada debe utilizar verde.

Las opciones no seleccionadas deben utilizar un color neutro.

Cuando el usuario se encuentre en Inicio:

Inicio debe aparecer seleccionado.

Cuando esté en Reservas:

Reservas debe aparecer seleccionado.

Cuando esté en Rutinas:

Rutinas debe aparecer seleccionado.

Cuando esté en Perfil:

Perfil debe aparecer seleccionado.

NO reemplaces BottomNavigationBar por Navigation Drawer.

NO agregues menú hamburguesa.

==================================================
11. DISEÑO GENERAL
==================================================

Utiliza principalmente componentes estándar de Jetpack Compose y Material 3:

- Scaffold
- Column
- Row
- Box
- Spacer
- Card
- CardDefaults
- LazyColumn
- Text
- Button
- TextButton
- Icon
- IconButton
- NavigationBar
- NavigationBarItem
- AlertDialog
- MaterialTheme
- RoundedCornerShape
- Modifier
- padding
- fillMaxWidth
- fillMaxSize
- weight
- Arrangement
- Alignment

Utiliza iconos Material ya disponibles en el proyecto.

NO agregues librerías externas únicamente por estética.

Paleta aproximada:

Verde principal:
#087F6B o un verde petróleo similar.

Verde oscuro:
#00695C o equivalente.

Verde claro:
tono verde muy suave para Cards, iconos y estados.

Fondo:
gris muy claro.

Cards:
gris/blanco suave.

Confirmada:
verde suave.

Completada:
gris.

Cancelada:
rojo suave.

No es obligatorio utilizar exactamente estos códigos si el Theme existente ofrece una solución equivalente.

==================================================
12. SCAFFOLD Y ESPACIADO
==================================================

Cuando utilices Scaffold:

Scaffold(
    bottomBar = {
        BottomNavigationBar(navController)
    }
) { innerPadding ->

    // contenido
}

TODO contenido debe respetar innerPadding.

Evita:

- contenido debajo de la barra de estado
- contenido detrás del BottomBar
- botones cortados
- Cards pegadas a los bordes
- textos superpuestos

==================================================
13. NAVEGACIÓN: NO ROMPER
==================================================

La navegación existente es parte fundamental del ejercicio.

CONSERVA:

Screen.kt
AppNavigation.kt
BottomNavigationBar.kt

Mantén las rutas existentes.

Especialmente conserva:

classId

en:

ClassDetail
Confirmation

Mantén:

navArgument()
NavType.IntType

cuando corresponda.

NO utilices variables globales para sustituir argumentos de navegación.

==================================================
14. CAMBIOS MÍNIMOS
==================================================

Aplica el principio de mínimo cambio.

Si algo ya funciona:
NO lo reescribas sin necesidad.

Si una pantalla solamente necesita mejoras visuales:
modifica principalmente su UI.

Si una ruta ya funciona:
consérvala.

Si:

Screen.ClassDetail.createRoute(classId)

funciona:
CONSÉRVALO.

Si ConfirmationScreen recibe classId correctamente:
conserva esa lógica.

Si BottomNavigationBar ya funciona:
conserva su navegación.

La mejora funcional principal adicional debe ser:

Cancelación local de una reserva confirmada mediante AlertDialog.

==================================================
15. RESTRICCIONES
==================================================

NO:

- cambiar el package principal
- crear otro proyecto
- eliminar Navigation Compose
- eliminar Screen.kt
- eliminar AppNavigation.kt
- eliminar BottomNavigationBar.kt
- eliminar classId
- sustituir navegación por variables globales
- agregar Firebase
- agregar backend
- agregar base de datos
- agregar APIs externas
- agregar autenticación
- agregar ViewModel sin necesidad
- modificar versiones de Gradle sin necesidad
- modificar versiones de Compose sin necesidad
- agregar dependencias innecesarias
- modificar applicationId
- agregar edición de perfil
- introducir una arquitectura exageradamente compleja
- eliminar funcionalidades que ya funcionan

==================================================
16. ANTES DE MODIFICAR
==================================================

ANTES de escribir código:

1. Inspecciona los archivos existentes.
2. Identifica los packages reales.
3. Revisa Screen.kt.
4. Revisa AppNavigation.kt.
5. Revisa BottomNavigationBar.kt.
6. Revisa todas las Screens.
7. Revisa build.gradle.kts.
8. Identifica qué ya funciona.
9. Conserva la lógica existente siempre que sea posible.
10. Identifica únicamente los archivos que necesitan cambios.

No asumas rutas o funciones sin revisar primero el proyecto.

==================================================
17. VALIDACIÓN FINAL
==================================================

Cuando termines:

1. Revisa imports.
2. Busca referencias no resueltas.
3. Comprueba todas las rutas.
4. Comprueba classId.
5. Comprueba popBackStack().
6. Comprueba Inicio.
7. Comprueba Detalle de clase.
8. Comprueba Reservar cupo.
9. Comprueba Confirmación.
10. Comprueba Ver mis reservas.
11. Comprueba Reservas.
12. Comprueba Cancelar reserva.
13. Comprueba AlertDialog.
14. Comprueba Rutinas.
15. Comprueba Perfil.
16. Comprueba que aparezca Victor Santamaria.
17. Comprueba los cuatro destinos del BottomBar.
18. Comprueba el indicador de pestaña seleccionada.
19. Compila el proyecto.
20. Corrige cualquier error introducido.

NO finalices mientras existan errores de compilación provocados por tus modificaciones.

==================================================
18. FLUJO FINAL
==================================================

Debe funcionar:

INICIO
  |
  | seleccionar clase
  v
DETALLE DE CLASE
  |
  | Reservar cupo
  v
CONFIRMACIÓN
  |
  | Ver mis reservas
  v
MIS RESERVAS
  |
  | Cancelar reserva
  v
ALERT DIALOG
  |
  | Confirmar
  v
RESERVA CANCELADA

Además:

BOTTOM BAR
├── Inicio
├── Reservas
├── Rutinas
└── Perfil
      |
      v
Victor Santamaria
Plan Premium
14 Clases
3 Rachas

==================================================
19. RESULTADO ESPERADO
==================================================

Quiero una versión CON IA de la aplicación existente que:

- conserve la arquitectura actual
- conserve Navigation Compose
- conserve classId
- conserve el flujo de reserva
- conserve BottomNavigationBar
- se parezca lo máximo posible a la referencia visual de TECSUP Fit
- utilice la identidad verde mostrada
- tenga Home mejorado
- tenga Detalle de clase mejorado
- tenga Confirmación mejorada
- tenga Mis reservas mejorado
- tenga Rutinas funcional
- tenga Perfil similar a la referencia
- muestre Victor Santamaria
- mantenga Plan Premium
- mantenga 14 Clases
- mantenga 3 Rachas
- agregue cancelación de reservas mediante AlertDialog
- continúe siendo entendible para un estudiante de Jetpack Compose
- compile correctamente

IMPORTANTE:

REALIZA LOS CAMBIOS DIRECTAMENTE SOBRE EL PROYECTO ACTUAL.

No quiero solamente código de ejemplo.
No quiero instrucciones para modificarlo manualmente.
No quiero una propuesta teórica.

Inspecciona, modifica, compila, corrige y deja funcional el proyecto existente.

Al finalizar dame únicamente un resumen breve indicando:

- archivos modificados
- mejora visual realizada
- mejora funcional agregada
- navegación conservada
- funcionamiento del BottomBar
- resultado de compilación

```
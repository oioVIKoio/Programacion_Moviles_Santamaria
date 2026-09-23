# Prompt utilizado - Clínica Tecsup CON IA

Este archivo contiene el prompt utilizado para generar la versión **CON IA** de Clínica Tecsup a partir de la versión funcional desarrollada previamente.

El objetivo fue conservar la navegación y funcionalidades existentes, mejorar la interfaz visual e incorporar funcionalidades adicionales sin reconstruir el proyecto desde cero.

---

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

El proyecto representa una aplicación móvil de una clínica para consultar médicos y gestionar citas.

Está desarrollado utilizando:

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose

El package principal existente es:

com.santamaria.clinicatecsup

Actualmente existe aproximadamente esta estructura:

navigation/
- Screen.kt
- AppNavigation.kt
- AppDrawer.kt

screens/
- HomeScreen.kt
- DoctorDetailScreen.kt
- AppointmentScreen.kt
- ConfirmationScreen.kt
- AppointmentsScreen.kt
- MedicalHistoryScreen.kt
- ProfileScreen.kt (crear si todavía no existe)

MainActivity.kt

La aplicación YA FUNCIONA.

Actualmente ya se implementó:

- Pantalla principal.
- Lista de especialidades.
- Filtro de médicos por especialidad.
- Lista de médicos.
- Navegación al perfil del médico.
- Envío de doctorId mediante Navigation Compose.
- Pantalla de perfil del médico.
- Pantalla para agendar una cita.
- Selección de fecha.
- Selección de hora.
- Pantalla de confirmación.
- Pantalla Mis citas.
- Pantalla Historial médico.
- Navigation Drawer.
- Navegación mediante NavController.
- NavHost.
- composable().
- navigate().
- popBackStack().
- navArgument().
- NavType.

QUIERO CONSERVAR TODO LO QUE YA FUNCIONA.

Además, la versión final debe incorporar:

- Cancelación local de citas confirmadas.
- Acceso a Perfil desde el Navigation Drawer.
- Pantalla de perfil del paciente.
- Edición local de información básica del paciente.

==================================================
2. OBJETIVO DE LA MODIFICACIÓN
==================================================

Mejora el proyecto existente para obtener una versión visualmente más cuidada y funcional, manteniendo el código entendible para un estudiante que está aprendiendo Jetpack Compose.

La aplicación debe mantener el concepto visual de una clínica moderna utilizando principalmente:

- violeta/morado como color principal
- fondos claros
- cards suaves
- esquinas redondeadas
- jerarquía visual clara
- espaciados consistentes
- iconografía Material
- botones principales morados
- estados visuales claramente diferenciados

NO quiero una interfaz exageradamente compleja.

La mejora debe notarse respecto a la versión base, pero debe seguir siendo posible entender el código utilizado.

==================================================
2.1 REFERENCIA VISUAL OBLIGATORIA
==================================================

La interfaz final debe reproducir LO MÁS FIELMENTE POSIBLE el diseño visual descrito a continuación.

NO quiero que inventes un diseño alternativo.
NO quiero una reinterpretación moderna diferente.
NO quiero que cambies la distribución principal.

La prioridad visual es aproximarse al diseño objetivo de "Clínica Salud+" manteniendo las funcionalidades existentes.

Debes respetar especialmente:

HOME:
- Encabezado superior morado de ancho completo.
- Texto "Clínica Salud+" destacado.
- Debajo, saludo "Hola, Victor".
- Chips pequeños para Cardiología y Pediatría.
- Título "Médicos disponibles".
- Cards compactas de médicos.
- Icono/avatar a la izquierda.
- Nombre y especialidad en el centro.
- Calificación con estrella a la derecha.
- Fondo general gris/lila muy claro.

PERFIL DEL MÉDICO:
- Flecha de regreso y título "Perfil del médico" arriba.
- Avatar circular grande y centrado.
- Nombre del médico centrado.
- Especialidad y valoración debajo.
- Descripción profesional debajo.
- Botón "Agendar cita" morado, ancho y ubicado cerca de la parte inferior.

AGENDAR CITA:
- Flecha atrás y título "Agendar cita".
- Texto "Selecciona fecha".
- Tres opciones horizontales:
  Jue 26 | Vie 27 | Sáb 28.
- La opción seleccionada debe tener fondo morado.
- Texto "Selecciona hora".
- Tres opciones horizontales:
  9:00 | 10:30 | 3:00.
- La hora seleccionada debe tener fondo morado.
- Botón "Confirmar cita" morado y ancho en la zona inferior.

CONFIRMACIÓN:
- Contenido principalmente centrado.
- Icono circular verde con check.
- Texto grande "¡Cita agendada!".
- Nombre del médico.
- Fecha y hora.
- Botón "Ver mis citas".

DRAWER:
- Panel blanco/claro.
- Borde o detalle morado.
- Encabezado:
  VS
  Victor Santamaria
  Paciente
- Separador horizontal.
- Opciones verticales:
  Inicio
  Mis citas
  Historial médico
  Perfil
- La opción seleccionada debe utilizar un fondo lila suave.
- Iconos a la izquierda.

MIS CITAS:
- Título grande "Mis citas".
- Cards verticales.
- Detalle morado en el lateral izquierdo de la cita activa.
- Nombre del médico destacado.
- Fecha y hora debajo.
- Estado mediante una etiqueta pequeña:
  Confirmada -> verde.
  Completada -> gris.
  Cancelada -> rojo suave.

HISTORIAL MÉDICO:
- Mantener la misma identidad visual.
- Cards claras y compactas.
- Fecha, especialidad, tipo de consulta y médico claramente organizados.

PERFIL DEL PACIENTE:
- Mantener la misma identidad visual morada/lila de Clínica Salud+.
- Encabezado con título "Perfil".
- Avatar circular grande con las iniciales "VS".
- Nombre "Victor Santamaria".
- Rol "Paciente".
- Card clara para información personal.
- Mostrar correo, teléfono, fecha de nacimiento y dirección.
- Iconos Material a la izquierda de cada dato.
- Botón morado "Editar perfil".
- Mantener una distribución compacta y coherente con las demás pantallas.

IMPORTANTE SOBRE LA FIDELIDAD:

Prioriza reproducir:

1. distribución
2. proporciones
3. ubicación de elementos
4. tamaños relativos
5. espaciados
6. cards
7. bordes redondeados
8. jerarquía tipográfica
9. paleta morada/lila
10. estados visuales

NO agregues elementos decorativos que cambien sustancialmente la apariencia solicitada.

NO rediseñes libremente las pantallas.

Si debes elegir entre una interfaz más sofisticada y una interfaz más parecida al diseño objetivo, ELIGE LA MÁS PARECIDA AL DISEÑO OBJETIVO.

==================================================
3. HOME / CLÍNICA SALUD+
==================================================

Mejora visualmente HomeScreen sin eliminar su funcionalidad actual.

Debe conservar:

Clínica Salud+
Hola, Victor

Especialidades:
- Cardiología
- Pediatría

Médicos disponibles:

Dra. Ana Torres
Cardiología
★ 4.9

Dr. Luis Vega
Pediatría
★ 4.7

Dra. Rosa Díaz
Dermatología
★ 4.8

Mantén el filtro por especialidad que YA EXISTE.

Al seleccionar Cardiología:
mostrar únicamente médicos de Cardiología.

Al seleccionar Pediatría:
mostrar únicamente médicos de Pediatría.

Debe ser posible volver a mostrar todos los médicos según la lógica actual.

Mejora visualmente las especialidades utilizando FilterChip, AssistChip o componentes equivalentes de Material 3 cuando sea apropiado.

Mejora las Cards de los médicos.

Cada Card debe mostrar claramente:

- icono o avatar del médico
- nombre
- especialidad
- calificación
- indicador visual de que puede abrirse

Al tocar un médico debe mantenerse:

Screen.DoctorDetail.createRoute(doctorId)

NO reemplaces doctorId por variables globales.

==================================================
4. PERFIL DEL MÉDICO
==================================================

Mejora DoctorDetailScreen manteniendo la navegación actual.

La información mostrada debe depender del doctorId recibido.

Mantén datos simulados para los médicos existentes.

Ejemplo para Ana Torres:

Dra. Ana Torres
Cardióloga
★ 4.9

Especialista en arritmias e hipertensión.
Formación en la Clínica Mayo.

Agregar o mantener:

[ AGENDAR CITA ]

El botón debe continuar navegando hacia AppointmentScreen enviando doctorId.

Debe existir una opción para volver mediante:

navController.popBackStack()

Visualmente quiero:

- encabezado claro
- avatar o icono médico
- nombre destacado
- especialidad
- calificación
- pequeña descripción
- botón principal morado
- buena distribución vertical

==================================================
5. AGENDAR CITA
==================================================

Mantén AppointmentScreen y su lógica existente.

Debe permitir seleccionar UNA fecha y UNA hora.

Fechas disponibles simuladas:

Jue 26
Vie 27
Sáb 28

Horarios:

9:00
10:30
3:00

IMPORTANTE:

La fecha seleccionada debe distinguirse visualmente.

La hora seleccionada debe distinguirse visualmente.

No deben poder existir simultáneamente dos fechas seleccionadas.

No deben poder existir simultáneamente dos horarios seleccionados.

Mantén el manejo de estado utilizando Compose.

El botón:

[ CONFIRMAR CITA ]

debe continuar enviando:

- doctorId
- fecha
- hora

hacia ConfirmationScreen mediante Navigation Compose.

NO reemplaces este flujo por variables globales.

==================================================
6. CONFIRMACIÓN DE CITA
==================================================

Mejora visualmente ConfirmationScreen.

Debe mostrar claramente:

✓

¡Cita agendada!

Nombre del médico
Fecha
Hora

[ VER MIS CITAS ]

Utiliza los argumentos recibidos desde AppointmentScreen.

No hardcodees siempre la misma información si doctorId permite identificar al médico correspondiente.

El botón "Ver mis citas" debe navegar a AppointmentsScreen.

==================================================
7. MIS CITAS
==================================================

Mejora AppointmentsScreen.

Debe mantener una lista mediante LazyColumn.

Debe mostrar como mínimo:

Dra. Ana Torres
Viernes 27, 10:30 am
Confirmada

Dr. Luis Vega
Miércoles 15, 3:00 pm
Completada

Los estados deben distinguirse visualmente.

Por ejemplo:

Confirmada -> indicador verde suave.
Completada -> indicador gris o neutro.
Cancelada -> indicador rojo suave.

==================================================
8. MEJORA FUNCIONAL CON IA: CANCELAR CITA
==================================================

Agrega una mejora funcional sobre la versión existente:

PERMITIR CANCELAR UNA CITA CONFIRMADA.

IMPORTANTE:
Esta funcionalidad debe ser sencilla y local.
NO agregar backend.
NO agregar base de datos.
NO agregar Firebase.
NO agregar ViewModel si no es realmente necesario.

En AppointmentsScreen:

Una cita cuyo estado sea "Confirmada" debe ofrecer una acción:

[ Cancelar cita ]

Al pulsarla:

1. Mostrar un AlertDialog de Material 3.

El diálogo debe indicar aproximadamente:

"¿Cancelar cita?"

"¿Estás seguro de que deseas cancelar esta cita?"

Opciones:

[ Volver ]
[ Cancelar cita ]

2. Si el usuario confirma:

Cambiar localmente el estado:

Confirmada
→
Cancelada

3. La interfaz debe actualizarse inmediatamente gracias al estado de Compose.

4. Una cita ya Cancelada NO debe mostrar nuevamente la opción para cancelarla.

5. Una cita Completada tampoco debe poder cancelarse.

Utiliza una solución sencilla con:

- remember
- mutableStateOf

o una estructura de estado equivalente apropiada para este alcance.

NO introduzcas una arquitectura compleja únicamente para implementar esta función.

==================================================
9. HISTORIAL MÉDICO
==================================================

Mejora MedicalHistoryScreen conservando su funcionalidad actual.

Debe mostrar registros simulados como:

15/08/2026
Cardiología
Control general
Dra. Ana Torres

10/06/2026
Medicina general
Consulta médica
Dr. Luis Vega

22/04/2026
Dermatología
Control de rutina
Dra. Rosa Díaz

Utiliza Cards y una jerarquía visual clara.

No necesita backend ni persistencia.

==================================================
10. PERFIL DEL PACIENTE
==================================================

Agrega una pantalla ProfileScreen para completar la opción "Perfil" del Navigation Drawer.

La opción "Perfil" debe ser completamente funcional.

Al pulsar "Perfil" desde el Drawer debe navegar a la pantalla de perfil del paciente.

Mantén:

- NavController
- NavHost
- Screen.kt
- AppNavigation.kt
- AppDrawer.kt
- Navigation Compose
- ModalNavigationDrawer

NO reemplaces la arquitectura actual.

--------------------------------------------------
10.1 RUTA DE PERFIL
--------------------------------------------------

Revisa primero Screen.kt y AppNavigation.kt.

Si todavía no existe una ruta para el perfil, agrega una ruta sencilla:

profile

Regístrala correctamente dentro del NavHost.

La opción "Perfil" de AppDrawer debe navegar hacia esta ruta utilizando NavController.

NO utilices variables globales para realizar la navegación.

--------------------------------------------------
10.2 PROFILESCREEN
--------------------------------------------------

Crea ProfileScreen.kt dentro del package real:

com.santamaria.clinicatecsup.screens

La pantalla representa el perfil del PACIENTE, no el perfil de un médico.

Debe mantener exactamente la misma identidad visual aplicada al resto de Clínica Salud+.

Mostrar:

Perfil

[ avatar circular con las iniciales VS ]

Victor Santamaria
Paciente

INFORMACIÓN PERSONAL

Correo
victor.santamaria@tecsup.edu.pe

Teléfono
+51 987 654 321

Fecha de nacimiento
15 de marzo de 2006

Dirección
Lima, Perú

No es necesario utilizar información real ni backend.

Estos son datos simulados para la interfaz.

--------------------------------------------------
10.3 DISEÑO VISUAL DEL PERFIL
--------------------------------------------------

Quiero que ProfileScreen se vea integrada con las demás pantallas.

Utiliza:

- fondo gris/lila muy claro
- color morado principal existente
- avatar circular
- Cards claras
- bordes redondeados
- iconos Material
- espaciados consistentes
- tipografía y tamaños coherentes con las otras pantallas

En la parte superior:

- botón/flecha o menú según la estructura actual
- título "Perfil"

Después:

- avatar circular
- iniciales "VS"
- Victor Santamaria
- Paciente

Debajo, una Card con la información personal.

Cada dato puede presentarse mediante una Row:

[icono] Correo
        victor.santamaria@tecsup.edu.pe

[icono] Teléfono
        +51 987 654 321

[icono] Fecha de nacimiento
        15 de marzo de 2006

[icono] Dirección
        Lima, Perú

Mantén el diseño COMPACTO.

NO conviertas esta pantalla en un formulario enorme.
NO agregues información médica sensible.
NO agregues backend.
NO agregues base de datos.
NO agregues Firebase.

--------------------------------------------------
10.4 EDITAR PERFIL
--------------------------------------------------

Agrega en la parte inferior:

[ EDITAR PERFIL ]

Al pulsarlo debe permitir editar LOCALMENTE únicamente:

- correo
- teléfono
- dirección

La edición puede implementarse en la misma pantalla utilizando estado de Jetpack Compose.

Utiliza una solución sencilla con:

- remember
- mutableStateOf
- OutlinedTextField

Cuando el usuario esté editando, el botón debe convertirse o complementarse con:

[ GUARDAR CAMBIOS ]

Al guardar:

- actualizar inmediatamente los datos mostrados
- salir del modo edición
- mantener los cambios únicamente mientras la aplicación siga ejecutándose

NO se requiere persistencia.

NO agregues ViewModel solamente para esta funcionalidad.

==================================================
11. NAVIGATION DRAWER
==================================================

CONSERVA AppDrawer.kt y la funcionalidad existente del Navigation Drawer.

El Drawer debe permitir acceder a:

- Inicio
- Mis citas
- Historial médico
- Perfil

Mejora su presentación visual.

En la parte superior debe mostrar:

VS

Victor Santamaria
Paciente

Después:

Inicio
Mis citas
Historial médico
Perfil

La opción Perfil debe utilizar un icono de persona.

Cuando el usuario se encuentre en ProfileScreen, Perfil debe mostrarse como la opción seleccionada utilizando el mismo fondo lila aplicado a las demás opciones seleccionadas.

Debe resaltarse visualmente la opción correspondiente a la pantalla actual cuando sea razonablemente posible sin complicar innecesariamente la arquitectura.

NO elimines ninguna opción existente.

NO elimines ModalNavigationDrawer.

NO sustituyas el Drawer por una navegación completamente diferente.

==================================================
12. DISEÑO GENERAL
==================================================

Utiliza principalmente componentes estándar de Jetpack Compose y Material 3:

- Scaffold
- TopAppBar
- Card
- CardDefaults
- Column
- Row
- Box
- Spacer
- LazyColumn
- LazyRow
- Text
- Button
- TextButton
- Icon
- IconButton
- FilterChip
- AlertDialog
- OutlinedTextField
- ModalNavigationDrawer
- NavigationDrawerItem
- MaterialTheme
- RoundedCornerShape
- Modifier
- padding
- fillMaxWidth
- fillMaxSize
- weight
- Arrangement
- Alignment

Utiliza iconos de Material cuando ya estén disponibles.

NO agregues librerías externas únicamente por estética.

Mantén una paleta consistente:

Morado principal aproximado:
#5B2A86 o un tono similar.

Morado secundario/lila:
#7E57C2 o similar.

Fondo:
gris/lila muy claro.

Cards:
blanco o gris muy claro.

Confirmada:
verde suave.

Cancelada:
rojo suave.

Completada:
gris/neutro.

No es obligatorio utilizar exactamente estos códigos si el Theme existente ofrece una solución equivalente.

==================================================
13. SCAFFOLD Y ESPACIADO
==================================================

Cuando utilices Scaffold:

Scaffold(
    topBar = { ... }
) { innerPadding ->

    // contenido
}

TODO contenido dentro de Scaffold debe respetar innerPadding.

Evita que:

- títulos queden debajo de la barra de estado
- botones queden debajo de la barra de navegación
- textos se superpongan
- Cards queden pegadas a los bordes
- elementos aparezcan cortados

Mantén espaciados coherentes.

==================================================
14. NAVEGACIÓN: NO ROMPER
==================================================

La navegación existente es una parte fundamental del ejercicio.

CONSERVA:

Screen.kt
AppNavigation.kt
AppDrawer.kt

Puedes modificarlos únicamente cuando sea necesario, pero NO reemplazarlos por otra arquitectura.

Mantén las rutas existentes.

Especialmente conserva:

doctorId

en:

DoctorDetail
Appointment
Confirmation

Mantén también:

navArgument()
NavType.IntType
NavType.StringType

cuando corresponda.

Para Perfil:

- agrega Screen.Profile si todavía no existe
- utiliza la ruta "profile"
- registra la ruta en NavHost
- conecta AppDrawer con Screen.Profile.route

NO utilices variables globales para sustituir argumentos de navegación.

==================================================
15. CAMBIOS MÍNIMOS
==================================================

Aplica el principio de mínimo cambio.

Si algo ya funciona:
NO lo reescribas sin necesidad.

Si una pantalla solamente necesita mejoras visuales:
modifica principalmente su UI.

Si una ruta ya funciona:
consérvala.

Si Screen.DoctorDetail.createRoute(doctorId) funciona:
consérvalo.

Si AppointmentScreen ya envía fecha y hora correctamente:
conserva esa lógica.

Si AppDrawer ya abre y cierra correctamente:
conserva esa lógica.

Para implementar Perfil, limítate principalmente a:

- crear ProfileScreen.kt
- agregar o ajustar la ruta Profile en Screen.kt
- registrar Profile en AppNavigation.kt
- conectar Perfil desde AppDrawer.kt
- realizar los imports estrictamente necesarios

NO reestructures innecesariamente las demás pantallas para agregar Perfil.

==================================================
16. RESTRICCIONES
==================================================

NO:

- cambiar el package principal
- crear otro proyecto
- borrar Navigation Compose
- eliminar Screen.kt
- eliminar AppNavigation.kt
- eliminar AppDrawer.kt
- eliminar doctorId
- reemplazar navegación por variables globales
- agregar Firebase
- agregar backend
- agregar base de datos
- agregar APIs externas
- agregar autenticación
- agregar ViewModel sin necesidad
- cambiar versiones de Gradle sin necesidad
- cambiar versiones de Compose sin necesidad
- agregar librerías externas innecesarias
- modificar applicationId sin necesidad
- introducir una arquitectura exageradamente compleja
- eliminar funcionalidades que actualmente funcionan
- eliminar la cancelación de citas si ya fue implementada
- eliminar AlertDialog si ya fue implementado
- agregar información médica sensible al perfil

==================================================
17. ANTES DE MODIFICAR
==================================================

ANTES de escribir código:

1. Inspecciona todos los archivos relevantes.
2. Identifica los packages reales.
3. Revisa Screen.kt.
4. Revisa AppNavigation.kt.
5. Revisa AppDrawer.kt.
6. Revisa todas las Screens.
7. Revisa build.gradle.kts.
8. Identifica qué funcionalidad ya existe.
9. Identifica qué archivos realmente necesitan cambios.
10. Conserva la lógica funcional existente siempre que sea posible.
11. Comprueba si ProfileScreen o Screen.Profile ya existen antes de crearlos.

No asumas nombres de funciones o rutas sin revisar primero el proyecto.

==================================================
18. DESPUÉS DE MODIFICAR
==================================================

Cuando termines:

1. Revisa todos los imports.
2. Busca referencias no resueltas.
3. Verifica todas las rutas usadas mediante navigate().
4. Comprueba que cada ruta esté registrada en NavHost.
5. Comprueba doctorId.
6. Comprueba fecha.
7. Comprueba hora.
8. Comprueba popBackStack().
9. Comprueba Navigation Drawer.
10. Comprueba filtro por especialidad.
11. Comprueba cancelación de citas.
12. Comprueba AlertDialog.
13. Comprueba que Screen.Profile exista.
14. Comprueba que la ruta Profile esté registrada en NavHost.
15. Comprueba Drawer → Perfil.
16. Comprueba Perfil → Editar perfil.
17. Comprueba Guardar cambios.
18. Comprueba que Inicio siga funcionando.
19. Comprueba que Mis citas siga funcionando.
20. Comprueba que Historial médico siga funcionando.
21. Comprueba que el flujo de agendamiento siga funcionando.
22. Compila el proyecto.
23. Corrige TODOS los errores de compilación introducidos.

NO finalices mientras existan errores de compilación provocados por tus modificaciones.

==================================================
19. FLUJO QUE DEBE FUNCIONAR
==================================================

El siguiente recorrido debe funcionar completamente:

HOME
  |
  | seleccionar especialidad
  v
FILTRAR MÉDICOS

HOME
  |
  | seleccionar médico
  v
PERFIL DEL MÉDICO
  |
  | Agendar cita
  v
AGENDAR CITA
  |
  | seleccionar fecha
  | seleccionar hora
  v
CONFIRMAR CITA
  |
  v
CONFIRMACIÓN
  |
  | Ver mis citas
  v
MIS CITAS
  |
  | Cancelar cita
  v
ALERT DIALOG
  |
  | confirmar
  v
CITA CANCELADA

Además:

DRAWER
├── Inicio
├── Mis citas
├── Historial médico
└── Perfil
      |
      v
   PROFILE SCREEN
      |
      ├── Editar perfil
      |       |
      |       v
      |   Guardar cambios
      |
      └── Drawer
              |
              ├── Inicio
              ├── Mis citas
              ├── Historial médico
              └── Perfil

Las demás rutas deben continuar funcionando EXACTAMENTE como ahora.

Todo debe continuar funcionando.

==================================================
20. RESULTADO ESPERADO
==================================================

Quiero una versión mejorada de la aplicación existente que:

- conserve la navegación original
- conserve las funcionalidades existentes
- mejore claramente la presentación visual
- se aproxime al diseño solicitado para Clínica Salud+
- mantenga el filtro de especialidades
- mantenga doctorId
- mantenga selección de fecha y hora
- mantenga la confirmación
- mantenga Mis citas
- mantenga Historial médico
- mantenga Navigation Drawer
- agregue cancelación de citas mediante AlertDialog
- incluya ProfileScreen para el paciente
- permita acceder a Perfil desde el Navigation Drawer
- muestre a Victor Santamaria como paciente
- permita editar localmente correo, teléfono y dirección
- mantenga el mismo diseño morado/lila en ProfileScreen
- siga siendo entendible para un estudiante de Jetpack Compose
- compile correctamente

IMPORTANTE:

REALIZA LOS CAMBIOS DIRECTAMENTE EN EL PROYECTO.

No quiero solamente explicaciones.
No quiero únicamente fragmentos de ejemplo.
No quiero una propuesta teórica.

Inspecciona, modifica, compila, corrige y deja funcional el proyecto existente.

Al finalizar, dame únicamente un resumen breve indicando:

- archivos modificados
- archivos nuevos, si existieran
- mejora funcional agregada
- ruta Profile agregada, si fue necesaria
- funcionamiento del perfil
- flujo final
- resultado de compilación
- cualquier decisión técnica importante
```

---

## Objetivo del prompt

El prompt fue estructurado para solicitar a la IA:

- Analizar el proyecto existente antes de modificarlo.
- Conservar la arquitectura basada en Navigation Compose.
- Mantener las rutas y argumentos existentes.
- Mejorar la interfaz siguiendo el diseño de Clínica Salud+.
- Conservar el filtro de especialidades.
- Mantener el flujo de agendamiento y confirmación de citas.
- Mantener Mis citas e Historial médico.
- Conservar y mejorar el Navigation Drawer.
- Incorporar cancelación de citas mediante `AlertDialog`.
- Incorporar el perfil de Victor Santamaria.
- Permitir la edición local de datos del perfil.
- Compilar y verificar el proyecto después de realizar los cambios.

---

## Resultado esperado

```text
Inicio
 ├── Filtro por especialidad
 ├── Médico
 │     └── Perfil del médico
 │           └── Agendar cita
 │                 └── Confirmación
 │                       └── Mis citas
 │                             └── Cancelar cita
 │
 └── Navigation Drawer
       ├── Inicio
       ├── Mis citas
       ├── Historial médico
       └── Perfil
             └── Editar perfil
```

La versión resultante debe conservar el funcionamiento previo del proyecto y aplicar las mejoras solicitadas sin incorporar backend, base de datos, Firebase ni una arquitectura innecesariamente compleja.
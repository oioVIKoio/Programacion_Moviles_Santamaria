# Prompts utilizados - Fase 2

## Prompt 1 - Estado compartido de favoritos

Necesito mejorar mi aplicación TECSUP Store desarrollada con Jetpack Compose.

Actualmente cada TarjetaProducto contiene un DropdownMenu con las opciones Favoritos, Compartir y Reportar. Necesito que al seleccionar Favoritos se pueda agregar o quitar el producto de un estado compartido ubicado en un composable superior.

La solución debe conservar la estructura existente con MainActivity, TarjetaProducto, AppNavegacion y AppDrawer.

## Prompt 2 - Contador de favoritos en el NavigationDrawer

Necesito conectar el estado compartido de favoritos con el NavigationDrawer.

El item Favoritos debe mostrar mediante un Badge la cantidad actual de productos marcados como favoritos. El contador debe actualizarse automáticamente cuando se agrega o quita un producto desde el DropdownMenu.

Si no existen productos favoritos, el Badge no debe mostrarse.

## Correcciones realizadas

Durante la integración inicial se produjo un problema en la distribución de TarjetaProducto: el nombre y precio de los productos quedaron comprimidos verticalmente y los elementos del DropdownMenu afectaron la distribución de la tarjeta.

Se corrigió la estructura manteniendo un Row como contenedor principal, una Column con Modifier.weight(1f) para reservar correctamente el espacio del nombre y precio, y un Box para mantener el DropdownMenu asociado al botón de tres puntos.

También se mantuvo el estado de favoritos en AppNavegacion para que TarjetaProducto y AppDrawer trabajen con una misma fuente de datos.
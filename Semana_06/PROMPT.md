# Prompts utilizados - Fase 2

## Prompt 1 - Estado compartido de favoritos

Necesito mejorar mi aplicación TECSUP Store desarrollada con Jetpack Compose.

Actualmente cada TarjetaProducto tiene un DropdownMenu con las opciones Favoritos, Compartir y Reportar. Quiero que al seleccionar Favoritos se comunique la acción hacia el composable principal para mantener un estado compartido de productos favoritos.

La solución debe mantener la estructura existente con MainActivity, TarjetaProducto, AppDrawer y AppNavegacion. No debe implementar todavía el badge o contador visual del NavigationDrawer, ya que eso se realizará en el siguiente avance.

La selección de Favoritos debe poder activarse y desactivarse para cada producto.
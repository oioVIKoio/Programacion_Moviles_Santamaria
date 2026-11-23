# PROMPTS - Laboratorio 09 (MuroCampus)

Fase 2, rama `con-ia`. Este es el prompt que pediría el proyecto terminado, tal cual la guía del laboratorio.

## Prompt

```text
Construye en Android Studio la app MuroCampus, tal cual la guía del Laboratorio 09 (Servicios Web y plataforma REST), con Kotlin, Jetpack Compose y Material 3, paquete com.tecsup.murocampus y minSdk 26. Que quede terminada y funcionando, en un solo bloque.

Servicio: JSONPlaceholder (https://jsonplaceholder.typicode.com/). Con Retrofit 2.11.0, OkHttp 4.12.0 (con logging-interceptor nivel BODY), kotlinx.serialization 1.7.3 (Json con ignoreUnknownKeys = true) y lifecycle-runtime-compose 2.8.7, más el permiso INTERNET.

Arquitectura MVVM + Repository, con estos archivos:
- model/Modelos.kt: Post(userId, id, title, body), Comentario(postId, id, name, email, body) y NuevoPost(userId, title, body), todos @Serializable, más Usuario como ejemplo.
- data/remote/PostApi.kt: interface con listar() @GET("posts"), detalle(id) @GET("posts/{id}") con @Path, comentarios(id) @GET("posts/{id}/comments") con @Path, porUsuario(userId) @GET("posts") con @Query("userId") y crear(nuevo) @POST("posts") con @Body; todas suspend y sin "/" al inicio de la ruta.
- data/remote/RetrofitClient.kt: object con val api by lazy, baseUrl terminada en "/".
- data/PostRepository.kt: interface y PostRepositoryRemote; cada función devuelve Result, usa try/catch y relanza CancellationException antes del catch (e: Exception).
- viewmodel/UiState.kt (Cargando, Exito, Error y mensajeDe: "Sin conexión a internet.\nRevisa tu red e inténtalo otra vez.", "Error del servidor (código)", "Respuesta inesperada", "Error desconocido"), PostsViewModel.kt y PostsViewModelFactory.kt. El ViewModel no conoce Retrofit, PostApi, HttpException ni Context.
- ui/Componentes.kt (TarjetaPost, Cargando, ErrorConReintento), PantallaLista.kt, PantallaDetalle.kt y PantallaCrear.kt. Ninguna pantalla llama a la API.

Comportamiento:
- Lista: barra superior azul con el título MuroCampus; Cargando con CircularProgressIndicator y "Cargando publicaciones…"; una tarjeta por publicación con "Usuario N", título en negrita y el texto con maxLines = 2; si falla, recuadro de error con el mensaje y el botón Reintentar. Estado en StateFlow<UiState<List<Post>>> con collectAsStateWithLifecycle y un when.
- Detalle: al tocar una tarjeta (idSeleccionado: Int?), título y texto completos y "Comentarios (N)" con el correo en azul y el texto de cada uno; post y comentarios se piden a la vez con async dentro de coroutineScope; BackHandler vuelve a la lista sin recargarla.
- Filtro: LazyRow de FilterChip con Todos y Usuario 1 a Usuario 10 (usuarioFiltro: StateFlow<Int?>, filtrar(userId) recarga con listar() o porUsuario()); si no hay resultados, "Sin publicaciones".
- Crear: FloatingActionButton "+" en la lista; formulario con dos OutlinedTextField (Título y Contenido) y botón Publicar deshabilitado si hay un campo vacío (isNotBlank); publicar(titulo, texto) con NuevoPost(userId = 1, ...) y StateFlow<UiState<Post>?>; éxito: "Publicado con id N" y se limpia el formulario; error: el mensaje y se conserva lo escrito.

Fase 2, tres mejoras sin romper el MVVM + Repository:
1. Borrar publicación: botón Eliminar en el detalle con @DELETE("posts/{id}") que devuelve Response<Unit> y revisa el código de respuesta (API, Repository, ViewModel y View).
2. Búsqueda: campo que filtra por título las publicaciones ya descargadas (ViewModel y View).
3. Encabezado con interceptor: un Interceptor de OkHttp que agrega Accept: application/json a todas las peticiones (RetrofitClient).

Textos de la interfaz en español con tildes. Que compile sin errores y que ninguna falla de red cierre la app.
```

## Resultado

La IA entregó el proyecto completo y se revisó que cumpliera la guía: los tres estados con Reintentar, el detalle con 5 comentarios, el filtro con 10 publicaciones por usuario, "Publicado con id 101" y que el ViewModel no tenga Retrofit, `PostApi`, `HttpException` ni `Context`. Las tres mejoras se probaron en el emulador antes de cada commit (evidencias en `capturas/`).

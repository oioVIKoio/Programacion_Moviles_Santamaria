package com.santamaria.saludpluscitas.ui.components

import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.santamaria.saludpluscitas.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.ui.graphics.Brush
import com.santamaria.saludpluscitas.data.model.Sede
import com.santamaria.saludpluscitas.ui.theme.MoradoMedio
import com.santamaria.saludpluscitas.ui.theme.MoradoOscuro
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.layout.RowScope
import com.santamaria.saludpluscitas.data.model.Medico
import com.santamaria.saludpluscitas.ui.theme.Estrella
import com.santamaria.saludpluscitas.ui.theme.VerdePastel
import com.santamaria.saludpluscitas.ui.theme.VerdeTexto
import com.santamaria.saludpluscitas.ui.theme.FondoClaro
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.data.model.Cita
import com.santamaria.saludpluscitas.data.model.Especialidad
import com.santamaria.saludpluscitas.data.model.Resultado
import com.santamaria.saludpluscitas.data.repository.Repositorio
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.theme.MoradoClaro
import com.santamaria.saludpluscitas.ui.theme.MoradoPrimario
import com.santamaria.saludpluscitas.ui.theme.MoradoPastel
import com.santamaria.saludpluscitas.ui.theme.BordeSuave
import com.santamaria.saludpluscitas.ui.theme.NaranjaPastel
import com.santamaria.saludpluscitas.ui.theme.NaranjaTexto
import com.santamaria.saludpluscitas.ui.theme.RojoPastel
import com.santamaria.saludpluscitas.ui.theme.RojoTexto
import com.santamaria.saludpluscitas.ui.theme.SuperficieBlanca
import com.santamaria.saludpluscitas.ui.theme.TextoSecundario

// Componentes reutilizables de la app.
// Pendientes (se crean junto con la pantalla que los usa):
//

// Botón morado redondeado de ancho completo ("Comenzar", "Registrarme", "Continuar"...).
@Composable
fun BotonPrimario(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MoradoPrimario),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(text = texto, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

// Campo de texto con el ícono en un recuadro a la izquierda (Registro y Login).
@Composable
fun CampoTexto(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    icono: ImageVector,
    modifier: Modifier = Modifier,
    error: String? = null,
    esPassword: Boolean = false,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MoradoClaro),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = MoradoPrimario
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            label = { Text(etiqueta) },
            singleLine = true,
            isError = error != null,
            supportingText = error?.let { { Text(it) } },
            visualTransformation = if (esPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (esPassword) KeyboardType.Password else tipoTeclado
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MoradoPrimario,
                unfocusedBorderColor = BordeSuave,
                focusedLabelColor = MoradoPrimario
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

// Texto normal seguido de un enlace morado ("¿Ya tienes cuenta? Iniciar sesión").
@Composable
fun TextoConEnlace(
    texto: String,
    enlace: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = texto,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = enlace,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MoradoPrimario,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable(onClick = onClick)
                .padding(4.dp)
        )
    }
}

// Tarjeta pastel del Inicio: ícono arriba y título abajo, del mismo color.
@Composable
fun TarjetaAccion(
    titulo: String,
    icono: ImageVector,
    colorFondo: Color,
    colorContenido: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorFondo),
        modifier = modifier.height(116.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = colorContenido,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = titulo,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorContenido
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

// Ícono y colores de cada especialidad (el modelo no trae ícono).
data class EstiloEspecialidad(
    val icono: ImageVector,
    val colorFondo: Color,
    val colorIcono: Color
)

fun estiloEspecialidad(especialidadId: Int): EstiloEspecialidad {
    return when (especialidadId) {
        1 -> EstiloEspecialidad(Icons.Default.Person, MoradoPastel, MoradoPrimario)
        2 -> EstiloEspecialidad(Icons.Default.ChildCare, NaranjaPastel, NaranjaTexto)
        3 -> EstiloEspecialidad(Icons.Default.Female, RojoPastel, RojoTexto)
        4 -> EstiloEspecialidad(Icons.Default.Favorite, RojoPastel, RojoTexto)
        5 -> EstiloEspecialidad(Icons.Default.Spa, NaranjaPastel, NaranjaTexto)
        6 -> EstiloEspecialidad(Icons.Default.Healing, MoradoPastel, MoradoPrimario)
        7 -> EstiloEspecialidad(Icons.Default.Visibility, MoradoPastel, MoradoPrimario)
        else -> EstiloEspecialidad(Icons.Default.Person, MoradoPastel, MoradoPrimario)
    }
}

// Ícono de la especialidad dentro de un círculo de color.
@Composable
fun IconoEspecialidad(
    especialidadId: Int,
    modifier: Modifier = Modifier,
    tamano: Int = 48
) {
    val estilo = estiloEspecialidad(especialidadId)
    Box(
        modifier = modifier
            .size(tamano.dp)
            .clip(RoundedCornerShape(50))
            .background(estilo.colorFondo),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = estilo.icono,
            contentDescription = null,
            tint = estilo.colorIcono,
            modifier = Modifier.size((tamano / 2).dp)
        )
    }
}

// Tarjeta pequeña del LazyRow de especialidades destacadas.
@Composable
fun TarjetaEspecialidadDestacada(
    especialidad: Especialidad,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SuperficieBlanca),
        border = BorderStroke(1.dp, BordeSuave),
        modifier = modifier.size(width = 104.dp, height = 116.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            IconoEspecialidad(especialidadId = especialidad.id)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = especialidad.nombre,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// Destino de la barra inferior.
data class DestinoBarra(
    val titulo: String,
    val icono: ImageVector,
    val ruta: String
)

private val destinosBarra = listOf(
    DestinoBarra("Inicio", Icons.Default.Home, Rutas.Home.ruta),
    DestinoBarra("Citas", Icons.Default.CalendarMonth, Rutas.MisCitas.ruta),
    DestinoBarra("Resultados", Icons.Default.Description, Rutas.Resultados.ruta),
    DestinoBarra("Perfil", Icons.Default.Person, Rutas.Perfil.ruta)
)

// NavigationBar con los 4 destinos principales. rutaActual marca cuál está activo.
@Composable
fun BarraNavegacion(
    navController: NavController,
    rutaActual: String
) {
    NavigationBar(containerColor = SuperficieBlanca) {
        destinosBarra.forEach { destino ->
            NavigationBarItem(
                selected = destino.ruta == rutaActual,
                onClick = {
                    if (destino.ruta != rutaActual) {
                        navController.navigate(destino.ruta) {
                            // Sin copias del mismo destino en la pila; Atrás vuelve al Inicio.
                            popUpTo(Rutas.Home.ruta)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(imageVector = destino.icono, contentDescription = null) },
                label = { Text(text = destino.titulo, fontSize = 12.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MoradoPrimario,
                    selectedTextColor = MoradoPrimario,
                    indicatorColor = MoradoClaro,
                    unselectedIconColor = TextoSecundario,
                    unselectedTextColor = TextoSecundario
                )
            )
        }
    }
}

// Barra superior de las vistas internas: flecha atrás, título centrado y acciones.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(
    titulo: String,
    onAtras: () -> Unit,
    acciones: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = titulo, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(onClick = onAtras) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás"
                )
            }
        },
        actions = acciones,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = SuperficieBlanca
        )
    )
}

// Buscador con lupa y fondo gris claro ("Buscar especialidad...").
@Composable
fun CampoBusqueda(
    valor: String,
    onValorChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        placeholder = { Text(placeholder, color = TextoSecundario) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TextoSecundario)
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MoradoPrimario,
            unfocusedBorderColor = BordeSuave,
            focusedContainerColor = FondoClaro,
            unfocusedContainerColor = FondoClaro
        ),
        modifier = modifier.fillMaxWidth()
    )
}

// Fila de la lista de especialidades: ícono de color, nombre, descripción y chevron.
@Composable
fun TarjetaEspecialidad(
    especialidad: Especialidad,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconoEspecialidad(especialidadId = especialidad.id)
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = especialidad.nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = especialidad.descripcion,
                fontSize = 13.sp,
                color = TextoSecundario
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextoSecundario
        )
    }
}

// Dos colores por especialidad para el degradado del avatar del médico.
fun degradadoEspecialidad(especialidadId: Int): List<Color> {
    return when (especialidadId) {
        1 -> listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9))
        2 -> listOf(Color(0xFFFB923C), Color(0xFFEC4899))
        3 -> listOf(Color(0xFFF472B6), Color(0xFFA855F7))
        4 -> listOf(Color(0xFFF87171), Color(0xFFBE185D))
        5 -> listOf(Color(0xFFFBBF24), Color(0xFFF97316))
        6 -> listOf(Color(0xFF60A5FA), Color(0xFF7C3AED))
        7 -> listOf(Color(0xFF2DD4BF), Color(0xFF6366F1))
        else -> listOf(MoradoMedio, MoradoPrimario)
    }
}

// "Dr. Luis Ramírez" → "LR" (sin el título Dr./Dra.).
fun iniciales(nombre: String): String {
    return nombre.split(" ")
        .filter { it.isNotBlank() && !it.endsWith(".") }
        .take(2)
        .joinToString("") { it.first().uppercase() }
}

// Círculo con degradado y las iniciales en blanco.
@Composable
fun AvatarIniciales(
    nombre: String,
    colores: List<Color>,
    modifier: Modifier = Modifier,
    tamano: Int = 64
) {
    Box(
        modifier = modifier
            .size(tamano.dp)
            .clip(CircleShape)
            .background(Brush.linearGradient(colores)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = iniciales(nombre),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = (tamano * 0.34f).sp
        )
    }
}

// Foto de cada médico según su id (res/drawable/medico_N.jpg).
private fun fotoMedico(id: Int): Int? = when (id) {
    1 -> R.drawable.medico_1
    2 -> R.drawable.medico_2
    3 -> R.drawable.medico_3
    4 -> R.drawable.medico_4
    5 -> R.drawable.medico_5
    6 -> R.drawable.medico_6
    7 -> R.drawable.medico_7
    8 -> R.drawable.medico_8
    9 -> R.drawable.medico_9
    10 -> R.drawable.medico_10
    11 -> R.drawable.medico_11
    12 -> R.drawable.medico_12
    13 -> R.drawable.medico_13
    14 -> R.drawable.medico_14
    15 -> R.drawable.medico_15
    16 -> R.drawable.medico_16
    else -> null
}

// Avatar del médico: su foto en círculo (o sus iniciales sobre el degradado de su
// especialidad si no tiene foto) y una insignia blanca con el ícono de la especialidad.
@Composable
fun AvatarMedico(
    medico: Medico,
    modifier: Modifier = Modifier,
    tamano: Int = 64
) {
    val estilo = estiloEspecialidad(medico.especialidadId)
    val insignia = (tamano * 0.4f).dp
    val foto = fotoMedico(medico.id)
    Box(modifier = modifier.size(tamano.dp)) {
        if (foto != null) {
            Image(
                painter = painterResource(id = foto),
                contentDescription = medico.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(tamano.dp)
                    .clip(CircleShape)
            )
        } else {
            AvatarIniciales(
                nombre = medico.nombre,
                colores = degradadoEspecialidad(medico.especialidadId),
                tamano = tamano
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(insignia)
                .clip(CircleShape)
                .background(Color.White)
                .padding(2.dp)
                .clip(CircleShape)
                .background(estilo.colorFondo),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = estilo.icono,
                contentDescription = null,
                tint = estilo.colorIcono,
                modifier = Modifier.size(insignia * 0.6f)
            )
        }
    }
}

// Avatar del paciente (Perfil y menú lateral): iniciales sobre degradado morado.
@Composable
fun AvatarPaciente(
    nombre: String,
    modifier: Modifier = Modifier,
    tamano: Int = 64
) {
    AvatarIniciales(
        nombre = nombre,
        colores = listOf(MoradoMedio, MoradoOscuro),
        modifier = modifier,
        tamano = tamano
    )
}

// Ícono pequeño + texto en una línea (sede y teléfono del médico).
@Composable
fun DatoChico(
    icono: ImageVector,
    texto: String,
    color: Color = TextoSecundario
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = texto, fontSize = 12.sp, color = color)
    }
}

// Tarjeta del médico: avatar, nombre, especialidad, calificación, sede, teléfono
// y chip de disponibilidad.
@Composable
fun TarjetaMedico(
    medico: Medico,
    onClick: () -> Unit
) {
    val especialidad = Repositorio.obtenerEspecialidad(medico.especialidadId)
    val sede = Repositorio.obtenerSede(medico.sedeId)

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SuperficieBlanca),
        border = BorderStroke(1.dp, BordeSuave),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarMedico(medico = medico)
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medico.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${medico.profesion} · ${especialidad?.nombre ?: ""}",
                    fontSize = 13.sp,
                    color = TextoSecundario
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Estrella,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${medico.calificacion} (${medico.resenas})",
                        fontSize = 13.sp,
                        color = TextoSecundario
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                DatoChico(Icons.Default.LocationOn, sede?.nombre ?: "")
                DatoChico(Icons.Default.Phone, medico.telefono)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = medico.disponibilidad,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = VerdeTexto,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clip(RoundedCornerShape(8.dp))
                        .background(VerdePastel)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// Tarjeta de la sede: franja con degradado morado e ícono de clínica,
// y debajo dirección, teléfono, horario y cuántos médicos atienden.
@Composable
fun TarjetaSede(
    sede: Sede,
    onClick: () -> Unit
) {
    val totalMedicos = Repositorio.medicosPorSede(sede.id).size

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SuperficieBlanca),
        border = BorderStroke(1.dp, BordeSuave),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .background(Brush.linearGradient(listOf(MoradoMedio, MoradoPrimario, MoradoOscuro)))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalHospital,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = sede.nombre,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = sede.distrito,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
                Text(
                    text = if (totalMedicos == 1) "1 médico" else "$totalMedicos médicos",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MoradoPrimario,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            DatoChico(Icons.Default.LocationOn, sede.direccion)
            Spacer(modifier = Modifier.height(4.dp))
            DatoChico(Icons.Default.Phone, sede.telefono)
            Spacer(modifier = Modifier.height(4.dp))
            DatoChico(Icons.Default.Schedule, sede.horario)
        }
    }
}

// Título de un grupo de médicos: ícono y nombre de la especialidad.
@Composable
fun EncabezadoEspecialidad(
    especialidad: Especialidad,
    total: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 12.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconoEspecialidad(especialidadId = especialidad.id, tamano = 32)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = especialidad.nombre,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(text = "$total", fontSize = 13.sp, color = TextoSecundario)
    }
}

// Mensaje centrado cuando una lista no tiene elementos.
@Composable
fun EstadoVacio(
    mensaje: String,
    modifier: Modifier = Modifier,
    icono: ImageVector = Icons.Outlined.SearchOff
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = TextoSecundario,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = mensaje,
            fontSize = 15.sp,
            color = TextoSecundario,
            textAlign = TextAlign.Center
        )
    }
}

// Tarjeta del médico arriba de Fecha y hora y del detalle de la cita:
// incluye sede y teléfono. Con mostrarCmp se agrega el CMP.
@Composable
fun ResumenMedico(
    medico: Medico,
    mostrarCmp: Boolean = false
) {
    val sede = Repositorio.obtenerSede(medico.sedeId)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(FondoClaro)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarMedico(medico = medico)
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(
                text = medico.nombre,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(text = medico.profesion, fontSize = 14.sp, color = TextoSecundario)
            if (mostrarCmp) {
                Text(text = "CMP: ${medico.cmp}", fontSize = 13.sp, color = TextoSecundario)
            }
            Spacer(modifier = Modifier.height(4.dp))
            DatoChico(Icons.Default.LocationOn, sede?.nombre ?: "")
            DatoChico(Icons.Default.Phone, medico.telefono)
        }
    }
}

// Celda del grid de horarios: morada si está seleccionada.
@Composable
fun ChipHorario(
    hora: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (seleccionado) MoradoPrimario else FondoClaro)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = hora,
            fontSize = 15.sp,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium,
            color = if (seleccionado) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

// Fila con ícono en recuadro, etiqueta pequeña y valor (Confirmar cita y resúmenes).
@Composable
fun FilaDato(
    icono: ImageVector,
    etiqueta: String,
    valor: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MoradoClaro),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icono, contentDescription = null, tint = MoradoPrimario)
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(text = etiqueta, fontSize = 13.sp, color = TextoSecundario)
            Text(
                text = valor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// Fase 2: fecha en español con java.time, en el formato de la guía.
private val formatoFecha = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM yyyy", Locale.forLanguageTag("es-PE"))

// "2026-10-13" → "Martes 13 de octubre 2026". Si no es ISO, la devuelve igual.
fun formatearFecha(fecha: String): String {
    return try {
        LocalDate.parse(fecha)
            .format(formatoFecha)
            .replaceFirstChar { it.uppercase() }
    } catch (e: DateTimeParseException) {
        fecha
    }
}

// "09:30" → "09:30 a 10:00" (cada cita dura 30 minutos).
fun rangoHora(hora: String): String {
    val partes = hora.split(":")
    if (partes.size != 2) return hora
    val total = partes[0].toInt() * 60 + partes[1].toInt() + 30
    val fin = "%02d:%02d".format(total / 60, total % 60)
    return "$hora a $fin"
}

// Resumen de una cita para Mis citas: médico, especialidad, fecha y hora.
@Composable
fun TarjetaCita(
    cita: Cita,
    onClick: () -> Unit
) {
    val medico = Repositorio.obtenerMedico(cita.medicoId)
    val especialidad = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SuperficieBlanca),
        border = BorderStroke(1.dp, BordeSuave),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconoEspecialidad(especialidadId = medico?.especialidadId ?: 0)
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medico?.nombre ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(text = especialidad?.nombre ?: "", fontSize = 13.sp, color = TextoSecundario)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatearFecha(cita.fecha),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = rangoHora(cita.hora),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MoradoPrimario
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = TextoSecundario
            )
        }
    }
}

// Reto 13: un examen con su médico, fecha y estado (Listo / En proceso).
@Composable
fun TarjetaResultado(resultado: Resultado) {
    val medico = Repositorio.obtenerMedico(resultado.medicoId)
    val fondoEstado = if (resultado.listo) VerdePastel else NaranjaPastel
    val textoEstado = if (resultado.listo) VerdeTexto else NaranjaTexto

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SuperficieBlanca),
        border = BorderStroke(1.dp, BordeSuave),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MoradoClaro),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Science, contentDescription = null, tint = MoradoPrimario)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = resultado.examen,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(text = medico?.nombre ?: "", fontSize = 13.sp, color = TextoSecundario)
                Text(text = formatearFecha(resultado.fecha), fontSize = 13.sp, color = TextoSecundario)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = resultado.detalle,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (resultado.listo) "Listo" else "En proceso",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = textoEstado,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(fondoEstado)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }
}

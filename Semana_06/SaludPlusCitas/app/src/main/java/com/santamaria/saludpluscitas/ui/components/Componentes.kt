package com.santamaria.saludpluscitas.ui.components

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
import com.santamaria.saludpluscitas.data.model.Especialidad
import com.santamaria.saludpluscitas.ui.theme.AzulClaro
import com.santamaria.saludpluscitas.ui.theme.AzulPrimario
import com.santamaria.saludpluscitas.ui.theme.AzulPastel
import com.santamaria.saludpluscitas.ui.theme.BordeSuave
import com.santamaria.saludpluscitas.ui.theme.NaranjaPastel
import com.santamaria.saludpluscitas.ui.theme.NaranjaTexto
import com.santamaria.saludpluscitas.ui.theme.RojoPastel
import com.santamaria.saludpluscitas.ui.theme.RojoTexto
import com.santamaria.saludpluscitas.ui.theme.SuperficieBlanca

// Componentes reutilizables de la app.
// Pendientes (se crean junto con la pantalla que los usa):
//
// TODO: BarraSuperior(titulo, onAtras, acciones)
//       TopAppBar con flecha atrás; la usan todas las vistas internas.
// TODO: TarjetaEspecialidad(especialidad, onClick)
//       Fila con ícono de color, nombre, descripción y chevron.
// TODO: TarjetaMedico(medico, onClick)
//       Avatar, nombre, profesión, calificación con estrella y chip de disponibilidad.
// TODO: ChipHorario(hora, seleccionado, onClick)
//       Celda del LazyVerticalGrid de horarios.
// TODO: TarjetaCita(cita, onClick)
//       Resumen de una cita para Mis citas.
// TODO: FilaDato(icono, etiqueta, valor)
//       Fila con ícono de Confirmar cita (Fecha, Hora, Tipo de atención, Dirección).
// TODO: EstadoVacio(mensaje)
//       Mensaje cuando una lista no tiene elementos.

// Botón azul redondeado de ancho completo ("Comenzar", "Registrarme", "Continuar"...).
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
        colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario),
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
                .background(AzulClaro),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = AzulPrimario
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
                focusedBorderColor = AzulPrimario,
                unfocusedBorderColor = BordeSuave,
                focusedLabelColor = AzulPrimario
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

// Texto normal seguido de un enlace azul ("¿Ya tienes cuenta? Iniciar sesión").
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
            color = AzulPrimario,
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
        1 -> EstiloEspecialidad(Icons.Default.Person, AzulPastel, AzulPrimario)
        2 -> EstiloEspecialidad(Icons.Default.ChildCare, NaranjaPastel, NaranjaTexto)
        3 -> EstiloEspecialidad(Icons.Default.Female, RojoPastel, RojoTexto)
        4 -> EstiloEspecialidad(Icons.Default.Favorite, RojoPastel, RojoTexto)
        5 -> EstiloEspecialidad(Icons.Default.Spa, NaranjaPastel, NaranjaTexto)
        6 -> EstiloEspecialidad(Icons.Default.Healing, AzulPastel, AzulPrimario)
        7 -> EstiloEspecialidad(Icons.Default.Visibility, AzulPastel, AzulPrimario)
        else -> EstiloEspecialidad(Icons.Default.Person, AzulPastel, AzulPrimario)
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

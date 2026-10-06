package com.santamaria.saludpluscitas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santamaria.saludpluscitas.ui.theme.AzulClaro
import com.santamaria.saludpluscitas.ui.theme.AzulPrimario
import com.santamaria.saludpluscitas.ui.theme.BordeSuave

// Componentes reutilizables de la app.
// Pendientes (se crean junto con la pantalla que los usa):
//
// TODO: BarraSuperior(titulo, onAtras, acciones)
//       TopAppBar con flecha atrás; la usan todas las vistas internas.
// TODO: TarjetaAccion(titulo, icono, colorFondo, colorContenido, onClick)
//       Tarjetas pastel del Inicio (Agendar cita, Mis citas, Mis datos, Resultados).
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

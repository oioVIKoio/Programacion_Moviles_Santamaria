package com.santamaria.avisoscampus.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santamaria.avisoscampus.ui.theme.AnilloPista
import com.santamaria.avisoscampus.ui.theme.AnilloProgreso
import com.santamaria.avisoscampus.ui.theme.Borde
import com.santamaria.avisoscampus.ui.theme.FondoIcono
import com.santamaria.avisoscampus.ui.theme.IconoOscuro
import com.santamaria.avisoscampus.ui.theme.JetBrainsMono
import com.santamaria.avisoscampus.ui.theme.Manrope
import com.santamaria.avisoscampus.ui.theme.Pizarra500
import com.santamaria.avisoscampus.ui.theme.Pizarra600
import com.santamaria.avisoscampus.ui.theme.Tinta

/** Texto con la familia del diseño y los parámetros que más se repiten. */
@Composable
fun Txt(
    texto: String,
    modifier: Modifier = Modifier,
    tam: TextUnit = 14.sp,
    peso: FontWeight = FontWeight.Normal,
    color: Color = Tinta,
    alto: TextUnit = TextUnit.Unspecified,
    espaciado: TextUnit = TextUnit.Unspecified,
    familia: FontFamily = Manrope,
    alinear: TextAlign? = null
) = Text(
    text = texto,
    modifier = modifier,
    color = color,
    textAlign = alinear,
    style = TextStyle(
        fontFamily = familia, fontSize = tam, fontWeight = peso,
        lineHeight = alto, letterSpacing = espaciado
    )
)

@Composable
fun Ico(id: Int, tam: Dp = 20.dp, color: Color = Tinta, modifier: Modifier = Modifier) =
    Icon(painterResource(id), contentDescription = null, tint = color, modifier = modifier.size(tam))

/** Tarjeta blanca de 24 dp con borde fino, como en el diseño. */
@Composable
fun Tarjeta(modifier: Modifier = Modifier, contenido: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(1.dp, Borde, RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) { contenido() }
}

/** Encabezado de sección: ícono en cuadro suave, título y número de paso. */
@Composable
fun EncabezadoSeccion(paso: String, titulo: String, icono: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(FondoIcono),
            contentAlignment = Alignment.Center
        ) { Ico(icono, 20.dp, IconoOscuro) }
        Spacer(Modifier.width(12.dp))
        Txt(titulo, Modifier.weight(1f), 16.sp, FontWeight.Bold, espaciado = (-0.2).sp)
        Txt(paso, tam = 12.sp, peso = FontWeight.SemiBold, color = Pizarra500, espaciado = 1.2.sp)
    }
}

/** Etiqueta redonda (estado del permiso, contador). */
@Composable
fun Pastilla(texto: String, fondo: Color, color: Color) {
    Txt(
        texto, tam = 12.sp, peso = FontWeight.SemiBold, color = color,
        modifier = Modifier.clip(CircleShape).background(fondo).padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Composable
fun BotonRelleno(
    texto: String, onClick: () -> Unit, fondo: Color, color: Color,
    modifier: Modifier = Modifier, icono: Int? = null, iconoAlFinal: Boolean = false, habilitado: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        modifier = modifier.heightIn(min = 48.dp),
        shape = RoundedCornerShape(16.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = fondo, contentColor = color,
            disabledContainerColor = fondo.copy(alpha = 0.5f), disabledContentColor = color.copy(alpha = 0.5f)
        )
    ) {
        if (iconoAlFinal) {
            Txt(texto, Modifier.weight(1f), 14.sp, FontWeight.SemiBold, color = if (habilitado) color else color.copy(alpha = 0.5f))
            if (icono != null) Ico(icono, 20.dp, if (habilitado) color else color.copy(alpha = 0.5f))
        } else {
            if (icono != null) { Ico(icono, 16.dp, if (habilitado) color else color.copy(alpha = 0.5f)); Spacer(Modifier.width(8.dp)) }
            Txt(texto, tam = 14.sp, peso = FontWeight.SemiBold, color = if (habilitado) color else color.copy(alpha = 0.5f))
        }
    }
}

@Composable
fun BotonBorde(texto: String, onClick: () -> Unit, modifier: Modifier = Modifier, habilitado: Boolean = true) {
    val c = if (habilitado) Color(0xFF334155) else Color(0xFF334155).copy(alpha = 0.5f)
    OutlinedButton(
        onClick = onClick,
        enabled = habilitado,
        modifier = modifier.heightIn(min = 48.dp),
        shape = RoundedCornerShape(16.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp),
        border = BorderStroke(1.dp, if (habilitado) Color(0xFFCBD5E1) else Color(0xFFCBD5E1).copy(alpha = 0.5f))
    ) { Txt(texto, tam = 14.sp, peso = FontWeight.SemiBold, color = c) }
}

/** Anillo de la cuenta regresiva: pista fija y arco que se acorta con los segundos que faltan. */
@Composable
fun AnilloCuenta(faltan: Int?, total: Int = 10) {
    Box(modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center) {
        Canvas(Modifier.size(56.dp)) {
            val grosor = 3.dp.toPx()
            val lado = size.minDimension - grosor
            val esquina = Offset(grosor / 2, grosor / 2)
            drawArc(AnilloPista, 0f, 360f, false, esquina, Size(lado, lado), style = Stroke(grosor))
            if (faltan != null) {
                drawArc(
                    AnilloProgreso, -90f, 360f * faltan / total, false, esquina, Size(lado, lado),
                    style = Stroke(grosor, cap = StrokeCap.Butt)
                )
            }
        }
        Row(verticalAlignment = Alignment.Bottom) {
            Txt(
                if (faltan == null) "10" else faltan.toString().padStart(2, '0'),
                tam = 18.sp, peso = FontWeight.SemiBold, familia = JetBrainsMono
            )
            Txt("s", Modifier.padding(start = 1.dp, bottom = 2.dp), 12.sp, color = Pizarra600)
        }
    }
}

package com.santamaria.avisoscampus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santamaria.avisoscampus.R
import com.santamaria.avisoscampus.model.Aviso
import com.santamaria.avisoscampus.model.Origen
import com.santamaria.avisoscampus.ui.componentes.Ico
import com.santamaria.avisoscampus.ui.componentes.Pastilla
import com.santamaria.avisoscampus.ui.componentes.Tarjeta
import com.santamaria.avisoscampus.ui.componentes.Txt
import com.santamaria.avisoscampus.ui.theme.AvionFondo
import com.santamaria.avisoscampus.ui.theme.AvionTexto
import com.santamaria.avisoscampus.ui.theme.BotonFondo
import com.santamaria.avisoscampus.ui.theme.BotonTexto
import com.santamaria.avisoscampus.ui.theme.IconoSuave
import com.santamaria.avisoscampus.ui.theme.MarinoBoton
import com.santamaria.avisoscampus.ui.theme.Pizarra600
import com.santamaria.avisoscampus.ui.theme.RecordatorioFondo
import com.santamaria.avisoscampus.ui.theme.RecordatorioTexto
import com.santamaria.avisoscampus.ui.theme.Separador
import com.santamaria.avisoscampus.ui.theme.SinPedirFondo

private val Filtros = listOf<Pair<String, Origen?>>(
    "Todos" to null, "Botón" to Origen.BOTON, "Recordatorio" to Origen.RECORDATORIO, "Modo avión" to Origen.MODO_AVION
)

/** Tarjeta Historial: no conoce el ViewModel, solo recibe la lista. El filtro es estado de pantalla. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    var filtro by rememberSaveable { mutableStateOf("Todos") }
    val origenFiltro = Filtros.first { it.first == filtro }.second
    val visibles = avisos.filter { origenFiltro == null || it.origen == origenFiltro }

    Tarjeta {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Ico(R.drawable.ic_historial, 20.dp, IconoSuave)
            Txt("Historial", Modifier.weight(1f).padding(start = 8.dp), 16.sp, FontWeight.Bold)
            Pastilla(if (avisos.size == 1) "1 aviso" else "${avisos.size} avisos", SinPedirFondo, Pizarra600)
        }
        if (avisos.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Filtros.forEach { (nombre, _) ->
                    val activo = nombre == filtro
                    Box(
                        modifier = Modifier
                            .heightIn(min = 44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (activo) MarinoBoton else Color(0xFFF8FAFC))
                            .clickable { filtro = nombre }
                            .padding(horizontal = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Txt(nombre, tam = 12.sp, peso = FontWeight.Medium, color = if (activo) Color.White else Pizarra600)
                    }
                }
            }
        }
        if (visibles.isEmpty()) {
            Column(Modifier.fillMaxWidth().padding(vertical = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier.size(56.dp).clip(RoundedCornerShape(16.dp)).background(Color(0xFFF0F4F8)),
                    contentAlignment = Alignment.Center
                ) { Ico(R.drawable.ic_campana, 24.dp, Color(0xFF58738A)) }
                Txt(
                    if (avisos.isEmpty()) "Sin avisos todavía" else "Sin avisos de este origen",
                    Modifier.padding(top = 12.dp), 14.sp, FontWeight.SemiBold, alinear = TextAlign.Center
                )
                Txt("Los próximos aparecerán aquí.", Modifier.padding(top = 6.dp), 14.sp, color = Pizarra600, alinear = TextAlign.Center)
            }
        } else {
            // Column + forEach (pocas filas): la pantalla ya se desplaza, no se usa LazyColumn
            Column(Modifier.padding(top = 8.dp)) {
                visibles.take(20).forEachIndexed { i, aviso ->
                    if (i > 0) Box(Modifier.fillMaxWidth().size(width = 0.dp, height = 1.dp).background(Separador))
                    FilaAviso(aviso)
                }
            }
        }
        Box(Modifier.fillMaxWidth().padding(top = 12.dp).size(width = 0.dp, height = 1.dp).background(Separador))
        Txt(
            "Datos ficticios · Historial guardado en este dispositivo",
            Modifier.fillMaxWidth().padding(top = 12.dp), 12.sp, color = Pizarra600, alto = 20.sp, alinear = TextAlign.Center
        )
    }
}

@Composable
private fun FilaAviso(aviso: Aviso) {
    val (fondo, texto) = when (aviso.origen) {
        Origen.BOTON -> BotonFondo to BotonTexto
        Origen.RECORDATORIO -> RecordatorioFondo to RecordatorioTexto
        Origen.MODO_AVION -> AvionFondo to AvionTexto
    }
    Column(Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
        Row(Modifier.fillMaxWidth().padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Txt(
                aviso.origen.texto, tam = 12.sp, peso = FontWeight.SemiBold, color = texto,
                modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(fondo).padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Box(Modifier.weight(1f))
            Txt(aviso.hora, tam = 12.sp, color = Pizarra600)
        }
        Txt(aviso.titulo, tam = 14.sp, peso = FontWeight.SemiBold)
        Txt(aviso.texto, Modifier.padding(top = 4.dp), 14.sp, color = Pizarra600)
    }
}

package com.santamaria.avisoscampus.ui

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.santamaria.avisoscampus.R
import com.santamaria.avisoscampus.model.PermisoUi
import com.santamaria.avisoscampus.notificacion.Notificador
import com.santamaria.avisoscampus.receiver.ModoAvionReceiver
import com.santamaria.avisoscampus.ui.componentes.AnilloCuenta
import com.santamaria.avisoscampus.ui.componentes.BotonBorde
import com.santamaria.avisoscampus.ui.componentes.BotonRelleno
import com.santamaria.avisoscampus.ui.componentes.EncabezadoSeccion
import com.santamaria.avisoscampus.ui.componentes.Ico
import com.santamaria.avisoscampus.ui.componentes.Pastilla
import com.santamaria.avisoscampus.ui.componentes.Tarjeta
import com.santamaria.avisoscampus.ui.componentes.Txt
import com.santamaria.avisoscampus.ui.theme.DenegadoFondo
import com.santamaria.avisoscampus.ui.theme.DenegadoTexto
import com.santamaria.avisoscampus.ui.theme.ErrorFondo
import com.santamaria.avisoscampus.ui.theme.ErrorTexto
import com.santamaria.avisoscampus.ui.theme.Fondo
import com.santamaria.avisoscampus.ui.theme.IconoSuave
import com.santamaria.avisoscampus.ui.theme.Lima
import com.santamaria.avisoscampus.ui.theme.LimaTexto
import com.santamaria.avisoscampus.ui.theme.Manrope
import com.santamaria.avisoscampus.ui.theme.Marino
import com.santamaria.avisoscampus.ui.theme.MarinoBoton
import com.santamaria.avisoscampus.ui.theme.PermitidoFondo
import com.santamaria.avisoscampus.ui.theme.CajaRecordatorio
import com.santamaria.avisoscampus.ui.theme.PermitidoTexto
import com.santamaria.avisoscampus.ui.theme.Pizarra300
import com.santamaria.avisoscampus.ui.theme.Pizarra500
import com.santamaria.avisoscampus.ui.theme.Pizarra600
import com.santamaria.avisoscampus.ui.theme.SinPedirFondo
import com.santamaria.avisoscampus.ui.theme.SwitchApagado
import com.santamaria.avisoscampus.viewmodel.AvisosViewModel
import kotlinx.coroutines.launch

@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current
    val permiso by vm.permiso.collectAsState()
    val faltan by vm.faltan.collectAsState()
    val escuchando by vm.escuchandoAvion.collectAsState()
    val avisos by vm.avisos.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    var recordatorioCancelado by rememberSaveable { mutableStateOf(false) }
    val scroll = rememberScrollState()
    val scope = rememberCoroutineScope()

    // El receptor vive solo mientras el switch está activado y se quita al salir (onDispose)
    DisposableEffect(escuchando) {
        if (escuchando) {
            val receptor = ModoAvionReceiver { activo -> vm.alCambiarModoAvion(activo) }
            ContextCompat.registerReceiver(
                contexto, receptor, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            onDispose { contexto.unregisterReceiver(receptor) }
        } else {
            onDispose { }
        }
    }

    val lanzador = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
        vm.permisoRespondido(concedido)
    }

    // El permiso se vuelve a revisar cada vez que la app regresa al frente (por ejemplo, al volver de Ajustes),
    // así la etiqueta y el mensaje de error nunca contradicen lo que el sistema ya permite.
    val actividad = contexto as ComponentActivity
    DisposableEffect(actividad) {
        val observador = LifecycleEventObserver { _, evento ->
            if (evento == Lifecycle.Event.ON_RESUME) {
                if (Notificador.tienePermiso(contexto)) vm.permisoRespondido(true)
                else if (vm.permiso.value == PermisoUi.Concedido) vm.permisoRespondido(false)
            }
        }
        actividad.lifecycle.addObserver(observador)
        onDispose { actividad.lifecycle.removeObserver(observador) }
    }

    LaunchedEffect(Unit) {
        vm.eventos.collect { aviso ->
            val mostrado = Notificador.mostrar(contexto, aviso)
            if (!mostrado) vm.noSePudoMostrar()
        }
    }

    Box(Modifier.fillMaxSize().background(Fondo)) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Encabezado(hayAvisos = avisos.isNotEmpty(), alCampana = { scope.launch { scroll.animateScrollTo(scroll.maxValue) } })

        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 28.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaPermiso(permiso) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    vm.permisoRespondido(true)
                }
            }

            TarjetaAvisos(
                mensaje = mensaje,
                alMostrar = { vm.avisarAhora() },
                alAbrirAjustes = {
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, contexto.packageName)
                    contexto.startActivity(intent)
                }
            )

            TarjetaRecordatorio(
                faltan = faltan,
                cancelado = recordatorioCancelado && faltan == null,
                alRecordar = { recordatorioCancelado = false; vm.recordarEn() },
                alCancelar = { recordatorioCancelado = true; vm.cancelarRecordatorio() }
            )

            TarjetaReceptor(escuchando) { vm.cambiarEscucha(it) }

            HistorialAvisos(avisos)

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF688B44)))
                Spacer(Modifier.size(8.dp))
                Txt("Hecho para aprender. Diseñado para tu campus.", tam = 12.sp, color = Pizarra600)
            }
        }
    }
    // Franjas fijas: lo que se desplaza nunca pasa por debajo de los íconos de la barra de estado,
    // la cámara ni la barra de navegación
    Box(Modifier.align(Alignment.TopCenter).fillMaxWidth().windowInsetsTopHeight(WindowInsets.safeDrawing).background(Marino))
    Box(Modifier.align(Alignment.BottomCenter).fillMaxWidth().windowInsetsBottomHeight(WindowInsets.navigationBars).background(Fondo))
    }
}

/** Cabecera marino: marca, campana, mensaje y a qué curso pertenece. */
@Composable
private fun Encabezado(hayAvisos: Boolean, alCampana: () -> Unit) {
    val lineaCirculo = Color(0xFF36516A)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(Marino)
            .drawBehind {
                // Dos círculos concéntricos de adorno, pegados al borde derecho del contenido (máx. 480 dp)
                val ancho = minOf(size.width, 480.dp.toPx())
                val derecha = (size.width + ancho) / 2
                val centro = Offset(derecha - 56.dp.toPx(), 192.dp.toPx())
                drawCircle(lineaCirculo, 112.dp.toPx(), centro, style = Stroke(1.dp.toPx()))
                drawCircle(lineaCirculo, 76.dp.toPx(), centro, style = Stroke(1.dp.toPx()))
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal))
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 36.dp)
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(36.dp).clip(RoundedCornerShape(12.dp)).background(Lima),
                        contentAlignment = Alignment.Center
                    ) { Ico(R.drawable.ic_campana, 20.dp, Marino) }
                    Spacer(Modifier.size(10.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Avisos") }
                            withStyle(SpanStyle(fontWeight = FontWeight.Normal, color = Lima)) { append("Campus") }
                        },
                        color = Color.White,
                        style = TextStyle(fontFamily = Manrope, fontSize = 18.sp, letterSpacing = (-0.3).sp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color(0xFF4A5E73), CircleShape)
                        .clickable(onClickLabel = "Ir al historial", onClick = alCampana),
                    contentAlignment = Alignment.Center
                ) {
                    Ico(R.drawable.ic_campana, 20.dp, Color.White)
                    if (hayAvisos) {
                        Box(Modifier.align(Alignment.TopEnd).padding(top = 10.dp, end = 10.dp).size(8.dp).clip(CircleShape).background(Lima))
                    }
                }
            }
            Row(Modifier.padding(top = 32.dp, bottom = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(6.dp).clip(CircleShape).background(Lima))
                Spacer(Modifier.size(8.dp))
                Txt("TU CAMPUS, AL DÍA", tam = 12.sp, peso = FontWeight.Medium, color = Lima, espaciado = 1.9.sp)
            }
            Txt("Lo importante,\nsiempre contigo.", tam = 32.sp, peso = FontWeight.SemiBold, color = Color.White, alto = 37.sp, espaciado = (-1).sp)
            Txt(
                "Tus avisos y recordatorios del campus,\nen un solo lugar.",
                Modifier.padding(top = 12.dp), 14.sp, color = Pizarra300, alto = 24.sp
            )
            Row(Modifier.fillMaxWidth().padding(top = 24.dp), verticalAlignment = Alignment.CenterVertically) {
                Txt(
                    "TECSUP", tam = 12.sp, peso = FontWeight.Bold, color = Color.White, espaciado = 0.6.sp,
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(Color(0xFF2B425B)).padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Spacer(Modifier.size(8.dp))
                Txt("Programación en Móviles", Modifier.weight(1f), 12.sp, color = Pizarra300)
                Txt(
                    "Laboratorio", tam = 12.sp, color = Pizarra300,
                    modifier = Modifier.border(1.dp, Color(0xFF52657A), CircleShape).padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun TarjetaPermiso(permiso: PermisoUi, alPedir: () -> Unit) = Tarjeta {
    val permitido = permiso == PermisoUi.Concedido
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Ico(R.drawable.ic_escudo, 20.dp, IconoSuave)
            Spacer(Modifier.size(8.dp))
            Txt("Permiso de notificaciones", tam = 14.sp, peso = FontWeight.Bold)
        }
        when (permiso) {
            PermisoUi.SinPedir -> Pastilla("Sin pedir", SinPedirFondo, Pizarra600)
            PermisoUi.Concedido -> Pastilla("Permitido", PermitidoFondo, PermitidoTexto)
            PermisoUi.Denegado -> Pastilla("Denegado", DenegadoFondo, DenegadoTexto)
        }
    }
    Txt(
        "Activa las notificaciones para no perderte ningún aviso importante.",
        Modifier.padding(bottom = 16.dp), color = Pizarra600, alto = 24.sp
    )
    BotonRelleno(
        texto = if (permitido) "Notificaciones permitidas" else "Permitir notificaciones",
        onClick = alPedir, fondo = Lima, color = LimaTexto, modifier = Modifier.fillMaxWidth(),
        icono = if (permitido) R.drawable.ic_check else R.drawable.ic_campana
    )
}

@Composable
private fun TarjetaAvisos(mensaje: String?, alMostrar: () -> Unit, alAbrirAjustes: () -> Unit) = Tarjeta {
    EncabezadoSeccion("01", "Avisos", R.drawable.ic_campana)
    Txt("Una entrega menos. Prueba tu primer aviso.", Modifier.padding(bottom = 16.dp), color = Pizarra600, alto = 24.sp)
    BotonRelleno(
        "Mostrar aviso", alMostrar, MarinoBoton, Color.White, Modifier.fillMaxWidth(),
        icono = R.drawable.ic_flecha, iconoAlFinal = true
    )
    if (mensaje != null) {
        Column(
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)).background(ErrorFondo).padding(16.dp)
        ) {
            Txt(mensaje, color = ErrorTexto, alto = 24.sp)
            Box(Modifier.heightIn(min = 44.dp).clickable(onClick = alAbrirAjustes), contentAlignment = Alignment.CenterStart) {
                Text(
                    "Abrir ajustes", color = ErrorTexto, textDecoration = TextDecoration.Underline,
                    style = TextStyle(fontFamily = Manrope, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
private fun TarjetaRecordatorio(faltan: Int?, cancelado: Boolean, alRecordar: () -> Unit, alCancelar: () -> Unit) = Tarjeta {
    EncabezadoSeccion("02", "Recordatorio", R.drawable.ic_reloj)
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            .clip(RoundedCornerShape(16.dp)).background(CajaRecordatorio).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnilloCuenta(faltan)
        Spacer(Modifier.size(16.dp))
        Column {
            Txt(if (faltan == null) "Un respiro y te avisamos" else "Faltan $faltan s", tam = 14.sp, peso = FontWeight.SemiBold)
            Txt(
                if (faltan == null) "Para ese laboratorio pendiente." else "Tu recordatorio está en camino.",
                Modifier.padding(top = 4.dp), 12.sp, color = Pizarra600
            )
        }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        BotonRelleno("Recordar en 10 s", alRecordar, MarinoBoton, Color.White, Modifier.weight(1f), habilitado = faltan == null)
        BotonBorde("Cancelar", alCancelar, habilitado = faltan != null)
    }
    if (cancelado) Txt("Recordatorio cancelado", Modifier.padding(top = 12.dp), color = Pizarra600)
}

@Composable
private fun TarjetaReceptor(escuchando: Boolean, alCambiar: (Boolean) -> Unit) = Tarjeta {
    EncabezadoSeccion("03", "Receptor de anuncios", R.drawable.ic_avion)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Txt("Escuchar el modo avión", tam = 14.sp, peso = FontWeight.SemiBold)
            Txt("Te avisamos cuando cambie.", Modifier.padding(top = 6.dp), 14.sp, color = Pizarra600, alto = 20.sp)
        }
        Switch(
            checked = escuchando,
            onCheckedChange = alCambiar,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White, checkedTrackColor = MarinoBoton, checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = Color.White, uncheckedTrackColor = SwitchApagado, uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

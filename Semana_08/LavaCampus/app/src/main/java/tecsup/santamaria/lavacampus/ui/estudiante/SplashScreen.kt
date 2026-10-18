package tecsup.santamaria.lavacampus.ui.estudiante

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import tecsup.santamaria.lavacampus.model.Rol
import tecsup.santamaria.lavacampus.ui.components.IconoServicio
import tecsup.santamaria.lavacampus.ui.components.LavaButton

// perfilGuardado: null mientras carga DataStore, "" sin sesión, o el rol guardado
@Composable
fun SplashScreen(perfilGuardado: String?, onComenzar: () -> Unit, onSesionGuardada: (Rol) -> Unit) {
    // Si ya hay perfil guardado, salta el login
    LaunchedEffect(perfilGuardado) {
        if (!perfilGuardado.isNullOrEmpty()) {
            delay(800)
            onSesionGuardada(Rol.valueOf(perfilGuardado))
        }
    }

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Burbuja(80.dp, Modifier.offset(40.dp, 80.dp))
        Burbuja(48.dp, Modifier.align(Alignment.TopEnd).offset((-48).dp, 140.dp))
        Burbuja(28.dp, Modifier.align(Alignment.CenterStart).offset(36.dp, 70.dp))
        Burbuja(20.dp, Modifier.align(Alignment.Center).offset(10.dp, 150.dp))

        Column(
            modifier = Modifier.fillMaxSize().navigationBarsPadding().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))
            IconoServicio(
                "wash",
                tamano = 96.dp,
                color = MaterialTheme.colorScheme.onPrimary,
                fondo = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(28.dp))
            Text("LavaCampus", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Tu ropa limpia, sin salir del campus",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(48.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ServicioMini("wash", "Lavado")
                ServicioMini("iron", "Planchado")
                ServicioMini("shirt", "En seco")
            }
            Spacer(Modifier.weight(1.2f))

            // Con sesión guardada no hace falta tocar nada
            if (perfilGuardado == "") {
                LavaButton(texto = "Comenzar", onClick = onComenzar)
                TextButton(onClick = onComenzar, modifier = Modifier.fillMaxWidth()) {
                    Text("Ya tengo una cuenta", style = MaterialTheme.typography.labelLarge)
                }
            } else {
                Spacer(Modifier.height(98.dp))
            }
        }
    }
}

@Composable
private fun ServicioMini(icono: String, texto: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(84.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f))
            .padding(vertical = 14.dp)
    ) {
        IconoServicio(icono, tamano = 36.dp, fondo = Color.Transparent)
        Spacer(Modifier.height(6.dp))
        Text(texto, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun Burbuja(tamano: Dp, modifier: Modifier) {
    Box(
        modifier
            .size(tamano)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f))
    )
}

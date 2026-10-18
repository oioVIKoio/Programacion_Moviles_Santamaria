package tecsup.santamaria.lavacampus.ui.estudiante

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.model.Rol
import tecsup.santamaria.lavacampus.ui.components.LavaButton
import tecsup.santamaria.lavacampus.ui.components.LavaTextField
import tecsup.santamaria.lavacampus.ui.theme.LavaTheme
import tecsup.santamaria.lavacampus.viewmodel.SesionViewModel

// Un solo login para los dos roles: el color cambia con la pestaña elegida
@Composable
fun LoginScreen(vm: SesionViewModel, onIngresar: (Rol) -> Unit) {
    val usuario by vm.usuarioActual.collectAsState()
    val error by vm.error.collectAsState()
    val oscuro by vm.modoOscuro.collectAsState()

    var rol by rememberSaveable { mutableStateOf(Rol.CLIENTE) }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(usuario) {
        usuario?.let { onIngresar(Rol.valueOf(it.rol)) }
    }

    LavaTheme(rol = rol, oscuro = oscuro) {
        Surface(color = MaterialTheme.colorScheme.surface, modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Text("Bienvenido", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "Ingresa para continuar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(24.dp))

                SelectorRol(seleccionado = rol, onCambio = {
                    rol = it
                    vm.limpiarError()
                })
                Spacer(Modifier.height(24.dp))

                LavaTextField(
                    valor = correo,
                    onCambio = { correo = it; vm.limpiarError() },
                    etiqueta = "Correo",
                    icono = Icons.Outlined.MailOutline,
                    teclado = KeyboardType.Email
                )
                Spacer(Modifier.height(12.dp))
                LavaTextField(
                    valor = clave,
                    onCambio = { clave = it; vm.limpiarError() },
                    etiqueta = "Contraseña",
                    icono = Icons.Outlined.Lock,
                    esClave = true
                )
                Text(
                    "¿Olvidaste tu contraseña?",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )

                error?.let {
                    Text(
                        it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                Spacer(Modifier.height(24.dp))
                LavaButton(texto = "Ingresar", onClick = { vm.login(correo, clave, rol) })
                Spacer(Modifier.height(20.dp))

                if (rol == Rol.CLIENTE) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(
                            "¿No tienes cuenta? ",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Regístrate",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    // En el mockup del administrador va este aviso en vez del registro
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                            .padding(16.dp)
                    ) {
                        Text(
                            "Modo práctica",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Puedes cambiar de perfil desde Ajustes para probar ambos roles.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectorRol(seleccionado: Rol, onCambio: (Rol) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp)
    ) {
        Rol.entries.forEach { rol ->
            val activo = rol == seleccionado
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onCambio(rol) }
            ) {
                Text(
                    rol.etiqueta,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (activo) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

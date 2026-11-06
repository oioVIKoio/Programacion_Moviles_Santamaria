package tecsup.santamaria.lavacampus.ui.soporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.model.Rol
import tecsup.santamaria.lavacampus.ui.components.AppTopBar
import tecsup.santamaria.lavacampus.ui.components.LavaButton
import tecsup.santamaria.lavacampus.ui.components.LavaTextField
import tecsup.santamaria.lavacampus.ui.theme.LavaTheme
import tecsup.santamaria.lavacampus.viewmodel.SesionViewModel


// Registro del rol soporte: se abre desde "Regístrate" en la pestaña Soporte del login
@Composable
fun RegistroSoporteScreen(vm: SesionViewModel, onAtras: () -> Unit, onRegistrado: () -> Unit) {
    val error by vm.error.collectAsState()
    val oscuro by vm.modoOscuro.collectAsState()

    var codigo by rememberSaveable { mutableStateOf("") }
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellido by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var confirmacion by rememberSaveable { mutableStateOf("") }

    LavaTheme(rol = Rol.SOPORTE, oscuro = oscuro) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = { AppTopBar("Registro de soporte", onAtras = onAtras) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LavaTextField(codigo, { codigo = it; vm.limpiarError() }, "Código", icono = Icons.Outlined.Badge)
                LavaTextField(nombre, { nombre = it; vm.limpiarError() }, "Nombre", icono = Icons.Outlined.Person)
                LavaTextField(apellido, { apellido = it; vm.limpiarError() }, "Apellido", icono = Icons.Outlined.Person)
                LavaTextField(correo, { correo = it; vm.limpiarError() }, "Correo", icono = Icons.Outlined.MailOutline, teclado = KeyboardType.Email)
                LavaTextField(clave, { clave = it; vm.limpiarError() }, "Contraseña", icono = Icons.Outlined.Lock, esClave = true)
                LavaTextField(confirmacion, { confirmacion = it; vm.limpiarError() }, "Confirmar contraseña", icono = Icons.Outlined.Lock, esClave = true)

                error?.let {
                    Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
                }

                LavaButton(
                    texto = "Registrarme",
                    onClick = { vm.registrarSoporte(codigo, nombre, apellido, correo, clave, confirmacion, onRegistrado) }
                )
            }
        }
    }
}

package tecsup.santamaria.lavacampus.ui.administrador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.model.Rol
import tecsup.santamaria.lavacampus.ui.components.AppTopBar
import tecsup.santamaria.lavacampus.ui.components.BotonCambiarPerfil
import tecsup.santamaria.lavacampus.ui.components.LavaButton
import tecsup.santamaria.lavacampus.ui.components.ListaPreferencias
import tecsup.santamaria.lavacampus.ui.components.NotaDataStore
import tecsup.santamaria.lavacampus.ui.components.Preferencia
import tecsup.santamaria.lavacampus.ui.components.TarjetaUsuario
import tecsup.santamaria.lavacampus.viewmodel.SesionViewModel

@Composable
fun AjustesScreen(
    sesion: SesionViewModel,
    barraInferior: @Composable () -> Unit,
    onAtras: () -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val usuario by sesion.usuarioActual.collectAsState()
    val oscuro by sesion.modoOscuro.collectAsState()
    val notificaciones by sesion.notificaciones.collectAsState()
    val recibir by sesion.recibirPedidos.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar("Ajustes", onAtras = onAtras) },
        bottomBar = barraInferior
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            usuario?.let { TarjetaUsuario(it.nombre, it.correo, "Perfil activo") }
            BotonCambiarPerfil(destino = Rol.CLIENTE.etiqueta, onClick = onCambiarPerfil)
            Text("Preferencias", style = MaterialTheme.typography.titleMedium)
            ListaPreferencias(
                listOf(
                    Preferencia("Modo oscuro", "Cambia el tema de la app", oscuro, sesion::cambiarModoOscuro),
                    Preferencia("Notificaciones", "Avisar pedidos nuevos", notificaciones, sesion::cambiarNotificaciones),
                    Preferencia("Recibir pedidos", "Abrir o cerrar la recepción", recibir, sesion::cambiarRecibirPedidos)
                )
            )
            NotaDataStore()
            LavaButton(
                texto = "Cerrar sesión",
                onClick = onCerrarSesion,
                contorno = true,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

package tecsup.santamaria.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tecsup.santamaria.lavacampus.model.Rol
import tecsup.santamaria.lavacampus.model.Unidad
import tecsup.santamaria.lavacampus.ui.components.AppTopBar
import tecsup.santamaria.lavacampus.ui.components.BotonCambiarPerfil
import tecsup.santamaria.lavacampus.ui.components.LavaButton
import tecsup.santamaria.lavacampus.ui.components.ListaPreferencias
import tecsup.santamaria.lavacampus.ui.components.NotaDataStore
import tecsup.santamaria.lavacampus.ui.components.Preferencia
import tecsup.santamaria.lavacampus.ui.components.TarjetaUsuario
import tecsup.santamaria.lavacampus.ui.components.cantidadTexto
import tecsup.santamaria.lavacampus.viewmodel.MisPedidosViewModel
import tecsup.santamaria.lavacampus.viewmodel.SesionViewModel

@Composable
fun PerfilScreen(
    sesion: SesionViewModel,
    pedidos: MisPedidosViewModel,
    barraInferior: @Composable () -> Unit,
    onAtras: () -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val usuario by sesion.usuarioActual.collectAsState()
    val oscuro by sesion.modoOscuro.collectAsState()
    val avisos by sesion.notificaciones.collectAsState()
    val activos by pedidos.activos.collectAsState()
    val totales by pedidos.totales.collectAsState()
    val kilos by pedidos.kilosLavados.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar("Perfil", onAtras = onAtras) },
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
            usuario?.let { TarjetaUsuario(it.nombre, it.correo, Rol.CLIENTE.etiqueta) }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Contador("${activos.size}", "Pedidos activos", Modifier.weight(1f))
                Contador("$totales", "Pedidos totales", Modifier.weight(1f))
                Contador("${cantidadTexto(kilos, Unidad.KG)} kg", "Lavados", Modifier.weight(1f))
            }
            BotonCambiarPerfil(destino = Rol.ADMIN.etiqueta, onClick = onCambiarPerfil)
            Text("Preferencias", style = MaterialTheme.typography.titleMedium)
            ListaPreferencias(
                listOf(
                    Preferencia("Modo oscuro", "Cambia el tema de la app", oscuro, sesion::cambiarModoOscuro),
                    Preferencia("Avisos de pedido listo", "Notificar cuando puedas recoger", avisos, sesion::cambiarNotificaciones)
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

@Composable
private fun Contador(valor: String, texto: String, modifier: Modifier) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(14.dp), modifier = modifier) {
        Column(Modifier.padding(vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(valor, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(texto, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        }
    }
}

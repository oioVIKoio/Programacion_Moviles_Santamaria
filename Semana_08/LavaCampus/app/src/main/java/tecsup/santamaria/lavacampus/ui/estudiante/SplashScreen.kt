package tecsup.santamaria.lavacampus.ui.estudiante

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import tecsup.santamaria.lavacampus.ui.components.IconoServicio

@Composable
fun SplashScreen(onTerminar: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500)
        onTerminar()
    }
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconoServicio("wash", tamano = 96.dp, color = MaterialTheme.colorScheme.primary, fondo = Color.White)
        Spacer(Modifier.height(20.dp))
        Text("LavaCampus", style = MaterialTheme.typography.headlineSmall, color = Color.White)
        Text("Lavandería universitaria", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.85f))
        Spacer(Modifier.height(32.dp))
        CircularProgressIndicator(color = Color.White)
    }
}

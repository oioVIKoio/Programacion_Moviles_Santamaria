package tecsup.santamaria.lavacampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import tecsup.santamaria.lavacampus.model.Rol

private val EstudianteClaro = lightColorScheme(
    primary = Turquesa,
    onPrimary = Color.White,
    primaryContainer = TurquesaContenedor,
    onPrimaryContainer = Color(0xFF155E75),
    secondary = Turquesa,
    background = TurquesaFondo,
    surface = Color.White,
    surfaceVariant = Color(0xFFF1F5F9),
    onBackground = TextoPrincipal,
    onSurface = TextoPrincipal,
    onSurfaceVariant = TextoSecundario,
    outline = Borde,
    error = Error
)

private val EstudianteOscuro = darkColorScheme(
    primary = TurquesaOscuro,
    onPrimary = Color(0xFF083344),
    primaryContainer = Color(0xFF155E75),
    onPrimaryContainer = TurquesaContenedor,
    secondary = TurquesaOscuro,
    background = FondoOscuro,
    surface = SuperficieOscura,
    surfaceVariant = Color(0xFF1E293B),
    onBackground = Color(0xFFE2E8F0),
    onSurface = Color(0xFFE2E8F0),
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = Color(0xFF334155),
    error = Color(0xFFF87171)
)

private val AdminClaro = lightColorScheme(
    primary = Violeta,
    onPrimary = Color.White,
    primaryContainer = VioletaContenedor,
    onPrimaryContainer = Color(0xFF4C1D95),
    secondary = Violeta,
    background = VioletaFondo,
    surface = Color.White,
    surfaceVariant = Color(0xFFF4F2FA),
    onBackground = TextoPrincipal,
    onSurface = TextoPrincipal,
    onSurfaceVariant = TextoSecundario,
    outline = Borde,
    error = Error
)

private val AdminOscuro = darkColorScheme(
    primary = VioletaOscuro,
    onPrimary = Color(0xFF2E1065),
    primaryContainer = Color(0xFF4C1D95),
    onPrimaryContainer = VioletaContenedor,
    secondary = VioletaOscuro,
    background = Color(0xFF0F0B1A),
    surface = Color(0xFF1C1630),
    surfaceVariant = Color(0xFF261E3D),
    onBackground = Color(0xFFE9E5F5),
    onSurface = Color(0xFFE9E5F5),
    onSurfaceVariant = Color(0xFFA59FC0),
    outline = Color(0xFF3B3256),
    error = Color(0xFFF87171)
)

// Un ColorScheme según el perfil. En las pantallas se usa MaterialTheme.colorScheme, nunca un color fijo
@Composable
fun LavaTheme(rol: Rol, oscuro: Boolean = false, content: @Composable () -> Unit) {
    val colores = when (rol) {
        Rol.CLIENTE -> if (oscuro) EstudianteOscuro else EstudianteClaro
        Rol.ADMIN -> if (oscuro) AdminOscuro else AdminClaro
    }
    MaterialTheme(colorScheme = colores, typography = Typography, content = content)
}

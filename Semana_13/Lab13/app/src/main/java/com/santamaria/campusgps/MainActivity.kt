package com.santamaria.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.santamaria.campusgps.data.LugaresRepositoryTecsup
import com.santamaria.campusgps.data.ReferenciaRepositoryDataStore
import com.santamaria.campusgps.data.UbicacionRepositoryGoogle
import com.santamaria.campusgps.ui.PantallaMapa
import com.santamaria.campusgps.ui.theme.CampusGPSTheme
import com.santamaria.campusgps.viewmodel.CampusViewModelFactory
import com.santamaria.campusgps.viewmodel.MapaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: MapaViewModel = viewModel(
                factory = CampusViewModelFactory(
                    UbicacionRepositoryGoogle(applicationContext),
                    LugaresRepositoryTecsup(),
                    ReferenciaRepositoryDataStore(applicationContext)
                )
            )
            val oscuro by vm.oscuro.collectAsState()
            // Íconos de la barra de estado y de navegación: claros sobre el tema oscuro, oscuros sobre el claro
            val vista = LocalView.current
            SideEffect {
                val controlador = WindowCompat.getInsetsController(window, vista)
                controlador.isAppearanceLightStatusBars = !oscuro
                controlador.isAppearanceLightNavigationBars = !oscuro
            }
            CampusGPSTheme(oscuro = oscuro) {
                PantallaMapa(vm)
            }
        }
    }
}

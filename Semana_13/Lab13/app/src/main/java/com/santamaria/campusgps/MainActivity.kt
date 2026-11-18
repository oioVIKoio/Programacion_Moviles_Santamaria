package com.santamaria.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
            CampusGPSTheme {
                val vm: MapaViewModel = viewModel(
                    factory = CampusViewModelFactory(
                        UbicacionRepositoryGoogle(applicationContext),
                        LugaresRepositoryTecsup(),
                        ReferenciaRepositoryDataStore(applicationContext)
                    )
                )
                PantallaMapa(vm)
            }
        }
    }
}

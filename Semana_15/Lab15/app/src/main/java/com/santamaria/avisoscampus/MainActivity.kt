package com.santamaria.avisoscampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.santamaria.avisoscampus.data.AvisosRepositoryDataStore
import com.santamaria.avisoscampus.data.avisosStore
import com.santamaria.avisoscampus.ui.PantallaAvisos
import com.santamaria.avisoscampus.ui.theme.AvisosCampusTheme
import com.santamaria.avisoscampus.viewmodel.AvisosViewModel
import com.santamaria.avisoscampus.viewmodel.AvisosViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cabecera marino bajo la barra de estado: íconos claros; la barra de navegación sigue al fondo claro
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        setContent {
            AvisosCampusTheme {
                val repo = remember { AvisosRepositoryDataStore(applicationContext.avisosStore) }
                val vm: AvisosViewModel = viewModel(factory = AvisosViewModelFactory(repo))
                PantallaAvisos(vm)
            }
        }
    }
}

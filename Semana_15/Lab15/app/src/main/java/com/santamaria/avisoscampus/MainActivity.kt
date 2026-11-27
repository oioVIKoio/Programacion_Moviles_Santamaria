package com.santamaria.avisoscampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.santamaria.avisoscampus.data.AvisosRepositoryMemoria
import com.santamaria.avisoscampus.ui.PantallaAvisos
import com.santamaria.avisoscampus.ui.theme.AvisosCampusTheme
import com.santamaria.avisoscampus.viewmodel.AvisosViewModel
import com.santamaria.avisoscampus.viewmodel.AvisosViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvisosCampusTheme {
                val vm: AvisosViewModel = viewModel(
                    factory = AvisosViewModelFactory(AvisosRepositoryMemoria())
                )
                PantallaAvisos(vm)
            }
        }
    }
}

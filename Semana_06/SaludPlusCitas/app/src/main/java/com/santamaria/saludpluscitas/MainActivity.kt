package com.santamaria.saludpluscitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.santamaria.saludpluscitas.navigation.AppNavigation
import com.santamaria.saludpluscitas.ui.theme.SaludPlusCitasTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaludPlusCitasTheme {
                AppNavigation()
            }
        }
    }
}

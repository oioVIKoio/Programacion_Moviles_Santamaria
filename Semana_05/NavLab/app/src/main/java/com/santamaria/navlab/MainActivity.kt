package com.santamaria.navlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.santamaria.navlab.navigation.AppNavigation
import com.santamaria.navlab.ui.theme.PracticaPROTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaPROTheme {
                AppNavigation()
            }
        }
    }
}

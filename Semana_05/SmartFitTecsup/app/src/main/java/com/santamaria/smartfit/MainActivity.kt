package com.santamaria.smartfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.santamaria.smartfit.navigation.AppNavigation
import com.santamaria.smartfit.ui.theme.SmartFitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartFitTheme {
                AppNavigation()
            }
        }
    }
}

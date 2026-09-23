package com.santamaria.clinicatecsup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.santamaria.clinicatecsup.navigation.AppNavigation
import com.santamaria.clinicatecsup.ui.theme.ClinicaTecsupTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClinicaTecsupTheme {
                AppNavigation()
            }
        }
    }
}
package com.santamaria.saludpluscitas.ui.screens.resultados

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Reto extra: modelo propio de resultado y lista fija en un LazyColumn.
@Composable
fun ResultadosScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        titulo = "13. Resultados",
        detalle = "",
        "Volver" to { navController.popBackStack() }
    )
}

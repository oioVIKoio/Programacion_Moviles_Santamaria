package com.santamaria.campusgps.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.ui.graphics.vector.ImageVector

/** Ícono de cada categoría: lo usan la lista y los marcadores del mapa. */
fun iconoDe(categoria: String): ImageVector = when (categoria) {
    "Estudio" -> Icons.Outlined.MenuBook
    "Comida" -> Icons.Outlined.LocalCafe
    "Eventos" -> Icons.Outlined.Event
    "Deporte" -> Icons.Outlined.SportsSoccer
    "Servicios" -> Icons.Outlined.Info
    else -> Icons.Outlined.Place
}

package com.santamaria.saludpluscitas.ui.screens.agendamiento

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.PantallaEnConstruccion

// TODO: Barra superior con flecha atrás.
//       Buscador "Buscar especialidad..." con estado; Repositorio.buscarEspecialidades en tiempo real.
//       LazyColumn de especialidades: ícono de color, nombre, descripción y chevron.
//       Al tocar una especialidad → Médicos con su especialidadId.
@Composable
fun EspecialidadesScreen(
    navController: NavController
) {
    PantallaEnConstruccion(
        titulo = "4. Especialidades",
        detalle = "",
        "Ver médicos (especialidad 3)" to { navController.navigate(Rutas.Medicos.crearRuta(3)) },
        "Volver" to { navController.popBackStack() }
    )
}

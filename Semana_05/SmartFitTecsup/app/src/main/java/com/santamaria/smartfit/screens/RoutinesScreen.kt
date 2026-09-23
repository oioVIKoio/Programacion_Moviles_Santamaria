package com.santamaria.smartfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.smartfit.components.BottomNavigationBar
import com.santamaria.smartfit.ui.theme.CardWhite
import com.santamaria.smartfit.ui.theme.DarkText
import com.santamaria.smartfit.ui.theme.GrayBackground
import com.santamaria.smartfit.ui.theme.PetrolGreen
import com.santamaria.smartfit.ui.theme.PetrolGreenDark
import com.santamaria.smartfit.ui.theme.PetrolGreenLight
import com.santamaria.smartfit.ui.theme.SoftGrayBg
import com.santamaria.smartfit.ui.theme.SoftGrayText
import com.santamaria.smartfit.ui.theme.SubText

data class RoutineItem(
    val title: String,
    val subtitle: String,
    val duration: String,
    val level: String,
    val icon: ImageVector
)

@Composable
fun RoutinesScreen(navController: NavController) {

    val rutinas = listOf(
        RoutineItem(
            title = "Rutina de Fuerza",
            subtitle = "Hipertrofia y resistencia muscular",
            duration = "45 min",
            level = "Intermedio",
            icon = Icons.Default.FitnessCenter
        ),
        RoutineItem(
            title = "Rutina Cardio & Resistance",
            subtitle = "Quemado calórico y resistencia cardiovascular",
            duration = "30 min",
            level = "Todos los niveles",
            icon = Icons.AutoMirrored.Filled.DirectionsRun
        ),
        RoutineItem(
            title = "Flexibilidad & Mobility",
            subtitle = "Estiramientos guiados y postura",
            duration = "20 min",
            level = "Principiante",
            icon = Icons.Default.SelfImprovement
        )
    )

    Scaffold(
        containerColor = GrayBackground,
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Text(
                text = "Mis rutinas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(rutinas) { rutina ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(PetrolGreenLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = rutina.icon,
                                    contentDescription = rutina.title,
                                    tint = PetrolGreen,
                                    modifier = Modifier.size(26.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = rutina.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = DarkText
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = rutina.subtitle,
                                    fontSize = 13.sp,
                                    color = SubText
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        color = PetrolGreenLight,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = rutina.duration,
                                            color = PetrolGreenDark,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                    Surface(
                                        color = SoftGrayBg,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = rutina.level,
                                            color = SoftGrayText,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

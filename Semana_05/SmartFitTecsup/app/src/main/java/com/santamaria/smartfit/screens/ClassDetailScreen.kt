package com.santamaria.smartfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.smartfit.navigation.Screen
import com.santamaria.smartfit.ui.theme.DarkText
import com.santamaria.smartfit.ui.theme.GrayBackground
import com.santamaria.smartfit.ui.theme.PetrolGreen
import com.santamaria.smartfit.ui.theme.PetrolGreenLight
import com.santamaria.smartfit.ui.theme.SoftGreenBg
import com.santamaria.smartfit.ui.theme.SoftGreenText
import com.santamaria.smartfit.ui.theme.SubText

data class ClassDetailInfo(
    val name: String,
    val info: String,
    val description: String,
    val spots: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailScreen(
    navController: NavController,
    classId: Int
) {
    val clases = listOf(
        ClassDetailInfo(
            name = "Yoga funcional",
            info = "7:00 am · Sala 2 · 60 min",
            description = "Práctica de yoga para mejorar la flexibilidad, postura y fuerza corporal. Cupos limitados.",
            spots = "10 de 12 cupos disponibles",
            icon = Icons.Default.SelfImprovement
        ),
        ClassDetailInfo(
            name = "Cross Training",
            info = "6:00 pm · Sala 1 · 45 min",
            description = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
            spots = "8 de 12 cupos disponibles",
            icon = Icons.Default.FitnessCenter
        ),
        ClassDetailInfo(
            name = "Spinning",
            info = "7:30 pm · Sala 3 · 45 min",
            description = "Ejercicio cardiovascular de alta intensidad sobre bicicleta fija. Cupos limitados.",
            spots = "6 de 12 cupos disponibles",
            icon = Icons.AutoMirrored.Filled.DirectionsBike
        )
    )

    val clase = clases.getOrElse(classId) { clases[1] }

    Scaffold(
        containerColor = GrayBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalle de clase",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = DarkText
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = DarkText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GrayBackground)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            // Área visual con icono de entrenamiento
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PetrolGreenLight),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.85f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = clase.icon,
                            contentDescription = clase.name,
                            tint = PetrolGreen,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = clase.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = clase.info,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = SubText
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = clase.description,
                fontSize = 15.sp,
                color = DarkText,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                color = SoftGreenBg,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = clase.spots,
                    color = SoftGreenText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(
                        Screen.Confirmation.createRoute(classId)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PetrolGreen,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "RESERVAR CUPO",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

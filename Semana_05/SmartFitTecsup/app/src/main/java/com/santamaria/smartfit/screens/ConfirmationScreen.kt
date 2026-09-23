package com.santamaria.smartfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.smartfit.navigation.Screen
import com.santamaria.smartfit.ui.theme.DarkText
import com.santamaria.smartfit.ui.theme.GrayBackground
import com.santamaria.smartfit.ui.theme.PetrolGreen
import com.santamaria.smartfit.ui.theme.PetrolGreenLight
import com.santamaria.smartfit.ui.theme.SubText

@Composable
fun ConfirmationScreen(
    navController: NavController,
    classId: Int
) {

    val clases = listOf(
        Triple("Yoga funcional", "Hoy, 7:00 am", "Sala 2"),
        Triple("Cross Training", "Hoy, 6:00 pm", "Sala 1"),
        Triple("Spinning", "Hoy, 7:30 pm", "Sala 3")
    )

    val clase = clases.getOrElse(classId) {
        clases[1]
    }

    Scaffold(
        containerColor = GrayBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Círculo verde claro con check
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(PetrolGreenLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Éxito",
                    tint = PetrolGreen,
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "¡Cupo reservado!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = clase.first,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = PetrolGreen,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${clase.second} · ${clase.third}",
                fontSize = 16.sp,
                color = SubText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.Reservations.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
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
                    text = "VER MIS RESERVAS",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

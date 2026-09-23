package com.santamaria.smartfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.smartfit.components.BottomNavigationBar
import com.santamaria.smartfit.navigation.Screen
import com.santamaria.smartfit.ui.theme.CardWhite
import com.santamaria.smartfit.ui.theme.DarkText
import com.santamaria.smartfit.ui.theme.GrayBackground
import com.santamaria.smartfit.ui.theme.PetrolGreen
import com.santamaria.smartfit.ui.theme.PetrolGreenDark
import com.santamaria.smartfit.ui.theme.PetrolGreenLight
import com.santamaria.smartfit.ui.theme.SubText

@Composable
fun HomeScreen(navController: NavController) {

    var selectedFilter by remember { mutableStateOf("Hoy") }

    val clases = listOf(
        Triple("Yoga funcional", "7:00 am · Sala 2", Icons.Default.SelfImprovement),
        Triple("Cross Training", "6:00 pm · Sala 1", Icons.Default.FitnessCenter),
        Triple("Spinning", "7:30 pm · Sala 3", Icons.AutoMirrored.Filled.DirectionsBike)
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
        ) {
            // Header verde de ancho completo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = PetrolGreen,
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    Text(
                        text = "TECSUP Fit",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Hola, Victor",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 16.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Opciones compactas: Hoy / Esta semana
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val filters = listOf("Hoy", "Esta semana")
                    filters.forEach { filter ->
                        val isSelected = selectedFilter == filter
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { selectedFilter = filter },
                            color = if (isSelected) PetrolGreen else PetrolGreenLight,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = filter,
                                color = if (isSelected) Color.White else PetrolGreenDark,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Clases disponibles",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(clases) { index, clase ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        Screen.ClassDetail.createRoute(index)
                                    )
                                },
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
                                        imageVector = clase.third,
                                        contentDescription = clase.first,
                                        tint = PetrolGreen,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = clase.first,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = DarkText
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = clase.second,
                                        fontSize = 14.sp,
                                        color = SubText
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

package com.santamaria.clinicatecsup.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.clinicatecsup.navigation.AppDrawer
import com.santamaria.clinicatecsup.navigation.Screen
import com.santamaria.clinicatecsup.ui.theme.BackgroundLight
import com.santamaria.clinicatecsup.ui.theme.PurpleContainer
import com.santamaria.clinicatecsup.ui.theme.PurplePrimary

data class MedicalRecord(
    val date: String,
    val specialty: String,
    val type: String,
    val doctor: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalHistoryScreen(
    navController: NavController
) {

    val registros = listOf(
        MedicalRecord(
            date = "15/08/2026",
            specialty = "Cardiología",
            type = "Control general",
            doctor = "Dra. Ana Torres"
        ),
        MedicalRecord(
            date = "10/06/2026",
            specialty = "Medicina general",
            type = "Consulta médica",
            doctor = "Dr. Luis Vega"
        ),
        MedicalRecord(
            date = "22/04/2026",
            specialty = "Dermatología",
            type = "Control de rutina",
            doctor = "Dra. Rosa Díaz"
        )
    )

    AppDrawer(
        navController = navController,
        currentRoute = Screen.MedicalHistory.route
    ) { openDrawer ->

        Scaffold(
            containerColor = BackgroundLight,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Historial médico",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = openDrawer) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BackgroundLight
                    )
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                Text(
                    text = "Historial médico de Victor",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(registros) { registro ->

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .background(
                                                PurpleContainer,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            )
                                    ) {
                                        Text(
                                            text = registro.specialty,
                                            color = PurplePrimary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }

                                    Text(
                                        text = registro.date,
                                        fontSize = 13.sp,
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = registro.type,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    text = "Atendido por: ${registro.doctor}",
                                    fontSize = 14.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
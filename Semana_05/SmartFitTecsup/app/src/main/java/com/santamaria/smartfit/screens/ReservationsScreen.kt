package com.santamaria.smartfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.smartfit.components.BottomNavigationBar
import com.santamaria.smartfit.ui.theme.CardWhite
import com.santamaria.smartfit.ui.theme.DarkText
import com.santamaria.smartfit.ui.theme.GrayBackground
import com.santamaria.smartfit.ui.theme.PetrolGreen
import com.santamaria.smartfit.ui.theme.SoftGrayBg
import com.santamaria.smartfit.ui.theme.SoftGrayText
import com.santamaria.smartfit.ui.theme.SoftGreenBg
import com.santamaria.smartfit.ui.theme.SoftGreenText
import com.santamaria.smartfit.ui.theme.SoftRedBg
import com.santamaria.smartfit.ui.theme.SoftRedText
import com.santamaria.smartfit.ui.theme.SubText

data class ReservationItem(
    val id: Int,
    val className: String,
    val schedule: String,
    var status: String // "Confirmada", "Completada", "Cancelada"
)

@Composable
fun ReservationsScreen(navController: NavController) {

    // Lista mutable de estado local de reservas
    val reservations = remember {
        mutableStateListOf(
            ReservationItem(1, "Cross Training", "Hoy, 6:00 pm", "Confirmada"),
            ReservationItem(2, "Yoga funcional", "Ayer, 7:00 am", "Completada")
        )
    }

    var selectedReservationToCancel by remember { mutableStateOf<ReservationItem?>(null) }

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
                text = "Mis reservas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(reservations) { item ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            // Detalle verde vertical a la izquierda para "Confirmada"
                            if (item.status == "Confirmada") {
                                Box(
                                    modifier = Modifier
                                        .width(6.dp)
                                        .fillMaxHeight()
                                        .background(PetrolGreen)
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.className,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = DarkText
                                    )

                                    // Etiqueta de estado
                                    val (bgColor, textColor) = when (item.status) {
                                        "Confirmada" -> SoftGreenBg to SoftGreenText
                                        "Completada" -> SoftGrayBg to SoftGrayText
                                        "Cancelada" -> SoftRedBg to SoftRedText
                                        else -> SoftGrayBg to SoftGrayText
                                    }

                                    Surface(
                                        color = bgColor,
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = item.status,
                                            color = textColor,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = item.schedule,
                                    fontSize = 14.sp,
                                    color = SubText
                                )

                                // Mostrar botón Cancelar reserva solo para reservas Confirmadas
                                if (item.status == "Confirmada") {
                                    Spacer(modifier = Modifier.height(12.dp))

                                    TextButton(
                                        onClick = {
                                            selectedReservationToCancel = item
                                        },
                                        modifier = Modifier.align(Alignment.End),
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = SoftRedText
                                        )
                                    ) {
                                        Text(
                                            text = "Cancelar reserva",
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // AlertDialog para Confirmar Cancelación
        selectedReservationToCancel?.let { reservation ->
            AlertDialog(
                onDismissRequest = {
                    selectedReservationToCancel = null
                },
                title = {
                    Text(
                        text = "¿Cancelar reserva?",
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                },
                text = {
                    Text(
                        text = "¿Estás seguro de que deseas cancelar esta reserva?",
                        color = DarkText,
                        fontSize = 14.sp
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val index = reservations.indexOfFirst { it.id == reservation.id }
                            if (index != -1) {
                                reservations[index] = reservations[index].copy(status = "Cancelada")
                            }
                            selectedReservationToCancel = null
                        }
                    ) {
                        Text(
                            text = "Cancelar reserva",
                            color = SoftRedText,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            selectedReservationToCancel = null
                        }
                    ) {
                        Text(
                            text = "Volver",
                            color = SubText,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                containerColor = CardWhite,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

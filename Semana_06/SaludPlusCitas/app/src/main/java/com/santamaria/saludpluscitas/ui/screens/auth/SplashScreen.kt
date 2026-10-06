package com.santamaria.saludpluscitas.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santamaria.saludpluscitas.R
import com.santamaria.saludpluscitas.navigation.Rutas
import com.santamaria.saludpluscitas.ui.components.BotonPrimario
import com.santamaria.saludpluscitas.ui.theme.AzulOscuro
import com.santamaria.saludpluscitas.ui.theme.AzulPrimario
import com.santamaria.saludpluscitas.ui.theme.TextoSecundario

// Fondo de la ilustración del diseño, para que la imagen no se note recortada.
private val FondoSplash = Color(0xFFF5F6FA)

@Composable
fun SplashScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoSplash)
            .safeDrawingPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        LogoSaludPlus()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Clínica",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro
        )
        Text(
            text = "SaludPlus",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AzulOscuro
        )
        Text(
            text = "Tu salud, nuestra prioridad",
            fontSize = 15.sp,
            color = TextoSecundario
        )

        Image(
            painter = painterResource(id = R.drawable.ilustracion_doctor),
            contentDescription = "Doctor",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        BotonPrimario(
            texto = "Comenzar",
            onClick = { navController.navigate(Rutas.Registro.ruta) }
        )

        TextButton(onClick = { navController.navigate(Rutas.Login.ruta) }) {
            Text(
                text = "Ya tengo una cuenta",
                color = AzulPrimario,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// Cruz azul con un corazón blanco al centro.
@Composable
private fun LogoSaludPlus() {
    Box(
        modifier = Modifier.size(96.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(96.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AzulPrimario)
        )
        Box(
            modifier = Modifier
                .width(96.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AzulPrimario)
        )
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
    }
}

package tecsup.santamaria.lavacampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import tecsup.santamaria.lavacampus.ui.navigation.NavLava

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavLava()
        }
    }
}


/*
*
formatos de diseño, agregar un rol soporte tenemos que registrar un
soporte, campos, codigo, nombre, apellido, clave y correo y luego tenemos que ingresar
por su clave y correo y confirmacion de clave para su iniciar sesion, en servicio, tiene q haber
uno q diga otros servicios,
* lavado,, planchado, seco, se explica donde comienza con quien se conecta en la vista
*
*
*
*
cual problematica cubre ese proyecto
* */
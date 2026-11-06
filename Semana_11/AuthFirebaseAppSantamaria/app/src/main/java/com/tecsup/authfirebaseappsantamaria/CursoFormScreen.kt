package com.tecsup.authfirebaseappsantamaria

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CursoFormScreen(
    cursoId: String?,
    onGuardado: () -> Unit,
    onVolver: () -> Unit
) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    val esEdicion = cursoId != null

    var nombre by remember { mutableStateOf("") }
    var docente by remember { mutableStateOf("") }
    var creditos by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Al editar, cargar los datos del curso
    LaunchedEffect(cursoId) {
        if (cursoId != null) {
            db.collection("cursos").document(cursoId).get()
                .addOnSuccessListener { doc ->
                    doc.toObject(Curso::class.java)?.let {
                        nombre = it.nombre
                        docente = it.docente
                        creditos = it.creditos.toString()
                    }
                }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                if (esEdicion) "Editar curso ✏️" else "Nuevo curso 📘",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del curso") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = docente,
                onValueChange = { docente = it },
                label = { Text("Docente") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = creditos,
                onValueChange = { creditos = it.filter { c -> c.isDigit() } },
                label = { Text("Créditos") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (nombre.isBlank() || docente.isBlank() || creditos.isBlank()) {
                        Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val datos = mapOf(
                        "nombre" to nombre.trim(),
                        "docente" to docente.trim(),
                        "creditos" to creditos.toInt(),
                        "userId" to uid
                    )

                    isLoading = true
                    // Crear o actualizar según el caso
                    val tarea = if (cursoId != null) {
                        db.collection("cursos").document(cursoId).set(datos)
                    } else {
                        db.collection("cursos").add(datos)
                    }

                    tarea.addOnCompleteListener { task ->
                        isLoading = false
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                if (esEdicion) "Curso actualizado ✅" else "Curso registrado ✅",
                                Toast.LENGTH_SHORT
                            ).show()
                            onGuardado()
                        } else {
                            Toast.makeText(
                                context,
                                "Error: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isLoading) "Guardando..." else "Guardar")
            }

            TextButton(onClick = onVolver) {
                Text("← Volver")
            }
        }
    }
}

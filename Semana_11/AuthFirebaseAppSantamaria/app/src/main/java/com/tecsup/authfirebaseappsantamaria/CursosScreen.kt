package com.tecsup.authfirebaseappsantamaria

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CursosScreen(
    onAgregar: () -> Unit,
    onEditar: (String) -> Unit,
    onVolver: () -> Unit
) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var cursos by remember { mutableStateOf(listOf<Curso>()) }
    var isLoading by remember { mutableStateOf(true) }
    var cursoAEliminar by remember { mutableStateOf<Curso?>(null) }

    // Leer: solo los cursos del usuario autenticado, en tiempo real
    DisposableEffect(uid) {
        val registro = db.collection("cursos")
            .whereEqualTo("userId", uid)
            .addSnapshotListener { snapshot, error ->
                isLoading = false
                if (error != null) {
                    Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                cursos = snapshot?.documents
                    ?.mapNotNull { it.toObject(Curso::class.java)?.copy(id = it.id) }
                    ?.sortedBy { it.nombre.lowercase() }
                    ?: emptyList()
            }
        onDispose { registro.remove() }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregar) {
                Text("+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Mis Cursos 📚", style = MaterialTheme.typography.headlineMedium)
            TextButton(onClick = onVolver) {
                Text("← Volver")
            }
            Spacer(Modifier.height(8.dp))

            when {
                isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                cursos.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no tienes cursos. Agrega uno con +")
                }
                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(cursos, key = { it.id }) { curso ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(16.dp)) {
                                Text(curso.nombre, style = MaterialTheme.typography.titleMedium)
                                Text("Docente: ${curso.docente}")
                                Text("Créditos: ${curso.creditos}")
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    TextButton(onClick = { onEditar(curso.id) }) { Text("Editar") }
                                    TextButton(onClick = { cursoAEliminar = curso }) { Text("Eliminar") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Eliminar: con confirmación
    cursoAEliminar?.let { curso ->
        AlertDialog(
            onDismissRequest = { cursoAEliminar = null },
            title = { Text("Eliminar curso") },
            text = { Text("¿Eliminar \"${curso.nombre}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    db.collection("cursos").document(curso.id).delete()
                        .addOnSuccessListener {
                            Toast.makeText(context, "Curso eliminado 🗑️", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    cursoAEliminar = null
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { cursoAEliminar = null }) { Text("Cancelar") }
            }
        )
    }
}

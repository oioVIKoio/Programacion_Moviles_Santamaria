package com.santamaria.saludpluscitas.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.santamaria.saludpluscitas.data.model.Cita
import com.santamaria.saludpluscitas.data.model.Especialidad
import com.santamaria.saludpluscitas.data.model.Medico
import com.santamaria.saludpluscitas.data.model.Resultado
import com.santamaria.saludpluscitas.data.model.Sede
import com.santamaria.saludpluscitas.data.model.Usuario
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeParseException

// Todos los datos viven en memoria (sin Room, SQLite ni Firebase).
// Se pierden al cerrar la app.
object Repositorio {

    // ---------------------------------------------------------------
    // Colecciones
    // ---------------------------------------------------------------

    // Usuario de prueba para poder iniciar sesión sin registrarse.
    val usuarios = mutableStateListOf(
        Usuario(
            id = 1,
            nombre = "Victor Santamaria",
            telefono = "987654321",
            correo = "victor.santamaria@gmail.com",
            password = "123456"
        )
    )

    var usuarioActual by mutableStateOf<Usuario?>(null)

    val especialidades = listOf(
        Especialidad(1, "Medicina General", "Atención integral"),
        Especialidad(2, "Pediatría", "Niños y adolescentes"),
        Especialidad(3, "Ginecología", "Salud de la mujer"),
        Especialidad(4, "Cardiología", "Corazón y vasos sanguíneos"),
        Especialidad(5, "Dermatología", "Piel, cabello y uñas"),
        Especialidad(6, "Traumatología", "Huesos y articulaciones"),
        Especialidad(7, "Oftalmología", "Salud visual")
    )

    val sedes = listOf(
        Sede(1, "SaludPlus Los Olivos", "Los Olivos", "Av. Carlos Izaguirre 123, Los Olivos", "(01) 521-4000", "Lun a Vie, 8:00 a 18:00"),
        Sede(2, "SaludPlus Miraflores", "Miraflores", "Av. José Larco 845, Miraflores", "(01) 445-2100", "Lun a Vie, 8:00 a 18:00")
    )

    val medicos = listOf(
        // Medicina General
        Medico(1, "Dr. Carlos Mendoza", 1, "Médico general", "10234", 4.7, 102, "Disponible hoy", 1, "987 120 341"),
        Medico(2, "Dra. Lucía Fernández", 1, "Médica general", "10876", 4.5, 64, "Disponible mañana", 2, "987 120 342"),
        // Pediatría
        Medico(3, "Dra. Patricia Vargas", 2, "Pediatra", "11452", 4.9, 140, "Disponible hoy", 1, "987 120 343"),
        Medico(4, "Dr. Jorge Salazar", 2, "Pediatra", "11987", 4.6, 71, "Disponible esta semana", 2, "987 120 344"),
        // Ginecología (datos del diseño de referencia)
        Medico(5, "Dra. Ana Torres", 3, "Ginecóloga", "12345", 4.9, 120, "Disponible hoy", 1, "987 120 345"),
        Medico(6, "Dra. Claudia Rojas", 3, "Ginecóloga", "12678", 4.8, 95, "Disponible mañana", 2, "987 120 346"),
        Medico(7, "Dr. Luis Ramírez", 3, "Ginecólogo", "12901", 4.7, 88, "Disponible hoy", 2, "987 120 347"),
        Medico(8, "Dra. Mariana Soto", 3, "Ginecóloga", "13024", 4.6, 76, "Disponible esta semana", 1, "987 120 348"),
        // Cardiología
        Medico(9, "Dr. Ricardo Paredes", 4, "Cardiólogo", "13456", 4.8, 110, "Disponible mañana", 1, "987 120 349"),
        Medico(10, "Dra. Elena Castro", 4, "Cardióloga", "13789", 4.7, 83, "Disponible hoy", 2, "987 120 350"),
        // Dermatología
        Medico(11, "Dra. Sofía Quispe", 5, "Dermatóloga", "14123", 4.8, 97, "Disponible hoy", 1, "987 120 351"),
        Medico(12, "Dr. Martín Herrera", 5, "Dermatólogo", "14456", 4.4, 52, "Disponible esta semana", 2, "987 120 352"),
        // Traumatología
        Medico(13, "Dr. Andrés Flores", 6, "Traumatólogo", "14789", 4.7, 91, "Disponible mañana", 1, "987 120 353"),
        Medico(14, "Dra. Valeria Ríos", 6, "Traumatóloga", "15012", 4.6, 68, "Disponible hoy", 2, "987 120 354"),
        // Oftalmología
        Medico(15, "Dr. Fernando Chávez", 7, "Oftalmólogo", "15345", 4.8, 105, "Disponible esta semana", 1, "987 120 355"),
        Medico(16, "Dra. Gabriela Núñez", 7, "Oftalmóloga", "15678", 4.5, 59, "Disponible mañana", 2, "987 120 356")
    )

    // Turnos de 30 minutos que atiende cada médico (mañana y tarde).
    val horariosBase = listOf(
        "08:00", "08:30", "09:00",
        "09:30", "10:00", "10:30",
        "11:00", "11:30", "12:00",
        "14:00", "14:30", "15:00",
        "15:30", "16:00", "16:30",
        "17:00", "17:30"
    )

    // Límite del texto libre "Motivo de consulta".
    const val MAX_MOTIVO = 200

    val citas = mutableStateListOf<Cita>()

    // Reto 13: lista fija de resultados de exámenes (no cambia en la app).
    val resultados = listOf(
        Resultado(1, "Hemograma completo", 1, "2026-09-18", true, "Valores dentro del rango normal"),
        Resultado(2, "Perfil lipídico", 9, "2026-09-25", true, "Colesterol LDL ligeramente elevado"),
        Resultado(3, "Glucosa en ayunas", 1, "2026-10-02", true, "92 mg/dL, dentro del rango normal"),
        Resultado(4, "Electrocardiograma", 10, "2026-10-05", false, "Pendiente de lectura del especialista"),
        Resultado(5, "Examen de vista", 15, "2026-10-06", false, "Resultados disponibles en 48 horas")
    )

    // ---------------------------------------------------------------
    // Usuarios y sesión
    // ---------------------------------------------------------------

    // Registra un usuario nuevo (sin iniciar sesión). Devuelve false
    // si el teléfono (o el correo, si se llenó) ya está registrado.
    fun registrarUsuario(
        nombre: String,
        telefono: String,
        correo: String,
        password: String
    ): Boolean {
        val tel = telefono.trim()
        val email = correo.trim()

        val existe = usuarios.any {
            it.telefono == tel || (email.isNotBlank() && it.correo.equals(email, ignoreCase = true))
        }
        if (existe) return false

        val nuevo = Usuario(
            id = (usuarios.maxOfOrNull { it.id } ?: 0) + 1,
            nombre = nombre.trim(),
            telefono = tel,
            correo = email,
            password = password
        )
        // No inicia sesión: el paciente vuelve al Login y entra con su cuenta.
        usuarios.add(nuevo)
        return true
    }

    // Busca al usuario por teléfono o correo y contraseña.
    // Si existe, lo guarda en usuarioActual y devuelve true.
    fun iniciarSesion(usuario: String, password: String): Boolean {
        val dato = usuario.trim()
        val encontrado = usuarios.find {
            (it.telefono == dato ||
                (it.correo.isNotBlank() && it.correo.equals(dato, ignoreCase = true))) &&
                it.password == password
        }
        usuarioActual = encontrado
        return encontrado != null
    }

    fun cerrarSesion() {
        usuarioActual = null
    }

    // ---------------------------------------------------------------
    // Especialidades y médicos
    // ---------------------------------------------------------------

    // Especialidades cuyo nombre contiene el texto (sin importar mayúsculas).
    fun buscarEspecialidades(texto: String): List<Especialidad> {
        val dato = texto.trim()
        return especialidades.filter { it.nombre.contains(dato, ignoreCase = true) }
    }

    // Las 3 primeras especialidades para el LazyRow del Inicio.
    fun especialidadesDestacadas(): List<Especialidad> {
        return especialidades.take(3)
    }

    fun obtenerEspecialidad(id: Int): Especialidad? {
        return especialidades.find { it.id == id }
    }

    fun obtenerMedico(id: Int): Medico? {
        return medicos.find { it.id == id }
    }

    fun obtenerSede(id: Int): Sede? {
        return sedes.find { it.id == id }
    }

    fun obtenerCita(id: Int): Cita? {
        return citas.find { it.id == id }
    }

    // Médicos de una especialidad, mejor calificados primero.
    fun medicosPorEspecialidad(especialidadId: Int): List<Medico> {
        return medicos
            .filter { it.especialidadId == especialidadId }
            .sortedByDescending { it.calificacion }
    }

    // Médicos de una especialidad cuyo nombre contiene el texto.
    fun buscarMedicos(especialidadId: Int, texto: String): List<Medico> {
        val dato = texto.trim()
        return medicosPorEspecialidad(especialidadId)
            .filter { it.nombre.contains(dato, ignoreCase = true) }
    }

    // Médicos de una sede, ordenados por especialidad y luego por calificación.
    fun medicosPorSede(sedeId: Int): List<Medico> {
        return medicos
            .filter { it.sedeId == sedeId }
            .sortedWith(compareBy<Medico> { it.especialidadId }.thenByDescending { it.calificacion })
    }

    // Médicos agrupados por especialidad (en el orden de la lista de especialidades).
    // Con sedeId solo entran los de esa sede; con texto se filtra por nombre del médico
    // o de la especialidad.
    fun medicosPorEspecialidadAgrupados(sedeId: Int? = null, texto: String = ""): Map<Especialidad, List<Medico>> {
        val dato = texto.trim()
        return especialidades.associateWith { especialidad ->
            medicos
                .filter { it.especialidadId == especialidad.id }
                .filter { sedeId == null || it.sedeId == sedeId }
                .filter {
                    it.nombre.contains(dato, ignoreCase = true) ||
                        especialidad.nombre.contains(dato, ignoreCase = true)
                }
                .sortedByDescending { it.calificacion }
        }.filterValues { it.isNotEmpty() }
    }

    // ---------------------------------------------------------------
    // Citas
    // ---------------------------------------------------------------

    // Horarios de horariosBase que siguen libres ese día:
    // - sin los que el médico ya tiene reservados,
    // - sin los que el paciente ya ocupa con otro médico,
    // - y si la fecha es hoy, sin las horas que ya pasaron.
    fun horariosDisponibles(medicoId: Int, fecha: String): List<String> {
        val dia = parsearFecha(fecha) ?: return emptyList()
        val hoy = LocalDate.now()
        if (dia.isBefore(hoy) || dia.dayOfWeek in finDeSemana) return emptyList()

        val ocupadas = citas
            .filter { it.fecha == fecha && (it.medicoId == medicoId || it.usuarioId == usuarioActual?.id) }
            .map { it.hora }
        val ahora = LocalTime.now()
        return horariosBase
            .filter { it !in ocupadas }
            .filter { dia != hoy || LocalTime.parse(it).isAfter(ahora) }
    }

    // Revisa que la cita se pueda agendar. Devuelve el mensaje de error
    // o null si todo está bien.
    fun validarCita(medicoId: Int, fecha: String, hora: String, motivo: String): String? {
        val usuario = usuarioActual ?: return "Tu sesión terminó. Vuelve a iniciar sesión."
        obtenerMedico(medicoId) ?: return "El médico elegido no existe."
        val dia = parsearFecha(fecha) ?: return "La fecha no es válida."
        if (dia.isBefore(LocalDate.now())) return "No puedes agendar en una fecha pasada."
        if (dia.dayOfWeek in finDeSemana) return "La clínica no atiende sábados ni domingos."
        if (hora !in horariosBase) return "La hora elegida no es válida."
        if (dia == LocalDate.now() && !LocalTime.parse(hora).isAfter(LocalTime.now())) {
            return "Esa hora ya pasó. Elige otra."
        }
        if (citas.any { it.medicoId == medicoId && it.fecha == fecha && it.hora == hora }) {
            return "Ese horario ya no está disponible. Vuelve y elige otro."
        }
        if (citas.any { it.usuarioId == usuario.id && it.fecha == fecha && it.hora == hora }) {
            return "Ya tienes otra cita a esa misma hora."
        }
        if (motivo.trim().length > MAX_MOTIVO) return "El motivo no puede pasar de $MAX_MOTIVO caracteres."
        return null
    }

    private val finDeSemana = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

    private fun parsearFecha(fecha: String): LocalDate? {
        return try {
            LocalDate.parse(fecha)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    // Crea la cita del usuarioActual. Devuelve null si validarCita
    // encuentra algún problema (horario tomado, fecha pasada, sin sesión...).
    fun agendarCita(
        medicoId: Int,
        fecha: String,
        hora: String,
        motivo: String
    ): Cita? {
        if (validarCita(medicoId, fecha, hora, motivo) != null) return null
        val usuario = usuarioActual ?: return null

        val cita = Cita(
            id = (citas.maxOfOrNull { it.id } ?: 0) + 1,
            usuarioId = usuario.id,
            medicoId = medicoId,
            fecha = fecha,
            hora = hora,
            motivo = motivo.trim()
        )
        citas.add(cita)
        return cita
    }

    // Citas del usuarioActual ordenadas por fecha y hora.
    fun citasDelUsuario(): List<Cita> {
        val usuario = usuarioActual ?: return emptyList()
        return citas
            .filter { it.usuarioId == usuario.id }
            .sortedWith(compareBy({ it.fecha }, { it.hora }))
    }

    // Reto extra (Detalle de cita). Borra la cita y su horario vuelve a
    // quedar libre. Devuelve false si no existía.
    fun cancelarCita(id: Int): Boolean {
        return citas.removeIf { it.id == id }
    }
}

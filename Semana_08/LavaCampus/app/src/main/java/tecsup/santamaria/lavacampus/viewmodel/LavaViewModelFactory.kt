package tecsup.santamaria.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Crea cualquier ViewModel de la app pasándole lo que necesita (repository, preferencias...)
class LavaViewModelFactory(private val crear: () -> ViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = crear() as T
}

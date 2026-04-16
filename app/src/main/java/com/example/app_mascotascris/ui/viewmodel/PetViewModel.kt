package com.example.app_mascotascris.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mascotascris.data.local.AppDatabase
import com.example.app_mascotascris.data.local.entities.PetEntity
import com.example.app_mascotascris.data.repository.PetRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PetViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PetRepository
    private val _selectedFilter = MutableStateFlow("Todos")
    val selectedFilter: StateFlow<String> = _selectedFilter

    val pets: StateFlow<List<PetEntity>> = _selectedFilter
        .flatMapLatest { filter ->
            repository.getPetsByType(filter)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        val petDao = AppDatabase.getDatabase(application).petDao()
        repository = PetRepository(petDao)
        
        // Insertar datos de prueba si la base de datos está vacía
        viewModelScope.launch {
            repository.allPets.first().let { 
                if (it.isEmpty()) {
                    repository.insertPet(PetEntity(name = "Buddy", type = "Perro", age = "2 años", description = "Amigable", status = "Disponible"))
                    repository.insertPet(PetEntity(name = "Luna", type = "Gato", age = "1 año", description = "Tranquila", status = "Disponible"))
                    repository.insertPet(PetEntity(name = "Snowy", type = "Conejo", age = "6 meses", description = "Saltarín", status = "Disponible"))
                    repository.insertPet(PetEntity(name = "Piolín", type = "Aves", age = "1 año", description = "Cantante", status = "Disponible"))
                }
            }
        }
    }

    fun setFilter(filter: String) {
        _selectedFilter.value = filter
    }

    fun addPet(name: String, type: String, age: String, description: String, imageUrl: String? = null) {
        viewModelScope.launch {
            repository.insertPet(
                PetEntity(
                    name = name,
                    type = type,
                    age = age,
                    description = description,
                    status = "Disponible",
                    imageUrl = imageUrl
                )
            )
        }
    }
}

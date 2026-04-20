package com.example.app_mascotascris.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mascotascris.data.local.AppDatabase
import com.example.app_mascotascris.data.local.entities.PetEntity
import com.example.app_mascotascris.data.local.entities.AdoptionFormEntity
import com.example.app_mascotascris.data.repository.PetRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PetViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PetRepository
    private val _selectedFilter = MutableStateFlow("Todos")
    val selectedFilter: StateFlow<String> = _selectedFilter

    @OptIn(ExperimentalCoroutinesApi::class)
    val pets: StateFlow<List<PetEntity>> = _selectedFilter
        .flatMapLatest { filter ->
            repository.getPetsByType(filter)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val adoptionForms: StateFlow<List<AdoptionFormEntity>> by lazy {
        repository.allForms.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    init {
        val database = AppDatabase.getDatabase(application)
        repository = PetRepository(database.petDao(), database.adoptionFormDao())
        
        viewModelScope.launch {
            repository.allPets.first().let { 
                if (it.isEmpty()) {
                    val packageName = application.packageName
                    repository.insertPet(PetEntity(name = "Doby", type = "Perro", breed = "Labrador", age = "2 años", description = "Amigable y juguetón.", status = "Disponible", hasVaccines = true, shelter = "Huellitas", imageUrl = "android.resource://$packageName/drawable/doby"))
                    repository.insertPet(PetEntity(name = "Katty", type = "Gato", breed = "Siamés", age = "1 año", description = "Tranquila.", status = "Disponible", hasVaccines = true, shelter = "Gatitos", imageUrl = "android.resource://$packageName/drawable/katty"))
                    repository.insertPet(PetEntity(name = "Nieve", type = "Conejo", breed = "Cabeza de León", age = "6 meses", description = "Curioso.", status = "Disponible", hasVaccines = false, shelter = "Bosque", imageUrl = "android.resource://$packageName/drawable/nieve"))
                }
            }
        }
    }

    fun setFilter(filter: String) {
        _selectedFilter.value = filter
    }

    fun getPetById(id: Int): Flow<PetEntity?> {
        return repository.getPetById(id)
    }

    fun addAdoptionForm(petName: String, applicantName: String, phone: String, reason: String, housing: String, hasOtherPets: String) {
        viewModelScope.launch {
            repository.insertAdoptionForm(
                AdoptionFormEntity(
                    petName = petName,
                    applicantName = applicantName,
                    phoneNumber = phone,
                    reason = reason,
                    housingType = housing,
                    hasOtherPets = hasOtherPets
                )
            )
        }
    }

    fun addPet(name: String, type: String, breed: String, age: String, description: String, hasVaccines: Boolean, shelter: String, imageUrl: String? = null) {
        viewModelScope.launch {
            repository.insertPet(PetEntity(name = name, type = type, breed = breed, age = age, description = description, status = "Disponible", hasVaccines = hasVaccines, shelter = shelter, imageUrl = imageUrl))
        }
    }
}

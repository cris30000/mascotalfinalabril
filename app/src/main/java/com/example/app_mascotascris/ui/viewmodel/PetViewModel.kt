package com.example.app_mascotascris.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mascotascris.data.local.AppDatabase
import com.example.app_mascotascris.data.local.entities.PetEntity
import com.example.app_mascotascris.data.local.entities.AdoptionFormEntity
import com.example.app_mascotascris.data.local.entities.UserEntity
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

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser

    init {
        val database = AppDatabase.getDatabase(application)
        repository = PetRepository(database.petDao(), database.adoptionFormDao(), database.userDao())
        
        viewModelScope.launch {
            repository.allPets.first().let { 
                if (it.isEmpty()) {
                    val packageName = application.packageName
                    repository.insertPet(PetEntity(
                        nombre_mascota = "Doby", 
                        especie = "Perro", 
                        edad_aprox = "2 años", 
                        genero = "Macho",
                        estado = "Disponible",
                        lugar_rescate = "Calle Central",
                        descripcion = "Amigable y juguetón.",
                        condiciones_especiales = "Ninguna",
                        foto_principal = "android.resource://$packageName/drawable/doby",
                        galeria_fotos = "",
                        necesita_hogar_temporal = false,
                        apto_con_ninos = true,
                        apto_con_otros_animales = true,
                        fecha_ingreso = "2023-10-01"
                    ))
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

    fun addPet(
        nombre: String,
        especie: String,
        edad: String,
        genero: String,
        estado: String,
        lugarRescate: String,
        descripcion: String,
        condiciones: String,
        fotoPrincipal: String?,
        galeria: String,
        hogarTemporal: Boolean,
        conNinos: Boolean,
        conOtros: Boolean,
        fechaIngreso: String,
        fundacionId: Int?
    ) {
        viewModelScope.launch {
            repository.insertPet(PetEntity(
                nombre_mascota = nombre,
                especie = especie,
                edad_aprox = edad,
                genero = genero,
                estado = estado,
                lugar_rescate = lugarRescate,
                descripcion = descripcion,
                condiciones_especiales = condiciones,
                foto_principal = fotoPrincipal,
                galeria_fotos = galeria,
                necesita_hogar_temporal = hogarTemporal,
                apto_con_ninos = conNinos,
                apto_con_otros_animales = conOtros,
                fecha_ingreso = fechaIngreso,
                fundacion_id = fundacionId
            ))
        }
    }

    // User Operations
    fun registerUser(nombre: String, apellidos: String, email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = UserEntity(nombre = nombre, apellidos = apellidos, email = email, password = pass)
            repository.registerUser(user)
            onSuccess()
        }
    }

    fun loginUser(email: String, pass: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(email, pass)
            if (user != null) {
                _currentUser.value = user
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }
}

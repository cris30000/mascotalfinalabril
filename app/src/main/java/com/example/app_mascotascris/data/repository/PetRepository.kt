package com.example.app_mascotascris.data.repository

import com.example.app_mascotascris.data.local.dao.PetDao
import com.example.app_mascotascris.data.local.dao.AdoptionFormDao
import com.example.app_mascotascris.data.local.dao.UserDao
import com.example.app_mascotascris.data.local.entities.PetEntity
import com.example.app_mascotascris.data.local.entities.AdoptionFormEntity
import com.example.app_mascotascris.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

class PetRepository(
    private val petDao: PetDao,
    private val adoptionFormDao: AdoptionFormDao,
    private val userDao: UserDao
) {
    val allPets: Flow<List<PetEntity>> = petDao.getAllPets()
    val allForms: Flow<List<AdoptionFormEntity>> = adoptionFormDao.getAllForms()

    fun getPetsByType(type: String): Flow<List<PetEntity>> {
        return if (type == "Todos") {
            petDao.getAllPets()
        } else {
            petDao.getPetsByEspecie(type)
        }
    }

    fun getPetById(id: Int): Flow<PetEntity?> {
        return petDao.getPetById(id)
    }

    suspend fun insertPet(pet: PetEntity) {
        petDao.insertPet(pet)
    }

    suspend fun deletePet(pet: PetEntity) {
        petDao.deletePet(pet)
    }

    suspend fun insertAdoptionForm(form: AdoptionFormEntity) {
        adoptionFormDao.insertForm(form)
    }

    // User Methods
    suspend fun registerUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return userDao.login(email, password)
    }
}

package com.example.app_mascotascris.data.local.dao

import androidx.room.*
import com.example.app_mascotascris.data.local.entities.PetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Query("SELECT * FROM pets")
    fun getAllPets(): Flow<List<PetEntity>>

    @Query("SELECT * FROM pets WHERE type = :type")
    fun getPetsByType(type: String): Flow<List<PetEntity>>

    @Query("SELECT * FROM pets WHERE id = :id")
    fun getPetById(id: Int): Flow<PetEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetEntity)

    @Delete
    suspend fun deletePet(pet: PetEntity)
}

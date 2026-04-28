package com.example.app_mascotascris.data.local.dao

import androidx.room.*
import com.example.app_mascotascris.data.local.entities.AdoptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AdoptionDao {
    @Query("SELECT * FROM adoptions")
    fun getAllAdoptions(): Flow<List<AdoptionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdoption(adoption: AdoptionEntity)

    @Delete
    suspend fun deleteAdoption(adoption: AdoptionEntity)
}

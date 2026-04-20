package com.example.app_mascotascris.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app_mascotascris.data.local.entities.AdoptionFormEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AdoptionFormDao {
    @Query("SELECT * FROM adoption_forms ORDER BY date DESC")
    fun getAllForms(): Flow<List<AdoptionFormEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForm(form: AdoptionFormEntity)
}

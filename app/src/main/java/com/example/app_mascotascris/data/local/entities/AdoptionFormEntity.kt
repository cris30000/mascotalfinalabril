package com.example.app_mascotascris.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adoption_forms")
data class AdoptionFormEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val petName: String,
    val applicantName: String,
    val phoneNumber: String,
    val reason: String,
    val housingType: String,
    val hasOtherPets: String,
    val date: Long = System.currentTimeMillis()
)

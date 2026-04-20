package com.example.app_mascotascris.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String, // Perro, Gato, Conejo, Ave
    val breed: String,
    val age: String,
    val description: String,
    val status: String, // Disponible, Adoptado, Rescatado
    val hasVaccines: Boolean = false,
    val shelter: String,
    val imageUrl: String? = null
)

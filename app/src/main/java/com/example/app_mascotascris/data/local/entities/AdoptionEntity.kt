package com.example.app_mascotascris.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adoptions")
data class AdoptionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fecha_adopcion: String,
    val estado: String, // Pendiente, Aprobada, Rechazada, Cerrada
    val razon_rechazo: String? = null,
    val fecha_cierre: String? = null,
    val solicitud_id: Int,
    val user_id: Int,
    val mascota_id: Int,
    val fundacion_id: Int,
    val administrador_id: Int? = null,
    val created_at: Long = System.currentTimeMillis(),
    val updated_at: Long = System.currentTimeMillis()
)

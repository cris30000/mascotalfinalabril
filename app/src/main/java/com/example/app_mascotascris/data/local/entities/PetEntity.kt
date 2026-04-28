package com.example.app_mascotascris.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre_mascota: String,
    val especie: String,
    val edad_aprox: String,
    val genero: String,
    val estado: String,
    val lugar_rescate: String,
    val descripcion: String,
    val condiciones_especiales: String?,
    val foto_principal: String?,
    val galeria_fotos: String?, // Lista de URIs separadas por comas
    val necesita_hogar_temporal: Boolean = false,
    val apto_con_ninos: Boolean = true,
    val apto_con_otros_animales: Boolean = true,
    val fecha_ingreso: String,
    val fecha_salida: String? = null,
    val fundacion_id: Int? = null,
    val created_at: Long = System.currentTimeMillis(),
    val updated_at: Long = System.currentTimeMillis()
)

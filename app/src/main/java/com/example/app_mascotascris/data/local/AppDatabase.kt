package com.example.app_mascotascris.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_mascotascris.data.local.dao.PetDao
import com.example.app_mascotascris.data.local.dao.AdoptionFormDao
import com.example.app_mascotascris.data.local.dao.UserDao
import com.example.app_mascotascris.data.local.entities.PetEntity
import com.example.app_mascotascris.data.local.entities.AdoptionFormEntity
import com.example.app_mascotascris.data.local.entities.AdoptionEntity
import com.example.app_mascotascris.data.local.entities.UserEntity

@Database(entities = [PetEntity::class, AdoptionFormEntity::class, AdoptionEntity::class, UserEntity::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun adoptionFormDao(): AdoptionFormDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mascotas_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

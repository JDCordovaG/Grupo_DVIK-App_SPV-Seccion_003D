package com.example.grupo_dvik_app_spv_seccion_003d.data.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.dao.RegistroDao
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity

@Database(
    entities = [RegistroEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun registroDao(): RegistroDao
}
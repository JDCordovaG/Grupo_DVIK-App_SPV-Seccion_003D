package com.example.grupo_dvik_app_spv_seccion_003d.data.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "registros")
data class Registro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String, // Ej: "DBT", "Estado de Ánimo", "Adicciones"
    val descripcion: String,
    val fecha: String
)
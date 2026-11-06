package com.example.grupo_dvik_app_spv_seccion_003d.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catalogo_habilidades_dbt")
data class HabilidadDbtEntity(
    @PrimaryKey(autoGenerate = true)
    val idHabilidad: Int = 0,
    val nombreHabilidad: String,
    val categoria: String,
    val descripcionEducativa: String
)

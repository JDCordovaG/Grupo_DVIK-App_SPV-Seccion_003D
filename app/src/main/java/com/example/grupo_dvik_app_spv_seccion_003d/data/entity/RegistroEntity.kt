package com.example.grupo_dvik_app_spv_seccion_003d.data.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "registros")
data class RegistroEntity(

    @PrimaryKey(autoGenerate = true)
    val idRegistro: Int = 0,

    val idUsuario: Int,

    val idAmbito: Int,

    val fechaHora: String,

    val estadoSincronizacion: String
)
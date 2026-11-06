
package com.example.grupo_dvik_app_spv_seccion_003d.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuarios",
    indices = [
        Index(
            value = ["nombreNormalizado"],
            unique = true
        )
    ]
)
data class UsuarioEntity(

    @PrimaryKey(autoGenerate = true)
    val idUsuario: Int = 0,

    val nombreUsuario: String,

    val nombreNormalizado: String,

    val contrasenaHash: String,

    val contrasenaSal: String,

    val fotoRuta: String? = null
)

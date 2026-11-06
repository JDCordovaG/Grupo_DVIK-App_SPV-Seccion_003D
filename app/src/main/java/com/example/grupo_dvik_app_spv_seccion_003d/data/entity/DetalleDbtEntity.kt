package com.example.grupo_dvik_app_spv_seccion_003d.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalle_dbt",
    foreignKeys = [
        ForeignKey(
            entity = RegistroEntity::class,
            parentColumns = ["idRegistro"],
            childColumns = ["idRegistro"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = HabilidadDbtEntity::class,
            parentColumns = ["idHabilidad"],
            childColumns = ["idHabilidad"]
        )
    ],
    indices = [
        Index(value = ["idHabilidad"])
    ]
)
data class DetalleDbtEntity(
    @PrimaryKey
    val idRegistro: Int,
    val idHabilidad: Int,
    val situacionEmocion: String,
    val nivelIntensidad: Int,
    val utilidadPercibida: String
)

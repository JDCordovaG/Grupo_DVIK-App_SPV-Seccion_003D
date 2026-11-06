package com.example.grupo_dvik_app_spv_seccion_003d.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalle_animo",
    foreignKeys = [
        ForeignKey(
            entity = RegistroEntity::class,
            parentColumns = ["idRegistro"],
            childColumns = ["idRegistro"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DetalleAnimoEntity(
    @PrimaryKey
    val idRegistro: Int,
    val categoriaAnimo: String,
    val nivelIntensidad: Int,
    val contexto: String,
    val notaOpcional: String? = null
)

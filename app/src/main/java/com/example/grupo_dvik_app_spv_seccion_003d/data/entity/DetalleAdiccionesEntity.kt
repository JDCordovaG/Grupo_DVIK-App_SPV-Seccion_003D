package com.example.grupo_dvik_app_spv_seccion_003d.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalle_adicciones",
    foreignKeys = [
        ForeignKey(
            entity = RegistroEntity::class,
            parentColumns = ["idRegistro"],
            childColumns = ["idRegistro"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DetalleAdiccionesEntity(
    @PrimaryKey
    val idRegistro: Int,
    val nivelImpulsoCraving: Int,
    val desencadenante: String,
    val accionProtectora: String,
    val estadoObjetivo: String
)

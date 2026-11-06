
package com.example.grupo_dvik_app_spv_seccion_003d.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalle_neurodesarrollo",
    foreignKeys = [
        ForeignKey(
            entity = RegistroEntity::class,
            parentColumns = ["idRegistro"],
            childColumns = ["idRegistro"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DetalleNeurodesarrolloEntity(
    @PrimaryKey
    val idRegistro: Int,
    val rutinaTarea: String,
    val contexto: String,
    val estrategiaUtilizada: String,
    val resultadoPercibido: String
)

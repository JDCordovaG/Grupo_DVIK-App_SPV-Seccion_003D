package com.example.grupo_dvik_app_spv_seccion_003d.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleNeurodesarrolloEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleAnimoEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleAdiccionesEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.HabilidadDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity

@Dao
interface DetalleRegistroDao {

    @Insert
    suspend fun insertarRegistro(
        registro: RegistroEntity
    ): Long

    // Neurodesarrollo

    @Insert
    suspend fun insertarNeurodesarrollo(
        detalle: DetalleNeurodesarrolloEntity
    )

    @Update
    suspend fun actualizarNeurodesarrollo(
        detalle: DetalleNeurodesarrolloEntity
    )

    @Query(
        "SELECT * FROM detalle_neurodesarrollo WHERE idRegistro = :idRegistro"
    )
    suspend fun obtenerNeurodesarrollo(
        idRegistro: Int
    ): DetalleNeurodesarrolloEntity?

    // DBT

    @Insert
    suspend fun insertarDbt(
        detalle: DetalleDbtEntity
    )

    @Update
    suspend fun actualizarDbt(
        detalle: DetalleDbtEntity
    )

    @Query(
        "SELECT * FROM detalle_dbt WHERE idRegistro = :idRegistro"
    )
    suspend fun obtenerDbt(
        idRegistro: Int
    ): DetalleDbtEntity?

    // Estado de animo

    @Insert
    suspend fun insertarAnimo(
        detalle: DetalleAnimoEntity
    )

    @Update
    suspend fun actualizarAnimo(
        detalle: DetalleAnimoEntity
    )

    @Query(
        "SELECT * FROM detalle_animo WHERE idRegistro = :idRegistro"
    )
    suspend fun obtenerAnimo(
        idRegistro: Int
    ): DetalleAnimoEntity?

    // Adicciones e impulsos

    @Insert
    suspend fun insertarAdicciones(
        detalle: DetalleAdiccionesEntity
    )

    @Update
    suspend fun actualizarAdicciones(
        detalle: DetalleAdiccionesEntity
    )

    @Query(
        "SELECT * FROM detalle_adicciones WHERE idRegistro = :idRegistro"
    )
    suspend fun obtenerAdicciones(
        idRegistro: Int
    ): DetalleAdiccionesEntity?

    // Catalogo de habilidades DBT

    @Insert
    suspend fun insertarHabilidad(
        habilidad: HabilidadDbtEntity
    )

    @Query(
        "SELECT * FROM catalogo_habilidades_dbt ORDER BY nombreHabilidad"
    )
    suspend fun obtenerHabilidades():
            List<HabilidadDbtEntity>

    // Guardar registro y detalle juntos

    @Transaction
    suspend fun crearRegistroAnimo(
        registro: RegistroEntity,
        detalle: DetalleAnimoEntity
    ) {

        val nuevoId = insertarRegistro(
            registro
        )

        insertarAnimo(
            detalle.copy(
                idRegistro = nuevoId.toInt()
            )
        )
    }

    @Transaction
    suspend fun crearRegistroNeurodesarrollo(
        registro: RegistroEntity,
        detalle: DetalleNeurodesarrolloEntity
    ) {

        val nuevoId = insertarRegistro(
            registro
        )

        insertarNeurodesarrollo(
            detalle.copy(
                idRegistro = nuevoId.toInt()
            )
        )
    }

    @Transaction
    suspend fun crearRegistroDbt(
        registro: RegistroEntity,
        detalle: DetalleDbtEntity
    ) {

        val nuevoId = insertarRegistro(
            registro
        )

        insertarDbt(
            detalle.copy(
                idRegistro = nuevoId.toInt()
            )
        )
    }

    @Transaction
    suspend fun crearRegistroAdicciones(
        registro: RegistroEntity,
        detalle: DetalleAdiccionesEntity
    ) {

        val nuevoId = insertarRegistro(
            registro
        )

        insertarAdicciones(
            detalle.copy(
                idRegistro = nuevoId.toInt()
            )
        )
    }
}

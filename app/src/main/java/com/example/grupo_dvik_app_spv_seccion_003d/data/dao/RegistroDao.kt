package com.example.grupo_dvik_app_spv_seccion_003d.data.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistroDao {

    @Query("SELECT * FROM registros ORDER BY idRegistro DESC")
    fun observarTodos(): Flow<List<RegistroEntity>>

    @Insert
    suspend fun insertar(
        registro: RegistroEntity
    )

    @Update
    suspend fun actualizar(
        registro: RegistroEntity
    )

    @Delete
    suspend fun eliminar(
        registro: RegistroEntity
    )

    @Query(
        "SELECT * FROM registros WHERE idRegistro = :id LIMIT 1"
    )
    suspend fun obtenerPorId(
        id: Int
    ): RegistroEntity?
}
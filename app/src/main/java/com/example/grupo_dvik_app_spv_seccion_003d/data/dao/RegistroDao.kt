package com.example.grupo_dvik_app_spv_seccion_003d.data.dao

import androidx.room3.*
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.Registro
import kotlinx.coroutines.flow.Flow


@Dao
interface RegistroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(registro: Registro)

    @Query("SELECT * FROM registros ORDER BY id DESC")
    fun obtenerTodos(): Flow<List<Registro>>

    @Update
    suspend fun actualizar(registro: Registro)

    @Delete
    suspend fun eliminar(registro: Registro)
}
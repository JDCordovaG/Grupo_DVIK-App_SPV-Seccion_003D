
package com.example.grupo_dvik_app_spv_seccion_003d.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistroDao {

    @Query(
        "SELECT * FROM registros ORDER BY idRegistro DESC"
    )
    fun observarTodos(): Flow<List<RegistroEntity>>

    @Query(
        """
        SELECT * FROM registros
        WHERE idUsuario = :idUsuario
        ORDER BY idRegistro DESC
        """
    )
    fun observarPorUsuario(
        idUsuario: Int
    ): Flow<List<RegistroEntity>>

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
        """
        UPDATE registros
        SET estadoSincronizacion = :nuevoEstado
        WHERE idRegistro = :idRegistro
        AND idUsuario = :idUsuario
        """
    )
    suspend fun actualizarEstadoDelUsuario(
        idRegistro: Int,
        idUsuario: Int,
        nuevoEstado: String
    ): Int

    @Query(
        """
        DELETE FROM registros
        WHERE idRegistro = :idRegistro
        AND idUsuario = :idUsuario
        """
    )
    suspend fun eliminarDelUsuario(
        idRegistro: Int,
        idUsuario: Int
    ): Int

    @Query(
        """
        SELECT * FROM registros
        WHERE idRegistro = :id
        LIMIT 1
        """
    )
    suspend fun obtenerPorId(
        id: Int
    ): RegistroEntity?

    @Query(
        "SELECT COUNT(*) FROM registros"
    )
    suspend fun contar(): Int
}

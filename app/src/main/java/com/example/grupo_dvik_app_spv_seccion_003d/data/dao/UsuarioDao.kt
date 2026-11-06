
package com.example.grupo_dvik_app_spv_seccion_003d.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun insertar(
        usuario: UsuarioEntity
    ): Long

    @Query(
        "SELECT * FROM usuarios WHERE nombreNormalizado = :nombre LIMIT 1"
    )
    suspend fun obtenerPorNombre(
        nombre: String
    ): UsuarioEntity?

    @Query(
        "SELECT * FROM usuarios WHERE idUsuario = :id LIMIT 1"
    )
    suspend fun obtenerPorId(
        id: Int
    ): UsuarioEntity?

    @Query(
        """
        SELECT COALESCE(MAX(idUsuario), 0)
        FROM (
            SELECT idUsuario FROM registros
            UNION ALL
            SELECT idUsuario FROM usuarios
        )
        """
    )
    suspend fun obtenerMaximoIdUsado(): Int

    @Query(
        """
        UPDATE usuarios
        SET fotoRuta = :ruta
        WHERE idUsuario = :idUsuario
        """
    )
    suspend fun actualizarFotoRuta(
        idUsuario: Int,
        ruta: String
    ): Int

    @Transaction
    suspend fun insertarNuevo(
        usuario: UsuarioEntity
    ): Long {

        val existente = obtenerPorNombre(
            usuario.nombreNormalizado
        )

        if (existente != null) {
            return -1L
        }

        val nuevoId =
            obtenerMaximoIdUsado() + 1

        return insertar(
            usuario.copy(
                idUsuario = nuevoId
            )
        )
    }
}

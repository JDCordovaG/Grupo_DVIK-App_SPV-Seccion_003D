
package com.example.grupo_dvik_app_spv_seccion_003d.repository

import android.content.Context
import androidx.room.Room
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.AppDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.MIGRACION_1_2
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import kotlinx.coroutines.flow.Flow
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.MIGRACION_2_3

class RegistroRepository(
    context: Context
) {

    private val database =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "mente_en_equilibrio.db"
        )
            .addMigrations(MIGRACION_1_2, MIGRACION_2_3)
            .build()

    private val dao =
        database.registroDao()

    val registros =
        dao.observarTodos()

    fun observarPorUsuario(
        idUsuario: Int
    ): Flow<List<RegistroEntity>> {

        return dao.observarPorUsuario(
            idUsuario
        )
    }

    suspend fun insertar(
        registro: RegistroEntity
    ) {

        dao.insertar(registro)
    }

    suspend fun actualizar(
        registro: RegistroEntity
    ) {

        dao.actualizar(registro)
    }

    suspend fun eliminar(
        registro: RegistroEntity
    ) {

        dao.eliminar(registro)
    }

    suspend fun actualizarEstadoDelUsuario(
        idRegistro: Int,
        idUsuario: Int,
        nuevoEstado: String
    ): Boolean {

        val cantidad =
            dao.actualizarEstadoDelUsuario(
                idRegistro,
                idUsuario,
                nuevoEstado
            )

        return cantidad > 0
    }

    suspend fun eliminarDelUsuario(
        idRegistro: Int,
        idUsuario: Int
    ): Boolean {

        val cantidad =
            dao.eliminarDelUsuario(
                idRegistro,
                idUsuario
            )

        return cantidad > 0
    }

    suspend fun obtener(
        id: Int
    ): RegistroEntity? {

        return dao.obtenerPorId(id)
    }

    suspend fun contar(): Int {

        return dao.contar()
    }
}
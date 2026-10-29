package com.example.grupo_dvik_app_spv_seccion_003d.repository

import android.content.Context
import androidx.room.Room
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.AppDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity

class RegistroRepository(
    context: Context
) {

    private val database =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "mente_en_equilibrio.db"
        ).build()

    private val dao =
        database.registroDao()

    val registros =
        dao.observarTodos()

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

    suspend fun obtener(
        id: Int
    ): RegistroEntity? {

        return dao.obtenerPorId(id)
    }

    suspend fun contar(): Int {

        return dao.contar()
    }
}
package com.example.grupo_dvik_app_spv_seccion_003d.repository

import com.example.grupo_dvik_app_spv_seccion_003d.data.dao.RegistroDao
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity

class RegistroRepository(
    private val dao: RegistroDao
) {

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
}
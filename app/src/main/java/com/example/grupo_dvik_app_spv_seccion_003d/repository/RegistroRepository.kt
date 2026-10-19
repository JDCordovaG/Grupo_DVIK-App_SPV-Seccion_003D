package com.example.grupo_dvik_app_spv_seccion_003d.repository

import com.example.grupo_dvik_app_spv_seccion_003d.data.dao.RegistroDao
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.Registro
import kotlinx.coroutines.flow.Flow

class RegistroRepository(private val registroDao: RegistroDao) {
    val todosLosRegistros: Flow<List<Registro>> = registroDao.obtenerTodos()

    suspend fun insertar(registro: Registro) {
        registroDao.insertar(registro)
    }

    suspend fun actualizar(registro: Registro) {
        registroDao.actualizar(registro)
    }

    suspend fun eliminar(registro: Registro) {
        registroDao.eliminar(registro)
    }
}
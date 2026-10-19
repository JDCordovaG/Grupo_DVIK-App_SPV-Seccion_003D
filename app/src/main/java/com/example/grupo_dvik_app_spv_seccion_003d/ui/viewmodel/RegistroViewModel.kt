package com.example.grupo_dvik_app_spv_seccion_003d.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import com.example.grupo_dvik_app_spv_seccion_003d.repository.RegistroRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val repository: RegistroRepository
) : ViewModel() {

    val registros: StateFlow<List<RegistroEntity>> =
        repository.registros
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun insertarRegistro(
        registro: RegistroEntity
    ) {

        viewModelScope.launch {

            repository.insertar(
                registro
            )
        }
    }

    fun actualizarRegistro(
        registro: RegistroEntity
    ) {

        viewModelScope.launch {

            repository.actualizar(
                registro
            )
        }
    }

    fun eliminarRegistro(
        registro: RegistroEntity
    ) {

        viewModelScope.launch {

            repository.eliminar(
                registro
            )
        }
    }

    fun obtenerRegistro(
        id: Int
    ) {

        viewModelScope.launch {

            repository.obtener(id)
        }
    }
}
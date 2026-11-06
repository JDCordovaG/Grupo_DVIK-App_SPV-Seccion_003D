package com.example.grupo_dvik_app_spv_seccion_003d.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import com.example.grupo_dvik_app_spv_seccion_003d.repository.RegistroRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class InicioResumenViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        RegistroRepository(application)

    private val _registros =
        MutableStateFlow<List<RegistroEntity>>(emptyList())

    val registros: StateFlow<List<RegistroEntity>> =
        _registros

    private var observacion: Job? = null

    fun cargarRegistros(idUsuario: Int) {

        observacion?.cancel()

        _registros.value = emptyList()

        if (idUsuario <= 0) {
            return
        }

        observacion = viewModelScope.launch {

            repository.observarPorUsuario(idUsuario)
                .collect { lista ->

                    _registros.value =
                        lista.filter {
                            it.idUsuario == idUsuario
                        }
                }
        }
    }
}

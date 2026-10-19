package com.example.grupo_dvik_app_spv_seccion_003d.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.Registro
import com.example.grupo_dvik_app_spv_seccion_003d.repository.RegistroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RegistroViewModel(private val repository: RegistroRepository) : ViewModel() {

    private val _registros = MutableStateFlow<List<Registro>>(emptyList())
    val registros: StateFlow<List<Registro>> = _registros

    init {
        viewModelScope.launch {
            repository.todosLosRegistros.collect { lista ->
                _registros.value = lista
            }
        }
    }

    fun guardarRegistro(tipo: String, descripcion: String, fecha: String) {
        if (validarCampos(tipo, descripcion, fecha)) {
            viewModelScope.launch {
                repository.insertar(Registro(tipo = tipo, descripcion = descripcion, fecha = fecha))
            }
        }
    }

    fun actualizarRegistro(registro: Registro) {
        if (validarCampos(registro.tipo, registro.descripcion, registro.fecha)) {
            viewModelScope.launch {
                repository.actualizar(registro)
            }
        }
    }

    fun eliminarRegistro(registro: Registro) {
        viewModelScope.launch {
            repository.eliminar(registro)
        }
    }

    // Validaciones requeridas
    private fun validarCampos(tipo: String, descripcion: String, fecha: String): Boolean {
        return tipo.isNotBlank() && descripcion.isNotBlank() && fecha.isNotBlank()
    }
}
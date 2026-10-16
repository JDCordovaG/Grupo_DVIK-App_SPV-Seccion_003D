package com.example.grupo_dvik_app_spv_seccion_003d.viewmodel

import androidx.lifecycle.ViewModel
import com.example.grupo_dvik_app_spv_seccion_003d.validation.Validaciones

class LoginViewModel : ViewModel() {

    private val validaciones = Validaciones()

    fun validarLogin(
        nombre: String,
        contrasena: String
    ): String {

        if (!validaciones.validarNombre(nombre)) {
            return "El nombre es obligatorio"
        }

        if (!validaciones.validarContrasena(contrasena)) {
            return "La contraseña debe tener mínimo 8 caracteres, una mayúscula y una minúscula"
        }

        return "OK"
    }
}

package com.example.grupo_dvik_app_spv_seccion_003d.viewmodel

import androidx.lifecycle.ViewModel
import com.example.grupo_dvik_app_spv_seccion_003d.validation.Validaciones

class LoginViewModel : ViewModel() {

    private val validaciones = Validaciones()

    fun validarLogin(
        nombre: String,
        contrasena: String
    ): String {

        if (nombre.isBlank()) {
            return "El usuario es obligatorio"
        }

        if (nombre.any { it.isWhitespace() }) {
            return "El usuario no debe contener espacios"
        }

        if (nombre.contains(".")) {
            return "El usuario no debe contener puntos (.)"
        }

        if (nombre.contains("-")) {
            return "El usuario no debe contener guiones (-)"
        }

        if (!validaciones.validarNombre(nombre)) {
            return "El usuario solo puede contener letras y números"
        }

        if (!validaciones.validarContrasena(contrasena)) {
            return "La contraseña debe tener mínimo 8 caracteres, una mayúscula y una minúscula"
        }

        return "OK"
    }
}

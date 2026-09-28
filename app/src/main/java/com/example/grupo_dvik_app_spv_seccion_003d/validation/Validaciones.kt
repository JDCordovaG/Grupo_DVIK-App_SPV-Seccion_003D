package com.example.grupo_dvik_app_spv_seccion_003d.validation

class Validaciones {

    fun validarNombre(nombre: String): Boolean {
        return nombre.isNotBlank()
    }

    fun validarContrasena(contrasena: String): Boolean {

        if (contrasena.length < 8) {
            return false
        }

        val tieneMayuscula = contrasena.any {
            it.isUpperCase()
        }

        val tieneMinuscula = contrasena.any {
            it.isLowerCase()
        }

        return tieneMayuscula && tieneMinuscula
    }
}
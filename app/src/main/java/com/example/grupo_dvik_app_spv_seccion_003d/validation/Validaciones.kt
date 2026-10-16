package com.example.grupo_dvik_app_spv_seccion_003d.validation

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Locale

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

    fun validarCampo(campo: String): Boolean {
        return campo.isNotBlank()
    }

    fun validarNumero(numero: String): Boolean {

        val valor = numero.toIntOrNull()

        return valor != null && valor > 0
    }

    fun validarFechaHora(fechaHora: String): Boolean {

        val formatoTexto =
            Regex("""\d{2}/\d{2}/\d{4} \d{2}:\d{2}""")

        if (!formatoTexto.matches(fechaHora)) {
            return false
        }

        val formato = SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        )

        formato.isLenient = false

        val posicion = ParsePosition(0)

        val fecha = formato.parse(
            fechaHora,
            posicion
        )

        return fecha != null &&
                posicion.index == fechaHora.length
    }
}
package com.example.grupo_dvik_app_spv_seccion_003d.data

import android.content.Context
import com.example.grupo_dvik_app_spv_seccion_003d.model.Registro

class RegistroRepository(context: Context) {

    private val preferencias = context.getSharedPreferences(
        "registros_app",
        Context.MODE_PRIVATE
    )

    fun guardarRegistros(registros: List<Registro>) {

        val texto = registros.joinToString(";") { registro ->
            "${registro.idRegistro}|" +
                    "${registro.idUsuario}|" +
                    "${registro.idAmbito}|" +
                    "${registro.fechaHora}|" +
                    registro.estadoSincronizacion
        }

        preferencias.edit()
            .putString("registros", texto)
            .apply()
    }

    fun cargarRegistros(): List<Registro> {

        val texto = preferencias.getString(
            "registros",
            ""
        )

        if (texto.isNullOrEmpty()) {
            return emptyList()
        }

        return texto.split(";").mapNotNull { fila ->

            val datos = fila.split("|")

            if (datos.size == 5) {

                Registro(
                    idRegistro = datos[0].toInt(),
                    idUsuario = datos[1].toInt(),
                    idAmbito = datos[2].toInt(),
                    fechaHora = datos[3],
                    estadoSincronizacion = datos[4]
                )

            } else {
                null
            }
        }
    }
}
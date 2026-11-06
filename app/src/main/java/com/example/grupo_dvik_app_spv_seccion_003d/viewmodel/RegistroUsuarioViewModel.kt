
package com.example.grupo_dvik_app_spv_seccion_003d.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.AppDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.MIGRACION_1_2
import com.example.grupo_dvik_app_spv_seccion_003d.repository.UsuarioRepository
import com.example.grupo_dvik_app_spv_seccion_003d.validation.Validaciones
import kotlinx.coroutines.launch
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.MIGRACION_2_3

class RegistroUsuarioViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "mente_en_equilibrio.db"
        )
            .addMigrations(MIGRACION_1_2, MIGRACION_2_3)
            .build()

    private val repository =
        UsuarioRepository(
            database.usuarioDao()
        )

    private val validaciones =
        Validaciones()

    var mensaje by mutableStateOf("")
        private set

    var registroCorrecto by mutableStateOf(false)
        private set

    var registrando by mutableStateOf(false)
        private set

    fun registrarUsuario(
        nombre: String,
        contrasena: String,
        confirmarContrasena: String
    ) {

        if (registrando) {
            return
        }

        registroCorrecto = false

        if (nombre.isBlank()) {
            mensaje = "El usuario es obligatorio"
            return
        }

        if (nombre.any { it.isWhitespace() }) {
            mensaje = "El usuario no debe contener espacios"
            return
        }

        if (nombre.contains(".")) {
            mensaje = "El usuario no debe contener puntos (.)"
            return
        }

        if (nombre.contains("-")) {
            mensaje = "El usuario no debe contener guiones (-)"
            return
        }

        if (!validaciones.validarNombre(nombre)) {
            mensaje = "El usuario solo puede contener letras y números"
            return
        }

        if (!validaciones.validarContrasena(contrasena)) {
            mensaje =
                "La contraseña debe tener mínimo 8 caracteres, una mayúscula y una minúscula"
            return
        }

        if (contrasena != confirmarContrasena) {
            mensaje = "Las contraseñas no coinciden"
            return
        }

        registrando = true
        mensaje = ""

        viewModelScope.launch {

            try {

                val existente =
                    repository.obtenerPorNombre(nombre)

                if (existente != null) {

                    mensaje =
                        "El nombre de usuario ya está registrado"

                } else {

                    val creado =
                        repository.registrarUsuario(
                            nombre,
                            contrasena
                        )

                    if (creado) {

                        mensaje =
                            "Usuario creado correctamente"

                        registroCorrecto = true

                    } else {

                        mensaje =
                            "El nombre de usuario ya está registrado"
                    }
                }

            } catch (e: Exception) {

                mensaje =
                    "No se pudo registrar el usuario"

            } finally {

                registrando = false
            }
        }
    }

    fun limpiarMensaje() {
        mensaje = ""
        registroCorrecto = false
    }
}

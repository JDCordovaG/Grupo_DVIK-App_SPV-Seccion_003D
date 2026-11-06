
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
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.UsuarioEntity
import com.example.grupo_dvik_app_spv_seccion_003d.repository.UsuarioRepository
import com.example.grupo_dvik_app_spv_seccion_003d.validation.Validaciones
import kotlinx.coroutines.launch
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.MIGRACION_2_3

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val validaciones =
        Validaciones()

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

    var mensaje by mutableStateOf("")
        private set

    var ingresando by mutableStateOf(false)
        private set

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

    fun iniciarSesion(
        nombre: String,
        contrasena: String,
        onLoginCorrecto: (UsuarioEntity) -> Unit
    ) {

        if (ingresando) {
            return
        }

        val resultado = validarLogin(
            nombre,
            contrasena
        )

        if (resultado != "OK") {
            mensaje = resultado
            return
        }

        ingresando = true
        mensaje = ""

        viewModelScope.launch {

            try {

                val usuario =
                    repository.autenticarUsuario(
                        nombre,
                        contrasena
                    )

                if (usuario != null) {

                    mensaje = ""

                    onLoginCorrecto(usuario)

                } else {

                    mensaje =
                        "Usuario o contraseña incorrectos"
                }

            } catch (e: Exception) {

                mensaje =
                    "No se pudo iniciar sesión"

            } finally {

                ingresando = false
            }
        }
    }

    fun limpiarMensaje() {
        mensaje = ""
    }
}

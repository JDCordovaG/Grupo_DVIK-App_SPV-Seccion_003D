
package com.example.grupo_dvik_app_spv_seccion_003d.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import com.example.grupo_dvik_app_spv_seccion_003d.model.Registro
import com.example.grupo_dvik_app_spv_seccion_003d.repository.RegistroRepository
import com.example.grupo_dvik_app_spv_seccion_003d.validation.Validaciones
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AdminViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        RegistroRepository(application)

    private val validaciones =
        Validaciones()

    private val _registros =
        MutableStateFlow<List<Registro>>(emptyList())

    val registros: StateFlow<List<Registro>> =
        _registros

    private var usuarioActual = 0

    private var trabajoObservacion: Job? = null

    var fotoPerfil by
    mutableStateOf<Bitmap?>(null)
        private set

    var mensaje by
    mutableStateOf("")
        private set

    fun seleccionarUsuario(
        idUsuario: Int
    ) {

        if (idUsuario <= 0) {
            return
        }

        if (usuarioActual == idUsuario) {
            return
        }

        trabajoObservacion?.cancel()

        usuarioActual = idUsuario

        _registros.value = emptyList()

        trabajoObservacion =
            viewModelScope.launch {

                repository
                    .observarPorUsuario(idUsuario)
                    .collect { lista ->

                        _registros.value =
                            lista.map { registro ->

                                Registro(
                                    idRegistro =
                                        registro.idRegistro,
                                    idUsuario =
                                        registro.idUsuario,
                                    idAmbito =
                                        registro.idAmbito,
                                    fechaHora =
                                        registro.fechaHora,
                                    estadoSincronizacion =
                                        registro.estadoSincronizacion
                                )
                            }
                    }
            }
    }

    fun crearRegistro(
        idAmbitoTexto: String,
        fechaHora: String,
        estado: String
    ): Boolean {

        val idActual = usuarioActual

        if (idActual <= 0) {

            mensaje =
                "Debe iniciar sesión para crear registros"

            return false
        }

        if (
            !validaciones.validarAmbito(
                idAmbitoTexto
            )
        ) {

            mensaje =
                "Debe seleccionar un ámbito válido"

            return false
        }

        if (
            !validaciones.validarCampo(
                fechaHora
            )
        ) {

            mensaje =
                "La fecha y hora son obligatorias"

            return false
        }

        if (
            !validaciones.validarFechaHora(
                fechaHora
            )
        ) {

            mensaje =
                "Ingrese una fecha y hora valida"

            return false
        }

        if (
            !validaciones.validarFechaActualOFutura(
                fechaHora
            )
        ) {

            mensaje =
                "La fecha y hora no pueden ser anteriores al momento actual"

            return false
        }

        if (
            !validaciones.validarEstado(
                estado
            )
        ) {

            mensaje =
                "Seleccione un estado válido"

            return false
        }

        val nuevoRegistro =
            RegistroEntity(
                idUsuario = idActual,
                idAmbito =
                    idAmbitoTexto.toInt(),
                fechaHora = fechaHora,
                estadoSincronizacion = estado
            )

        viewModelScope.launch {

            repository.insertar(
                nuevoRegistro
            )

            mensaje =
                "Registro agregado correctamente"
        }

        return true
    }

    fun eliminarRegistro(
        registro: Registro
    ) {

        val idActual = usuarioActual

        if (
            idActual <= 0 ||
            registro.idUsuario != idActual
        ) {

            mensaje =
                "No puede eliminar un registro de otra cuenta"

            return
        }

        viewModelScope.launch {

            val eliminado =
                repository.eliminarDelUsuario(
                    registro.idRegistro,
                    idActual
                )

            mensaje =
                if (eliminado) {
                    "Registro eliminado correctamente"
                } else {
                    "No se pudo eliminar el registro"
                }
        }
    }

    fun modificarRegistro(
        registro: Registro,
        nuevoEstado: String
    ): Boolean {

        val idActual = usuarioActual

        if (
            idActual <= 0 ||
            registro.idUsuario != idActual
        ) {

            mensaje =
                "No puede modificar un registro de otra cuenta"

            return false
        }

        if (
            !validaciones.validarEstado(
                nuevoEstado
            )
        ) {

            mensaje =
                "Seleccione un estado válido"

            return false
        }

        viewModelScope.launch {

            val modificado =
                repository.actualizarEstadoDelUsuario(
                    registro.idRegistro,
                    idActual,
                    nuevoEstado
                )

            mensaje =
                if (modificado) {
                    "Registro actualizado correctamente"
                } else {
                    "No se pudo actualizar el registro"
                }
        }

        return true
    }

    fun actualizarFoto(
        bitmap: Bitmap?
    ) {

        if (bitmap != null) {

            fotoPerfil = bitmap

            mensaje =
                "Foto de perfil actualizada correctamente"

        } else {

            mensaje =
                "No se pudo obtener la fotografía"
        }
    }

    fun limpiarMensaje() {

        mensaje = ""
    }
}

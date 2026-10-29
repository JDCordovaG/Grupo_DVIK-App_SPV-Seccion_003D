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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AdminViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        RegistroRepository(application)

    private val validaciones =
        Validaciones()

    val registros: StateFlow<List<Registro>> =
        repository.registros
            .map { lista ->

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
            .stateIn(
                scope = viewModelScope,
                started =
                    SharingStarted.WhileSubscribed(
                        5_000
                    ),
                initialValue = emptyList()
            )

    var fotoPerfil by
    mutableStateOf<Bitmap?>(null)
        private set

    var mensaje by
    mutableStateOf("")
        private set

    init {

        viewModelScope.launch {

            if (repository.contar() == 0) {

                repository.insertar(
                    RegistroEntity(
                        idUsuario = 1,
                        idAmbito = 1,
                        fechaHora =
                            "28/09/2026 10:30",
                        estadoSincronizacion =
                            "Sincronizado"
                    )
                )

                repository.insertar(
                    RegistroEntity(
                        idUsuario = 1,
                        idAmbito = 2,
                        fechaHora =
                            "28/09/2026 11:15",
                        estadoSincronizacion =
                            "Pendiente"
                    )
                )

                repository.insertar(
                    RegistroEntity(
                        idUsuario = 2,
                        idAmbito = 3,
                        fechaHora =
                            "28/09/2026 12:00",
                        estadoSincronizacion =
                            "Sincronizado"
                    )
                )
            }
        }
    }

    fun crearRegistro(
        idUsuarioTexto: String,
        idAmbitoTexto: String,
        fechaHora: String,
        estado: String
    ): Boolean {

        if (
            !validaciones.validarNumero(
                idUsuarioTexto
            )
        ) {

            mensaje =
                "Debe ingresar un usuario válido"

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
                "La fecha y hora deben tener formato dd/MM/yyyy HH:mm"

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
                idUsuario =
                    idUsuarioTexto.toInt(),
                idAmbito =
                    idAmbitoTexto.toInt(),
                fechaHora =
                    fechaHora,
                estadoSincronizacion =
                    estado
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

        viewModelScope.launch {

            repository.eliminar(
                RegistroEntity(
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
            )

            mensaje =
                "Registro eliminado correctamente"
        }
    }

    fun modificarRegistro(
        registro: Registro,
        nuevoEstado: String
    ): Boolean {

        if (
            !validaciones.validarEstado(
                nuevoEstado
            )
        ) {

            mensaje =
                "Seleccione un estado válido"

            return false
        }

        val registroActualizado =
            RegistroEntity(
                idRegistro =
                    registro.idRegistro,
                idUsuario =
                    registro.idUsuario,
                idAmbito =
                    registro.idAmbito,
                fechaHora =
                    registro.fechaHora,
                estadoSincronizacion =
                    nuevoEstado
            )

        viewModelScope.launch {

            repository.actualizar(
                registroActualizado
            )

            mensaje =
                "Registro actualizado correctamente"
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
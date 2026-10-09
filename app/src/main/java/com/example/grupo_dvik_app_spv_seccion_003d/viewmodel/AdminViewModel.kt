package com.example.grupo_dvik_app_spv_seccion_003d.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.grupo_dvik_app_spv_seccion_003d.data.RegistroRepository
import com.example.grupo_dvik_app_spv_seccion_003d.model.Registro
import com.example.grupo_dvik_app_spv_seccion_003d.validation.Validaciones

class AdminViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = RegistroRepository(application)

    private val validaciones = Validaciones()

    val registros = mutableStateListOf<Registro>()

    var mensaje by mutableStateOf("")
        private set

    init {

        val registrosGuardados = repository.cargarRegistros()

        if (registrosGuardados.isNotEmpty()) {

            registros.addAll(registrosGuardados)

        } else {

            registros.addAll(
                listOf(
                    Registro(
                        idRegistro = 1,
                        idUsuario = 1,
                        idAmbito = 1,
                        fechaHora = "28/09/2026 10:30",
                        estadoSincronizacion = "Sincronizado"
                    ),
                    Registro(
                        idRegistro = 2,
                        idUsuario = 1,
                        idAmbito = 2,
                        fechaHora = "28/09/2026 11:15",
                        estadoSincronizacion = "Pendiente"
                    ),
                    Registro(
                        idRegistro = 3,
                        idUsuario = 2,
                        idAmbito = 3,
                        fechaHora = "28/09/2026 12:00",
                        estadoSincronizacion = "Sincronizado"
                    )
                )
            )

            guardar()
        }
    }

    fun crearRegistro(
        idUsuarioTexto: String,
        idAmbitoTexto: String,
        fechaHora: String,
        estado: String
    ): Boolean {

        if (!validaciones.validarNumero(idUsuarioTexto)) {
            mensaje = "Debe ingresar un usuario válido"
            return false
        }

        if (!validaciones.validarNumero(idAmbitoTexto)) {
            mensaje = "Debe ingresar un ámbito válido"
            return false
        }

        if (!validaciones.validarCampo(fechaHora)) {
            mensaje = "La fecha y hora son obligatorias"
            return false
        }

        if (!validaciones.validarCampo(estado)) {
            mensaje = "El estado es obligatorio"
            return false
        }

        val nuevoRegistro = Registro(
            idRegistro = obtenerSiguienteId(),
            idUsuario = idUsuarioTexto.toInt(),
            idAmbito = idAmbitoTexto.toInt(),
            fechaHora = fechaHora,
            estadoSincronizacion = estado
        )

        registros.add(nuevoRegistro)

        guardar()

        mensaje = "Registro agregado correctamente"

        return true
    }

    fun eliminarRegistro(registro: Registro) {

        registros.remove(registro)

        guardar()

        mensaje = "Registro eliminado correctamente"
    }

    fun modificarRegistro(
        registro: Registro,
        nuevoEstado: String
    ): Boolean {

        if (!validaciones.validarCampo(nuevoEstado)) {
            mensaje = "El estado no puede estar vacío"
            return false
        }

        val posicion = registros.indexOf(registro)

        if (posicion == -1) {
            mensaje = "No se encontró el registro"
            return false
        }

        registros[posicion] = registro.copy(
            estadoSincronizacion = nuevoEstado
        )

        guardar()

        mensaje = "Registro actualizado correctamente"

        return true
    }

    private fun obtenerSiguienteId(): Int {

        var mayorId = 0

        for (registro in registros) {

            if (registro.idRegistro > mayorId) {
                mayorId = registro.idRegistro
            }
        }

        return mayorId + 1
    }

    private fun guardar() {

        repository.guardarRegistros(
            registros
        )
    }

    fun limpiarMensaje() {
        mensaje = ""
    }
}
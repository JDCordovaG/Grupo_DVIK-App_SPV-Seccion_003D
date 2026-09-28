package com.example.grupo_dvik_app_spv_seccion_003d.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.grupo_dvik_app_spv_seccion_003d.model.Registro

class AdminViewModel : ViewModel() {

    val registros = mutableStateListOf(
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

    fun eliminarRegistro(registro: Registro) {
        registros.remove(registro)
    }

    fun modificarRegistro(
        registro: Registro,
        nuevoEstado: String
    ) {

        val posicion = registros.indexOf(registro)

        if (posicion != -1) {

            registros[posicion] = registro.copy(
                estadoSincronizacion = nuevoEstado
            )
        }
    }
}
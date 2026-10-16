package com.example.grupo_dvik_app_spv_seccion_003d.model

data class Registro(
    val idRegistro: Int,
    val idUsuario: Int,
    val idAmbito: Int,
    val fechaHora: String,
    val estadoSincronizacion: String
)
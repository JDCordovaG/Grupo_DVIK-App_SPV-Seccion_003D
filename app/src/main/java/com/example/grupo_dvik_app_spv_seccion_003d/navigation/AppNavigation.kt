package com.example.grupo_dvik_app_spv_seccion_003d.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.grupo_dvik_app_spv_seccion_003d.ui.AdminScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.LoginScreen

@Composable
fun AppNavigation() {

    var pantallaActual by remember {
        mutableStateOf("login")
    }

    when (pantallaActual) {

        "login" -> {

            LoginScreen(
                onLoginCorrecto = {
                    pantallaActual = "admin"
                }
            )
        }

        "admin" -> {

            AdminScreen(
                onVolver = {
                    pantallaActual = "login"
                }
            )
        }
    }
}
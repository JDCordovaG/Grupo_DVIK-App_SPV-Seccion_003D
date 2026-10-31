package com.example.grupo_dvik_app_spv_seccion_003d.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.grupo_dvik_app_spv_seccion_003d.ui.AdminScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.LoginScreen

@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    var nombreUsuario by remember {
        mutableStateOf("")
    }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {

            LoginScreen(
                onLoginCorrecto = { nombre ->

                    nombreUsuario = nombre

                    navController.navigate(
                        "admin"
                    )
                }
            )
        }

        composable("admin") {

            AdminScreen(
                nombreUsuario = nombreUsuario,
                onVolver = {

                    navController
                        .popBackStack()
                }
            )
        }
    }
}
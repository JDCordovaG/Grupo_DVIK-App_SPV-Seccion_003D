package com.example.grupo_dvik_app_spv_seccion_003d.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.grupo_dvik_app_spv_seccion_003d.ui.AdminScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.LoginScreen

@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {

            LoginScreen(
                onLoginCorrecto = {

                    navController.navigate(
                        "admin"
                    )
                }
            )
        }

        composable("admin") {

            AdminScreen(
                onVolver = {

                    navController
                        .popBackStack()
                }
            )
        }
    }
}
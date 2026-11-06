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
import com.example.grupo_dvik_app_spv_seccion_003d.ui.HomeScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.LoginScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.NuevoRegistroScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.PerfilScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.RegistroUsuarioScreen

@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    var nombreUsuario by remember {
        mutableStateOf("")
    }

    var idUsuarioActual by remember {
        mutableStateOf(0)
    }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {

            LoginScreen(
                onLoginCorrecto = { id, nombre ->

                    idUsuarioActual = id
                    nombreUsuario = nombre

                    navController.navigate("home") {

                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onCrearCuenta = {

                    navController.navigate(
                        "registro_usuario"
                    )
                }
            )
        }

        composable("registro_usuario") {

            RegistroUsuarioScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("home") {

            HomeScreen(
                nombreUsuario = nombreUsuario,

                onMiPerfil = {
                    navController.navigate("perfil")
                },

                onMisRegistros = {
                    navController.navigate("admin")
                },

                onNuevoRegistro = {
                    navController.navigate("nuevo_registro")
                },

                onCerrarSesion = {

                    nombreUsuario = ""
                    idUsuarioActual = 0

                    navController.navigate("login") {

                        popUpTo("home") {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        composable("nuevo_registro") {

            NuevoRegistroScreen(
                idUsuario = idUsuarioActual,

                onVolver = {
                    navController.popBackStack()
                },

                onVerHistorial = {

                    navController.popBackStack()

                    navController.navigate("admin")
                }
            )
        }

        composable("perfil") {

            PerfilScreen(
                nombreUsuario = nombreUsuario,
                idUsuario = idUsuarioActual,

                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("admin") {

            AdminScreen(
                nombreUsuario = nombreUsuario,
                idUsuario = idUsuarioActual,

                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}

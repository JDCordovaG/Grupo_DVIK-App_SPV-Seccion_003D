
package com.example.grupo_dvik_app_spv_seccion_003d.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

                    navController.navigate(
                        "admin"
                    )
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

        composable("admin") {

            var mostrarConfirmacion by remember {
                mutableStateOf(false)
            }

            BackHandler(
                enabled = !mostrarConfirmacion
            ) {
                mostrarConfirmacion = true
            }

            AdminScreen(
                nombreUsuario = nombreUsuario,
                idUsuario = idUsuarioActual,
                onVolver = {
                    mostrarConfirmacion = true
                }
            )

            if (mostrarConfirmacion) {

                AlertDialog(
                    onDismissRequest = {
                        mostrarConfirmacion = false
                    },

                    title = {
                        Text(
                            "¿Volver al inicio de sesión?"
                        )
                    },

                    text = {
                        Text(
                            "¿Estás seguro de que deseas volver a la pantalla de inicio de sesión?"
                        )
                    },

                    confirmButton = {

                        TextButton(
                            onClick = {

                                mostrarConfirmacion = false

                                navController.popBackStack()

                                nombreUsuario = ""
                                idUsuarioActual = 0
                            }
                        ) {
                            Text("Sí, volver")
                        }
                    },

                    dismissButton = {

                        TextButton(
                            onClick = {
                                mostrarConfirmacion = false
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

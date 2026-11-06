package com.example.grupo_dvik_app_spv_seccion_003d.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.grupo_dvik_app_spv_seccion_003d.model.Ambito
import com.example.grupo_dvik_app_spv_seccion_003d.ui.AdminScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.CentroInformacionScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.DetalleRegistroScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.HomeScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.LoginScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.NuevoRegistroScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.PerfilScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.RegistroUsuarioScreen
import com.example.grupo_dvik_app_spv_seccion_003d.ui.components.BarraInferior

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    var nombreUsuario by remember {
        mutableStateOf("")
    }

    var idUsuarioActual by remember {
        mutableStateOf(0)
    }

    var ambitoInicial by remember {
        mutableStateOf(Ambito.ANIMO)
    }

    val entradaActual by
    navController.currentBackStackEntryAsState()

    val rutaActual =
        entradaActual?.destination?.route

    val mostrarBarra =
        idUsuarioActual > 0 &&
                rutaActual in listOf(
            "home",
            "admin",
            "centro_informacion",
            "perfil"
        )

    Scaffold(
        bottomBar = {

            if (mostrarBarra) {

                BarraInferior(
                    rutaActual = rutaActual,

                    onNavegar = { destino ->

                        if (destino != rutaActual) {

                            navController.navigate(destino) {

                                popUpTo("home") {
                                    inclusive = false
                                }

                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    ) { paddingInterno ->

        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(paddingInterno)
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
                    idUsuario = idUsuarioActual,

                    onMiPerfil = {
                        navController.navigate("perfil")
                    },

                    onMisRegistros = {
                        navController.navigate("admin")
                    },

                    onNuevoRegistro = {

                        ambitoInicial = Ambito.ANIMO

                        navController.navigate("nuevo_registro")
                    },

                    onCentroInformacion = {

                        navController.navigate("centro_informacion")
                    },

                    onCerrarSesion = {

                        nombreUsuario = ""
                        idUsuarioActual = 0
                        ambitoInicial = Ambito.ANIMO

                        navController.navigate("login") {

                            popUpTo("home") {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("centro_informacion") {

                CentroInformacionScreen(
                    onVolver = {
                        navController.popBackStack()
                    },

                    onRegistrarAmbito = { ambito ->

                        ambitoInicial = ambito

                        navController.navigate("nuevo_registro")
                    }
                )
            }

            composable("nuevo_registro") {

                NuevoRegistroScreen(
                    idUsuario = idUsuarioActual,
                    ambitoInicial = ambitoInicial,

                    onVolver = {
                        navController.popBackStack()
                    },

                    onVerHistorial = {

                        navController.navigate("admin") {

                            popUpTo("home") {
                                inclusive = false
                            }

                            launchSingleTop = true
                        }
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
                    },

                    onNuevoRegistro = {

                        ambitoInicial = Ambito.ANIMO

                        navController.navigate("nuevo_registro")
                    },

                    onVerDetalle = { idRegistro ->

                        navController.navigate(
                            "detalle/$idRegistro"
                        )
                    }
                )
            }

            composable("detalle/{idRegistro}") { entrada ->

                val idRegistro =
                    entrada.arguments
                        ?.getString("idRegistro")
                        ?.toIntOrNull() ?: 0

                DetalleRegistroScreen(
                    idRegistro = idRegistro,
                    idUsuario = idUsuarioActual,

                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

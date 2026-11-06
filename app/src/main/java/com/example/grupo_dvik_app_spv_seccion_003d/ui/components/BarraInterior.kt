package com.example.grupo_dvik_app_spv_seccion_003d.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BarraInferior(
    rutaActual: String?,
    onNavegar: (String) -> Unit
) {

    NavigationBar {

        NavigationBarItem(
            selected = rutaActual == "home",
            onClick = {
                onNavegar("home")
            },
            icon = {
                Text("🏠")
            },
            label = {
                Text("Inicio")
            }
        )

        NavigationBarItem(
            selected = rutaActual == "admin",
            onClick = {
                onNavegar("admin")
            },
            icon = {
                Text("📋")
            },
            label = {
                Text("Registros")
            }
        )

        NavigationBarItem(
            selected = rutaActual == "centro_informacion",
            onClick = {
                onNavegar("centro_informacion")
            },
            icon = {
                Text("📖")
            },
            label = {
                Text("Info")
            }
        )

        NavigationBarItem(
            selected = rutaActual == "perfil",
            onClick = {
                onNavegar("perfil")
            },
            icon = {
                Text("👤")
            },
            label = {
                Text("Perfil")
            }
        )
    }
}

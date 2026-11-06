package com.example.grupo_dvik_app_spv_seccion_003d.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    nombreUsuario: String,
    onMiPerfil: () -> Unit,
    onMisRegistros: () -> Unit,
    onNuevoRegistro: () -> Unit,
    onCentroInformacion: () -> Unit,
    onCerrarSesion: () -> Unit
) {

    var mostrarConfirmacion by remember {
        mutableStateOf(false)
    }

    BackHandler(
        enabled = !mostrarConfirmacion
    ) {
        mostrarConfirmacion = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Mente en Equilibrio",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "¡Bienvenido, $nombreUsuario!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Tu espacio personal de autorregistro",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Mi perfil",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Consulta los datos de tu cuenta y cambia tu fotografía.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onMiPerfil,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver mi perfil")
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Mis registros",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Consulta, crea y administra tus registros personales.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onNuevoRegistro,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Nuevo registro")
                }

                Spacer(Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onMisRegistros,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver mis registros")
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Centro de Información",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Conoce los cuatro ámbitos, sus características y ejemplos de registros.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onCentroInformacion,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Explorar información")
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        TextButton(
            onClick = {
                mostrarConfirmacion = true
            }
        ) {
            Text("Cerrar sesión")
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Esta aplicación utiliza datos ficticios de prueba. No reemplaza apoyo profesional.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }

    if (mostrarConfirmacion) {

        AlertDialog(
            onDismissRequest = {
                mostrarConfirmacion = false
            },

            title = {
                Text("¿Cerrar sesión?")
            },

            text = {
                Text(
                    "¿Estás seguro de que deseas cerrar tu sesión?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        mostrarConfirmacion = false
                        onCerrarSesion()
                    }
                ) {
                    Text("Sí, cerrar sesión")
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

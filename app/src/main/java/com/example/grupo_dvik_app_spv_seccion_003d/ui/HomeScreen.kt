package com.example.grupo_dvik_app_spv_seccion_003d.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.grupo_dvik_app_spv_seccion_003d.model.Ambito
import com.example.grupo_dvik_app_spv_seccion_003d.viewmodel.InicioResumenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    nombreUsuario: String,
    idUsuario: Int,
    onMiPerfil: () -> Unit,
    onMisRegistros: () -> Unit,
    onNuevoRegistro: () -> Unit,
    onCentroInformacion: () -> Unit,
    onCerrarSesion: () -> Unit,
    inicioViewModel: InicioResumenViewModel = viewModel()
) {

    val registros by inicioViewModel.registros.collectAsState()

    LaunchedEffect(idUsuario) {
        inicioViewModel.cargarRegistros(idUsuario)
    }

    val registrosUsuario = registros.filter {
        it.idUsuario == idUsuario
    }

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
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Mente en Equilibrio",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Text(
            text = "¡Bienvenido, $nombreUsuario!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Tu espacio personal de autorregistro",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Resumen de registros

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Mi resumen",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${registrosUsuario.size}",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Registros guardados",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(4.dp))

                Ambito.entries.forEach { ambito ->

                    val cantidad = registrosUsuario.count {
                        it.idAmbito == ambito.id
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(ambito.nombre)

                        Text(
                            text = "$cantidad",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (registrosUsuario.isEmpty()) {

                    Text(
                        text = "Todavía no tienes registros. Puedes crear el primero.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Acceso a registros

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Mis registros",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Registra tus experiencias y consulta tu historial personal.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Button(
                    onClick = onNuevoRegistro,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Nuevo registro")
                }

                OutlinedButton(
                    onClick = onMisRegistros,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver historial")
                }
            }
        }

        // Centro de informacion

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Centro de Información",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Conoce los cuatro ámbitos, sus características y ejemplos.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Button(
                    onClick = onCentroInformacion,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Explorar información")
                }
            }
        }

        // Perfil

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Mi perfil",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Consulta tu cuenta y fotografía personal.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedButton(
                    onClick = onMiPerfil,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver mi perfil")
                }
            }
        }

        TextButton(
            onClick = {
                mostrarConfirmacion = true
            }
        ) {
            Text("Cerrar sesión")
        }

        Text(
            text = "Esta aplicación utiliza datos ficticios de prueba. No realiza diagnósticos ni reemplaza la atención profesional.",
            style = MaterialTheme.typography.bodySmall,
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

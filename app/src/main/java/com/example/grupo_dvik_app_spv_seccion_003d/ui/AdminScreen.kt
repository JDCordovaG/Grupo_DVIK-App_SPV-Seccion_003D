package com.example.grupo_dvik_app_spv_seccion_003d.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.grupo_dvik_app_spv_seccion_003d.model.Ambito
import com.example.grupo_dvik_app_spv_seccion_003d.model.Registro
import com.example.grupo_dvik_app_spv_seccion_003d.ui.components.ConfirmarEliminacionDialog
import com.example.grupo_dvik_app_spv_seccion_003d.ui.components.RegistroCard
import com.example.grupo_dvik_app_spv_seccion_003d.viewmodel.AdminViewModel
import kotlinx.coroutines.delay

@Composable
fun AdminScreen(
    nombreUsuario: String,
    idUsuario: Int,
    onVolver: () -> Unit,
    onNuevoRegistro: () -> Unit,
    onVerDetalle: (Int) -> Unit,
    adminViewModel: AdminViewModel = viewModel()
) {

    val registros by adminViewModel.registros.collectAsState()

    LaunchedEffect(idUsuario) {

        adminViewModel.seleccionarUsuario(idUsuario)
    }

    var ambitoFiltro by remember {
        mutableStateOf<Ambito?>(null)
    }

    var registroEliminar by remember {
        mutableStateOf<Registro?>(null)
    }

    val registrosFiltrados = registros.filter { registro ->

        registro.idUsuario == idUsuario &&
                (
                        ambitoFiltro == null ||
                                registro.idAmbito == ambitoFiltro?.id
                        )
    }

    LaunchedEffect(adminViewModel.mensaje) {

        if (adminViewModel.mensaje.isNotEmpty()) {

            delay(2500)

            adminViewModel.limpiarMensaje()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Mis registros",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(4.dp))

        Text("Historial personal de $nombreUsuario")

        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = onVolver
            ) {

                Text("Volver")
            }

            Button(
                onClick = onNuevoRegistro
            ) {

                Text("Nuevo registro")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Filtrar por ámbito",
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            item {

                FilterChip(
                    selected = ambitoFiltro == null,
                    onClick = {
                        ambitoFiltro = null
                    },
                    label = {
                        Text("Todos")
                    }
                )
            }

            items(Ambito.entries) { ambito ->

                FilterChip(
                    selected = ambitoFiltro == ambito,
                    onClick = {
                        ambitoFiltro = ambito
                    },
                    label = {
                        Text(ambito.nombre)
                    }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        if (adminViewModel.mensaje.isNotEmpty()) {

            Text(
                text = adminViewModel.mensaje,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(8.dp))
        }

        Text(
            text = "Registros mostrados: ${registrosFiltrados.size}",
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn {

            if (registrosFiltrados.isEmpty()) {

                item {

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "No hay registros para este filtro.",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            items(
                items = registrosFiltrados,
                key = {
                    it.idRegistro
                }
            ) { registro ->

                RegistroCard(
                    registro = registro,
                    onVerDetalle = onVerDetalle,
                    onEliminar = {
                        registroEliminar = it
                    }
                )
            }
        }
    }

    val registroAEliminar = registroEliminar

    if (registroAEliminar != null) {

        ConfirmarEliminacionDialog(
            registro = registroAEliminar,

            onConfirmar = {

                adminViewModel.eliminarRegistro(
                    registroAEliminar
                )

                registroEliminar = null
            },

            onCancelar = {

                registroEliminar = null
            }
        )
    }
}

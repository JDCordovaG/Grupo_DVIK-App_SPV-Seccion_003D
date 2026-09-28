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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.grupo_dvik_app_spv_seccion_003d.model.Registro
import com.example.grupo_dvik_app_spv_seccion_003d.viewmodel.AdminViewModel

@Composable
fun AdminScreen(
    onVolver: () -> Unit,
    adminViewModel: AdminViewModel = viewModel()
) {

    var registroEditar by remember {
        mutableStateOf<Registro?>(null)
    }

    var nuevoEstado by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Panel Administrador",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Gestión de Historial"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {
                onVolver()
            }
        ) {

            Text("Volver")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyColumn {

            items(adminViewModel.registros) { registro ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Registro: ${registro.idRegistro}"
                        )

                        Text(
                            text = "Usuario: ${registro.idUsuario}"
                        )

                        Text(
                            text = "Ámbito: ${registro.idAmbito}"
                        )

                        Text(
                            text = "Fecha: ${registro.fechaHora}"
                        )

                        Text(
                            text = "Estado: ${registro.estadoSincronizacion}"
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Button(
                                onClick = {

                                    registroEditar = registro

                                    nuevoEstado =
                                        registro.estadoSincronizacion
                                }
                            ) {

                                Text("Editar")
                            }

                            Button(
                                onClick = {

                                    adminViewModel.eliminarRegistro(
                                        registro
                                    )
                                }
                            ) {

                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }

    if (registroEditar != null) {

        AlertDialog(
            onDismissRequest = {
                registroEditar = null
            },

            title = {
                Text("Editar registro")
            },

            text = {

                OutlinedTextField(
                    value = nuevoEstado,
                    onValueChange = {
                        nuevoEstado = it
                    },
                    label = {
                        Text("Estado de sincronización")
                    }
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        val registro = registroEditar

                        if (registro != null) {

                            adminViewModel.modificarRegistro(
                                registro,
                                nuevoEstado
                            )
                        }

                        registroEditar = null
                    }
                ) {

                    Text("Guardar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        registroEditar = null
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }
}
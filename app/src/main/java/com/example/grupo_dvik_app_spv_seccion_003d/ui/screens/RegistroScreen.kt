package com.example.grupo_dvik_app_spv_seccion_003d.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import com.example.grupo_dvik_app_spv_seccion_003d.ui.viewmodel.RegistroViewModel

@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel
) {

    val registros by
    viewModel.registros.collectAsStateWithLifecycle()

    var idUsuario by remember {
        mutableStateOf("")
    }

    var idAmbito by remember {
        mutableStateOf("")
    }

    var fechaHora by remember {
        mutableStateOf("")
    }

    var estado by remember {
        mutableStateOf("")
    }

    var mensajeError by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Registros",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = idUsuario,
            onValueChange = {
                idUsuario = it
            },
            label = {
                Text("ID Usuario")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = idAmbito,
            onValueChange = {
                idAmbito = it
            },
            label = {
                Text("ID Ámbito")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fechaHora,
            onValueChange = {
                fechaHora = it
            },
            label = {
                Text("Fecha y hora")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado,
            onValueChange = {
                estado = it
            },
            label = {
                Text("Estado")
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (mensajeError.isNotEmpty()) {

            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = {

                val usuario =
                    idUsuario.toIntOrNull()

                val ambito =
                    idAmbito.toIntOrNull()

                if (
                    usuario == null ||
                    ambito == null ||
                    fechaHora.isBlank() ||
                    estado.isBlank()
                ) {

                    mensajeError =
                        "Todos los campos deben ser válidos"

                } else {

                    mensajeError = ""

                    viewModel.insertarRegistro(
                        RegistroEntity(
                            idUsuario = usuario,
                            idAmbito = ambito,
                            fechaHora = fechaHora,
                            estadoSincronizacion = estado
                        )
                    )

                    idUsuario = ""
                    idAmbito = ""
                    fechaHora = ""
                    estado = ""
                }
            }
        ) {

            Text("Guardar registro")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyColumn {

            items(
                items = registros,
                key = {
                    it.idRegistro
                }
            ) { registro ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text =
                                "Registro: ${registro.idRegistro}"
                        )

                        Text(
                            text =
                                "Usuario: ${registro.idUsuario}"
                        )

                        Text(
                            text =
                                "Ámbito: ${registro.idAmbito}"
                        )

                        Text(
                            text =
                                "Fecha: ${registro.fechaHora}"
                        )

                        Text(
                            text =
                                "Estado: ${registro.estadoSincronizacion}"
                        )

                        Button(
                            onClick = {
                                viewModel.eliminarRegistro(
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
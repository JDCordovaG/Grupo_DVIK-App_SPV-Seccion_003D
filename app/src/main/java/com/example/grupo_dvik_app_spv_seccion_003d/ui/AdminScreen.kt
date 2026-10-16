package com.example.grupo_dvik_app_spv_seccion_003d.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.grupo_dvik_app_spv_seccion_003d.model.Registro
import com.example.grupo_dvik_app_spv_seccion_003d.viewmodel.AdminViewModel
import kotlinx.coroutines.delay

private fun formatearFechaHora(texto: String): String {

    val numeros = texto
        .filter { it.isDigit() }
        .take(12)

    var resultado = ""

    for (i in numeros.indices) {

        resultado += numeros[i]

        if (i == 1) {
            resultado += "/"
        }

        if (i == 3) {
            resultado += "/"
        }

        if (i == 7) {
            resultado += " "
        }

        if (i == 9) {
            resultado += ":"
        }
    }

    return resultado
}

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

    var mostrarNuevoRegistro by remember {
        mutableStateOf(false)
    }

    var idUsuario by remember {
        mutableStateOf("")
    }

    var idAmbito by remember {
        mutableStateOf("")
    }

    var fechaHora by remember {
        mutableStateOf("")
    }

    var estadoRegistro by remember {
        mutableStateOf("")
    }

    var errorNuevoRegistro by remember {
        mutableStateOf("")
    }

    var errorEditarRegistro by remember {
        mutableStateOf("")
    }

    LaunchedEffect(adminViewModel.mensaje) {

        if (
            adminViewModel.mensaje.isNotEmpty() &&
            errorNuevoRegistro.isEmpty() &&
            errorEditarRegistro.isEmpty()
        ) {

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
            text = "Panel Administrador",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    onVolver()
                }
            ) {
                Text("Volver")
            }

            Button(
                onClick = {

                    errorNuevoRegistro = ""

                    mostrarNuevoRegistro = true
                }
            ) {
                Text("Nuevo registro")
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        AnimatedVisibility(
            visible =
                adminViewModel.mensaje.isNotEmpty() &&
                        errorNuevoRegistro.isEmpty() &&
                        errorEditarRegistro.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {

                Text(
                    text = adminViewModel.mensaje,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Text(
            text = "Registros guardados: ${adminViewModel.registros.size}",
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
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
                            text = "Registro: ${registro.idRegistro}",
                            fontWeight = FontWeight.Bold
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
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            Button(
                                onClick = {

                                    registroEditar = registro

                                    nuevoEstado =
                                        registro.estadoSincronizacion

                                    errorEditarRegistro = ""
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

    if (mostrarNuevoRegistro) {

        AlertDialog(
            onDismissRequest = {

                mostrarNuevoRegistro = false

                errorNuevoRegistro = ""
            },

            title = {
                Text("Nuevo registro")
            },

            text = {

                Column {

                    OutlinedTextField(
                        value = idUsuario,
                        onValueChange = {
                            idUsuario = it
                        },
                        label = {
                            Text("ID Usuario")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    OutlinedTextField(
                        value = idAmbito,
                        onValueChange = {
                            idAmbito = it
                        },
                        label = {
                            Text("ID Ámbito")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    OutlinedTextField(
                        value = fechaHora,
                        onValueChange = {
                            fechaHora = formatearFechaHora(it)
                        },
                        label = {
                            Text("Fecha y hora")
                        },
                        placeholder = {
                            Text("dd/MM/yyyy HH:mm")
                        },
                        supportingText = {
                            Text(
                                "Ingrese solo números. Ejemplo: 091020261430"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    OutlinedTextField(
                        value = estadoRegistro,
                        onValueChange = {
                            estadoRegistro = it
                        },
                        label = {
                            Text("Estado")
                        },
                        singleLine = true
                    )

                    if (errorNuevoRegistro.isNotEmpty()) {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = errorNuevoRegistro,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        val creado =
                            adminViewModel.crearRegistro(
                                idUsuario,
                                idAmbito,
                                fechaHora,
                                estadoRegistro
                            )

                        if (creado) {

                            idUsuario = ""
                            idAmbito = ""
                            fechaHora = ""
                            estadoRegistro = ""

                            errorNuevoRegistro = ""

                            mostrarNuevoRegistro = false

                        } else {

                            errorNuevoRegistro =
                                adminViewModel.mensaje

                            adminViewModel.limpiarMensaje()
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {

                        mostrarNuevoRegistro = false

                        errorNuevoRegistro = ""
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (registroEditar != null) {

        AlertDialog(
            onDismissRequest = {

                registroEditar = null

                errorEditarRegistro = ""
            },

            title = {
                Text("Editar registro")
            },

            text = {

                Column {

                    Text(
                        text = "Modifique el estado del registro."
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    OutlinedTextField(
                        value = nuevoEstado,
                        onValueChange = {
                            nuevoEstado = it
                        },
                        label = {
                            Text("Estado de sincronización")
                        },
                        singleLine = true
                    )

                    if (errorEditarRegistro.isNotEmpty()) {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = errorEditarRegistro,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        val registro = registroEditar

                        if (registro != null) {

                            val modificado =
                                adminViewModel.modificarRegistro(
                                    registro,
                                    nuevoEstado
                                )

                            if (modificado) {

                                errorEditarRegistro = ""

                                registroEditar = null

                            } else {

                                errorEditarRegistro =
                                    adminViewModel.mensaje

                                adminViewModel.limpiarMensaje()
                            }
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {

                        registroEditar = null

                        errorEditarRegistro = ""
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
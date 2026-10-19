package com.example.grupo_dvik_app_spv_seccion_003d.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.grupo_dvik_app_spv_seccion_003d.ui.viewmodel.RegistroViewModel


@Composable
fun RegistroScreen(viewModel: RegistroViewModel) {
    val registros by viewModel.registros.collectAsState()
    var tipo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Formulario de ingreso y validaciones
        OutlinedTextField(value = tipo, onValueChange = {
            val it = null
            tipo = it
        }, label = { Text("Tipo (Ej: DBT)") })
        OutlinedTextField(value = descripcion, onValueChange = {
            val it = null
            descripcion = it
        }, label = { Text("Descripción") })
        OutlinedTextField(value = fecha, onValueChange = {
            val it = null
            fecha = it
        }, label = { Text("Fecha") })

        if (errorMensaje) {
            Text("Todos los campos son obligatorios", color = MaterialTheme.colorScheme.error)
        }

        Button(onClick = {
            if (tipo.isBlank() || descripcion.isBlank() || fecha.isBlank()) {
                errorMensaje = true
            } else {
                errorMensaje = false
                viewModel.guardarRegistro(tipo, descripcion, fecha)
                tipo = ""; descripcion = ""; fecha = ""
            }
        }) {
            Text("Guardar Registro")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar, editar y eliminar registros
        LazyColumn {
            items(registros) { registro ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Tipo: ${registro.tipo}", style = MaterialTheme.typography.titleMedium)
                        Text("Descripción: ${registro.descripcion}")
                        Text("Fecha: ${registro.fecha}")
                        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                            TextButton(onClick = { /* Lógica para cargar en formulario y usar viewModel.actualizarRegistro() */ }) {
                                Text("Editar")
                            }
                            TextButton(onClick = { viewModel.eliminarRegistro(registro) }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
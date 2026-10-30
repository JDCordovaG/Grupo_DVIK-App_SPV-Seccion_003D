package com.example.grupo_dvik_app_spv_seccion_003d.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.grupo_dvik_app_spv_seccion_003d.model.Registro

@Composable
fun ConfirmarEliminacionDialog(
    registro: Registro,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            onCancelar()
        },

        title = {

            Text(
                text =
                    "Eliminar registro"
            )
        },

        text = {

            Text(
                text =
                    "¿Estás seguro de eliminar el registro ${registro.idRegistro}?"
            )
        },

        confirmButton = {

            TextButton(
                onClick = {
                    onConfirmar()
                }
            ) {

                Text(
                    text = "Eliminar"
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = {
                    onCancelar()
                }
            ) {

                Text(
                    text = "Cancelar"
                )
            }
        }
    )
}
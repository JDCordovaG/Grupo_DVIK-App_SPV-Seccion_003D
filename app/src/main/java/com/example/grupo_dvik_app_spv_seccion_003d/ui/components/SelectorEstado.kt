package com.example.grupo_dvik_app_spv_seccion_003d.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectorEstado(
    estadoSeleccionado: String,
    onEstadoSeleccionado: (String) -> Unit
) {

    Column {

        Text(
            text = "Seleccione el estado"
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    onEstadoSeleccionado("Pendiente")
                }
            ) {
                Text("Pendiente")
            }

            Button(
                onClick = {
                    onEstadoSeleccionado("Sincronizado")
                }
            ) {
                Text("Sincronizado")
            }
        }

        if (estadoSeleccionado.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Estado seleccionado: $estadoSeleccionado"
            )
        }
    }
}
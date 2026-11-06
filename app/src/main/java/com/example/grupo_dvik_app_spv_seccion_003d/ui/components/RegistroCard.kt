package com.example.grupo_dvik_app_spv_seccion_003d.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.grupo_dvik_app_spv_seccion_003d.model.Ambito
import com.example.grupo_dvik_app_spv_seccion_003d.model.Registro

@Composable
fun RegistroCard(
    registro: Registro,
    onVerDetalle: (Int) -> Unit,
    onEliminar: (Registro) -> Unit
) {

    val nombreAmbito =
        Ambito.entries.find {
            it.id == registro.idAmbito
        }?.nombre ?: "Desconocido"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = nombreAmbito,
                fontWeight = FontWeight.Bold
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
                        onVerDetalle(registro.idRegistro)
                    }
                ) {

                    Text("Ver detalle")
                }

                OutlinedButton(
                    onClick = {
                        onEliminar(registro)
                    }
                ) {

                    Text("Eliminar")
                }
            }
        }
    }
}

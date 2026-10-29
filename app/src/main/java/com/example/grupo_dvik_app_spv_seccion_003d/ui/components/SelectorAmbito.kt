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
import com.example.grupo_dvik_app_spv_seccion_003d.model.Ambito

@Composable
fun SelectorAmbito(
    ambitoSeleccionado: Ambito?,
    onAmbitoSeleccionado: (Ambito) -> Unit
) {

    Column {

        Text(
            text = "Seleccione el ámbito"
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
                    onAmbitoSeleccionado(
                        Ambito.NEURODESARROLLO
                    )
                }
            ) {
                Text("Neurodesarrollo")
            }

            Button(
                onClick = {
                    onAmbitoSeleccionado(
                        Ambito.DBT
                    )
                }
            ) {
                Text("DBT")
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    onAmbitoSeleccionado(
                        Ambito.ANIMO
                    )
                }
            ) {
                Text("Ánimo")
            }

            Button(
                onClick = {
                    onAmbitoSeleccionado(
                        Ambito.ADICCIONES
                    )
                }
            ) {
                Text("Adicciones")
            }
        }

        if (ambitoSeleccionado != null) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Ámbito seleccionado: ${ambitoSeleccionado.nombre}"
            )
        }
    }
}
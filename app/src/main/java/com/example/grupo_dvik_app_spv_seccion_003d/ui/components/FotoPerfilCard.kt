package com.example.grupo_dvik_app_spv_seccion_003d.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FotoPerfilCard(
    nombreUsuario: String,
    foto: Bitmap?,
    onTomarFoto: () -> Unit
) {

    Card {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            if (foto != null) {

                val rotacion =
                    if (foto.width > foto.height) {
                        90f
                    } else {
                        0f
                    }

                Image(
                    bitmap =
                        foto.asImageBitmap(),
                    contentDescription =
                        "Foto de perfil",
                    modifier = Modifier
                        .width(90.dp)
                        .height(90.dp)
                        .rotate(rotacion)
                )

            } else {

                Text(
                    text = "Sin foto",
                    modifier = Modifier
                        .width(90.dp)
                        .padding(12.dp),
                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
            }

            Spacer(
                modifier =
                    Modifier.width(16.dp)
            )

            Column {

                Text(
                    text = nombreUsuario,
                    style =
                        MaterialTheme
                            .typography
                            .titleLarge,
                    fontWeight =
                        FontWeight.Bold
                )

                Text(
                    text = "Usuario activo",
                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                Button(
                    onClick = {
                        onTomarFoto()
                    }
                ) {

                    Text(
                        text =
                            if (foto == null) {
                                "Tomar foto"
                            } else {
                                "Cambiar foto"
                            }
                    )
                }
            }
        }
    }
}
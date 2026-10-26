package com.example.grupo_dvik_app_spv_seccion_003d.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun FotoPerfilCard(
    foto: Bitmap
) {

    Spacer(
        modifier = Modifier.height(12.dp)
    )

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                bitmap = foto.asImageBitmap(),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .width(180.dp)
                    .height(180.dp)
            )
        }
    }
}
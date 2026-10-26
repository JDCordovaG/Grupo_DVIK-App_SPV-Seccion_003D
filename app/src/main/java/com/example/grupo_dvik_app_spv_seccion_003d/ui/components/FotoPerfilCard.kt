package com.example.grupo_dvik_app_spv_seccion_003d.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
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

        Image(
            bitmap = foto.asImageBitmap(),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(8.dp)
        )
    }
}
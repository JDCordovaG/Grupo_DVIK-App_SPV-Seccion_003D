
package com.example.grupo_dvik_app_spv_seccion_003d.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.grupo_dvik_app_spv_seccion_003d.ui.components.FotoPerfilCard
import com.example.grupo_dvik_app_spv_seccion_003d.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    nombreUsuario: String,
    idUsuario: Int,
    onVolver: () -> Unit,
    perfilViewModel: PerfilViewModel = viewModel()
) {

    LaunchedEffect(idUsuario) {

        perfilViewModel.cargarFoto(
            idUsuario
        )
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->

            perfilViewModel.guardarFoto(
                idUsuario,
                bitmap
            )
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.Center
    ) {

        Text(
            text = "Mi perfil",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Información de tu cuenta",
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        FotoPerfilCard(
            nombreUsuario = nombreUsuario,
            foto = perfilViewModel.fotoPerfil,

            onTomarFoto = {

                if (!perfilViewModel.guardando) {

                    cameraLauncher.launch(null)
                }
            }
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Datos de usuario",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "Nombre: $nombreUsuario"
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "ID de usuario: $idUsuario"
                )
            }
        }

        if (perfilViewModel.guardando) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Guardando fotografía...",
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (perfilViewModel.mensaje.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = perfilViewModel.mensaje,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        TextButton(
            onClick = onVolver,
            enabled = !perfilViewModel.guardando
        ) {

            Text("Volver al inicio")
        }
    }
}

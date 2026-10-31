package com.example.grupo_dvik_app_spv_seccion_003d.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.grupo_dvik_app_spv_seccion_003d.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginCorrecto: (String) -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {

    var nombre by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    var mensaje by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.Center
    ) {

        Text(
            text =
                "Grupo_DVIK-App_SPV-Seccion_003D",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color =
                MaterialTheme
                    .colorScheme
                    .secondary
        )

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        Text(
            text = "Mente en Equilibrio",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color =
                MaterialTheme
                    .colorScheme
                    .primary
        )

        Text(
            text =
                "Tu espacio de autorregistro",
            fontSize = 16.sp,
            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 32.dp
            )
        )

        Text(
            text =
                "Ingresa tus datos para continuar",
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 8.dp
                ),
            textAlign =
                TextAlign.Center
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre")
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 12.dp
                )
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
            },
            label = {
                Text("Contraseña")
            },
            singleLine = true,
            visualTransformation =
                PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 16.dp
                )
        )

        Button(
            onClick = {

                val resultado =
                    loginViewModel
                        .validarLogin(
                            nombre,
                            contrasena
                        )

                if (resultado == "OK") {

                    mensaje = ""

                    onLoginCorrecto(
                        nombre.trim()
                    )

                } else {

                    mensaje = resultado
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {

            Text(
                text = "Ingresar",
                fontSize = 16.sp
            )
        }

        if (mensaje.isNotEmpty()) {

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(
                text = mensaje,
                color =
                    MaterialTheme
                        .colorScheme
                        .error,
                textAlign =
                    TextAlign.Center
            )
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        Text(
            text =
                "Esta aplicación utiliza datos ficticios de prueba. No reemplaza apoyo profesional.",
            fontSize = 12.sp,
            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant,
            textAlign =
                TextAlign.Center
        )
    }
}
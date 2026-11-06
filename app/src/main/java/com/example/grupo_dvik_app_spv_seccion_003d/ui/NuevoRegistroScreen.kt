package com.example.grupo_dvik_app_spv_seccion_003d.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.grupo_dvik_app_spv_seccion_003d.model.Ambito
import com.example.grupo_dvik_app_spv_seccion_003d.viewmodel.NuevoRegistroViewModel
import kotlin.math.roundToInt

@Composable
fun NuevoRegistroScreen(
    idUsuario: Int,
    onVolver: () -> Unit,
    onVerHistorial: () -> Unit,
    ambitoInicial: Ambito = Ambito.ANIMO,
    nuevoRegistroViewModel: NuevoRegistroViewModel = viewModel()
) {

    var ambitoSeleccionado by remember(ambitoInicial) {
        mutableStateOf(ambitoInicial)
    }

    // Estado de animo
    var categoria by remember { mutableStateOf("") }
    var intensidadAnimo by remember { mutableFloatStateOf(5f) }
    var contextoAnimo by remember { mutableStateOf("") }
    var nota by remember { mutableStateOf("") }

    // Neurodesarrollo
    var rutinaTarea by remember { mutableStateOf("") }
    var contextoNeuro by remember { mutableStateOf("") }
    var estrategiaUtilizada by remember { mutableStateOf("") }
    var resultadoPercibido by remember { mutableStateOf("") }

    // DBT
    var idHabilidad by remember { mutableStateOf(0) }
    var situacionEmocion by remember { mutableStateOf("") }
    var intensidadDbt by remember { mutableFloatStateOf(5f) }
    var utilidadPercibida by remember { mutableStateOf("") }

    // Adicciones
    var nivelImpulso by remember { mutableFloatStateOf(5f) }
    var desencadenante by remember { mutableStateOf("") }
    var accionProtectora by remember { mutableStateOf("") }
    var estadoObjetivo by remember { mutableStateOf("") }

    val editable =
        !nuevoRegistroViewModel.guardando &&
                !nuevoRegistroViewModel.guardadoCorrectamente

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Nuevo registro",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text("Selecciona el ámbito que deseas registrar.")

        Text(
            text = "Ámbito",
            fontWeight = FontWeight.Bold
        )

        Ambito.entries.forEach { ambito ->

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = ambitoSeleccionado == ambito,
                    enabled = editable,
                    onClick = {
                        ambitoSeleccionado = ambito
                        nuevoRegistroViewModel.limpiarMensaje()
                    }
                )

                Text(ambito.nombre)
            }
        }

        Spacer(Modifier.height(8.dp))

        when (ambitoSeleccionado) {

            Ambito.ANIMO -> {

                Text(
                    text = "Registrar estado de ánimo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "¿Cómo te sientes?",
                    fontWeight = FontWeight.Bold
                )

                listOf(
                    "Muy bien",
                    "Bien",
                    "Neutral",
                    "Mal",
                    "Muy mal"
                ).forEach { opcion ->

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = categoria == opcion,
                            enabled = editable,
                            onClick = { categoria = opcion }
                        )

                        Text(opcion)
                    }
                }

                Text(
                    "Nivel de intensidad: ${intensidadAnimo.roundToInt()}"
                )

                Slider(
                    value = intensidadAnimo,
                    onValueChange = { intensidadAnimo = it },
                    valueRange = 1f..10f,
                    steps = 8,
                    enabled = editable
                )

                CampoRegistro(
                    valor = contextoAnimo,
                    alCambiar = { contextoAnimo = it },
                    etiqueta = "Contexto",
                    habilitado = editable
                )

                CampoRegistro(
                    valor = nota,
                    alCambiar = { nota = it },
                    etiqueta = "Nota opcional",
                    habilitado = editable
                )
            }

            Ambito.NEURODESARROLLO -> {

                Text(
                    text = "Registrar Neurodesarrollo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Registra una rutina, tarea o estrategia utilizada."
                )

                CampoRegistro(
                    valor = rutinaTarea,
                    alCambiar = { rutinaTarea = it },
                    etiqueta = "Rutina o tarea",
                    habilitado = editable
                )

                CampoRegistro(
                    valor = contextoNeuro,
                    alCambiar = { contextoNeuro = it },
                    etiqueta = "Contexto",
                    habilitado = editable
                )

                CampoRegistro(
                    valor = estrategiaUtilizada,
                    alCambiar = { estrategiaUtilizada = it },
                    etiqueta = "Estrategia utilizada",
                    habilitado = editable
                )

                CampoRegistro(
                    valor = resultadoPercibido,
                    alCambiar = { resultadoPercibido = it },
                    etiqueta = "Resultado percibido",
                    habilitado = editable
                )
            }

            Ambito.DBT -> {

                Text(
                    text = "Registrar habilidad DBT",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "DBT es una terapia que enseña habilidades para manejar emociones y situaciones difíciles."
                )

                Text(
                    text = "¿Qué habilidad utilizaste?",
                    fontWeight = FontWeight.Bold
                )

                if (nuevoRegistroViewModel.cargandoHabilidades) {

                    Text("Cargando habilidades...")

                } else if (
                    nuevoRegistroViewModel.habilidadesDbt.isEmpty()
                ) {

                    Text(
                        text = "No se pudieron cargar las habilidades DBT.",
                        color = MaterialTheme.colorScheme.error
                    )

                } else {

                    nuevoRegistroViewModel.habilidadesDbt.forEach {
                            habilidad ->

                        val explicacion = when (
                            habilidad.nombreHabilidad
                        ) {
                            "Observar" ->
                                "Notar lo que siento sin reaccionar inmediatamente."

                            "Describir" ->
                                "Expresar con palabras lo que estoy sintiendo."

                            "STOP" ->
                                "Detenerme, tomar distancia y pensar antes de actuar."

                            "Acción opuesta" ->
                                "Elegir una acción diferente cuando mi impulso no me ayuda."

                            "DEAR MAN" ->
                                "Comunicar lo que necesito de forma clara y respetuosa."

                            else ->
                                habilidad.descripcionEducativa
                        }

                        Row(
                            verticalAlignment = Alignment.Top
                        ) {

                            RadioButton(
                                selected = idHabilidad == habilidad.idHabilidad,
                                enabled = editable,
                                onClick = {
                                    idHabilidad = habilidad.idHabilidad
                                }
                            )

                            Column(
                                modifier = Modifier.padding(
                                    top = 10.dp,
                                    bottom = 8.dp
                                )
                            ) {

                                Text(
                                    text = habilidad.nombreHabilidad,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = explicacion
                                )

                                Text(
                                    text = habilidad.categoria,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Situación o emoción",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Describe qué ocurrió o qué emoción sentiste."
                )

                CampoRegistro(
                    valor = situacionEmocion,
                    alCambiar = { situacionEmocion = it },
                    etiqueta = "¿Qué ocurrió?",
                    habilitado = editable
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Intensidad de la emoción: ${intensidadDbt.roundToInt()}/10",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Indica qué tan intensa fue la emoción. 1 es muy baja y 10 es muy alta."
                )

                Slider(
                    value = intensidadDbt,
                    onValueChange = { intensidadDbt = it },
                    valueRange = 1f..10f,
                    steps = 8,
                    enabled = editable
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Utilidad percibida",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "¿Te ayudó la habilidad que elegiste? Explica brevemente de qué manera."
                )

                CampoRegistro(
                    valor = utilidadPercibida,
                    alCambiar = { utilidadPercibida = it },
                    etiqueta = "¿Cómo te ayudó?",
                    habilitado = editable
                )
            }

            Ambito.ADICCIONES -> {

                Text(
                    text = "Registrar Adicciones e impulsos",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Nivel de impulso: ${nivelImpulso.roundToInt()}"
                )

                Slider(
                    value = nivelImpulso,
                    onValueChange = { nivelImpulso = it },
                    valueRange = 1f..10f,
                    steps = 8,
                    enabled = editable
                )

                CampoRegistro(
                    valor = desencadenante,
                    alCambiar = { desencadenante = it },
                    etiqueta = "Desencadenante",
                    habilitado = editable
                )

                CampoRegistro(
                    valor = accionProtectora,
                    alCambiar = { accionProtectora = it },
                    etiqueta = "Acción protectora",
                    habilitado = editable
                )

                CampoRegistro(
                    valor = estadoObjetivo,
                    alCambiar = { estadoObjetivo = it },
                    etiqueta = "Estado del objetivo",
                    habilitado = editable
                )
            }
        }

        if (nuevoRegistroViewModel.mensaje.isNotEmpty()) {

            Text(
                text = nuevoRegistroViewModel.mensaje,
                color = if (
                    nuevoRegistroViewModel.guardadoCorrectamente
                ) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        }

        if (nuevoRegistroViewModel.guardadoCorrectamente) {

            Button(
                onClick = onVerHistorial,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Ver mis registros")
            }

        } else {

            Button(
                onClick = {

                    when (ambitoSeleccionado) {

                        Ambito.ANIMO -> {

                            nuevoRegistroViewModel.guardarAnimo(
                                idUsuario = idUsuario,
                                categoria = categoria,
                                intensidad = intensidadAnimo.roundToInt(),
                                contexto = contextoAnimo,
                                nota = nota
                            )
                        }

                        Ambito.NEURODESARROLLO -> {

                            nuevoRegistroViewModel.guardarNeurodesarrollo(
                                idUsuario = idUsuario,
                                rutinaTarea = rutinaTarea,
                                contexto = contextoNeuro,
                                estrategiaUtilizada = estrategiaUtilizada,
                                resultadoPercibido = resultadoPercibido
                            )
                        }

                        Ambito.DBT -> {

                            nuevoRegistroViewModel.guardarDbt(
                                idUsuario = idUsuario,
                                idHabilidad = idHabilidad,
                                situacionEmocion = situacionEmocion,
                                nivelIntensidad = intensidadDbt.roundToInt(),
                                utilidadPercibida = utilidadPercibida
                            )
                        }

                        Ambito.ADICCIONES -> {

                            nuevoRegistroViewModel.guardarAdicciones(
                                idUsuario = idUsuario,
                                nivelImpulso = nivelImpulso.roundToInt(),
                                desencadenante = desencadenante,
                                accionProtectora = accionProtectora,
                                estadoObjetivo = estadoObjetivo
                            )
                        }
                    }
                },
                enabled = editable,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    if (nuevoRegistroViewModel.guardando) {
                        "Guardando..."
                    } else {
                        "Guardar registro"
                    }
                )
            }
        }

        TextButton(
            onClick = onVolver,
            enabled = !nuevoRegistroViewModel.guardando,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

            Text("Volver")
        }
    }
}

@Composable
private fun CampoRegistro(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    habilitado: Boolean
) {

    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        label = { Text(etiqueta) },
        modifier = Modifier.fillMaxWidth(),
        enabled = habilitado,
        maxLines = 3
    )
}

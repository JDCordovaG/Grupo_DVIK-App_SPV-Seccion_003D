
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.grupo_dvik_app_spv_seccion_003d.model.Ambito
import com.example.grupo_dvik_app_spv_seccion_003d.viewmodel.DetalleRegistroViewModel
import kotlin.math.roundToInt

@Composable
fun DetalleRegistroScreen(
    idRegistro: Int,
    idUsuario: Int,
    onVolver: () -> Unit,
    detalleViewModel: DetalleRegistroViewModel = viewModel()
) {

    LaunchedEffect(idRegistro, idUsuario) {

        detalleViewModel.cargarDetalle(
            idRegistro,
            idUsuario
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Detalle del registro",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedButton(
            onClick = onVolver,
            enabled = !detalleViewModel.guardando
        ) {
            Text("Volver")
        }

        if (detalleViewModel.cargando) {

            Text("Cargando registro...")

        } else {

            val registro = detalleViewModel.registro

            if (registro == null) {

                Text(
                    text = detalleViewModel.mensaje,
                    color = MaterialTheme.colorScheme.error
                )

            } else {

                val nombreAmbito =
                    Ambito.entries.find {
                        it.id == registro.idAmbito
                    }?.nombre ?: "Desconocido"

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = nombreAmbito,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "Registro N° ${registro.idRegistro}"
                        )

                        Text(
                            "Fecha: ${registro.fechaHora}"
                        )

                        Text(
                            "Sincronización: ${registro.estadoSincronizacion}"
                        )
                    }
                }

                if (detalleViewModel.editando) {

                    Text(
                        text = "Editar información",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    when (registro.idAmbito) {

                        Ambito.ANIMO.id -> {

                            Text(
                                text = "Estado de ánimo",
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
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    RadioButton(
                                        selected =
                                            detalleViewModel.categoriaAnimo ==
                                                    opcion,
                                        onClick = {
                                            detalleViewModel.categoriaAnimo =
                                                opcion
                                        },
                                        enabled =
                                            !detalleViewModel.guardando
                                    )

                                    Text(opcion)
                                }
                            }

                            ControlIntensidad(
                                titulo = "Intensidad",
                                valor = detalleViewModel.intensidadAnimo,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.intensidadAnimo = it
                                }
                            )

                            CampoEdicion(
                                etiqueta = "Contexto",
                                valor = detalleViewModel.contextoAnimo,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.contextoAnimo = it
                                }
                            )

                            CampoEdicion(
                                etiqueta = "Nota opcional",
                                valor = detalleViewModel.notaAnimo,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.notaAnimo = it
                                }
                            )
                        }

                        Ambito.NEURODESARROLLO.id -> {

                            CampoEdicion(
                                etiqueta = "Rutina o tarea",
                                valor = detalleViewModel.rutinaTarea,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.rutinaTarea = it
                                }
                            )

                            CampoEdicion(
                                etiqueta = "Contexto",
                                valor = detalleViewModel.contextoNeuro,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.contextoNeuro = it
                                }
                            )

                            CampoEdicion(
                                etiqueta = "Estrategia utilizada",
                                valor =
                                    detalleViewModel.estrategiaUtilizada,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.estrategiaUtilizada = it
                                }
                            )

                            CampoEdicion(
                                etiqueta = "Resultado percibido",
                                valor =
                                    detalleViewModel.resultadoPercibido,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.resultadoPercibido = it
                                }
                            )
                        }

                        Ambito.DBT.id -> {

                            Text(
                                text = "Habilidad utilizada",
                                fontWeight = FontWeight.Bold
                            )

                            detalleViewModel.habilidadesDbt.forEach {
                                    habilidad ->

                                Row(
                                    verticalAlignment =
                                        Alignment.Top
                                ) {

                                    RadioButton(
                                        selected =
                                            detalleViewModel.idHabilidad ==
                                                    habilidad.idHabilidad,
                                        onClick = {
                                            detalleViewModel.idHabilidad =
                                                habilidad.idHabilidad
                                        },
                                        enabled =
                                            !detalleViewModel.guardando
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
                                            text =
                                                habilidad.descripcionEducativa,
                                            style =
                                                MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }

                            CampoEdicion(
                                etiqueta = "Situación o emoción",
                                valor = detalleViewModel.situacionEmocion,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.situacionEmocion = it
                                }
                            )

                            ControlIntensidad(
                                titulo = "Intensidad de la emoción",
                                valor = detalleViewModel.intensidadDbt,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.intensidadDbt = it
                                }
                            )

                            Text(
                                "Utilidad percibida: explica cuánto te ayudó la habilidad."
                            )

                            CampoEdicion(
                                etiqueta = "¿Cómo te ayudó?",
                                valor = detalleViewModel.utilidadPercibida,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.utilidadPercibida = it
                                }
                            )
                        }

                        Ambito.ADICCIONES.id -> {

                            ControlIntensidad(
                                titulo = "Nivel de impulso",
                                valor = detalleViewModel.nivelImpulso,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.nivelImpulso = it
                                }
                            )

                            CampoEdicion(
                                etiqueta = "Desencadenante",
                                valor = detalleViewModel.desencadenante,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.desencadenante = it
                                }
                            )

                            CampoEdicion(
                                etiqueta = "Acción protectora",
                                valor = detalleViewModel.accionProtectora,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.accionProtectora = it
                                }
                            )

                            CampoEdicion(
                                etiqueta = "Estado del objetivo",
                                valor = detalleViewModel.estadoObjetivo,
                                habilitado = !detalleViewModel.guardando,
                                alCambiar = {
                                    detalleViewModel.estadoObjetivo = it
                                }
                            )
                        }
                    }

                    if (detalleViewModel.mensaje.isNotEmpty()) {

                        Text(
                            text = detalleViewModel.mensaje,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Button(
                        onClick = {
                            detalleViewModel.guardarCambios()
                        },
                        enabled = !detalleViewModel.guardando,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            if (detalleViewModel.guardando) {
                                "Guardando..."
                            } else {
                                "Guardar cambios"
                            }
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            detalleViewModel.cancelarEdicion()
                        },
                        enabled = !detalleViewModel.guardando,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("Cancelar edición")
                    }

                } else {

                    Text(
                        text = "Información del registro",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (!detalleViewModel.tieneDetalles) {

                        Text(
                            "Este registro no tiene detalles específicos. " +
                                    "Puede haber sido creado con el formulario anterior."
                        )

                    } else {

                        detalleViewModel.detalles.forEach {
                                (etiqueta, valor) ->

                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Column(
                                    modifier = Modifier.padding(14.dp)
                                ) {

                                    Text(
                                        text = etiqueta,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    Text(valor)
                                }
                            }
                        }
                    }

                    if (detalleViewModel.mensaje.isNotEmpty()) {

                        Text(
                            text = detalleViewModel.mensaje,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (detalleViewModel.tieneDetalles) {

                        Button(
                            onClick = {
                                detalleViewModel.iniciarEdicion()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text("Editar registro")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CampoEdicion(
    etiqueta: String,
    valor: String,
    habilitado: Boolean,
    alCambiar: (String) -> Unit
) {

    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        label = {
            Text(etiqueta)
        },
        enabled = habilitado,
        modifier = Modifier.fillMaxWidth(),
        maxLines = 3
    )
}

@Composable
private fun ControlIntensidad(
    titulo: String,
    valor: Int,
    habilitado: Boolean,
    alCambiar: (Int) -> Unit
) {

    Text(
        text = "$titulo: $valor/10",
        fontWeight = FontWeight.Bold
    )

    Slider(
        value = valor.toFloat(),
        onValueChange = {
            alCambiar(it.roundToInt())
        },
        valueRange = 1f..10f,
        steps = 8,
        enabled = habilitado
    )
}

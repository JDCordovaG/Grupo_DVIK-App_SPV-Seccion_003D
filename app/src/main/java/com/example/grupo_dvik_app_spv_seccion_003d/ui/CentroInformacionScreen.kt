package com.example.grupo_dvik_app_spv_seccion_003d.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.grupo_dvik_app_spv_seccion_003d.model.Ambito

@Composable
fun CentroInformacionScreen(
    onVolver: () -> Unit,
    onRegistrarAmbito: (Ambito) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Centro de Información",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Conoce los cuatro ámbitos de Mente en Equilibrio y aprende qué puedes registrar en cada uno."
        )

        OutlinedButton(
            onClick = onVolver
        ) {
            Text("Volver al inicio")
        }

        Ambito.entries.forEach { ambito ->

            val explicacion = when (ambito) {

                Ambito.NEURODESARROLLO ->
                    "Este ámbito permite organizar experiencias relacionadas con rutinas, tareas y estrategias personales."

                Ambito.DBT ->
                    "DBT significa Terapia Dialéctico-Conductual. Incluye habilidades para reconocer emociones y responder a situaciones difíciles."

                Ambito.ANIMO ->
                    "Permite identificar cómo te sientes y registrar las situaciones relacionadas con tu estado de ánimo."

                Ambito.ADICCIONES ->
                    "Permite registrar impulsos, situaciones desencadenantes y acciones protectoras relacionadas con objetivos personales."
            }

            val contenido = when (ambito) {

                Ambito.NEURODESARROLLO ->
                    "Rutina o tarea, contexto, estrategia utilizada y resultado percibido."

                Ambito.DBT ->
                    "Habilidad utilizada, situación o emoción, intensidad emocional y utilidad percibida."

                Ambito.ANIMO ->
                    "Categoría de ánimo, nivel de intensidad, contexto y nota opcional."

                Ambito.ADICCIONES ->
                    "Nivel de impulso, desencadenante, acción protectora y estado del objetivo."
            }

            val ejemplo = when (ambito) {

                Ambito.NEURODESARROLLO ->
                    "Organicé mis tareas con una lista y logré terminar una actividad pendiente."

                Ambito.DBT ->
                    "Durante una discusión utilicé STOP para detenerme antes de responder."

                Ambito.ANIMO ->
                    "Hoy me sentí bien después de compartir tiempo con mi familia."

                Ambito.ADICCIONES ->
                    "Identifiqué una situación que generó un impulso y elegí una actividad alternativa."
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text = ambito.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "¿Qué es?",
                        fontWeight = FontWeight.Bold
                    )

                    Text(explicacion)

                    Text(
                        text = "¿Qué puedes registrar?",
                        fontWeight = FontWeight.Bold
                    )

                    Text(contenido)

                    Text(
                        text = "Ejemplo",
                        fontWeight = FontWeight.Bold
                    )

                    Text(ejemplo)

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Button(
                        onClick = {
                            onRegistrarAmbito(ambito)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("Registrar en ${ambito.nombre}")
                    }
                }
            }
        }

        Text(
            text = "Contenido educativo y ejemplos ficticios. Esta aplicación no realiza diagnósticos ni reemplaza la atención profesional.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

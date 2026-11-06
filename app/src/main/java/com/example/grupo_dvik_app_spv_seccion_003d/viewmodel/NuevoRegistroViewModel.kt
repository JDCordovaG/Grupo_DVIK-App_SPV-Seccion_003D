package com.example.grupo_dvik_app_spv_seccion_003d.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.AppDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.MIGRACION_1_2
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.MIGRACION_2_3
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleAdiccionesEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleAnimoEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleNeurodesarrolloEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.HabilidadDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import com.example.grupo_dvik_app_spv_seccion_003d.model.Ambito
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NuevoRegistroViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "mente_en_equilibrio.db"
        )
            .addMigrations(
                MIGRACION_1_2,
                MIGRACION_2_3
            )
            .build()

    private val dao =
        database.detalleRegistroDao()

    var mensaje by mutableStateOf("")
        private set

    var guardando by mutableStateOf(false)
        private set

    var guardadoCorrectamente by mutableStateOf(false)
        private set

    var habilidadesDbt by mutableStateOf(
        emptyList<HabilidadDbtEntity>()
    )
        private set

    var cargandoHabilidades by mutableStateOf(true)
        private set

    init {
        cargarHabilidadesDbt()
    }

    private fun cargarHabilidadesDbt() {

        viewModelScope.launch {

            try {

                val existentes = dao.obtenerHabilidades()

                if (existentes.isEmpty()) {

                    val habilidades = listOf(
                        HabilidadDbtEntity(
                            nombreHabilidad = "Observar",
                            categoria = "Atención plena",
                            descripcionEducativa =
                                "Prestar atención a lo que ocurre."
                        ),
                        HabilidadDbtEntity(
                            nombreHabilidad = "Describir",
                            categoria = "Atención plena",
                            descripcionEducativa =
                                "Poner en palabras lo que se observa."
                        ),
                        HabilidadDbtEntity(
                            nombreHabilidad = "STOP",
                            categoria = "Tolerancia al malestar",
                            descripcionEducativa =
                                "Detenerse y observar antes de actuar."
                        ),
                        HabilidadDbtEntity(
                            nombreHabilidad = "Acción opuesta",
                            categoria = "Regulación emocional",
                            descripcionEducativa =
                                "Reconocer un impulso y considerar otra acción."
                        ),
                        HabilidadDbtEntity(
                            nombreHabilidad = "DEAR MAN",
                            categoria = "Efectividad interpersonal",
                            descripcionEducativa =
                                "Organizar una petición de manera clara."
                        )
                    )

                    for (habilidad in habilidades) {
                        dao.insertarHabilidad(habilidad)
                    }
                }

                habilidadesDbt = dao.obtenerHabilidades()

            } catch (e: Exception) {

                habilidadesDbt = emptyList()

            } finally {

                cargandoHabilidades = false
            }
        }
    }

    private fun obtenerFechaActual(): String {

        return SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        ).format(Date())
    }

    private fun nuevoRegistro(
        idUsuario: Int,
        ambito: Ambito
    ): RegistroEntity {

        return RegistroEntity(
            idUsuario = idUsuario,
            idAmbito = ambito.id,
            fechaHora = obtenerFechaActual(),
            estadoSincronizacion = "Pendiente"
        )
    }

    fun limpiarMensaje() {
        mensaje = ""
    }

    fun guardarAnimo(
        idUsuario: Int,
        categoria: String,
        intensidad: Int,
        contexto: String,
        nota: String
    ) {

        if (guardando || guardadoCorrectamente) return

        if (idUsuario <= 0) {
            mensaje = "Debe iniciar sesión"
            return
        }

        if (categoria.isBlank()) {
            mensaje = "Seleccione un estado de ánimo"
            return
        }

        if (intensidad !in 1..10) {
            mensaje = "Seleccione una intensidad válida"
            return
        }

        if (contexto.isBlank()) {
            mensaje = "Ingrese el contexto"
            return
        }

        guardando = true
        mensaje = ""

        viewModelScope.launch {

            try {

                val detalle = DetalleAnimoEntity(
                    idRegistro = 0,
                    categoriaAnimo = categoria,
                    nivelIntensidad = intensidad,
                    contexto = contexto.trim(),
                    notaOpcional = nota.trim().ifEmpty { null }
                )

                dao.crearRegistroAnimo(
                    nuevoRegistro(idUsuario, Ambito.ANIMO),
                    detalle
                )

                guardadoCorrectamente = true
                mensaje = "Estado de ánimo guardado correctamente"

            } catch (e: Exception) {

                mensaje = "No se pudo guardar el registro"

            } finally {

                guardando = false
            }
        }
    }

    fun guardarNeurodesarrollo(
        idUsuario: Int,
        rutinaTarea: String,
        contexto: String,
        estrategiaUtilizada: String,
        resultadoPercibido: String
    ) {

        if (guardando || guardadoCorrectamente) return

        if (idUsuario <= 0) {
            mensaje = "Debe iniciar sesión"
            return
        }

        if (rutinaTarea.isBlank()) {
            mensaje = "Ingrese una rutina o tarea"
            return
        }

        if (contexto.isBlank()) {
            mensaje = "Ingrese el contexto"
            return
        }

        if (estrategiaUtilizada.isBlank()) {
            mensaje = "Ingrese la estrategia utilizada"
            return
        }

        if (resultadoPercibido.isBlank()) {
            mensaje = "Ingrese el resultado percibido"
            return
        }

        guardando = true
        mensaje = ""

        viewModelScope.launch {

            try {

                val detalle = DetalleNeurodesarrolloEntity(
                    idRegistro = 0,
                    rutinaTarea = rutinaTarea.trim(),
                    contexto = contexto.trim(),
                    estrategiaUtilizada = estrategiaUtilizada.trim(),
                    resultadoPercibido = resultadoPercibido.trim()
                )

                dao.crearRegistroNeurodesarrollo(
                    nuevoRegistro(
                        idUsuario,
                        Ambito.NEURODESARROLLO
                    ),
                    detalle
                )

                guardadoCorrectamente = true
                mensaje = "Neurodesarrollo guardado correctamente"

            } catch (e: Exception) {

                mensaje = "No se pudo guardar el registro"

            } finally {

                guardando = false
            }
        }
    }

    fun guardarDbt(
        idUsuario: Int,
        idHabilidad: Int,
        situacionEmocion: String,
        nivelIntensidad: Int,
        utilidadPercibida: String
    ) {

        if (guardando || guardadoCorrectamente) return

        if (idUsuario <= 0) {
            mensaje = "Debe iniciar sesión"
            return
        }

        if (habilidadesDbt.none { it.idHabilidad == idHabilidad }) {
            mensaje = "Seleccione una habilidad DBT válida"
            return
        }

        if (situacionEmocion.isBlank()) {
            mensaje = "Ingrese la situación o emoción"
            return
        }

        if (nivelIntensidad !in 1..10) {
            mensaje = "Seleccione una intensidad válida"
            return
        }

        if (utilidadPercibida.isBlank()) {
            mensaje = "Ingrese la utilidad percibida"
            return
        }

        guardando = true
        mensaje = ""

        viewModelScope.launch {

            try {

                val detalle = DetalleDbtEntity(
                    idRegistro = 0,
                    idHabilidad = idHabilidad,
                    situacionEmocion = situacionEmocion.trim(),
                    nivelIntensidad = nivelIntensidad,
                    utilidadPercibida = utilidadPercibida.trim()
                )

                dao.crearRegistroDbt(
                    nuevoRegistro(idUsuario, Ambito.DBT),
                    detalle
                )

                guardadoCorrectamente = true
                mensaje = "Registro DBT guardado correctamente"

            } catch (e: Exception) {

                mensaje = "No se pudo guardar el registro DBT"

            } finally {

                guardando = false
            }
        }
    }

    fun guardarAdicciones(
        idUsuario: Int,
        nivelImpulso: Int,
        desencadenante: String,
        accionProtectora: String,
        estadoObjetivo: String
    ) {

        if (guardando || guardadoCorrectamente) return

        if (idUsuario <= 0) {
            mensaje = "Debe iniciar sesión"
            return
        }

        if (nivelImpulso !in 1..10) {
            mensaje = "Seleccione un nivel de impulso válido"
            return
        }

        if (desencadenante.isBlank()) {
            mensaje = "Ingrese el desencadenante"
            return
        }

        if (accionProtectora.isBlank()) {
            mensaje = "Ingrese la acción protectora"
            return
        }

        if (estadoObjetivo.isBlank()) {
            mensaje = "Ingrese el estado del objetivo"
            return
        }

        guardando = true
        mensaje = ""

        viewModelScope.launch {

            try {

                val detalle = DetalleAdiccionesEntity(
                    idRegistro = 0,
                    nivelImpulsoCraving = nivelImpulso,
                    desencadenante = desencadenante.trim(),
                    accionProtectora = accionProtectora.trim(),
                    estadoObjetivo = estadoObjetivo.trim()
                )

                dao.crearRegistroAdicciones(
                    nuevoRegistro(idUsuario, Ambito.ADICCIONES),
                    detalle
                )

                guardadoCorrectamente = true
                mensaje = "Registro de Adicciones guardado correctamente"

            } catch (e: Exception) {

                mensaje = "No se pudo guardar el registro"

            } finally {

                guardando = false
            }
        }
    }

    override fun onCleared() {

        database.close()

        super.onCleared()
    }
}

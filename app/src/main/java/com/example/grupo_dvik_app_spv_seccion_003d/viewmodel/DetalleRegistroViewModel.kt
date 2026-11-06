
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
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.HabilidadDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import com.example.grupo_dvik_app_spv_seccion_003d.model.Ambito
import kotlinx.coroutines.launch

class DetalleRegistroViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "mente_en_equilibrio.db"
    )
        .addMigrations(MIGRACION_1_2, MIGRACION_2_3)
        .build()

    private val registroDao = database.registroDao()
    private val detalleDao = database.detalleRegistroDao()

    private var idUsuarioActual = 0

    var registro by mutableStateOf<RegistroEntity?>(null)
        private set

    var detalles by mutableStateOf(
        emptyList<Pair<String, String>>()
    )
        private set

    var habilidadesDbt by mutableStateOf(
        emptyList<HabilidadDbtEntity>()
    )
        private set

    var cargando by mutableStateOf(true)
        private set

    var guardando by mutableStateOf(false)
        private set

    var editando by mutableStateOf(false)
        private set

    var tieneDetalles by mutableStateOf(false)
        private set

    var mensaje by mutableStateOf("")
        private set

    // Estado de animo
    var categoriaAnimo by mutableStateOf("")
    var intensidadAnimo by mutableStateOf(5)
    var contextoAnimo by mutableStateOf("")
    var notaAnimo by mutableStateOf("")

    // Neurodesarrollo
    var rutinaTarea by mutableStateOf("")
    var contextoNeuro by mutableStateOf("")
    var estrategiaUtilizada by mutableStateOf("")
    var resultadoPercibido by mutableStateOf("")

    // DBT
    var idHabilidad by mutableStateOf(0)
    var situacionEmocion by mutableStateOf("")
    var intensidadDbt by mutableStateOf(5)
    var utilidadPercibida by mutableStateOf("")

    // Adicciones e impulsos
    var nivelImpulso by mutableStateOf(5)
    var desencadenante by mutableStateOf("")
    var accionProtectora by mutableStateOf("")
    var estadoObjetivo by mutableStateOf("")

    fun cargarDetalle(
        idRegistro: Int,
        idUsuario: Int
    ) {

        cargando = true
        editando = false
        tieneDetalles = false
        mensaje = ""
        registro = null
        detalles = emptyList()
        idUsuarioActual = idUsuario

        viewModelScope.launch {

            try {

                val encontrado =
                    registroDao.obtenerPorId(idRegistro)

                if (
                    encontrado == null ||
                    encontrado.idUsuario != idUsuario ||
                    idUsuario <= 0
                ) {

                    mensaje = "Registro no encontrado o sin acceso"

                } else {

                    registro = encontrado

                    when (encontrado.idAmbito) {

                        Ambito.ANIMO.id -> {

                            val dato =
                                detalleDao.obtenerAnimo(idRegistro)

                            if (dato != null) {

                                categoriaAnimo = dato.categoriaAnimo
                                intensidadAnimo = dato.nivelIntensidad
                                contextoAnimo = dato.contexto
                                notaAnimo = dato.notaOpcional ?: ""

                                tieneDetalles = true
                            }
                        }

                        Ambito.NEURODESARROLLO.id -> {

                            val dato =
                                detalleDao.obtenerNeurodesarrollo(
                                    idRegistro
                                )

                            if (dato != null) {

                                rutinaTarea = dato.rutinaTarea
                                contextoNeuro = dato.contexto
                                estrategiaUtilizada =
                                    dato.estrategiaUtilizada
                                resultadoPercibido =
                                    dato.resultadoPercibido

                                tieneDetalles = true
                            }
                        }

                        Ambito.DBT.id -> {

                            val dato =
                                detalleDao.obtenerDbt(idRegistro)

                            if (dato != null) {

                                habilidadesDbt =
                                    detalleDao.obtenerHabilidades()

                                idHabilidad = dato.idHabilidad
                                situacionEmocion =
                                    dato.situacionEmocion
                                intensidadDbt =
                                    dato.nivelIntensidad
                                utilidadPercibida =
                                    dato.utilidadPercibida

                                tieneDetalles = true
                            }
                        }

                        Ambito.ADICCIONES.id -> {

                            val dato =
                                detalleDao.obtenerAdicciones(
                                    idRegistro
                                )

                            if (dato != null) {

                                nivelImpulso =
                                    dato.nivelImpulsoCraving
                                desencadenante =
                                    dato.desencadenante
                                accionProtectora =
                                    dato.accionProtectora
                                estadoObjetivo =
                                    dato.estadoObjetivo

                                tieneDetalles = true
                            }
                        }
                    }

                    if (tieneDetalles) {

                        detalles = prepararDetalles(
                            encontrado.idAmbito
                        )
                    }
                }

            } catch (e: Exception) {

                mensaje = "No se pudo cargar el registro"

            } finally {

                cargando = false
            }
        }
    }

    private fun prepararDetalles(
        idAmbito: Int
    ): List<Pair<String, String>> {

        return when (idAmbito) {

            Ambito.ANIMO.id -> {

                listOf(
                    "Categoría" to categoriaAnimo,
                    "Intensidad" to "$intensidadAnimo/10",
                    "Contexto" to contextoAnimo,
                    "Nota" to (
                            notaAnimo.ifBlank { "Sin nota" }
                            )
                )
            }

            Ambito.NEURODESARROLLO.id -> {

                listOf(
                    "Rutina o tarea" to rutinaTarea,
                    "Contexto" to contextoNeuro,
                    "Estrategia utilizada" to estrategiaUtilizada,
                    "Resultado percibido" to resultadoPercibido
                )
            }

            Ambito.DBT.id -> {

                val habilidad = habilidadesDbt.find {
                    it.idHabilidad == idHabilidad
                }

                listOf(
                    "Habilidad" to (
                            habilidad?.nombreHabilidad
                                ?: "No encontrada"
                            ),
                    "Situación o emoción" to situacionEmocion,
                    "Intensidad emocional" to "$intensidadDbt/10",
                    "Utilidad percibida" to utilidadPercibida
                )
            }

            Ambito.ADICCIONES.id -> {

                listOf(
                    "Nivel de impulso" to "$nivelImpulso/10",
                    "Desencadenante" to desencadenante,
                    "Acción protectora" to accionProtectora,
                    "Estado del objetivo" to estadoObjetivo
                )
            }

            else -> emptyList()
        }
    }

    fun iniciarEdicion() {

        if (
            registro != null &&
            tieneDetalles &&
            !cargando &&
            !guardando
        ) {

            editando = true
            mensaje = ""
        }
    }

    fun cancelarEdicion() {

        if (guardando) {
            return
        }

        val actual = registro ?: return

        // Volver a cargar los valores originales.
        cargarDetalle(
            actual.idRegistro,
            idUsuarioActual
        )
    }

    private fun validarEdicion(): String {

        return when (registro?.idAmbito) {

            Ambito.ANIMO.id -> {

                when {

                    categoriaAnimo.isBlank() ->
                        "Seleccione un estado de ánimo"

                    intensidadAnimo !in 1..10 ->
                        "Seleccione una intensidad válida"

                    contextoAnimo.isBlank() ->
                        "Ingrese el contexto"

                    else -> ""
                }
            }

            Ambito.NEURODESARROLLO.id -> {

                when {

                    rutinaTarea.isBlank() ->
                        "Ingrese una rutina o tarea"

                    contextoNeuro.isBlank() ->
                        "Ingrese el contexto"

                    estrategiaUtilizada.isBlank() ->
                        "Ingrese la estrategia utilizada"

                    resultadoPercibido.isBlank() ->
                        "Ingrese el resultado percibido"

                    else -> ""
                }
            }

            Ambito.DBT.id -> {

                when {

                    habilidadesDbt.none {
                        it.idHabilidad == idHabilidad
                    } ->
                        "Seleccione una habilidad DBT válida"

                    situacionEmocion.isBlank() ->
                        "Ingrese la situación o emoción"

                    intensidadDbt !in 1..10 ->
                        "Seleccione una intensidad válida"

                    utilidadPercibida.isBlank() ->
                        "Ingrese la utilidad percibida"

                    else -> ""
                }
            }

            Ambito.ADICCIONES.id -> {

                when {

                    nivelImpulso !in 1..10 ->
                        "Seleccione un nivel de impulso válido"

                    desencadenante.isBlank() ->
                        "Ingrese el desencadenante"

                    accionProtectora.isBlank() ->
                        "Ingrese la acción protectora"

                    estadoObjetivo.isBlank() ->
                        "Ingrese el estado del objetivo"

                    else -> ""
                }
            }

            else -> "Ámbito no válido"
        }
    }

    fun guardarCambios() {

        val actual = registro ?: return

        if (
            !editando ||
            !tieneDetalles ||
            cargando ||
            guardando ||
            idUsuarioActual <= 0
        ) {
            return
        }

        val error = validarEdicion()

        if (error.isNotEmpty()) {

            mensaje = error
            return
        }

        guardando = true
        mensaje = ""

        viewModelScope.launch {

            try {

                val comprobacion =
                    registroDao.obtenerPorId(
                        actual.idRegistro
                    )

                if (
                    comprobacion == null ||
                    comprobacion.idUsuario != idUsuarioActual ||
                    comprobacion.idAmbito != actual.idAmbito
                ) {

                    mensaje = "No se puede modificar este registro"
                    return@launch
                }

                when (actual.idAmbito) {

                    Ambito.ANIMO.id -> {

                        val anterior =
                            detalleDao.obtenerAnimo(
                                actual.idRegistro
                            ) ?: error("Detalle no encontrado")

                        detalleDao.actualizarAnimo(
                            anterior.copy(
                                categoriaAnimo = categoriaAnimo,
                                nivelIntensidad = intensidadAnimo,
                                contexto = contextoAnimo.trim(),
                                notaOpcional =
                                    notaAnimo.trim().ifEmpty {
                                        null
                                    }
                            )
                        )

                        contextoAnimo = contextoAnimo.trim()
                        notaAnimo = notaAnimo.trim()
                    }

                    Ambito.NEURODESARROLLO.id -> {

                        val anterior =
                            detalleDao.obtenerNeurodesarrollo(
                                actual.idRegistro
                            ) ?: error("Detalle no encontrado")

                        detalleDao.actualizarNeurodesarrollo(
                            anterior.copy(
                                rutinaTarea = rutinaTarea.trim(),
                                contexto = contextoNeuro.trim(),
                                estrategiaUtilizada =
                                    estrategiaUtilizada.trim(),
                                resultadoPercibido =
                                    resultadoPercibido.trim()
                            )
                        )

                        rutinaTarea = rutinaTarea.trim()
                        contextoNeuro = contextoNeuro.trim()
                        estrategiaUtilizada =
                            estrategiaUtilizada.trim()
                        resultadoPercibido =
                            resultadoPercibido.trim()
                    }

                    Ambito.DBT.id -> {

                        val anterior =
                            detalleDao.obtenerDbt(
                                actual.idRegistro
                            ) ?: error("Detalle no encontrado")

                        detalleDao.actualizarDbt(
                            anterior.copy(
                                idHabilidad = idHabilidad,
                                situacionEmocion =
                                    situacionEmocion.trim(),
                                nivelIntensidad = intensidadDbt,
                                utilidadPercibida =
                                    utilidadPercibida.trim()
                            )
                        )

                        situacionEmocion = situacionEmocion.trim()
                        utilidadPercibida = utilidadPercibida.trim()
                    }

                    Ambito.ADICCIONES.id -> {

                        val anterior =
                            detalleDao.obtenerAdicciones(
                                actual.idRegistro
                            ) ?: error("Detalle no encontrado")

                        detalleDao.actualizarAdicciones(
                            anterior.copy(
                                nivelImpulsoCraving = nivelImpulso,
                                desencadenante =
                                    desencadenante.trim(),
                                accionProtectora =
                                    accionProtectora.trim(),
                                estadoObjetivo =
                                    estadoObjetivo.trim()
                            )
                        )

                        desencadenante = desencadenante.trim()
                        accionProtectora = accionProtectora.trim()
                        estadoObjetivo = estadoObjetivo.trim()
                    }

                    else -> error("Ámbito no válido")
                }

                detalles = prepararDetalles(
                    actual.idAmbito
                )

                editando = false
                mensaje = "Cambios guardados correctamente"

            } catch (e: Exception) {

                mensaje = "No se pudieron guardar los cambios"

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

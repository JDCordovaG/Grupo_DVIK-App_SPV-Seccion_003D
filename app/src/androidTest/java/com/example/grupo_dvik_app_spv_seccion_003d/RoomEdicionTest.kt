package com.example.grupo_dvik_app_spv_seccion_003d

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.AppDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleAdiccionesEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleNeurodesarrolloEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.HabilidadDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomEdicionTest {

    private lateinit var database: AppDatabase

    @Before
    fun preparar() {
        val contexto =
            InstrumentationRegistry.getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(
            contexto,
            AppDatabase::class.java
        ).build()
    }

    @After
    fun cerrar() {
        database.close()
    }

    @Test
    fun editarNeurodesarrollo() = runBlocking {
        val dao = database.detalleRegistroDao()

        dao.crearRegistroNeurodesarrollo(
            RegistroEntity(
                idUsuario = 1,
                idAmbito = 1,
                fechaHora = "06/11/2026 10:00",
                estadoSincronizacion = "Pendiente"
            ),
            DetalleNeurodesarrolloEntity(
                idRegistro = 0,
                rutinaTarea = "Estudiar",
                contexto = "Casa",
                estrategiaUtilizada = "Lista",
                resultadoPercibido = "Pendiente"
            )
        )

        val anterior = dao.obtenerNeurodesarrollo(1)!!

        dao.actualizarNeurodesarrollo(
            anterior.copy(
                resultadoPercibido = "Completado"
            )
        )

        assertEquals(
            "Completado",
            dao.obtenerNeurodesarrollo(1)?.resultadoPercibido
        )
    }

    @Test
    fun editarDbt() = runBlocking {
        val dao = database.detalleRegistroDao()

        dao.insertarHabilidad(
            HabilidadDbtEntity(
                nombreHabilidad = "STOP",
                categoria = "Tolerancia al malestar",
                descripcionEducativa = "Detenerse antes de actuar"
            )
        )

        val idHabilidad =
            dao.obtenerHabilidades()[0].idHabilidad

        dao.crearRegistroDbt(
            RegistroEntity(
                idUsuario = 1,
                idAmbito = 2,
                fechaHora = "06/11/2026 10:00",
                estadoSincronizacion = "Pendiente"
            ),
            DetalleDbtEntity(
                idRegistro = 0,
                idHabilidad = idHabilidad,
                situacionEmocion = "Nervios",
                nivelIntensidad = 5,
                utilidadPercibida = "Regular"
            )
        )

        val anterior = dao.obtenerDbt(1)!!

        dao.actualizarDbt(
            anterior.copy(
                nivelIntensidad = 2,
                utilidadPercibida = "Buena"
            )
        )

        val actualizado = dao.obtenerDbt(1)

        assertEquals(2, actualizado?.nivelIntensidad)
        assertEquals("Buena", actualizado?.utilidadPercibida)
    }

    @Test
    fun editarAdicciones() = runBlocking {
        val dao = database.detalleRegistroDao()

        dao.crearRegistroAdicciones(
            RegistroEntity(
                idUsuario = 1,
                idAmbito = 4,
                fechaHora = "06/11/2026 10:00",
                estadoSincronizacion = "Pendiente"
            ),
            DetalleAdiccionesEntity(
                idRegistro = 0,
                nivelImpulsoCraving = 6,
                desencadenante = "Estres",
                accionProtectora = "Caminar",
                estadoObjetivo = "En progreso"
            )
        )

        val anterior = dao.obtenerAdicciones(1)!!

        dao.actualizarAdicciones(
            anterior.copy(
                nivelImpulsoCraving = 2,
                accionProtectora = "Respirar"
            )
        )

        val actualizado = dao.obtenerAdicciones(1)

        assertEquals(2, actualizado?.nivelImpulsoCraving)
        assertEquals(
            "Respirar",
            actualizado?.accionProtectora
        )
    }

    @Test
    fun usuarioPuedeActualizarEstadoPropio() = runBlocking {
        val id = database.detalleRegistroDao().insertarRegistro(
            RegistroEntity(
                idUsuario = 1,
                idAmbito = 3,
                fechaHora = "06/11/2026 10:00",
                estadoSincronizacion = "Pendiente"
            )
        ).toInt()

        val actualizados =
            database.registroDao().actualizarEstadoDelUsuario(
                id,
                1,
                "Sincronizado"
            )

        assertEquals(1, actualizados)

        assertEquals(
            "Sincronizado",
            database.registroDao()
                .obtenerPorId(id)?.estadoSincronizacion
        )
    }

    @Test
    fun usuarioNoPuedeActualizarEstadoAjeno() = runBlocking {
        val id = database.detalleRegistroDao().insertarRegistro(
            RegistroEntity(
                idUsuario = 1,
                idAmbito = 3,
                fechaHora = "06/11/2026 10:00",
                estadoSincronizacion = "Pendiente"
            )
        ).toInt()

        val actualizados =
            database.registroDao().actualizarEstadoDelUsuario(
                id,
                2,
                "Sincronizado"
            )

        assertEquals(0, actualizados)

        assertEquals(
            "Pendiente",
            database.registroDao()
                .obtenerPorId(id)?.estadoSincronizacion
        )
    }
}

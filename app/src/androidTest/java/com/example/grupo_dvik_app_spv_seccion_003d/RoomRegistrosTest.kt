package com.example.grupo_dvik_app_spv_seccion_003d

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.AppDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleAdiccionesEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleAnimoEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleNeurodesarrolloEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.HabilidadDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomRegistrosTest {

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
    fun crearRegistroNeurodesarrollo() = runBlocking {
        val registro = RegistroEntity(
            idUsuario = 1,
            idAmbito = 1,
            fechaHora = "06/11/2026 10:00",
            estadoSincronizacion = "Pendiente"
        )

        val detalle = DetalleNeurodesarrolloEntity(
            idRegistro = 0,
            rutinaTarea = "Organizar tareas",
            contexto = "Casa",
            estrategiaUtilizada = "Lista",
            resultadoPercibido = "Completado"
        )

        database.detalleRegistroDao()
            .crearRegistroNeurodesarrollo(registro, detalle)

        assertEquals(1, database.registroDao().contar())

        assertEquals(
            "Organizar tareas",
            database.detalleRegistroDao()
                .obtenerNeurodesarrollo(1)?.rutinaTarea
        )
    }

    @Test
    fun crearRegistroDbt() = runBlocking {
        val dao = database.detalleRegistroDao()

        dao.insertarHabilidad(
            HabilidadDbtEntity(
                nombreHabilidad = "STOP",
                categoria = "Tolerancia al malestar",
                descripcionEducativa = "Detenerse antes de actuar"
            )
        )

        val idHabilidad =
            dao.obtenerHabilidades().first().idHabilidad

        val registro = RegistroEntity(
            idUsuario = 1,
            idAmbito = 2,
            fechaHora = "06/11/2026 10:00",
            estadoSincronizacion = "Pendiente"
        )

        val detalle = DetalleDbtEntity(
            idRegistro = 0,
            idHabilidad = idHabilidad,
            situacionEmocion = "Discusion",
            nivelIntensidad = 5,
            utilidadPercibida = "Util"
        )

        dao.crearRegistroDbt(registro, detalle)

        assertEquals(
            idHabilidad,
            dao.obtenerDbt(1)?.idHabilidad
        )

        assertEquals(1, database.registroDao().contar())
    }

    @Test
    fun crearRegistroAnimo() = runBlocking {
        val registro = RegistroEntity(
            idUsuario = 1,
            idAmbito = 3,
            fechaHora = "06/11/2026 10:00",
            estadoSincronizacion = "Pendiente"
        )

        val detalle = DetalleAnimoEntity(
            idRegistro = 0,
            categoriaAnimo = "Feliz",
            nivelIntensidad = 4,
            contexto = "Universidad",
            notaOpcional = "Buen dia"
        )

        database.detalleRegistroDao()
            .crearRegistroAnimo(registro, detalle)

        assertEquals(
            "Feliz",
            database.detalleRegistroDao()
                .obtenerAnimo(1)?.categoriaAnimo
        )

        assertEquals(1, database.registroDao().contar())
    }

    @Test
    fun crearRegistroAdicciones() = runBlocking {
        val registro = RegistroEntity(
            idUsuario = 1,
            idAmbito = 4,
            fechaHora = "06/11/2026 10:00",
            estadoSincronizacion = "Pendiente"
        )

        val detalle = DetalleAdiccionesEntity(
            idRegistro = 0,
            nivelImpulsoCraving = 3,
            desencadenante = "Estres",
            accionProtectora = "Caminar",
            estadoObjetivo = "En progreso"
        )

        database.detalleRegistroDao()
            .crearRegistroAdicciones(registro, detalle)

        assertEquals(
            "Caminar",
            database.detalleRegistroDao()
                .obtenerAdicciones(1)?.accionProtectora
        )

        assertEquals(1, database.registroDao().contar())
    }

    @Test
    fun editarRegistroAnimo() = runBlocking {
        val dao = database.detalleRegistroDao()

        dao.crearRegistroAnimo(
            RegistroEntity(
                idUsuario = 1,
                idAmbito = 3,
                fechaHora = "06/11/2026 10:00",
                estadoSincronizacion = "Pendiente"
            ),
            DetalleAnimoEntity(
                idRegistro = 0,
                categoriaAnimo = "Triste",
                nivelIntensidad = 5,
                contexto = "Casa"
            )
        )

        val anterior = dao.obtenerAnimo(1)!!

        dao.actualizarAnimo(
            anterior.copy(
                categoriaAnimo = "Tranquilo",
                nivelIntensidad = 2
            )
        )

        val actualizado = dao.obtenerAnimo(1)

        assertEquals(
            "Tranquilo",
            actualizado?.categoriaAnimo
        )

        assertEquals(2, actualizado?.nivelIntensidad)
    }

    @Test
    fun eliminarRegistroYDetalle() = runBlocking {
        val dao = database.detalleRegistroDao()

        dao.crearRegistroAnimo(
            RegistroEntity(
                idUsuario = 1,
                idAmbito = 3,
                fechaHora = "06/11/2026 10:00",
                estadoSincronizacion = "Pendiente"
            ),
            DetalleAnimoEntity(
                idRegistro = 0,
                categoriaAnimo = "Feliz",
                nivelIntensidad = 3,
                contexto = "Casa"
            )
        )

        val eliminados =
            database.registroDao().eliminarDelUsuario(1, 1)

        assertEquals(1, eliminados)

        assertNull(
            database.registroDao().obtenerPorId(1)
        )

        assertNull(dao.obtenerAnimo(1))
    }

    @Test
    fun separarRegistrosPorUsuario() = runBlocking {
        val dao = database.detalleRegistroDao()

        dao.insertarRegistro(
            RegistroEntity(
                idUsuario = 1,
                idAmbito = 3,
                fechaHora = "06/11/2026 10:00",
                estadoSincronizacion = "Pendiente"
            )
        )

        dao.insertarRegistro(
            RegistroEntity(
                idUsuario = 2,
                idAmbito = 4,
                fechaHora = "06/11/2026 11:00",
                estadoSincronizacion = "Pendiente"
            )
        )

        val usuarioUno =
            database.registroDao().observarPorUsuario(1).first()

        val usuarioDos =
            database.registroDao().observarPorUsuario(2).first()

        assertEquals(1, usuarioUno.size)
        assertEquals(1, usuarioDos.size)
        assertEquals(1, usuarioUno[0].idUsuario)
        assertEquals(2, usuarioDos[0].idUsuario)
    }

    @Test
    fun usuarioNoPuedeEliminarRegistroAjeno() = runBlocking {
        val id = database.detalleRegistroDao().insertarRegistro(
            RegistroEntity(
                idUsuario = 1,
                idAmbito = 3,
                fechaHora = "06/11/2026 10:00",
                estadoSincronizacion = "Pendiente"
            )
        ).toInt()

        val eliminados =
            database.registroDao().eliminarDelUsuario(id, 2)

        assertEquals(0, eliminados)

        assertNotNull(
            database.registroDao().obtenerPorId(id)
        )
    }
}

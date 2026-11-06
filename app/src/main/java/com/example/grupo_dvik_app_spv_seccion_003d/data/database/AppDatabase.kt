
package com.example.grupo_dvik_app_spv_seccion_003d.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.dao.RegistroDao
import com.example.grupo_dvik_app_spv_seccion_003d.data.dao.UsuarioDao
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.UsuarioEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleNeurodesarrolloEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleAnimoEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.DetalleAdiccionesEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.HabilidadDbtEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.dao.DetalleRegistroDao

val MIGRACION_1_2 = object : Migration(1, 2) {

    override fun migrate(
        db: SupportSQLiteDatabase
    ) {

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS usuarios (
                idUsuario INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                nombreUsuario TEXT NOT NULL,
                nombreNormalizado TEXT NOT NULL,
                contrasenaHash TEXT NOT NULL,
                contrasenaSal TEXT NOT NULL,
                fotoRuta TEXT
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE UNIQUE INDEX IF NOT EXISTS
            index_usuarios_nombreNormalizado
            ON usuarios (nombreNormalizado)
            """.trimIndent()
        )
    }
}

val MIGRACION_2_3 = object : Migration(2, 3) {

    override fun migrate(
        db: SupportSQLiteDatabase
    ) {

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS detalle_neurodesarrollo (
                idRegistro INTEGER NOT NULL PRIMARY KEY,
                rutinaTarea TEXT NOT NULL,
                contexto TEXT NOT NULL,
                estrategiaUtilizada TEXT NOT NULL,
                resultadoPercibido TEXT NOT NULL,
                FOREIGN KEY (idRegistro)
                    REFERENCES registros(idRegistro)
                    ON UPDATE NO ACTION
                    ON DELETE CASCADE
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS catalogo_habilidades_dbt (
                idHabilidad INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                nombreHabilidad TEXT NOT NULL,
                categoria TEXT NOT NULL,
                descripcionEducativa TEXT NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS detalle_dbt (
                idRegistro INTEGER NOT NULL PRIMARY KEY,
                idHabilidad INTEGER NOT NULL,
                situacionEmocion TEXT NOT NULL,
                nivelIntensidad INTEGER NOT NULL,
                utilidadPercibida TEXT NOT NULL,
                FOREIGN KEY (idRegistro)
                    REFERENCES registros(idRegistro)
                    ON UPDATE NO ACTION
                    ON DELETE CASCADE,
                FOREIGN KEY (idHabilidad)
                    REFERENCES catalogo_habilidades_dbt(idHabilidad)
                    ON UPDATE NO ACTION
                    ON DELETE NO ACTION
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE INDEX IF NOT EXISTS
            index_detalle_dbt_idHabilidad
            ON detalle_dbt (idHabilidad)
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS detalle_animo (
                idRegistro INTEGER NOT NULL PRIMARY KEY,
                categoriaAnimo TEXT NOT NULL,
                nivelIntensidad INTEGER NOT NULL,
                contexto TEXT NOT NULL,
                notaOpcional TEXT,
                FOREIGN KEY (idRegistro)
                    REFERENCES registros(idRegistro)
                    ON UPDATE NO ACTION
                    ON DELETE CASCADE
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS detalle_adicciones (
                idRegistro INTEGER NOT NULL PRIMARY KEY,
                nivelImpulsoCraving INTEGER NOT NULL,
                desencadenante TEXT NOT NULL,
                accionProtectora TEXT NOT NULL,
                estadoObjetivo TEXT NOT NULL,
                FOREIGN KEY (idRegistro)
                    REFERENCES registros(idRegistro)
                    ON UPDATE NO ACTION
                    ON DELETE CASCADE
            )
            """.trimIndent()
        )
    }
}

@Database(
    entities = [
        RegistroEntity::class,
        UsuarioEntity::class,
        DetalleNeurodesarrolloEntity::class,
        DetalleDbtEntity::class,
        DetalleAnimoEntity::class,
        DetalleAdiccionesEntity::class,
        HabilidadDbtEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun registroDao(): RegistroDao

    abstract fun usuarioDao(): UsuarioDao

    abstract fun detalleRegistroDao(): DetalleRegistroDao
}

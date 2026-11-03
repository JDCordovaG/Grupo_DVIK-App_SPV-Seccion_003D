
package com.example.grupo_dvik_app_spv_seccion_003d.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.dao.RegistroDao
import com.example.grupo_dvik_app_spv_seccion_003d.data.dao.UsuarioDao
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.RegistroEntity
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.UsuarioEntity

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

@Database(
    entities = [
        RegistroEntity::class,
        UsuarioEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun registroDao(): RegistroDao

    abstract fun usuarioDao(): UsuarioDao
}

package com.example.grupo_dvik_app_spv_seccion_003d.data.database












import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.dao.RegistroDao
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.Registro


@Database(entities = [Registro::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registroDao(): RegistroDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "registro_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
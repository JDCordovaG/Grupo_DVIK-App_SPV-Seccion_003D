
package com.example.grupo_dvik_app_spv_seccion_003d.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.AppDatabase
import com.example.grupo_dvik_app_spv_seccion_003d.data.database.MIGRACION_1_2
import com.example.grupo_dvik_app_spv_seccion_003d.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PerfilViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "mente_en_equilibrio.db"
        )
            .addMigrations(MIGRACION_1_2)
            .build()

    private val repository =
        UsuarioRepository(
            database.usuarioDao()
        )

    private var idUsuarioActual = 0

    var fotoPerfil by mutableStateOf<Bitmap?>(null)
        private set

    var mensaje by mutableStateOf("")
        private set

    var guardando by mutableStateOf(false)
        private set

    fun cargarFoto(
        idUsuario: Int
    ) {

        if (idUsuario <= 0) {
            return
        }

        idUsuarioActual = idUsuario
        fotoPerfil = null
        mensaje = ""

        viewModelScope.launch {

            try {

                val imagen = withContext(
                    Dispatchers.IO
                ) {

                    val usuario =
                        repository.obtenerPorId(
                            idUsuario
                        )

                    val ruta = usuario?.fotoRuta

                    if (ruta != null) {

                        BitmapFactory.decodeFile(
                            ruta
                        )

                    } else {
                        null
                    }
                }

                if (idUsuarioActual == idUsuario) {
                    fotoPerfil = imagen
                }

            } catch (e: Exception) {

                if (idUsuarioActual == idUsuario) {
                    mensaje =
                        "No se pudo cargar la fotografía"
                }
            }
        }
    }

    fun guardarFoto(
        idUsuario: Int,
        bitmap: Bitmap?
    ) {

        if (bitmap == null) {

            mensaje =
                "No se pudo obtener la fotografía"

            return
        }

        if (
            idUsuario <= 0 ||
            idUsuario != idUsuarioActual
        ) {

            mensaje =
                "No se pudo identificar al usuario"

            return
        }

        if (guardando) {
            return
        }

        guardando = true
        mensaje = ""

        viewModelScope.launch {

            try {

                val guardada = withContext(
                    Dispatchers.IO
                ) {

                    val archivo = File(
                        getApplication<Application>().filesDir,
                        "perfil_usuario_$idUsuario.jpg"
                    )

                    FileOutputStream(archivo).use {
                            salida ->

                        val correcto =
                            bitmap.compress(
                                Bitmap.CompressFormat.JPEG,
                                90,
                                salida
                            )

                        if (!correcto) {
                            throw IOException(
                                "No se pudo guardar la imagen"
                            )
                        }
                    }

                    repository.actualizarFotoRuta(
                        idUsuario,
                        archivo.absolutePath
                    )
                }

                if (idUsuarioActual == idUsuario) {

                    if (guardada) {

                        fotoPerfil = bitmap

                        mensaje =
                            "Fotografía guardada correctamente"

                    } else {

                        mensaje =
                            "No se pudo actualizar el perfil"
                    }
                }

            } catch (e: Exception) {

                if (idUsuarioActual == idUsuario) {

                    mensaje =
                        "No se pudo guardar la fotografía"
                }

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

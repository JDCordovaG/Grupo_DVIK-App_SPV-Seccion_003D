
package com.example.grupo_dvik_app_spv_seccion_003d.repository

import com.example.grupo_dvik_app_spv_seccion_003d.data.dao.UsuarioDao
import com.example.grupo_dvik_app_spv_seccion_003d.data.entity.UsuarioEntity
import com.example.grupo_dvik_app_spv_seccion_003d.security.ProteccionContrasena
import com.example.grupo_dvik_app_spv_seccion_003d.validation.Validaciones
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class UsuarioRepository(
    private val dao: UsuarioDao
) {

    private val proteccion =
        ProteccionContrasena()

    private val validaciones =
        Validaciones()

    suspend fun registrarUsuario(
        nombre: String,
        contrasena: String
    ): Boolean {

        if (!validaciones.validarNombre(nombre)) {
            return false
        }

        if (!validaciones.validarContrasena(contrasena)) {
            return false
        }

        val nombreNormalizado =
            nombre.lowercase(Locale.ROOT)

        if (
            dao.obtenerPorNombre(
                nombreNormalizado
            ) != null
        ) {
            return false
        }

        val sal =
            proteccion.generarSal()

        val hash = withContext(
            Dispatchers.Default
        ) {
            proteccion.generarHash(
                contrasena,
                sal
            )
        }

        val usuario = UsuarioEntity(
            nombreUsuario = nombre,
            nombreNormalizado = nombreNormalizado,
            contrasenaHash = hash,
            contrasenaSal = sal
        )

        return dao.insertarNuevo(usuario) != -1L
    }

    suspend fun autenticarUsuario(
        nombre: String,
        contrasena: String
    ): UsuarioEntity? {

        val nombreNormalizado =
            nombre.lowercase(Locale.ROOT)

        val usuario = dao.obtenerPorNombre(
            nombreNormalizado
        ) ?: return null

        val coincide = withContext(
            Dispatchers.Default
        ) {
            proteccion.verificarContrasena(
                contrasena,
                usuario.contrasenaSal,
                usuario.contrasenaHash
            )
        }

        return if (coincide) {
            usuario
        } else {
            null
        }
    }

    suspend fun obtenerPorNombre(
        nombre: String
    ): UsuarioEntity? {

        return dao.obtenerPorNombre(
            nombre.lowercase(Locale.ROOT)
        )
    }

    suspend fun obtenerPorId(
        id: Int
    ): UsuarioEntity? {

        return dao.obtenerPorId(id)
    }
}

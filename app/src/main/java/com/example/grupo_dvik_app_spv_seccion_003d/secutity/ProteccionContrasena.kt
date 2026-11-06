
package com.example.grupo_dvik_app_spv_seccion_003d.security

import android.os.Build
import android.util.Base64
import java.security.GeneralSecurityException
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class ProteccionContrasena {

    fun generarSal(): String {

        val bytes = ByteArray(16)

        SecureRandom().nextBytes(bytes)

        return Base64.encodeToString(
            bytes,
            Base64.NO_WRAP
        )
    }

    fun generarHash(
        contrasena: String,
        sal: String
    ): String {

        val algoritmo: String
        val iteraciones: Int

        if (Build.VERSION.SDK_INT >= 26) {
            algoritmo = "PBKDF2WithHmacSHA256"
            iteraciones = 600000
        } else {
            algoritmo = "PBKDF2WithHmacSHA1"
            iteraciones = 1400000
        }

        val hash = calcularHash(
            contrasena,
            sal,
            algoritmo,
            iteraciones
        )

        return "$algoritmo:$iteraciones:$hash"
    }

    fun verificarContrasena(
        contrasena: String,
        sal: String,
        hashGuardado: String
    ): Boolean {

        val partes = hashGuardado.split(":")

        if (partes.size != 3) {
            return false
        }

        val algoritmo = partes[0]
        val iteraciones = partes[1].toIntOrNull()
            ?: return false

        if (
            algoritmo != "PBKDF2WithHmacSHA256" &&
            algoritmo != "PBKDF2WithHmacSHA1"
        ) {
            return false
        }

        if (
            iteraciones < 1 ||
            iteraciones > 1400000
        ) {
            return false
        }

        if (
            Build.VERSION.SDK_INT < 26 &&
            algoritmo == "PBKDF2WithHmacSHA256"
        ) {
            return false
        }

        return try {

            val hashCalculado = calcularHash(
                contrasena,
                sal,
                algoritmo,
                iteraciones
            )

            val actual = Base64.decode(
                hashCalculado,
                Base64.NO_WRAP
            )

            val esperado = Base64.decode(
                partes[2],
                Base64.NO_WRAP
            )

            MessageDigest.isEqual(
                actual,
                esperado
            )

        } catch (e: GeneralSecurityException) {
            false

        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun calcularHash(
        contrasena: String,
        sal: String,
        algoritmo: String,
        iteraciones: Int
    ): String {

        val bytesSal = Base64.decode(
            sal,
            Base64.NO_WRAP
        )

        val especificacion = PBEKeySpec(
            contrasena.toCharArray(),
            bytesSal,
            iteraciones,
            256
        )

        return try {

            val fabrica = SecretKeyFactory.getInstance(
                algoritmo
            )

            val bytesHash = fabrica.generateSecret(
                especificacion
            ).encoded

            Base64.encodeToString(
                bytesHash,
                Base64.NO_WRAP
            )

        } finally {
            especificacion.clearPassword()
        }
    }
}

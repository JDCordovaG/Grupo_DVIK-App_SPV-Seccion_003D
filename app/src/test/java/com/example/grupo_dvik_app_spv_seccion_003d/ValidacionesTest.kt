package com.example.grupo_dvik_app_spv_seccion_003d

import com.example.grupo_dvik_app_spv_seccion_003d.validation.Validaciones
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ValidacionesTest {

    private lateinit var validaciones: Validaciones

    @Before
    fun preparar() {
        validaciones = Validaciones()
    }

    @Test
    fun nombreCorrecto() {
        assertTrue(validaciones.validarNombre("Kevin123"))
    }

    @Test
    fun nombreVacio() {
        assertFalse(validaciones.validarNombre(""))
    }

    @Test
    fun nombreConEspacios() {
        assertFalse(validaciones.validarNombre("Kevin Reyes"))
    }

    @Test
    fun nombreConPunto() {
        assertFalse(validaciones.validarNombre("Kevin.123"))
    }

    @Test
    fun contrasenaCorrecta() {
        assertTrue(validaciones.validarContrasena("Clave123"))
    }

    @Test
    fun contrasenaMuyCorta() {
        assertFalse(validaciones.validarContrasena("Aa123"))
    }

    @Test
    fun contrasenaSinMayuscula() {
        assertFalse(validaciones.validarContrasena("clave1234"))
    }

    @Test
    fun contrasenaSinMinuscula() {
        assertFalse(validaciones.validarContrasena("CLAVE1234"))
    }

    @Test
    fun campoCorrecto() {
        assertTrue(validaciones.validarCampo("Ejemplo"))
    }

    @Test
    fun campoVacio() {
        assertFalse(validaciones.validarCampo("   "))
    }

    @Test
    fun estadoPendienteValido() {
        assertTrue(validaciones.validarEstado("Pendiente"))
    }

    @Test
    fun estadoSincronizadoValido() {
        assertTrue(validaciones.validarEstado("Sincronizado"))
    }

    @Test
    fun estadoIncorrecto() {
        assertFalse(validaciones.validarEstado("Guardado"))
    }

    @Test
    fun ambitosCorrectos() {
        for (id in 1..4) {
            assertTrue(validaciones.validarAmbito(id.toString()))
        }
    }

    @Test
    fun ambitosIncorrectos() {
        assertFalse(validaciones.validarAmbito("0"))
        assertFalse(validaciones.validarAmbito("5"))
        assertFalse(validaciones.validarAmbito("texto"))
    }

    @Test
    fun numeroPositivo() {
        assertTrue(validaciones.validarNumero("5"))
    }

    @Test
    fun numeroCero() {
        assertFalse(validaciones.validarNumero("0"))
    }

    @Test
    fun numeroNegativo() {
        assertFalse(validaciones.validarNumero("-3"))
    }

    @Test
    fun numeroTexto() {
        assertFalse(validaciones.validarNumero("abc"))
    }

    @Test
    fun fechaHoraValida() {
        assertTrue(validaciones.validarFechaHora("06/11/2026 09:30"))
    }

    @Test
    fun fechaHoraFormatoIncorrecto() {
        assertFalse(validaciones.validarFechaHora("6/11/2026 09:30"))
    }

    @Test
    fun fechaHoraInexistente() {
        assertFalse(validaciones.validarFechaHora("31/02/2026 10:00"))
    }

    @Test
    fun fechaHoraHoraIncorrecta() {
        assertFalse(validaciones.validarFechaHora("06/11/2026 25:00"))
    }

    @Test
    fun fechaActualOFutura() {
        val formato = SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        )

        val futuro = Calendar.getInstance()
        futuro.add(Calendar.DAY_OF_MONTH, 1)

        val pasado = Calendar.getInstance()
        pasado.add(Calendar.DAY_OF_MONTH, -1)

        assertTrue(
            validaciones.validarFechaActualOFutura(
                formato.format(futuro.time)
            )
        )

        assertFalse(
            validaciones.validarFechaActualOFutura(
                formato.format(pasado.time)
            )
        )
    }
}

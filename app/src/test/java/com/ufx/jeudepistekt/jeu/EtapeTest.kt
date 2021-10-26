package com.ufx.jeudepistekt.jeu

import com.ufx.jeudepistekt.jeu.element.EtapElement
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test

internal class EtapeTest {

    fun createEtape(): Etape {return Etape(0, listOf(), mapOf(), listOf(listOf(EtapElement(EtapElement.Companion.TYPE.TXT,"texte")),listOf(),listOf()))}

    @Test
    fun evaluateButtonListener() {
        val e = createEtape()
        e.buttonwaiters["val0"] = 0
        e.buttonwaiters["val1"] = 1

        assertThrows(UninitializedPropertyAccessException().javaClass) { e.evaluateButtonListener("val0") }
        assertDoesNotThrow { e.evaluateButtonListener("val1") }
        assertEquals(0,e.buttonwaiters["val0"])
        assertNull(e.buttonwaiters["val1"])

    }

    @Test
    fun evaluateEditListener() {
        val e = createEtape()
        e.qrwaiters["val0"] = 0
        e.qrwaiters["val1"] = 1
        e.qrwaiters["val2"] = 2

        e.evaluateEditListener("val1","response","notresponse","")
        e.evaluateEditListener("val2","response","notresponse","response")

        assertEquals(1,e.qrwaiters["val1"])
        assertNull(e.qrwaiters["val2"])
    }

    @Test
    fun evaluateQr() {
    }
}
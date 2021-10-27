package com.ufx.jeudepistekt.jeu

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class StageTest {

    @Test
    fun evaluateButtonListener() {
        val e = Stage("0", understages = mutableListOf(Stage("val0"),Stage("val1")))

        e.evaluateButtonListener("val0")
        assertEquals(1,e.understages.size)
        assertEquals("val1",e.understages.first().name)
    }

    @Test
    fun evaluateEditListener() {
        val e = Stage("0", understages = mutableListOf(Stage("val0"),Stage("val1")))


        e.evaluateEditListener("val0","response","notresponse","")
        e.evaluateEditListener("val1","response","notresponse","response")

        assertEquals(1,e.understages.size)
        assertEquals("val0",e.understages.first().name)
    }

    @Test
    fun evaluateQr() {
        val e = Stage("0", understages = mutableListOf(Stage("val0"),Stage("val1")))

        e.evaluateButtonListener("val0")
        assertEquals(1,e.understages.size)
        assertEquals("val1",e.understages.first().name)

    }
}
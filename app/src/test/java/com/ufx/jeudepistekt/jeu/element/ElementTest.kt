package com.ufx.jeudepistekt.jeu.element

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ElementTest {

    private fun createElement(): List<Element> {
        return listOf(TXT("Hello?"), IMG("world.png"))
    }

    @Test
    fun reverseFactory() {
        val l: List<Element> = createElement()
        assertEquals("TXT", l[0]::class.simpleName)
        assertEquals("IMG", l[1]::class.simpleName)

        assertEquals(l[0].content, l[0].reverseFactory().content)
        assertEquals(l[0]::class.simpleName, l[0].reverseFactory().type.name)

    }

}
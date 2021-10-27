package com.ufx.jeudepistekt.jeu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class VariablesTest{

    @Test
    fun evaluateConditionTest() {
        val variables = Variables()
        variables.values["var1"] = 1
        variables.values["var2"] = 3
        var a = variables.evaluateCondition("1 == 2")
        assertEquals(false,a )

        a = variables.evaluateCondition("var1 == 1 && 2 == 2")
        assertEquals(true,a )

        a = variables.evaluateCondition("1 == 1 && 2 == 2 || 1 == 3")
        assertEquals(true,a )

    }
}
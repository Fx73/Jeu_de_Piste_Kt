package com.ufx.jeudepistekt.jeu

import com.ufx.jeudepistekt.jeu.element.EtapElement
import com.ufx.jeudepistekt.jeu.element.EtapElement.Companion.TYPE
import com.ufx.jeudepistekt.jeu.element.TXT
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ScenarioTest {
    private val scenario = Scenario("test","test","test","")


    @Test
    fun evaluateConditionTest() {
        scenario.variable["TYPE.VAR1"] = 1
        scenario.variable["TYPE.VAR2"] = 3
        var a = evaluateCondition("1 = 2")
        Assertions.assertEquals(false,a )

        a = evaluateCondition("TYPE.VAR1 = 1 && 2 = 2")
        Assertions.assertEquals(true,a )

        a = evaluateCondition("1 = 1 && 2 = 2 || 1 = 3")
        Assertions.assertEquals(true,a )

    }


    private fun evaluateCondition(cond : String):Boolean{
        var all = true
        val aa = cond.split("&&")
        for (a in aa){
            val bb = a.split("||")
            var ball = false
            for (b in bb){
                val c = b.split("=")
                val c0 = scenario.variable[c[0].trim()]?:c[0].trim().toInt()
                val c1 = scenario.variable[c[1].trim()]?:c[1].trim().toInt()

                ball = ball || (c0 == c1)
            }
            all = all && ball
        }
        return  all
    }


    @Test
    fun complexScenario(){
        val scenario = Scenario("Complex Scenario", "unittest","this is a test scenario","no")

        val e1 = Etape(
            number = 0,
            elements = listOf(TXT("Ceci est le test de TXT").reverseFactory()),
            next = mapOf()
        )

    }
}
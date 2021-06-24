package com.ufx.jeudepistekt

import com.ufx.jeudepistekt.jeu.Scenario
import org.junit.Assert
import org.junit.Test

class GameTest {
    private val scenario = Scenario("test","test","test","")


    @Test
    fun evaluateConditionTest() {
        scenario.variable["var1"] = 1
        scenario.variable["var2"] = 3
      var a = evaluateCondition("1 = 2")
        Assert.assertEquals(false,a )

        a = evaluateCondition("var1 = 1 && 2 = 2")
        Assert.assertEquals(true,a )

        a = evaluateCondition("1 = 1 && 2 = 2 || 1 = 3")
        Assert.assertEquals(true,a )

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

}
package com.ufx.jeudepistekt.jeu

import com.ufx.jeudepistekt.jeu.element.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ScenarioTest {
    val scenario = Scenario("Kalte", "fx", "Yo ceci est un scenario test", "", etapes = listOf(Etape(0, elements = listOf(TXT("Hello?").reverseFactory(), IMG("world.png").reverseFactory()), mapOf())))



    @Test
    fun evaluateConditionTest() {
        scenario.variable["var1"] = 1
        scenario.variable["var2"] = 3
        var a = scenario.evaluateCondition("1 == 2")
        Assertions.assertEquals(false,a )

        a = scenario.evaluateCondition("var1 == 1 && 2 == 2")
        Assertions.assertEquals(true,a )

        a = scenario.evaluateCondition("1 == 1 && 2 == 2 || 1 == 3")
        Assertions.assertEquals(true,a )

    }


    @Test
    fun buildScenarioFromJson(){
        val json = "{\"title\":\"Kalte\",\"creator\":\"fx\",\"description\":\"Yo ceci est un scenario test\",\"copyright\":\"\",\"variable\":{\"var1\":1,\"var2\":3},\"andapes\":[{\"number\":0,\"elements\":[{\"type\":\"TXT\",\"content\":\"Hello?\",\"additional\":[]},{\"type\":\"IMG\",\"content\":\"world.png\",\"additional\":[]}],\"next\":{}}]}"
        val s = Scenario.buildScenarioFromJson(json)

        Assertions.assertEquals("Kalte", s.title)
        Assertions.assertEquals("TXT", s.etapes[0].elements[0].type.name)
        Assertions.assertEquals("world.png", s.etapes[0].elements[1].content)

    }


    fun complexScenario() : Scenario{
        val e0 = Etape(
            number = 0,
            elements = listOf(
                TXT("This is the test of TXT and IMG").reverseFactory(),
                IMG("e0i0").reverseFactory()
            ),
            next = mapOf(Pair("e1",1))
        )

        val e1 = Etape(
            number = 1,
            elements = listOf(
                TXT("This is the test of BTN").reverseFactory(),
                BTN("BTN ?", arrayOf("0")).reverseFactory()
            ),
            next = mapOf(Pair("e2",2)),
            underelems= listOf(
                listOf(
                    TXT("Test is ok").reverseFactory()
                )
            )
        )

        val e2 = Etape(
            number = 2,
            elements = listOf(
                TXT("This is the test of EDT").reverseFactory(),
                EDT("Write answer ?", arrayOf("reponse","answer")).reverseFactory()
            ),
            next = mapOf(Pair("e3",3)),
            underelems= listOf(
                listOf(
                    TXT("Test is ok").reverseFactory()
                )
            )
        )

        val e3 = Etape(
            number = 3,
            elements = listOf(
                TXT("This is the test of ETP part 1").reverseFactory(),
                ETP("e4").reverseFactory()
            ),
            next = mapOf()
        )

        val e4 = Etape(
            number = 4,
            elements = listOf(
                TXT("This is the test of ETP part 2 and TST").reverseFactory(),
                TST ("Test ok").reverseFactory()
            ),
            next = mapOf(Pair("e5",5))
        )

        val e5 = Etape(
            number = 5,
            elements = listOf(
                TXT("This is the test of QRC, LCK and UCK").reverseFactory(),
                LCK ("6").reverseFactory() ,
                BTN ("unlock",additional= arrayOf("0")).reverseFactory()
            ),
            next = mapOf(Pair("e6",6)),
            underelems= listOf(
                listOf(
                    TXT("Lock will be released").reverseFactory(),
                    UCK("100").reverseFactory()
                )
            )
        )


        val e6 = Etape(
            number = 6,
            elements = listOf(
                TXT("This is the test of QRC").reverseFactory(),
                QRC ("QRC",additional= arrayOf("0")).reverseFactory()
            ),
            next = mapOf(Pair("e100",100)),
            underelems= listOf(
                listOf(
                    TXT("Test is ok").reverseFactory(),
                )
            )
        )

        val e100 = Etape(
            number = 100,
            elements = listOf(
                TXT("End of tests").reverseFactory(),
            ),
            next = mapOf()
        )
        return Scenario("Complex Scenario Test", "unitest","this is a test scenario","no",etapes = listOf(e0,e1,e2,e3,e4,e5,e100))
    }
}
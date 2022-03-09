package com.ufx.jeudepistekt.jeu

import com.ufx.jeudepistekt.jeu.element.*

class ScenarioTest {
    val scenario: Scenario = Scenario(
        "Testing Scenario",
        "fx",
        "This is a test scenario",
        "",
        stages = listOf(
            Stage(
                "0",
                elements = listOf(TXT("Hello?").reverseFactory(), IMG("world.png").reverseFactory())
            )
        )
    )


    fun complexScenario(): Scenario {
        val e0 = Stage(
            name = "e0",
            elements = listOf(
                TXT("This is the test 0 of TXT and IMG").reverseFactory(),
                IMG("e0i0").reverseFactory()
            ),
            next = mutableListOf("e1")
        )

        val e1 = Stage(
            name = "e1",
            elements = listOf(
                TXT("This is the test 1 of BTN").reverseFactory(),
                BTN("BTN ?", arrayOf("u0")).reverseFactory()
            ),
            next = mutableListOf("e2", "e0"),
            understages = mutableListOf(
                Stage(
                    name = "u0",
                    elements = listOf(
                        TXT("Test is ok").reverseFactory()
                    )
                )
            )
        )

        val e2 = Stage(
            name = "e2",
            elements = listOf(
                TXT("This is the test 2 of EDT").reverseFactory(),
                EDT("Write answer", arrayOf("u0", "response", "answer")).reverseFactory()
            ),
            next = mutableListOf("e3", "e1"),
            understages = mutableListOf(
                Stage(
                    name = "u0",
                    elements = listOf(
                        TXT("Test is ok").reverseFactory()
                    )
                )
            )
        )

        val e3 = Stage(
            name = "e3",
            elements = listOf(
                TXT("This is the test 3 of ETP part 1").reverseFactory(),
                ETP("e4").reverseFactory()
            ),
            next = mutableListOf("e4", "e3")
        )

        val e4 = Stage(
            name = "e4",
            elements = listOf(
                TXT("This is the test 4 of ETP part 2 and TST").reverseFactory(),
                TST("Test ok, should have jumped the 3").reverseFactory()
            ),
            next = mutableListOf("e5", "e3")
        )

        val e5 = Stage(
            name = "e5",
            elements = listOf(
                TXT("This is the test 5 of VAR and lock").reverseFactory(),
                LCK("e6").reverseFactory(),
                BTN("unlock", additional = arrayOf("u0")).reverseFactory()
            ),
            next = mutableListOf("e6", "e4"),
            understages = mutableListOf(
                Stage(
                    name = "u0",
                    elements = listOf(
                        TXT("Lock will be released").reverseFactory(),
                        UCK("e6").reverseFactory()
                    )
                )
            )
        )


        val e6 = Stage(
            name = "e6",
            elements = listOf(
                TXT("This is the test 6 of QRC").reverseFactory(),
                QRC("QRC").reverseFactory()
            ),
            next = mutableListOf("e100", "e5"),
            understages = mutableListOf(
                Stage(
                    name = "QRC",
                    elements = listOf(
                        TXT("Test is ok").reverseFactory()
                    )
                )
            )
        )

        val e100 = Stage(
            name = "e100",
            elements = listOf(
                TXT("End of tests").reverseFactory(),
            )
        )

        val variables = Variables()
        variables.values["var1"] = 1
        variables.values["var2"] = 2

        return Scenario(
            "Complex Scenario Test",
            "unitest",
            "this is a test scenario",
            "no",
            variables = variables,
            stages = listOf(e0, e1, e2, e3, e4, e5, e6, e100)
        )
    }
}
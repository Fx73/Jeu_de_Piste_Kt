package com.ufx.jeudepistekt.tools

import org.junit.jupiter.api.Test

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

import java.io.File


import com.ufx.jeudepistekt.jeu.Etape
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.ScenarioTest
import com.ufx.jeudepistekt.jeu.element.EtapElement
import com.ufx.jeudepistekt.jeu.element.IMG
import com.ufx.jeudepistekt.jeu.element.TXT
import kotlinx.serialization.decodeFromString


class KxJsonTest {

    @Test
    fun writeJsonFile() {
        val s = ScenarioTest().complexScenario()

        val json = Json.encodeToString(s)
        println(json)

        val jsonpretty = Json { prettyPrint = true }

        File("src/main/assets/ScenarioTest.json").writeText(jsonpretty.encodeToString(s))

    }

    @Test
    fun readJsonFile() {
        val jsonFileString = File("src/main/assets/ScenarioTest.json").readText()


        val scenario = Json.decodeFromString<Scenario>(jsonFileString)
        println(scenario.title)
    }

}
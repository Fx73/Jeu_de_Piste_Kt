package com.ufx.jeudepistekt.tools


import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.ScenarioTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test


class KxJsonTest {

    @Test
    @Order(1)
    fun writeJsonFile() {

        val json = Json.encodeToString(ScenarioTest().scenario)
        println(json)

        val jsonpretty = Json { prettyPrint = true }

        File("src/main/assets/ScenarioTest.json").writeText(jsonpretty.encodeToString(ScenarioTest().complexScenario()))
        File("src/main/assets/ScenarioFile.json").writeText(jsonpretty.encodeToString(ScenarioTest().complexScenario()))

    }

    @Test
    @Order(2)
    fun readJsonFile() {
        val jsonFileString = File("src/main/assets/ScenarioTest.json").readText()


        val scenario = Json.decodeFromString<Scenario>(jsonFileString)
        Assertions.assertEquals(ScenarioTest().complexScenario().title,scenario.title)
        Assertions.assertEquals(ScenarioTest().complexScenario().creator,scenario.creator)
        Assertions.assertEquals(ScenarioTest().complexScenario().stages.size,scenario.stages.size)
    }

}
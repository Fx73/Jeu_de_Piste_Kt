package com.ufx.jeudepistekt

import com.google.gson.GsonBuilder
import com.ufx.jeudepistekt.jeu.EtapElem
import com.ufx.jeudepistekt.jeu.Etape
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.TYPE
import org.junit.Test
import java.io.File

class JsonTest {


    @Test
    fun buildJsonFile() {
        val e = Etape(0,elems = listOf(EtapElem(TYPE.TXT,"YO Bien ?"), EtapElem(TYPE.IMG,"start3.jpg")),mapOf())
        val s = Scenario("Kalte","eeee","Yo ceci est un scenario test","", etapes = listOf(e))
        s.variable["var1"] = 1
        s.variable["var2"] = 3


        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

        val jsonsPretty: String = gsonPretty.toJson(s)

        println(jsonsPretty)
        File("src/main/assets/ScenarioFile.json").writeText(jsonsPretty)

    }


}
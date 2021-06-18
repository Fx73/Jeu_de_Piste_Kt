package com.ufx.jeudepistekt

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ufx.jeudepistekt.jeu.EtapElem
import com.ufx.jeudepistekt.jeu.Etape
import com.ufx.jeudepistekt.jeu.Scenario
import org.junit.Test
import java.io.File

class JsonTest {


    @Test
    fun buildJsonFile() {
        val e = Etape(0,elems = listOf(EtapElem(EtapElem.TYPE.TXT,"YO Bien ?"), EtapElem(EtapElem.TYPE.IMG,"start3.jpg")),code = "Ceci est du code")
        val s = Scenario("Kalte","eeee","Yo ceci est un scenario test", etapes = listOf(e))
        s.variable["var1"] = 1
        s.variable["var2"] = "OK"


        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

        val jsonsPretty: String = gsonPretty.toJson(s)

        println(jsonsPretty)
        File("src/main/assets/ScenarioFile.json").writeText(jsonsPretty)

    }


}
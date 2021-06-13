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
        var e : Etape = Etape(0,texts = listOf(EtapElem("Yo",0), EtapElem("CAVA ?",1)),code = "Ceci est du code")
        var s : Scenario = Scenario("Kalte","eeee","fx","fqfdsdqsdgs", etapes = listOf(e))
        s.variable["var1"] = 1
        s.variable["var2"] = "OK"


        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

        val jsonsPretty: String = gsonPretty.toJson(s)

        println(jsonsPretty)
        File("src/main/assets/essai.json").writeText(jsonsPretty)

    }


}
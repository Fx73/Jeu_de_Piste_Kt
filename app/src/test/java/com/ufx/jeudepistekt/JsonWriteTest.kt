package com.ufx.jeudepistekt

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.ufx.jeudepistekt.jeu.Etape
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.elem.EtapElem
import com.ufx.jeudepistekt.jeu.elem.IMG
import com.ufx.jeudepistekt.jeu.elem.TXT
import org.junit.Test
import java.io.File




class JsonWriteTest {


    @Test
    fun buildJsonFile() {
        val elems = listOf(TXT("Hello?"), IMG("world.png"))
        val e = Etape(0,elems = elems,mapOf())
        val s = Scenario("Kalte","eeee","Yo ceci est un scenario test","", etapes = listOf(e))
        s.variable["var1"] = 1
        s.variable["var2"] = 3

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(EtapElem::class.java, JsonSerializer<EtapElem>{
                src, _, context ->
            val result = JsonObject()
            result.add("type", JsonPrimitive(src.javaClass.simpleName))
            result.add("elem", context.serialize(src, src.javaClass))
            return@JsonSerializer result
        })

        val jsonPretty: String = gsonBuilder.setPrettyPrinting().create().toJson(e)

        println(jsonPretty)
        File("src/main/assets/test.json").writeText(jsonPretty)

    }


}
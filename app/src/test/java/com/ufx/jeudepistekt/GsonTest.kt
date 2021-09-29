package com.ufx.jeudepistekt

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.ufx.jeudepistekt.jeu.Etape
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.elem.EtapElem
import com.ufx.jeudepistekt.jeu.elem.IMG
import com.ufx.jeudepistekt.jeu.elem.TXT
import org.junit.Test
import java.io.File




class GsonTest {


    @Test
    fun writeJsonFile() {
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

    @Test
    fun readJsonFile() {
        val jsonFileString = File("src/main/assets/test.json").readText()

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(EtapElem::class.java, JsonDeserializer<EtapElem>{
                json, _, context ->
            val jsonObject = json.asJsonObject
            val type = jsonObject["type"].asString
            val element = jsonObject["elem"]
            return@JsonDeserializer try {
                context.deserialize(element,Class.forName("com.ufx.jeudepistekt.jeu.elem.$type"))
            } catch (cnfe: ClassNotFoundException) {
                throw JsonParseException("Unknown element type: $type", cnfe)
            }
        })

        val outtype = object : TypeToken<Etape>() {}.type

        val s : Scenario = gsonBuilder.create().fromJson(jsonFileString, outtype)
        println(s.title)
    }
}
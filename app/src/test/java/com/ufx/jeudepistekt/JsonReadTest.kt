package com.ufx.jeudepistekt

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.ufx.jeudepistekt.jeu.Etape
import com.ufx.jeudepistekt.jeu.Scenario
import com.ufx.jeudepistekt.jeu.elem.EtapElem

import org.junit.Test
import java.io.File

class JsonReadTest {


    @Test
    fun buildJsonFile() {
        val jsonFileString = File("src/main/assets/test.json").readText()

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(EtapElem::class.java, JsonDeserializer<EtapElem>{
                json, _, context ->
            val jsonObject = json.asJsonObject
            val type = jsonObject["type"].asString
            val element = jsonObject["elem"]
            return@JsonDeserializer try {
                context.deserialize(element,EtapElem::class.java)
            } catch (cnfe: ClassNotFoundException) {
                throw JsonParseException("Unknown element type: $type", cnfe)
            }
        })

        val outtype = object : TypeToken<Etape>() {}.type

        val s : Scenario = gsonBuilder.create().fromJson(jsonFileString, outtype)
        println(s.title)
    }


}
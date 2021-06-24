package com.ufx.jeudepistekt.jeu

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Scenario(
    var title : String,
    var creator : String,
    var description : String,
    var copyright: String,
    val variable : MutableMap<String, Int> = mutableMapOf(),
    val etapes : List<Etape> = listOf()
)
{



    companion object {

        fun buildScenarioFromJson(jsonFileString:String): Scenario {
            val outtype = object : TypeToken<Scenario>() {}.type
            return Gson().fromJson(jsonFileString, outtype)
        }

    }
}
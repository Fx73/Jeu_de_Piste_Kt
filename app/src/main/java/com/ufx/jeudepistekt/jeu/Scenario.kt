package com.ufx.jeudepistekt.jeu

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ufx.jeudepistekt.BuildConfig


class Scenario(
    var title : String,
    var creator : String,
    var description : String,
    var copyright: String,
    var version : String = BuildConfig.VERSION_NAME,
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
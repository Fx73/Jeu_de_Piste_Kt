package com.ufx.jeudepistekt.jeu

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileInputStream


class Scenario(
    var title : String,
    var creator : String,
    val variable : MutableMap<String, Any> = mutableMapOf(),
    val etapes : List<Etape> = listOf()
)
{



    companion object {

        fun buildScenarioFromJson(context: Context, filename:String): Scenario {
            val filestream = context.openFileInput(filename)

            val jsonFileString = filestream.bufferedReader().use { it.readText() }
            val gson = Gson()
            val outtype = object : TypeToken<Scenario>() {}.type

            return gson.fromJson(jsonFileString, outtype)
        }

    }
}
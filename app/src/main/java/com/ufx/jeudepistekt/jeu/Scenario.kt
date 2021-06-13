package com.ufx.jeudepistekt.jeu

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


class Scenario(
    var title : String,
    var icon : String,
    var creator : String,
    var idkey : String,
    val variable : MutableMap<String, Any> = mutableMapOf(),
    val etapes : List<Etape> = listOf()
)
{



    companion object {

        fun buildScenarioFromJson(context: Context, filename:String): Scenario {
            val jsonFileString = getJsonDataFromAsset(context, filename)
            val gson = Gson()
            val outtype = object : TypeToken<Scenario>() {}.type

            return gson.fromJson(jsonFileString, outtype)
        }

        fun getJsonDataFromAsset(context: Context, fileName: String): String? {
            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString
        }
    }
}
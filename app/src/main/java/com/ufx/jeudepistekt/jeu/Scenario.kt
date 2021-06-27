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

    var etape = 0
    fun getEtap () = etapes[etape]









    fun evaluateCondition(cond : String):Boolean{
        var all = true
        val aa = cond.split("&&")
        for (a in aa){
            val bb = a.split("||")
            var ball = false
            for (b in bb){
                val c = if(b.contains("==")) b.split("==") else b.split("!=")

                val c0 = variable[c[0].trim()]?:c[0].trim().toInt()
                val c1 = variable[c[1].trim()]?:c[1].trim().toInt()

                ball = ball || (c0 == c1)
            }
            all = all && ball
        }
        return  all
    }











    companion object {

        fun buildScenarioFromJson(jsonFileString:String): Scenario {
            val outtype = object : TypeToken<Scenario>() {}.type
            return Gson().fromJson(jsonFileString, outtype)
        }

    }
}
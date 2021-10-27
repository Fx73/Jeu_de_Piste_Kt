package com.ufx.jeudepistekt.jeu

import kotlinx.serialization.Serializable

@Serializable
class Variables {
    val values : MutableMap<String, Int> = mutableMapOf()



    fun evaluateCondition(cond : String):Boolean{
        var all = true
        val aa = cond.split("&&")
        for (a in aa){
            val bb = a.split("||")
            var ball = false
            for (b in bb){
                val c = if(b.contains("==")) b.split("==") else b.split("!=")

                val c0 = values[c[0].trim()]?:c[0].trim().toInt()
                val c1 = values[c[1].trim()]?:c[1].trim().toInt()

                ball = ball || (c0 == c1)
            }
            all = all && ball
        }
        return  all
    }
}
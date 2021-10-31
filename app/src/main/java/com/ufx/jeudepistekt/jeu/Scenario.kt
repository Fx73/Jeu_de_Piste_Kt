package com.ufx.jeudepistekt.jeu

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import com.ufx.jeudepistekt.BuildConfig
import com.ufx.jeudepistekt.tools.Storer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import com.ufx.jeudepistekt.GameFragment.Companion.context
import com.ufx.jeudepistekt.GameFragment.Companion.layout

@Serializable
class Scenario(
    var title : String,
    var creator : String,
    var description : String,
    var copyright: String,
    var version : String = BuildConfig.VERSION_NAME,
    val variables : Variables = Variables(),
    val stages : List<Stage> = listOf()
)
{
    @Transient lateinit var storer:Storer


    private var stage = stages[0]

    fun loadStage (name:String){
        stage = stages.first { it.name == name }
        println("LOADING STAGE : ${stage.name}")
        User.saveScenario(storer.getKey(),stage.name,variables,context)
        layout.removeAllViews()
        for (e in stage.elements)
            stage.loadElement(e)
    }







    fun evaluateQr(s : String):Boolean{
        if(stage.evaluateQrListener(s))
            return true

        for (w in stage.next){
            if(w == s){
                loadStage(s)
                return true
            }
        }
        return false
    }



    companion object {
        fun buildScenarioFromJson(jsonFile: String): Scenario {
            return Json.decodeFromString(jsonFile)
        }
    }
}
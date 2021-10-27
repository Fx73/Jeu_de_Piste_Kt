package com.ufx.jeudepistekt.jeu

import com.ufx.jeudepistekt.GameActivity
import com.ufx.jeudepistekt.jeu.element.StageElement
import kotlinx.serialization.Serializable
import java.util.Locale.getDefault

@Serializable
class Stage(
    var name: String,
    var elements: List<StageElement> = listOf(),
    var next: MutableList<String> = mutableListOf(),
    var understages: MutableList<Stage> = mutableListOf()
)
{

//region waiters
    fun evaluateButtonListener(id:String){
        loadElemsFromWaiters(id)
    }

    fun evaluateEditListener(id:String, response : String ,verif1 : String, verif2 : String){
        if(response.trim().lowercase(getDefault()) == verif1.lowercase(getDefault()) || (verif2 != "" && response.trim().lowercase(getDefault()) == verif2.lowercase(getDefault())))
            loadElemsFromWaiters(id)

    }

    fun evaluateQrListener(s : String):Boolean {
        return loadElemsFromWaiters(s)
    }

    private fun loadElemsFromWaiters(id : String):Boolean{
        val us = understages.find { it.name == id }?:return false

        for (e in us.elements)
            loadElement(e)

        understages.remove(us)
        return true
    }



//endregion

    fun loadElement(e : StageElement) {
        if(e.condition == "" || GameActivity.scenario.variables.evaluateCondition(e.condition))
            e.instantiate(this)
    }



}


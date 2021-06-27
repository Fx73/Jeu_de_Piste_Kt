package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

abstract class EtapElem(
    var content : String,
    var condition : String = ""
    ){

    open fun instantiate(context : Context, l : LinearLayout, scenario: Scenario){

    }

    fun factory (type: String):EtapElem{
        when (type){
            "TXT"-> return TXT(content)
        }
        return TST(content)
    }

}
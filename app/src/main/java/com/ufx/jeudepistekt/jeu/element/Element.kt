package com.ufx.jeudepistekt.jeu.element

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

import com.ufx.jeudepistekt.jeu.element.EtapElement.Companion.TYPE

open class Element(val content: String, val additional: Array<String> = arrayOf()) {


    open fun instantiate(context : Context, l : LinearLayout, scenario: Scenario){
        println("THIS IS DEFAULT ELEM : IT CANNOT APPEAR !")
    }

    fun reverseFactory():EtapElement{
        val type : TYPE? = this::class.simpleName?.let { TYPE.valueOf(it) }

        return if(type == null){
            EtapElement(TYPE.TST, "!ERROR OF TYPE ELEM! $content",additional)
        }else{
            EtapElement(type, content, additional)
        }
    }
}
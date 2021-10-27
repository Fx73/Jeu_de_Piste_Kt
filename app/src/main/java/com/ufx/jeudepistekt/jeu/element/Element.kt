package com.ufx.jeudepistekt.jeu.element

import com.ufx.jeudepistekt.jeu.Stage

import com.ufx.jeudepistekt.jeu.element.StageElement.Companion.TYPE

open class Element(val content: String, val additional: Array<String> = arrayOf()) {

    open fun instantiate(stage: Stage){
        println("THIS IS DEFAULT ELEM : IT CANNOT APPEAR !")
    }

    fun reverseFactory():StageElement{
        val type : TYPE? = this::class.simpleName?.let { TYPE.valueOf(it) }

        return if(type == null){
            StageElement(TYPE.TST, "!ERROR OF TYPE ELEM! $content",additional)
        }else{
            StageElement(type, content, additional)
        }
    }
}
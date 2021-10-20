package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

open class Elem(val content: String,val additional: Array<String> = arrayOf()) {


    open fun instantiate(context : Context, l : LinearLayout, scenario: Scenario){
        println("THIS IS DEFAULT ELEM : IT CANNOT APPEAR !")
    }
}
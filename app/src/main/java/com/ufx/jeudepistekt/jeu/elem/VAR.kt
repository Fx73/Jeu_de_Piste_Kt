package com.ufx.jeudepistekt.jeu.elem

import android.content.Context
import android.widget.LinearLayout
import com.ufx.jeudepistekt.jeu.Scenario

class VAR(content: String) : EtapElem(content) {

    override fun instantiate(context : Context, l : LinearLayout,scenario:Scenario) {
        val split = content.split("=")
        var v = scenario.variable[split[0]] ?: return

        if(split.size == 2){
            v = split[1].toInt()
        }

        if(split.size == 3){
            when (split[1]){
                "+" -> v+= split[2].toInt()
                "-" -> v-= split[2].toInt()
                "*" -> v*= split[2].toInt()
                "/" -> v/= split[2].toInt()
            }
        }

        scenario.variable[split[0]] = v

    }

}